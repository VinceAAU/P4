package dk.aau.cs_24_sw_4_16.carl.CstToAst;


public class StatementNode extends AstNode{
    public AstNode getNode() {
        return node;
    }

    AstNode node;

    public StatementNode(AstNode node) {
        this.node = node;
    }

    @Override
    public  <T> T accept(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return " "+  this.node;
    }
}
