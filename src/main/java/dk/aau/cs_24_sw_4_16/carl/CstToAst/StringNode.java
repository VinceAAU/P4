package dk.aau.cs_24_sw_4_16.carl.CstToAst;

public class StringNode extends AstNode {
    private String value;

    public StringNode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}