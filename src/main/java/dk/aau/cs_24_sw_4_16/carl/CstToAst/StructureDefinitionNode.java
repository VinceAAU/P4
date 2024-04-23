package dk.aau.cs_24_sw_4_16.carl.CstToAst;

import java.util.ArrayList;
import java.util.List;

public class StructureDefinitionNode extends AstNode {
    private String type;
    private final IdentifierNode identifier;
    private final List<VariableDeclarationNode> variableDeclaration;
    List<AstNode> variables;

    public StructureDefinitionNode(AstNode identifier, String type, List<VariableDeclarationNode> variableDeclaration) {
        this.identifier = (IdentifierNode) identifier;
        this.type = type;
        this.variableDeclaration = variableDeclaration;
        variables = new ArrayList<AstNode>();
    }

    public String getType() {
        return type;
    }

    public List<AstNode> getVariables() {
        return variables;
    }

    public List<VariableDeclarationNode> getVariableDeclaration() {
        return variableDeclaration;
    }

    public IdentifierNode getIdentifier() {
        return identifier;
    }

    public List<VariableDeclarationNode> getVariableDeclarations() {
        return variableDeclaration;
    }

}
