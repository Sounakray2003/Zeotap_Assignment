import React, { useState } from "react";

const CombineRulesForm = () => {
  const [rules, setRules] = useState([{ id: 1, rule: "" }]);
  const [operator, setOperator] = useState("AND");
  const [result, setResult] = useState("");

  const handleAddRule = () => {
    setRules([...rules, { id: rules.length + 1, rule: "" }]);
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    
    const ruleTexts = rules.map((rule) => rule.rule);

    try {
      const response = await fetch(
        "https://rule-engine-64c2.onrender.com/api/v1/rules/combine",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({ rules: ruleTexts, operator }),
        }
      );
      const data = await response.json();
      if (data.success) {
        const treeStructure = printTree(data.AST.ast);
        setResult(treeStructure);
      } else {
        setResult("Error: " + data.message);
      }
    } catch (error) {
      console.error(error);
      setResult("Error: Failed to combine rules");
    }
  };

  const printTree = (node, prefix = "", isLeft = true) => {
    if (!node) return "";
    let result =
      prefix +
      (isLeft ? "├── " : "└── ") +
      (node.type === "operand" ? node.value : node.type) +
      "\n";

    if (node.left) {
      result += printTree(node.left, prefix + (isLeft ? "│   " : "    "), true);
    }
    if (node.right) {
      result += printTree(
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
        {rules.map((rule, index) => (
          <div key={rule.id} className="rule-container">
            <label>Rule {index + 1}:</label>
            <textarea
              type="text"
              value={rule.rule}
              onChange={(e) => {
                const newRules = [...rules];
                newRules[index].rule = e.target.value;
                setRules(newRules);
              }}
              required
            />
          </div>
        ))}

        <label>Operator: </label>
        <select
          value={operator}
          onChange={(e) => setOperator(e.target.value)}
          className="dropdown"
        >
          <option value="AND">AND</option>
          <option value="OR">OR</option>
        </select>

        <div className="button-container">
          <button type="button" onClick={handleAddRule} className="btn">
            Add Another Rule
          </button>
          <button type="submit" className="btn">
            Combine Rules
          </button>
        </div>
      </form>
      <pre>{result}</pre> {/* Display the tree structure or error message */}
    </div>
  );
};

export default CombineRulesForm;
