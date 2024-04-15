package dk.aau.cs_24_sw_4_16.carl.CstToAst;

public class IdentifierNode extends AstNode {


    String identifier;

    public IdentifierNode(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    @Override
    public String toString() {
        return identifier;
    }


}
