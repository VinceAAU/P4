package dk.aau.cs_24_sw_4_16.carl.CstToAst;

public class BoolNode extends AstNode {
    boolean value;

    public BoolNode(String value) {
        this.value = Boolean.parseBoolean(value);
    }
    public Boolean getValue() {
        return value;
    }


    public void setValue(boolean value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return " " + value;
    }
}
