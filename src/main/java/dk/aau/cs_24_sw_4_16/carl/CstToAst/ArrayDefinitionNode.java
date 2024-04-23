package dk.aau.cs_24_sw_4_16.carl.CstToAst;

import lombok.Getter;

public class ArrayDefinitionNode extends AstNode {
    @Getter
    private final TypeNode type;

    // we need this only if dynamic sizing is allowed
    @Getter
    private final int size;

    private final IdentifierNode identifier;

    public ArrayDefinitionNode(TypeNode type, int size, IdentifierNode identifier) {
        this.type = type;
        this.size = size;
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier.toString();
    }
}
