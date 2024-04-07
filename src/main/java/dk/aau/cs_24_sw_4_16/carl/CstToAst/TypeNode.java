package dk.aau.cs_24_sw_4_16.carl.CstToAst;

public class TypeNode extends AstNode {
    String type;

    public TypeNode(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return " type: " + type;
    }


    @Override
    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
