import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Navbar from "./components/Navbar";
import CreateRule from "./pages/CreateRule";
import CombineRules from "./pages/CombineRules";
import EvaluateRule from "./pages/EvaluateRule";

const App = () => {
  return (
    <Router>
      <Navbar />
      <div className="container">
        <Routes>
          <Route path="/" element={<CreateRule />} />
          <Route path="/combine" element={<CombineRules />} />
          <Route path="/evaluate" element={<EvaluateRule />} />
        </Routes>
      </div>
    </Router>
  );
};

export default App;
