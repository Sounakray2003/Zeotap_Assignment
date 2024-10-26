import React, { useState } from "react";

const EvaluateRuleForm = () => {
  const [ruleName, setRuleName] = useState("");
  const [data, setData] = useState("");
  const [result, setResult] = useState("");

  const handleSubmit = async (event) => {
    event.preventDefault();
    try {
      const response = await fetch(
        "https://rule-engine-64c2.onrender.com/api/v1/rules/evaluate",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({ ast: ruleName, data: JSON.parse(data) }),
        }
      );

      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(errorData.message || "Something went wrong");
      }

      const res = await response.json();
      // console.log(result);
      setResult(JSON.stringify(res.result, null, 2));
    } catch (error) {
      setResult(`Error: ${error.message}`);
    }
  };

  return (
    <div>
      <form onSubmit={handleSubmit}>
        <div>
          <label>Rule: </label>
          <textarea
            type="text"
            value={ruleName}
            onChange={(e) => setRuleName(e.target.value)}
            required
          />
        </div>
        <div>
          <label>Data JSON: </label>
          <textarea
            value={data}
            onChange={(e) => setData(e.target.value)}
            required
          />
        </div>
        <button type="submit">Evaluate Rule</button>
      </form>
      <pre>{result}</pre>
    </div>
  );
};

export default EvaluateRuleForm;
