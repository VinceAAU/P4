package dk.aau.cs_24_sw_4_16.carl.CstToAst;

public class IntNode extends AstNode{
    int value;

    public IntNode(String value) {
        this.value = Integer.parseInt(value);
    }
    public int getValue() {
        return value;
    }



    @Override
    public String toString() {
        return "" + value;
    }
}
