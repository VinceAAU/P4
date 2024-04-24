package dk.aau.cs_24_sw_4_16.carl.CstToAst;

import java.util.List;

public class PropertyAccessNode extends AstNode {
    String list;
    List<IdentifierNode> identifiers;

    PropertyAccessNode(String list, List<IdentifierNode> identifiers) {
        this.list = list;
        this.identifiers = identifiers;
    }

    public String getList() {
        return list;
    }

    public List<IdentifierNode> getIdentifiers() {
        return identifiers;
    }
}
