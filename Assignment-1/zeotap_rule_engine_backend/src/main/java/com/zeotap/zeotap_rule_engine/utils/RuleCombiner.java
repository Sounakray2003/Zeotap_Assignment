package com.zeotap.zeotap_rule_engine.utils;

import com.zeotap.zeotap_rule_engine.models.Node;

import java.util.List;

public class RuleCombiner {
    public Node combineRules(List<Node> ruleNodes) {
        if (ruleNodes.isEmpty()) {
            return null;
        }

        if (ruleNodes.size() == 1) {
            return ruleNodes.get(0);
        }

        Node combinedRoot = new Node("AND", null, null, null);
        Node currentNode = combinedRoot;

        for (int i = 0; i < ruleNodes.size() - 2; i++) {
            currentNode.setLeft(ruleNodes.get(i));
            Node newNode = new Node("AND", null, null, null);
            currentNode.setRight(newNode);
            currentNode = newNode;
        }

        currentNode.setLeft(ruleNodes.get(ruleNodes.size() - 2));
        currentNode.setRight(ruleNodes.get(ruleNodes.size() - 1));

        return combinedRoot;
    }
}
