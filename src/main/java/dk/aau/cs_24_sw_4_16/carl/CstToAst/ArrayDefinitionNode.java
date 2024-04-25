package dk.aau.cs_24_sw_4_16.carl.CstToAst;

import lombok.Getter;

import java.util.List;

public class ArrayDefinitionNode extends AstNode {
    @Getter private final TypeNode type;

    // we need this only if dynamic sizing is allowed
    @Getter private final List<Integer> sizes;

    private IdentifierNode identifier;

    public ArrayDefinitionNode(TypeNode type, List<Integer> sizes, IdentifierNode identifier) {
        this.type = type;
        this.sizes = sizes;
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier.toString();
    }
}
