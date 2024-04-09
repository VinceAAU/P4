package dk.aau.cs_24_sw_4_16.carl.CstToAst;

public class AdditionNode extends AstNode {
    public AstNode left;
    public AstNode right;

    public AdditionNode(AstNode left, AstNode right) {
        this.left = left;
        this.right = right;
    }

    public AstNode getLeft() {
        return left;
    }

    public AstNode getRight() {
        return right;
    }

    @Override
    public String toString() {
        float leftValue = Float.parseFloat(left.toString());
        float rightValue = Float.parseFloat(right.toString());
        boolean isInteger = (leftValue == (int)leftValue && rightValue == (int)rightValue);
        float result = leftValue + rightValue;
        if (isInteger) {
            return Integer.toString((int)result);
        } else {
            return Float.toString(result);
        }
    }
}
