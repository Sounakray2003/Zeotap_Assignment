package com.zeotap.zeotap_rule_engine.utils;

import com.zeotap.zeotap_rule_engine.models.Node;

import java.util.*;

public class RuleParser {

    public Node parseRule(String ruleString) {
        Queue<String> tokens = tokenize(ruleString);
        return parseExpression(tokens);
    }

    private Queue<String> tokenize(String ruleString) {
        String[] rawTokens = ruleString.replaceAll("\\(", " ( ")
                .replaceAll("\\)", " ) ")
                .trim().split("\\s+");
        return new LinkedList<>(Arrays.asList(rawTokens));
    }

    private Node parseExpression(Queue<String> tokens) {
        Stack<Node> nodeStack = new Stack<>();
        Stack<String> operatorStack = new Stack<>();

        while (!tokens.isEmpty()) {
            String token = tokens.poll();

            switch (token) {
                case "(":
                    operatorStack.push(token);
                    break;
                case ")":
                    while (!operatorStack.peek().equals("(")) {
                        processOperator(nodeStack, operatorStack.pop());
                    }
                    operatorStack.pop(); // Remove the "("
                    break;
                case "AND":
                case "OR":
                    while (!operatorStack.isEmpty() && precedence(token) <= precedence(operatorStack.peek())) {
                        processOperator(nodeStack, operatorStack.pop());
                    }
                    operatorStack.push(token);
                    break;
                default:
                    nodeStack.push(parseCondition(token, tokens));
                    break;
            }
        }

        while (!operatorStack.isEmpty()) {
            processOperator(nodeStack, operatorStack.pop());
        }

        return nodeStack.pop();
    }

    private Node parseCondition(String attribute, Queue<String> tokens) {
        String operator = tokens.poll();
        String value = tokens.poll();
        return new Node("operand", null, null, attribute + " " + operator + " " + value);
    }

    private void processOperator(Stack<Node> nodeStack, String operator) {
        Node right = nodeStack.pop();
        Node left = nodeStack.pop();
        nodeStack.push(new Node(operator, left, right, null));
    }

    private int precedence(String operator) {
        if (operator.equals("AND")) return 2;
        if (operator.equals("OR")) return 1;
        return 0;
    }
}
