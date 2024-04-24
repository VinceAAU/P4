package dk.aau.cs_24_sw_4_16.carl.CstToAst;

public class PropertyAssignmentNode extends AstNode{
    private PropertyAccessNode propertyAccessNode;
    private AstNode value;

    public PropertyAssignmentNode(PropertyAccessNode propertyAccessNode, AstNode value) {
        this.propertyAccessNode = propertyAccessNode;
        this.value = value;
    }

    public PropertyAccessNode getPropertyAccessNode() {
        return propertyAccessNode;
    }

    public AstNode getValue() {
        return value;
    }
}
