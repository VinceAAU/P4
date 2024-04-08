package dk.aau.cs_24_sw_4_16.carl.CstToAst;

public class StringNode extends AstNode {
    private String text;

    public StringNode(String text) {
        this.text = text;
    }
    public String getText() {
        return text;
    }



    @Override
    public String toString() {
        return text;
    }
}