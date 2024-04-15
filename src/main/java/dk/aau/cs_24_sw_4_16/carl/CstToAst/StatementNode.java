package dk.aau.cs_24_sw_4_16.carl.CstToAst;


public class StatementNode extends AstNode {


    AstNode node;

    public StatementNode(AstNode node) {
        this.node = node;
    }
    public AstNode getNode() {
        return node;
    }

    @Override
    public String toString() {
        return " " + this.node;
    }
}
