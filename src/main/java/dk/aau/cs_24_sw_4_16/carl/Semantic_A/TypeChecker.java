package dk.aau.cs_24_sw_4_16.carl.Semantic_A;

import dk.aau.cs_24_sw_4_16.carl.CstToAst.*;

import java.util.*;

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
        if(node.getNode() instanceof  IfStatementNode) {
            visitIfStatement((IfStatementNode) node.getNode());
        }
    }


    public Type relationOperator_Type_check(RelationsAndLogicalOperatorNode node) {

       AstNode left = node.getLeft();
       AstNode right = node.getRight();

        Type left_type = Type.VOID;
        Type right_Type = Type.VOID;

        left_type = getType(left);
        right_Type = getType(right);

        if (left_type == Type.INT && right_Type == Type.INT) { // Her at udregning sker, som ikke burde ske.
            return Type.BOOLEAN;
        } else if (left_type == Type.FLOAT && right_Type == Type.FLOAT) {
            return Type.BOOLEAN;
        } else if (left_type == Type.BOOLEAN && right_Type == Type.BOOLEAN) {
            return Type.BOOLEAN;
        }

        System.out.println(
                "Wrong types for relation operation:" + left_type + ":" + left + " And:" + right + ":" + right_Type);
        return Type.VOID;
    }


    public Type binaryoperator_type_check(BinaryOperatorNode node) {
        AstNode left = node.getLeft(); // Får venstre x som i result=x+y i node form
        AstNode right = node.getRight();// Får højre y i node form

        Type left_type = Type.VOID;
        Type right_Type = Type.VOID;

        left_type = getType(left);
        right_Type = getType(right);

        if (left_type == Type.INT && right_Type == Type.INT) { // Her at udregning sker, som ikke burde ske.
            return Type.INT;

        } else if (left_type == Type.FLOAT && right_Type == Type.FLOAT) {
            return Type.FLOAT;
        }

        System.out.println(
                "Wrong types for binnary operation:" + left_type + ":" + left + " And:" + right + ":" + right_Type);
        return Type.VOID;

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
                    scopes.getLast().put(node.getIdentifier().toString(), Type_we_save_in_E_table);

                } else {
                    System.out.println("Tryied to asssign Type:" + assignmentType + " to the variable:" + identifier
                            + " that has the type:" + variableType
                            + " And that is hella iligal");
                }
            } else {
                throw new RuntimeException("variable " + node.getIdentifier() + " already exists");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public Type getVariable(IdentifierNode node) {
        if (scopes.getFirst().containsKey(node.getIdentifier().toString())) {
            System.out.println("Get Varible ident:" + node.getIdentifier());
            System.out.println("Get Varible" + scopes.getFirst().get(node.getIdentifier().toString()));
            return scopes.getFirst().get(node.getIdentifier().toString());
        }

        for (int i = activeScope.getLast(); i < scopes.size(); i++) {
            if (scopes.get(i).containsKey(node.getIdentifier().toString())) {
                System.out.println("Get Varible2" + scopes.getFirst().get(node.getIdentifier().toString()));
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

                Type rightType = getType(node.getValue());
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

/*
* Vi her skal vi sikre af hver if(exp) exp = bool.  i if statements har vi if if else  flere exp som alle skal være type bool
*
* */

    public void visitIfStatement(IfStatementNode node) {
        Type expression = Type.VOID;
        System.out.println("FYcj this code i hate the oop it sucsk ass");
        for (int i = 0; i < node.getExpressions().size(); i++) {
            expression = getType(node.getExpressions().get(i).getNode());
            System.out.println("this is expression:"+i+":"+node.getExpressions().get(i).getNode().getClass());
            if (expression != Type.BOOLEAN) {
                System.out.println("If statements expresion must resolve to bool expresion, and this resolve to Type:" + expression);
            }
        }
        for (int i = 0; i < node.getBlocks().size(); i++) {
            HashMap<String, Type> localTable = new HashMap<>();
            scopes.add(localTable);
            visitBlockNode(node.getBlocks().get(i));

            System.out.println(node.getBlocks().get(i));
            //Måske forkert, men det virker
            scopes.remove(localTable);
        }
//        scopes.remove(localTable);
    }

    public void visitBlockNode(BlockNode node) {
        for (AstNode statement : node.getStatements()) {
            visitStatements((StatementNode) statement);
        }
    }

    public Type getType(Object node) {
        Type type = Type.VOID;

        if (node instanceof IdentifierNode) {
            type = getVariable((IdentifierNode) node); // Vis x giv mig x value
        } else if (node instanceof BinaryOperatorNode) {
            type = binaryoperator_type_check((BinaryOperatorNode) node);
        } else if (node instanceof RelationsAndLogicalOperatorNode){
            type = relationOperator_Type_check((RelationsAndLogicalOperatorNode) node);
        }
//        else if (node instanceof ExpressionNode) {
//            if (((ExpressionNode) node).getNode() instanceof BinaryOperatorNode) {
//                type = binaryoperator_type_check((BinaryOperatorNode) node);
//            } else if (((ExpressionNode) node).getNode() instanceof RelationsAndLogicalOperatorNode) {
//                type = relationOperator_Type_check((RelationsAndLogicalOperatorNode) node);
//            }
//        }
        else if (node instanceof IntNode) {
            IntNode intNode = (IntNode) node;
            Object value = intNode.getValue();
            type = Type.INT;
        } else if (node instanceof FloatNode) {
            type = Type.FLOAT;
        } else if (node instanceof BoolNode) {
            type = Type.BOOLEAN;
        } else if (node instanceof ExpressionNode) {

        } else if (node instanceof StringNode) {
            System.out.println("We get into string node");
            type = Type.STRING;
        } else if (node instanceof TypeNode) {
            String type_fuck_me_why_did_we_save_types_as_String = ((TypeNode) node).getType();
            System.out.println(type_fuck_me_why_did_we_save_types_as_String + "get in gere");
            switch (type_fuck_me_why_did_we_save_types_as_String) {
                case "int":
                    return Type.INT;
                case "string":
                    return Type.STRING;
                case "bool":
                    return Type.BOOLEAN;
                case "float":
                    return Type.FLOAT;
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
