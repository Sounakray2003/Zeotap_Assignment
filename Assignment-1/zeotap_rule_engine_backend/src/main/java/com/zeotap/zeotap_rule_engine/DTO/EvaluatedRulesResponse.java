package com.zeotap.zeotap_rule_engine.DTO;


public class EvaluatedRulesResponse {
    private Boolean result;

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "EvaluatedRulesResponse{" +
                "result=" + result +
                '}';
    }
}
