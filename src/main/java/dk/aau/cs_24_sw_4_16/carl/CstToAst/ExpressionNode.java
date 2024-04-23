package dk.aau.cs_24_sw_4_16.carl.CstToAst;

public class ExpressionNode extends AstNode{
    public AstNode getNode() {
        return node;
    }

    AstNode node;

    public ExpressionNode(AstNode node) {
        this.node = node;
    }


    @Override
    public String toString() {
        return " " + this.node;
    }

    public int getValue() {
        return 0;
    }
}
