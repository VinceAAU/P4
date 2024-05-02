package dk.aau.cs_24_sw_4_16.carl.CstToAst;

import lombok.Getter;

import java.util.List;

@Getter
public class ArrayAssignmentNode extends AstNode {

    final private IdentifierNode identifier;
    final private List<AstNode> indices;
    final private AstNode value;

    public ArrayAssignmentNode(IdentifierNode identifier, List<AstNode> indices, ExpressionNode value){
        this.identifier = identifier;
        this.indices = indices;
        this.value = value;
    }

    public ArrayAssignmentNode(ArrayAccessNode arrayAccess, AstNode value) {
        this.identifier = arrayAccess.getIdentifier();
        this.indices = arrayAccess.getIndices();
        this.value = value;
    }
}
