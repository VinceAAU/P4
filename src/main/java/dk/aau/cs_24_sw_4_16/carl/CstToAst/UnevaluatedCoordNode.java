package dk.aau.cs_24_sw_4_16.carl.CstToAst;

import lombok.Getter;

public class UnevaluatedCoordNode extends AstNode{
    @Getter private final AstNode x;
    @Getter private final AstNode y;


    public UnevaluatedCoordNode(AstNode x, AstNode y) {
        this.x = x;
        this.y = y;
    }
}
