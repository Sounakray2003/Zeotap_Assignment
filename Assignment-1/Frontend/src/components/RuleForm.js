import React, { useState } from "react";
import { parseRuleString } from "../utils/viewAST"; // Optional, if needed

const RuleForm = () => {
  const [ruleString, setRuleString] = useState("");
  const [name, setName] = useState(""); // Added to input name
  const [result, setResult] = useState("");
  const handleSubmit = async (event) => {
    event.preventDefault();

    const response = await fetch(
      "https://rule-engine-64c2.onrender.com/api/v1/rules/create",
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ rule: ruleString, name }),
      }
    );

    const data = await response.json();
    if (data.success) {
      const ast = data.AST.ast;
      const treeStructure = printTreeToString(ast);

      // Display the parsed tree
      setResult(treeStructure);
    } else {
      setResult("Error: Unable to create rule");
    }
  };

  // Function to create a string representation of the tree
  const printTreeToString = (node, prefix = "", isLeft = true) => {
    if (!node) return "";

    let result = prefix + (isLeft ? "├── " : "└── ");

    // Check if the node is an operand or a logical operator
    if (node.type === "operand") {
      result += `${node.value}`; // Print the condition for operand nodes
    } else {
      result += `${node.type}`; // Print the logical operator (AND/OR)
    }

    result += "\n";

    // Traverse the left and right children
    if (node.left) {
      result += printTreeToString(
        node.left,
        prefix + (isLeft ? "│   " : "    "),
        true
      );
    }
    if (node.right) {
      result += printTreeToString(
        node.right,
        prefix + (isLeft ? "│   " : "    "),
        false
      );
    }

    return result;
  };

  return (
    <div>
      <form onSubmit={handleSubmit}>
        <div>
          <label>Name:</label>
          <input
            type="text"
            value={name}
            onChange={(e) => setName(e.target.value)}
            required
          />
        </div>
        <div>
          <label>Rule:</label>
          <textarea
            type="text"
            value={ruleString}
            onChange={(e) => setRuleString(e.target.value)}
            required
          />
        </div>
        <button type="submit">Create Rule</button>
      </form>
      <pre>{result}</pre>
    </div>
  );
};

export default RuleForm;
