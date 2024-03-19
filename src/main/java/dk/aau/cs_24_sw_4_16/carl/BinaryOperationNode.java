package dk.aau.cs_24_sw_4_16.carl;

public class BinaryOperationNode implements ASTNode {
    public ASTNode left;
    public Token opToken;
    public ASTNode right;

    public BinaryOperationNode(ASTNode left, Token opToken, ASTNode right) {
        this.left = left;
        this.opToken = opToken;
        this.right = right;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
