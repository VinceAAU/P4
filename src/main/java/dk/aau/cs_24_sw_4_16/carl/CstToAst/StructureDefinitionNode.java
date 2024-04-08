package dk.aau.cs_24_sw_4_16.carl.CstToAst;

import java.util.List;

public class StructureDefinitionNode extends AstNode {
    private final IdentifierNode identifier;
    private final List<VariableDeclarationNode> variables;

    public StructureDefinitionNode(AstNode identifier, List<VariableDeclarationNode> variables) {
        this.identifier = (IdentifierNode) identifier;
        this.variables = variables;
    }

    public IdentifierNode getIdentifier() {
        return identifier;
    }

    public List<VariableDeclarationNode> getVariables() {
        return variables;
    }

}
