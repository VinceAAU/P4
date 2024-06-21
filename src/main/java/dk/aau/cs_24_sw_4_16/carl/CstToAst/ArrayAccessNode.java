package dk.aau.cs_24_sw_4_16.carl.CstToAst;

import lombok.Getter;

import java.util.List;

@Getter
public class ArrayAccessNode extends AstNode {
    final private List<AstNode> indices;

    final private IdentifierNode identifier;

    public ArrayAccessNode(IdentifierNode identifier, List<AstNode> indices){
        this.identifier = identifier;
        this.indices = indices;
    }
}
