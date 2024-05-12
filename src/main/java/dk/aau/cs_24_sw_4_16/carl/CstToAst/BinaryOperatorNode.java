package dk.aau.cs_24_sw_4_16.carl.CstToAst;

import dk.aau.cs_24_sw_4_16.carl.Interpreter.EvaluatorExecutor;

public class BinaryOperatorNode extends AstNode {
    private final AstNode left;
    private final AstNode right;
    private final String operator;

    public BinaryOperatorNode(AstNode left, AstNode right, String operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    public static AstNode getAstNodeValue(AstNode left, AstNode right, String operator) {
        if (left instanceof IntNode && right instanceof IntNode) {
            int leftValue = ((IntNode) left).getValue();
            int rightValue = ((IntNode) right).getValue();
            return performOperation(leftValue, rightValue, operator);
        } else if (left instanceof FloatNode && right instanceof FloatNode) {
            float leftValue = ((FloatNode) left).getValue();
            float rightValue = ((FloatNode) right).getValue();
            return performOperation(leftValue, rightValue, operator);
        }
        return null;
    }

    //Why are these static? I feel like this goes against the idea of OOP
    //-Vincent
    public static AstNode performOperation(int leftValue, int rightValue, String operator) {
        return switch (operator) {
            case "+" -> new IntNode(String.valueOf(leftValue + rightValue));
            case "-" -> new IntNode(String.valueOf(leftValue - rightValue));
            case "*" -> new IntNode(String.valueOf(leftValue * rightValue));
            case "/" -> new IntNode(String.valueOf(leftValue / rightValue));
            case "%" -> new IntNode(String.valueOf(leftValue % rightValue));
            case ".." -> new IntNode(String.valueOf(EvaluatorExecutor.rand.nextInt(leftValue, rightValue)));
            default -> throw new IllegalArgumentException("Invalid operator: " + operator);
        };
    }

    public static AstNode performOperation(float leftValue, float rightValue, String operator) {
        return switch (operator) {
            case "+" -> new FloatNode(String.valueOf(leftValue + rightValue));
            case "-" -> new FloatNode(String.valueOf(leftValue - rightValue));
            case "*" -> new FloatNode(String.valueOf(leftValue * rightValue));
            case "/" -> new FloatNode(String.valueOf(leftValue / rightValue));
            case "%" -> new FloatNode(String.valueOf(leftValue % rightValue));
            case ".." -> new IntNode(String.valueOf(EvaluatorExecutor.rand.nextFloat(leftValue, rightValue)));
            default -> throw new IllegalArgumentException("Invalid operator: " + operator);
        };
    }

    public AstNode getLeft() {
        return left;
    }

    public AstNode getRight() {
        return right;
    }

    public String getOperator() {
        return operator;
    }

    @Override
    public String toString() {
        return ""+getAstNodeValue(getLeft(), getRight(), getOperator());
    }
}
