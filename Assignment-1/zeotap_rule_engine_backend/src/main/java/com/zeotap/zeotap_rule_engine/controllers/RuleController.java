package com.zeotap.zeotap_rule_engine.controllers;

import com.zeotap.zeotap_rule_engine.DTO.EvaluateRuleRequest;
import com.zeotap.zeotap_rule_engine.models.Node;
import com.zeotap.zeotap_rule_engine.services.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/rules")
public class RuleController {
    @Autowired
    private RuleService ruleService;

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

    @PostMapping("/create")
    public Node createRule(@RequestBody String ruleString) {
        return ruleService.createRule(ruleString);
    }

    @PostMapping("/combine")
    public Node combineRules(@RequestBody List<String> rules) {
        return ruleService.combineRules(rules);
    }

    @PostMapping("/evaluate")
    public boolean evaluateRule(@RequestBody EvaluateRuleRequest request) {
        Node root = request.getRule();
        Map<String, Object> data = request.getData();
        return ruleService.evaluateRule(root, data);
    }
}
