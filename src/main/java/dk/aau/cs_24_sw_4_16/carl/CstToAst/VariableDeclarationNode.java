package dk.aau.cs_24_sw_4_16.carl.CstToAst;

public class VariableDeclarationNode extends AstNode {

    IdentifierNode identifier;
    TypeNode type;
    AstNode value;
    public VariableDeclarationNode(IdentifierNode identifier, TypeNode type, AstNode value) {
        this.identifier =  identifier;
        this.type = type;
        this.value = value;
    }

    public IdentifierNode getIdentifier() {
        return identifier;
    }

    public TypeNode getType() {
        return type;
    }

    public AstNode getValue() {
        return value;
    }
    @Override
    public String toString() {
        return "Identifier: " + identifier +
                type +
                value;
    }
}
