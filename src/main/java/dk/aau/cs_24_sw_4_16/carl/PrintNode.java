package dk.aau.cs_24_sw_4_16.carl;

public class PrintNode implements ASTNode {
    public ASTNode expression;

    public PrintNode(ASTNode expression) {
        this.expression = expression;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
