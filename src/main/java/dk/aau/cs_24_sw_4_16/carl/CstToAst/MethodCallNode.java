package dk.aau.cs_24_sw_4_16.carl.CstToAst;

import dk.aau.cs_24_sw_4_16.carl.CARLParser;

public class MethodCallNode extends AstNode{
    PropertyAccessNode propertyAccessContext;
    AstNode value;
    IdentifierNode identifierNode;
    public MethodCallNode(PropertyAccessNode propertyAccessContext, AstNode value) {
        this.propertyAccessContext = propertyAccessContext;
        this.value = value;
    }

    public MethodCallNode(PropertyAccessNode propertyAccessContext, AstNode value, IdentifierNode identifierNode) {
        this.propertyAccessContext = propertyAccessContext;
        this.value = value;
        this.identifierNode = identifierNode;
    }

    public IdentifierNode getIdentifierNode() {
        return identifierNode;
    }

    public PropertyAccessNode getPropertyAccessContext() {
        return propertyAccessContext;
    }

    public AstNode getValue() {
        return value;
    }
}
