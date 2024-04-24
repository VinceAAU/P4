package dk.aau.cs_24_sw_4_16.carl.CstToAst;

import lombok.Getter;

public class CoordinateDeclarationNode extends AstNode {
    @Getter private final IdentifierNode identifier;
    @Getter private final AstNode x;
    @Getter private final AstNode y;

    public CoordinateDeclarationNode(IdentifierNode identifier, AstNode x, AstNode y){
        this.identifier = identifier;
        this.x = x;
        this.y = y;
    }
}
