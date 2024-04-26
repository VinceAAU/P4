package dk.aau.cs_24_sw_4_16.carl.Semantic_A;

import dk.aau.cs_24_sw_4_16.carl.CstToAst.*;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Stack;

public class TypeChecker {
    HashMap<String, FunctionDefinitionNode> fTable; // function table, identifier(x) og node
    HashMap<String, Type> ETable;// variable table, identifier(x) og node(int)
    Stack<HashMap<String, Type>> scopes; // scope table, variable identifier(x) og node
    Deque<Integer> activeScope;// Hvilket scope vi er i nu

    public TypeChecker() {
        fTable = new HashMap<>();
        ETable = new HashMap<>();
        scopes = new Stack<>();
        activeScope = new ArrayDeque<>();
        activeScope.push(0);
        scopes.add(ETable);
    }

    public void visitor(AstNode node) {
        if (node instanceof ProgramNode) {
            visitProgramNode((ProgramNode) node);
        }
    }

    public void visitProgramNode(ProgramNode node) {
        for (AstNode statement : node.getStatements()) {
            visitStatements((StatementNode) statement);
        }
    }

    public void visitStatements(StatementNode node) {
        if (node.getNode() instanceof VariableDeclarationNode) {
            visitVariableDeclaration((VariableDeclarationNode) node.getNode());
        }
    }


    private void visitVariableDeclaration(VariableDeclarationNode node) {
        boolean found = scopes.getFirst().containsKey(node.getIdentifier().toString());

        for (int i = activeScope.getLast(); i < scopes.size(); i++) {
            if (scopes.get(i).containsKey(node.getIdentifier().toString())) {
                found = true;
            }
        }
        if (!found) {
            System.out.println("We in! " + node.getType());
            Type toChange = getType(node);

            if (node.getValue() instanceof BinaryOperatorNode) {
              //  toChange = visit((BinaryOperatorNode) node.getValue());

               // scopes.getLast().put(node.getIdentifier().toString(), toChange);
            } else if (node.getValue() instanceof IdentifierNode) {
                for (HashMap<String, Type> ETable : scopes) {
                    if (ETable.containsKey(toChange.toString())) {
                        scopes.getLast().put(node.getIdentifier().toString(), ETable.get(toChange.toString()));
                    }
                }
            } else {
                scopes.getLast().put(node.getIdentifier().toString(), toChange);
            }

        } else {
            throw new RuntimeException("variable " + node.getIdentifier() + " already exists");
        }
    }


    public Type getType(AstNode node) {
        Type type = Type.VOID;

        if (node instanceof IdentifierNode) {
//            type = getVariable((IdentifierNode) node); // Vis x giv mig x value
        } else if (node instanceof BinaryOperatorNode) {
//            type = binaryoperator_type_check((BinaryOperatorNode) node);
        } else if (node instanceof IntNode) {
            type = Type.INT;
            System.out.println(((IntNode) node).getValue() + " We in intNode");
        } else if (node instanceof FloatNode) {
            type = Type.FLOAT;
        } else if (node instanceof BoolNode) {
            type = Type.BOOLEAN;
        } else if (node instanceof ExpressionNode){
//            if(((ExpressionNode) node).getNode() instanceof  BinaryOperatorNode){
//                type = binaryoperator_type_check((BinaryOperatorNode) node);
//            } else if (((ExpressionNode) node).getNode() instanceof RelationsAndLogicalOperatorNode){
//                type = relationOperator_Type_check((RelationsAndLogicalOperatorNode) node);
//            }
        } else if (false) {

        }
        return type;
    }

}
