package com.zeotap.zeotap_rule_engine.utils;

import com.zeotap.zeotap_rule_engine.models.Node;

import java.util.Map;

public class RuleEvaluator {

        public boolean evaluate(Node root, Map<String, Object> data) {
            if (root == null) {
                return true;
            }

            if ("operand".equals(root.getType())) {
                return evaluateCondition(root.getValue(), data);
            } else {
                boolean leftResult = evaluate(root.getLeft(), data);
                boolean rightResult = evaluate(root.getRight(), data);

                return "AND".equals(root.getType()) ? leftResult && rightResult : leftResult || rightResult;
            }
        }

        private boolean evaluateCondition(String condition, Map<String, Object> data) {
            String[] parts = condition.split(" ");
            String attribute = parts[0];
            String operator = parts[1];
            String value = parts[2];

            Object dataValue = data.get(attribute);
            if (dataValue == null) {
                return false;
            }

            switch (operator) {
                case ">":
                    return compareValues(dataValue, value) > 0;
                case "<":
                    return compareValues(dataValue, value) < 0;
                case "=":
                    return compareValues(dataValue, value) == 0;
                case ">=":
                    return compareValues(dataValue, value) >= 0;
                case "<=":
                    return compareValues(dataValue, value) <= 0;
                case "!=":
                    return compareValues(dataValue, value) != 0;
                default:
                    throw new IllegalArgumentException("Unsupported operator: " + operator);
            }
        }

        private int compareValues(Object dataValue, String value) {
            if (dataValue instanceof Number && value.matches("-?\\d+(\\.\\d+)?")) {
                double numericDataValue = ((Number) dataValue).doubleValue();
                double numericValue = Double.parseDouble(value);
                return Double.compare(numericDataValue, numericValue);
            } else {
                return dataValue.toString().compareTo(value);
            }
        }

    }
