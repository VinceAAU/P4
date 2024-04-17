package dk.aau.cs_24_sw_4_16.carl.CstToAst;

import java.util.ArrayList;
import java.util.List;

public class StructureDefinitionNode extends AstNode {
    private final IdentifierNode identifier;
    private final List<VariableDeclarationNode> variableDeclaration;
    List<AstNode> variables;

    public StructureDefinitionNode(AstNode identifier, List<VariableDeclarationNode> variableDeclaration) {
        this.identifier = (IdentifierNode) identifier;
        this.variableDeclaration = variableDeclaration;
        variables = new ArrayList<AstNode>();
    }

    public IdentifierNode getIdentifier() {
        return identifier;
    }

    public List<VariableDeclarationNode> getVariableDeclarations() {
        return variableDeclaration;
    }

}
