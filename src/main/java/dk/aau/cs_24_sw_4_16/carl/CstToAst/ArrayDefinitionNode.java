package dk.aau.cs_24_sw_4_16.carl.CstToAst;

import lombok.Getter;

import java.util.List;

public class ArrayDefinitionNode extends AstNode {
    @Getter private final TypeNode type;

    @Getter private final List<AstNode> sizes;

    @Getter private final IdentifierNode identifier;

    public ArrayDefinitionNode(TypeNode type, List<AstNode> sizes, IdentifierNode identifier) {
        this.type = type;
        this.sizes = sizes;
        this.identifier = identifier;
    }
}
