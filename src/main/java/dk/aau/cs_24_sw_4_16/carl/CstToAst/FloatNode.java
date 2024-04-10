package dk.aau.cs_24_sw_4_16.carl.CstToAst;

public class FloatNode extends AstNode{
    float value;

    public FloatNode(String value) {
        this.value = Float.parseFloat(value);
    }
    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return " value: " + value;
    }
}
