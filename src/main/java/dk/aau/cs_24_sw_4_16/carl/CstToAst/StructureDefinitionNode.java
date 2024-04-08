package dk.aau.cs_24_sw_4_16.carl.CstToAst;

import java.util.List;

public class StructureDefinitionNode extends AstNode {
    private final String name;
    private final List<VariableDeclarationNode> variables;

    public StructureDefinitionNode(String name, List<VariableDeclarationNode> variables) {
        this.name = name;
        this.variables = variables;
    }

    public String getName() {
        return name;
    }

    public List<VariableDeclarationNode> getVariables() {
        return variables;
    }

    @Override
    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visitStructureDefinitionNode(this);
    }
}
