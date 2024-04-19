package dk.aau.cs_24_sw_4_16.carl.CstToAst;

import lombok.Getter;

import java.util.List;

@Getter
public class ArrayAssignmentNode extends AstNode {

    final private IdentifierNode identifier;
    final private List<Integer> indices;
    final private AstNode value;

    public ArrayAssignmentNode(IdentifierNode identifier, List<Integer> indices, ExpressionNode value){
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
