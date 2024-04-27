package dk.aau.cs_24_sw_4_16.carl.Semantic_A;

import dk.aau.cs_24_sw_4_16.carl.CstToAst.*;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Stack;

import javax.print.DocFlavor.STRING;

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
        if (node.getNode() instanceof AssignmentNode) {
            visitAssignNode((AssignmentNode) node.getNode());
        }
    }

    private void visitVariableDeclaration(VariableDeclarationNode node) {

        try {
            boolean found = scopes.getFirst().containsKey(node.getIdentifier().toString());

            for (int i = activeScope.getLast(); i < scopes.size(); i++) {
                if (scopes.get(i).containsKey(node.getIdentifier().toString())) {
                    found = true;
                }
            }
            if (!found) {// Vi skal tjekke variable type mod det den type vi assigner til variablen.

                String identifier = node.getIdentifier().toString();

                Type variableType = getType(node.getType()); // Left side type

                AstNode ass = node.getValue(); // THis is right side should be a node
                Type assignmentType = getType(ass); // This should give right side type

                if (variableType == assignmentType) {
                    Type Type_we_save_in_E_table = variableType;

                    if (node.getValue() instanceof BinaryOperatorNode) {
                        // toChange = visit((BinaryOperatorNode) node.getValue());

                        // scopes.getLast().put(node.getIdentifier().toString(), toChange);
                    } else if (node.getValue() instanceof IdentifierNode) {
                        for (HashMap<String, Type> ETable : scopes) {
                            if (ETable.containsKey(Type_we_save_in_E_table.toString())) {
                                scopes.getLast().put(node.getIdentifier().toString(),
                                        ETable.get(Type_we_save_in_E_table.toString()));
                            }
                        }
                    } else {
                        scopes.getLast().put(node.getIdentifier().toString(), Type_we_save_in_E_table);
                    }
                } else {
                    System.out.println("Tryied to asssign Type:" + assignmentType + " to the variable:" + identifier
                            + " that has the type:" + variableType
                            + " And that is hella iligal");
                }
            } else {
                throw new RuntimeException("variable " + node.getIdentifier() + " already exists");
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public Type getVariable(IdentifierNode node) {
        for (HashMap<String, Type> ETable : scopes) {
            if (scopes.getFirst().containsKey(node.getIdentifier().toString())) {
                System.out.println("Get Varible ident:"+node.getIdentifier());
                System.out.println("Get Varible"+scopes.getFirst().get(node.getIdentifier().toString()));
                return scopes.getFirst().get(node.getIdentifier().toString());
            }
        }
        for (int i = activeScope.getLast(); i < scopes.size(); i++) {
            if (scopes.get(i).containsKey(node.getIdentifier().toString())) {
                System.out.println("Get Varible2"+scopes.getFirst().get(node.getIdentifier().toString()));
                return scopes.get(i).get(node.getIdentifier().toString());
            }
        }
        System.out.println("could not find the variable: " + node.getIdentifier());
        throw new RuntimeException("could not find the variable " + node.getIdentifier());

    }

    public void visitAssignNode(AssignmentNode node) {

        for (HashMap<String, Type> ETable : scopes) {
            if (ETable.containsKey(node.getIdentifier().toString())) {// hvis x er i scope
                // tjekke om det er lovligt.
                // hent gamle type og nye type.
                Type oldType = getType(node.getIdentifier());
                String identifier = node.getIdentifier().toString();
                System.out.println(oldType);

                Type rightType = getType(node.getValue());
                System.out.println("we get in hereaowidjawiodjwaoidja");
                // tjekke om det er lovligt.
                if (oldType != rightType) {
                    System.out.println("Tryied to asssign Type:" + rightType + " to the variable:" + identifier
                            + " that has the type:" + oldType
                            + " And that is hella iligal");
                }
                return;
            }
        }

        throw new RuntimeException("Variable '" + node.getIdentifier() + "' has not been defined yet.");
    }

    public Type getType(Object node) {
        Type type = Type.VOID;

        if (node instanceof IdentifierNode) {
            type = getVariable((IdentifierNode) node); // Vis x giv mig x value
        } else if (node instanceof BinaryOperatorNode) {
            // type = binaryoperator_type_check((BinaryOperatorNode) node);
        } else if (node instanceof IntNode) {
            IntNode intNode = (IntNode) node;
            Object value = intNode.getValue();
            if (value instanceof Integer) {
                type = Type.INT;
            }
        } else if (node instanceof FloatNode) {
            type = Type.FLOAT;
        } else if (node instanceof BoolNode) {
            type = Type.BOOLEAN;
        } else if (node instanceof ExpressionNode) {

        } else if (node instanceof StringNode) {
            System.out.println("We get into string node");
            type = Type.STRING;
        }

        else if (node instanceof TypeNode) {
            String type_fuck_me_why_did_we_save_types_as_String = ((TypeNode) node).getType();
            System.out.println(type_fuck_me_why_did_we_save_types_as_String + "get in gere");

            switch (type_fuck_me_why_did_we_save_types_as_String) {
                case "int":

                    return Type.INT;

                case "string":
                    return Type.STRING;

                default:
                    break;
            }

            if (node instanceof String) { // Directly handling raw Strings
                System.out.println("We get the string class raw string" + node);
                return Type.STRING;
            } else if (node instanceof Integer) { // Directly handling raw Integers
                return Type.INT;
            } else if (node instanceof Float) { // Directly handling raw Floats
                return Type.FLOAT;
            } else if (node instanceof Boolean) { // Directly handling raw Booleans
                return Type.BOOLEAN;
            }

        } else {
            System.out.println("The node we get failed to handle:" + node.getClass());
        }
        return type;
    }

}
