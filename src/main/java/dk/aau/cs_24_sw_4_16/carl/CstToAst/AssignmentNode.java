package dk.aau.cs_24_sw_4_16.carl.CstToAst;

public class AssignmentNode extends AstNode {
    IdentifierNode identifier;
    AstNode value;


    public AssignmentNode(AstNode identifier, AstNode value) {
        this.identifier = (IdentifierNode) identifier;

        this.value = value;
    }

    public IdentifierNode getIdentifier() {
        return identifier;
    }

    public AstNode getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Identifier: " + identifier +
                value;
    }

}
