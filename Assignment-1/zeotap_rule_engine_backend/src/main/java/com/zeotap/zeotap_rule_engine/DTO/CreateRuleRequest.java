package com.zeotap.zeotap_rule_engine.DTO;

public class CreateRuleRequest {
    private String ruleName;
    private String ruleDefinition;

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getRuleDefinition() {
        return ruleDefinition;
    }

    public void setRuleDefinition(String ruleDefinition) {
        this.ruleDefinition = ruleDefinition;
    }
}
