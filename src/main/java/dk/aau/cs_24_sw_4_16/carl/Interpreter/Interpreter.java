package dk.aau.cs_24_sw_4_16.carl.Interpreter;

import dk.aau.cs_24_sw_4_16.carl.CstToAst.*;
import dk.aau.cs_24_sw_4_16.carl.Expression.Expression;

public class Interpreter {


    public AstNode visit(AstNode node) {
        if (node instanceof ProgramNode) {
            return visit((ProgramNode) node);
        } else if (node instanceof StatementNode) {
            return visit((StatementNode) node);
        } else if (node instanceof ExpressionNode) {
            return visit((ExpressionNode) node);
        }
        return node;
    }


    public AstNode visit(StatementNode node) {
        if (node.getNode() instanceof AssignmentNode) {
            return visit((AssignmentNode) node.getNode());
        } else if (node.getNode() instanceof VariableDeclarationNode) {
            return visit((VariableDeclarationNode) node.getNode());
        } else if (node.getNode() instanceof FunctionCallNode) {
            return visit((FunctionCallNode) node.getNode());
        }
        return null;
    }


    public AstNode visit(AssignmentNode node) {
        System.out.println("Assigning " + node.getValue() + " to " + node.getIdentifier());
        System.out.println(node);
        return node;
    }


    public AstNode visit(VariableDeclarationNode node) {
        System.out.println("Declaring variable " + node.getIdentifier() + " of type " + node.getType() + " and " + node.getValue());
        System.out.println(node);
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

    public AstNode visit(FunctionCallNode node) {
        if (node.getFunctionName().getIdentifier().equals("print")) {
            String toPrint = "";
            for (AstNode argument : node.getArguments()) {
                if (argument instanceof StatementNode) {
                    toPrint += ((StatementNode) argument).getNode();
                } else {
                    toPrint += argument.toString();
                }
            }
            System.out.println(toPrint);

        }
        return node;
    }

    public AstNode visit(ExpressionNode node) {

        if (node.getNode() instanceof FunctionCallNode) {
            return visit((FunctionCallNode) node.getNode());
        }
        return node;
    }
}
