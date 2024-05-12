package dk.aau.cs_24_sw_4_16.carl.CstToAst;

import lombok.Getter;

@Getter
public class IdentifierNode extends AstNode {
    String identifier;
    public IdentifierNode(String identifier) {
        this.identifier = identifier;
    }
    @Override
    public String toString() {
        return identifier;
    }
}
