package dk.aau.cs_24_sw_4_16.carl.CstToAst;

public class RelationsAndLogicalOperatorNode extends AstNode {
    public AstNode left;
    public AstNode right;
    public String operator;

    public RelationsAndLogicalOperatorNode(AstNode left, AstNode right, String operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    public static AstNode getAstNodeValue(AstNode left, AstNode right, String operator) {
        if (left instanceof IntNode && right instanceof IntNode) {
            int leftValue = ((IntNode) left).getValue();
            int rightValue = ((IntNode) right).getValue();
            return performIntOnIntOperator(leftValue, rightValue, operator);
        } else if (left instanceof FloatNode && right instanceof FloatNode) {
            float leftValue = ((FloatNode) left).getValue();
            float rightValue = ((FloatNode) right).getValue();
            return performFloatOnFloatOperator(leftValue, rightValue, operator);
        } else if (left instanceof BoolNode && right instanceof BoolNode) {
            boolean leftValue = ((BoolNode) left).getValue();
            boolean rightValue = ((BoolNode) right).getValue();
            var value = performBoolOnBoolOperator(leftValue, rightValue, operator);
            return value;
        } else if (left instanceof IdentifierNode || right instanceof IdentifierNode) {
            return new RelationsAndLogicalOperatorNode(left, right, operator);

        }
        throw new RuntimeException("something went wrong in getAstNodeValue");
    }

    private static AstNode performIntOnIntOperator(int left, int right, String operator) {
        return getAstNode(left, right, operator);
    }

    private static AstNode performFloatOnFloatOperator(float left, float right, String operator) {
        return getAstNode(left, right, operator);
    }

    private static AstNode getAstNode(float left, float right, String operator) {
        return switch (operator) {
            case "==" -> new BoolNode(String.valueOf(left == right));
            case "!=" -> new BoolNode(String.valueOf(left != right));
            case "<" -> new BoolNode(String.valueOf(left < right));
            case ">" -> new BoolNode(String.valueOf(left > right));
            case ">=" -> new BoolNode(String.valueOf(left >= right));
            case "<=" -> new BoolNode(String.valueOf(left <= right));
            default -> throw new IllegalArgumentException("Invalid operator: " + operator);
        };
    }

    private static AstNode performBoolOnBoolOperator(boolean left, boolean right, String operator) {
        return switch (operator) {
            case "AND" -> new BoolNode(String.valueOf(left && right));
            case "OR" -> new BoolNode(String.valueOf(left || right));
            case "!" -> new BoolNode(String.valueOf(!left));
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
        return "gottem";
    }
}
