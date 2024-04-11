package dk.aau.cs_24_sw_4_16.carl.CstToAst;

public class ParameterNode extends AstNode{
    IdentifierNode identifier;
    TypeNode type;

    public ParameterNode(IdentifierNode identifier, TypeNode type) {
        this.identifier = identifier;
        this.type = type;
    }

    public IdentifierNode getIdentifier() {
        return identifier;
    }

    public TypeNode getType() {
        return type;
    }
}
