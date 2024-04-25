package dk.aau.cs_24_sw_4_16.carl.Semantic_A;

import dk.aau.cs_24_sw_4_16.carl.CstToAst.*;
import java.util.*;
import dk.aau.cs_24_sw_4_16.carl.Interpreter.InbuildClasses;;

public class Visitor {
    HashMap<String, FunctionDefinitionNode> fTable; // function table, identifier(x) og node
    HashMap<String, AstNode> vTable;// variable table, identifier(x) og node(int)
    Stack<HashMap<String, AstNode>> scopes; // scope table, variable identifier(x) og node
    Deque<Integer> activeScope;// Hvilket scope vi er i nu

    boolean printyes = true;// ! SLET SENERE

    public AstNode visit(AstNode node) {
        if (node instanceof ProgramNode) {
            if (printyes) {
                System.out.println("Hej jeg kommer int i visitor");
            }
            fTable = new HashMap<>();
            vTable = new HashMap<>();
            scopes = new Stack<>();
            activeScope = new ArrayDeque<>();
            activeScope.push(0);
            scopes.add(vTable);

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
            visit((AssignmentNode) node.getNode());
        } else if (node.getNode() instanceof VariableDeclarationNode) {
            visit((VariableDeclarationNode) node.getNode());
        } else if (node.getNode() instanceof FunctionCallNode) {
            return visit((FunctionCallNode) node.getNode());
        } else if (node.getNode() instanceof FunctionDefinitionNode) {
            return visit((FunctionDefinitionNode) node.getNode());
        } else if (node.getNode() instanceof WhileLoopNode) {
            return visit((WhileLoopNode) node.getNode());
        } else if (node.getNode() instanceof IfStatementNode) {
            visit((IfStatementNode) node.getNode());
        } else if (node.getNode() instanceof BinaryOperatorNode) {
            visit((BinaryOperatorNode) node.getNode());
            if (true) {
                System.out.println("Hej jeg kommer int i interpriter binaryoperator");
            }
        } else if (node.getNode() instanceof ReturnStatementNode) {
            return visit((ReturnStatementNode) node.getNode());
        }
        return null;
    }

    public AstNode visit(ReturnStatementNode node) {
        return node;
    }

    public void visit(IfStatementNode node) {
        HashMap<String, AstNode> localTable = new HashMap<>(); // ny lokalt value table
        scopes.add(localTable); // Tilføjer det til stacken med scopes
        boolean visited = false;
        for (int i = 0; i < node.getExpressions().size(); i++) { // Hver expression er en if statement. Ligesom hver
                                                                 // block er til den if statement
            AstNode toCheck = node.getExpressions().get(i).getNode();
            if (node.getExpressions().get(i).getNode() instanceof IdentifierNode) {
                toCheck = getVariable((IdentifierNode) node.getExpressions().get(i).getNode());// Hvis det er x så giv
                                                                                               // mig xnode
            } else if (node.getExpressions().get(i).getNode() instanceof RelationsAndLogicalOperatorNode) {
                toCheck = visit((RelationsAndLogicalOperatorNode) node.getExpressions().get(i).getNode());
            }
            if (toCheck instanceof BoolNode && ((BoolNode) toCheck).getValue()) {
                visit(node.getBlocks().get(i));
                visited = true;
                break;
            }
        }
        if (!visited && node.getExpressions().size() < node.getBlocks().size()) {
            visit(node.getBlocks().get(node.getBlocks().size() - 1));
        }
        scopes.remove(localTable);
    }

    public Type getVariable(IdentifierNode node) {
        // for (HashMap<String, AstNode> vTable : scopes) {
        if (scopes.getFirst().containsKey(node.getIdentifier().toString())) { // Tester om x er der, ev til bool
            return scopes.getFirst().get(node.getIdentifier().toString());// i scope, giv mig x node
        }

        for (int i = activeScope.getLast(); i < scopes.size(); i++) {// for at gå igennem hvert scope og tjekke for x
            if (scopes.get(i).containsKey(node.getIdentifier().toString())) {
                return scopes.get(i).get(node.getIdentifier().toString());// i i scope, giv mig x node
            }
        }
        throw new RuntimeException("could not find the variable " + node.getIdentifier());
    }

    public void visit(AssignmentNode node) {

        for (HashMap<String, AstNode> vTable : scopes) {
            if (vTable.containsKey(node.getIdentifier().toString())) { // tjek if variable x is scopes bool
                AstNode nodeToChange = vTable.get(node.getIdentifier().toString());// retunere node
                AstNode toChange = node.getValue(); // Højre side af assignmen x= tochange vil retunere node
                if (toChange instanceof BinaryOperatorNode) {
                    toChange = visit((BinaryOperatorNode) toChange); // if x= 20+20
                }
                if (toChange instanceof FunctionCallNode) {
                    toChange = visit((FunctionCallNode) toChange); // if x= functioncall(10)
                }
                if (toChange instanceof IdentifierNode) {
                    toChange = getVariable((IdentifierNode) toChange); // if x = y
                }
                if (node.getValue() instanceof RelationsAndLogicalOperatorNode) { // if x= 10<5
                    toChange = visit((RelationsAndLogicalOperatorNode) toChange);
                }
                AstNode finalToChange = toChange;
                switch (nodeToChange) {
                    case IntNode intNode when finalToChange instanceof IntNode ->
                        intNode.setValue(((IntNode) finalToChange).getValue());
                    case FloatNode floatNode when finalToChange instanceof FloatNode ->
                        floatNode.setValue(((FloatNode) finalToChange).getValue());
                    case StringNode stringNode when finalToChange instanceof StringNode ->
                        stringNode.setValue(((StringNode) finalToChange).getValue());
                    case BoolNode boolNode when finalToChange instanceof BoolNode ->
                        boolNode.setValue(((BoolNode) finalToChange).getValue());
                    case null, default -> throw new RuntimeException("Type mismatch");
                }
                return;
            }
        }

        throw new RuntimeException("Variable '" + node.getIdentifier() + "' has not been defined yet.");
    }

    public void visit(VariableDeclarationNode node) {
        boolean found = false;
        if (scopes.getFirst().containsKey(node.getIdentifier().toString())) {// finder x i scope FY FY
            found = true;
        }

        for (int i = activeScope.getLast(); i < scopes.size(); i++) {// finder x i et scope FY FY
            if (scopes.get(i).containsKey(node.getIdentifier().toString())) {
                found = true;
            }
        }
        if (!found) { // kunne ikke finde x i scope så kan gemme den

            AstNode toChange = node.getValue(); // x=value value = node eller 5
            if (node.getValue() instanceof BinaryOperatorNode) { // hvis x=2+2
                toChange = visit((BinaryOperatorNode) node.getValue()); // Henter type node efter operation node
                scopes.getLast().put(node.getIdentifier().toString(), toChange); // Her sætter vi x til type node i
                                                                                 // scope hash map
            } else if (toChange instanceof IdentifierNode) { // Vis x=y
                for (HashMap<String, AstNode> vTable : scopes) {// For hvert scope skal vi prøve at finde variable
                    if (vTable.containsKey(toChange.toString())) {// Vis vi finder den i et scope
                        scopes.getLast().put(node.getIdentifier().toString(), vTable.get(toChange.toString()));
                    }
                }
            } else {
                scopes.getLast().put(node.getIdentifier().toString(), toChange); // case hvor x=4
            }

        } else { // Fy fy den findes allerede
            throw new RuntimeException("variable " + node.getIdentifier() + " already exists");
        }
    }

    public AstNode visit(TypeNode node) {
        return node;
    }

    public AstNode visit(BoolNode node) {
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
        if (node.getFunctionName().toString().equals("print")) {
            InbuildClasses.print(node, scopes, activeScope);
        } else {
            HashMap<String, AstNode> localTable = new HashMap<>();
            scopes.add(localTable);
            activeScope.add(scopes.size() - 1);

            if (fTable.containsKey(node.getFunctionName().toString())) {
                FunctionDefinitionNode function = fTable.get(node.getFunctionName().toString());
                List<ParameterNode> arguments = function.getArguments().getParameters();
                for (int i = 0; i < function.getArguments().getParameters().size(); i++) {
                    visit(new VariableDeclarationNode(arguments.get(i).getIdentifier(), arguments.get(i).getType(),
                            node.getArgument(i)));
                }
                AstNode test = visit(function.getBlock());

                if (test instanceof ReturnStatementNode) {
                    AstNode returnValue = ((ReturnStatementNode) test).getReturnValue();
                    if (returnValue instanceof IdentifierNode) {
                        returnValue = getVariable((IdentifierNode) returnValue);
                    }
                    scopes.remove(localTable);
                    activeScope.removeLast();
                    if (function.getReturnType().getType().equals("void")) {
                        if (returnValue != null) {
                            throw new RuntimeException(
                                    "cannot return a value in void function call or have code after return statement");
                        }
                        return node;
                    }

                    return returnValue;
                }
            }
        }
        return node;
    }

    public AstNode visit(BlockNode node) {
        for (AstNode statement : node.getStatements()) {

            if (((StatementNode) statement).getNode() instanceof ReturnStatementNode) {
                return visit((StatementNode) statement);
            } else {
                visit((StatementNode) statement);
            }
        }
        return node;
    }

    public AstNode visit(ExpressionNode node) {
        if (node.getNode() instanceof FunctionCallNode) {
            return visit((FunctionCallNode) node.getNode());
        } else if (node.getNode() instanceof RelationsAndLogicalOperatorNode) {
            return visit((RelationsAndLogicalOperatorNode) node.getNode());
        } else if (node.getNode() instanceof BinaryOperatorNode) {
            return visit((BinaryOperatorNode) node.getNode());
        }
        return node;
    }

    public AstNode visit(FunctionDefinitionNode node) {
        if (!fTable.containsKey(node.getIdentifier().toString())) {
            fTable.put(node.getIdentifier().toString(), node);
        } else {
            throw new RuntimeException("function " + node.getIdentifier() + " already exists");
        }
        return node;
    }

    public Type binaryoperator_type_check(BinaryOperatorNode node) {
        AstNode left = node.getLeft(); // Får venstre x som i result=x+y i node form
        AstNode right = node.getRight();// Får højre y i node form

        Type left_type = Type.VOID;
        Type right_Type = Type.VOID;

        left_type = getType(left);
        right_Type = getType(right);

        // Lav type check her.

        if (left_type == Type.INT && right_Type == Type.INT) { // Her at udregning sker, som ikke burde ske.
            return Type.INT;
        } else if (left_type == Type.FLOAT && right_Type == Type.FLOAT) {
            return Type.FLOAT;
        }
        System.out.println(
                "Wrong types for binnary operation:" + left_type + ":" + left + " And:" + right + ":" + right_Type);
        return Type.VOID;
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
                "Wrong types for binnary operation:" + left_type + ":" + left + " And:" + right + ":" + right_Type);
        return Type.VOID;
    }

    public Type getType(AstNode node) {
        Type type = Type.VOID;
        if (node instanceof IdentifierNode) {
            type = getVariable((IdentifierNode) node); // Vis x giv mig x value
        } else if (node instanceof BinaryOperatorNode) {
            type = binaryoperator_type_check((BinaryOperatorNode) node);
        } else if (node instanceof IntNode) {
            type = Type.INT;
        } else if (node instanceof FloatNode) {
            type = Type.FLOAT;
        } else if (node instanceof BoolNode) {
            type = Type.BOOLEAN;
        }

        return type;

    }

    public void visit(WhileLoopNode node) { // ! Type cheking her er at checke expression while(expresion) sikre at det
                                            // er en bool type
        AstNode toCheck = (node.getExpression()).getNode();
        Type tocheck_type = Type.VOID;

        if (toCheck instanceof IdentifierNode) {
            tocheck_type = getVariable((IdentifierNode) node.getExpression().getNode()); // skal evaluere til bool
        } else if (toCheck instanceof RelationsAndLogicalOperatorNode) {
            tocheck_type = relationOperator_Type_check((RelationsAndLogicalOperatorNode) toCheck); // skal også til bool
        }
        if (toCheck instanceof BoolNode) {
            tocheck_type = Type.BOOLEAN;
        }

        if (tocheck_type != Type.BOOLEAN) {
            System.out.println("While loop condition wrong type should be bool, but was:"+tocheck_type + "and got thi node"+toCheck);
        }
        HashMap<String, AstNode> localTable = new HashMap<>();
        scopes.add(localTable);
        visit(node.getBlock());
        toCheck = visit(node.getExpression().getNode());
        scopes.remove(localTable);

    }
}
