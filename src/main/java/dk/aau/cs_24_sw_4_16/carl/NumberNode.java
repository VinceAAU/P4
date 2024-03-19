package dk.aau.cs_24_sw_4_16.carl;

public class NumberNode implements ASTNode {
    public Token token;

    public NumberNode(Token token) {
        this.token = token;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
