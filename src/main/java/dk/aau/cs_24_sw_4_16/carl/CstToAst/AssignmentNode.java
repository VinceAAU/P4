package dk.aau.cs_24_sw_4_16.carl.CstToAst;

public class AssignmentNode extends AstNode{
    String identifier;
    AstNode value;

    public String getIdentifier() {
        return identifier;
    }

    public AstNode getValue() {
        return value;
    }

    public AssignmentNode(String identifier, AstNode value) {
        this.identifier = identifier;

        this.value = value;
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "Identifier: " + identifier +
                value;
    }

}
