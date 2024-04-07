package dk.aau.cs_24_sw_4_16.carl.CstToAst;

public class VariableDeclarationNode extends AstNode {
    public String getIdentifier() {
        return identifier;
    }

    public TypeNode getType() {
        return type;
    }

    public AstNode getValue() {
        return value;
    }

    String identifier;
    TypeNode type;
    AstNode value;

    public VariableDeclarationNode(String identifier, TypeNode type, AstNode value) {
        this.identifier = identifier;
        this.type = type;
        this.value = value;
        System.out.println(this);
    }

    @Override
    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "Identifier: " + identifier +
                type +
                value;
    }
}
