package dk.aau.cs_24_sw_4_16.carl.Interpreter;

import dk.aau.cs_24_sw_4_16.carl.CstToAst.*;

public class Interpreter{
    

    public AstNode visit(AstNode node) {
        System.out.println(node);
        if (node instanceof ProgramNode) {
            return visit((ProgramNode) node);
        } else if (node instanceof StatementNode) {
            return visit((StatementNode) node);
        }
        return node;
    }


    public AstNode visit(StatementNode node) {
        if (node.getNode() instanceof AssignmentNode) {
            return visit((AssignmentNode) node.getNode());
        } else if (node.getNode() instanceof VariableDeclarationNode) {
            return visit((VariableDeclarationNode) node.getNode());
        }
        return null;
    }


    public AstNode visit(AssignmentNode node) {
        System.out.println("Assigning " + node.getValue() + " to " + node.getIdentifier()+ " and value " +  node.getValue());
        return node;
    }


    public AstNode visit(VariableDeclarationNode node) {
        System.out.println("Declaring variable " + node.getIdentifier() + " of type " + node.getType() + " and value " +  node.getValue());

        return node;
    }


    public AstNode visit(TypeNode node) {
        return node;
    }


    public AstNode visit(IntNode node) {
        return node;
    }


    public AstNode visit(FloatNode node) {
        return node;
    }


    public AstNode visit(ProgramNode node) {
        for (AstNode statement : node.getStatements()) {
            visit(statement);
        }
        return node;
    }
}
