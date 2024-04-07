package dk.aau.cs_24_sw_4_16.carl.CstToAst;

public class FloatNode extends AstNode{
    float value;

    public FloatNode(String value) {
        this.value = Float.parseFloat(value);
    }
    public float getValue() {
        return value;
    }

    @Override
    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visit(this); // The visit method decides the return type
    }
    @Override
    public String toString() {
        return " value: " + value;
    }
}
