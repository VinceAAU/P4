package dk.aau.cs_24_sw_4_16.carl.CstToAst;

import java.util.List;

public class PropertyAccessNode extends AstNode {
    String list;
    List<IdentifierNode> identifiers;

    PropertyAccessNode(String list, List<IdentifierNode> identifiers) {
        this.identifiers = identifiers;
    }
}
