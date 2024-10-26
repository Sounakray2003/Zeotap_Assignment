package com.zeotap.zeotap_rule_engine.services;

import com.zeotap.zeotap_rule_engine.models.Node;
import com.zeotap.zeotap_rule_engine.repositories.NodeRepository;
import com.zeotap.zeotap_rule_engine.utils.RuleCombiner;
import com.zeotap.zeotap_rule_engine.utils.RuleEvaluator;
import com.zeotap.zeotap_rule_engine.utils.RuleParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RuleService {

    @Autowired
    private NodeRepository nodeRepository;

    private final RuleParser ruleParser = new RuleParser();
    private final RuleEvaluator ruleEvaluator = new RuleEvaluator();
    private final RuleCombiner ruleCombiner = new RuleCombiner();

    public Node createRule(String ruleString) {
        Node root = ruleParser.parseRule(ruleString);
        return saveNode(root);
    }

    private Node saveNode(Node node) {
        if (node == null) {
            return null;
        }
        if (node.getLeft() != null) {
            node.setLeft(saveNode(node.getLeft()));
        }
        if (node.getRight() != null) {
            node.setRight(saveNode(node.getRight()));
        }
        return nodeRepository.save(node);
    }

    public Node combineRules(List<String> rules) {
        List<Node> ruleNodes = rules.stream()
                .map(this::createRule)
                .collect(Collectors.toList());

        Node combinedRoot = ruleCombiner.combineRules(ruleNodes);
        return saveNode(combinedRoot);
    }

    public boolean evaluateRule(Node root, Map<String, Object> data) {
        return ruleEvaluator.evaluate(root, data);
    }
}
