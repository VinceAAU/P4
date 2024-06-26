package dk.aau.cs_24_sw_4_16.carl.Interpreter;

import dk.aau.cs_24_sw_4_16.carl.CstToAst.*;
import dk.aau.cs_24_sw_4_16.carl.Semantic_A.Type;
import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

public class EvaluatorExecutor {
    HashMap<String, FunctionDefinitionNode> fTable;
    HashMap<String, AstNode> vTable;
    Stack<HashMap<String, AstNode>> scopes;
  
    HashMap<String, HashMap<String, AstNode>> tileInformationEnemy;
    HashMap<String, HashMap<String, AstNode>> tileInformationFloor;
    HashMap<String, HashMap<String, AstNode>> tileInformationWall;
    List<HashMap<String, AstNode>> rooms;
    Type wantedType = Type.UNKNOWN;
    // This will be the same instance for all instances of Interpreter
    // This might have unexpected consequences if we expand the program to use
    // more than one instance of Interpreter at a time, but it doesn't yet, so
    // it doesn't really matter. There's no better way of doing this, and in
    // the worst case we just get worse randomness.
    public static Random rand = new Random();

    public EvaluatorExecutor() {
        fTable = new HashMap<>();
        vTable = new HashMap<>();
        tileInformationEnemy = new HashMap<>();
        tileInformationFloor = new HashMap<>();
        tileInformationWall = new HashMap<>();
        scopes = new Stack<>();
        
        // activeScope.push(0);
        scopes.add(vTable);
        rooms = new ArrayList<>();
    }

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

    public AstNode roomCall(MethodCallNode node) {
        if (node.getPropertyAccessContext().getIdentifiers().get(0).toString().equals("size")) {
            return new IntNode(rooms.size());
        } else if (node.getPropertyAccessContext().getIdentifiers().get(0).toString().equals("get")) {
            if (((ArgumentListNode) node.getValue()).getList().get(0) instanceof IntNode) {
                int index = ((IntNode) ((ArgumentListNode) node.getValue()).getList().get(0)).getValue();
                if (index < rooms.size()) {
                    return rooms.get(((IntNode) ((ArgumentListNode) node.getValue()).getList().get(0)).getValue())
                            .get(node.getIdentifierNode().toString());
                } else {
                    throw new RuntimeException("out of bounds");
                }
            } else if (((ArgumentListNode) node.getValue()).getList().get(0) instanceof IdentifierNode) {
                IntNode in = ((IntNode) getVariable(
                        (IdentifierNode) ((ArgumentListNode) node.getValue()).getList().get(0)));
                int index = in.getValue();
                if (index < rooms.size()) {
                    return rooms.get(in.getValue()).get(node.getIdentifierNode().toString());
                } else {
                    throw new RuntimeException("out of bounds");
                }
            }
            {
                throw new RuntimeException("parameter must be an int");
            }
        }
        throw new RuntimeException("method call went wrong");
    }

    public AstNode visit(MethodCallNode node) {
        HashMap<String, HashMap<String, AstNode>> list;
        switch (node.getPropertyAccessContext().getList()) {
            case "enemy" -> list = tileInformationEnemy;
            case "wall" -> list = tileInformationWall;
            case "floor" -> list = tileInformationFloor;
            case "room" -> {
                return roomCall(node);
            }
            default -> throw new RuntimeException("list doesnt exist");
        }
        if (node.getPropertyAccessContext().getIdentifiers().get(0).toString().equals("size")) {
            return new IntNode(list.size());
        } else if (node.getPropertyAccessContext().getIdentifiers().get(0).toString().equals("get")) {
            if (((ArgumentListNode) node.getValue()).getList().get(0) instanceof IntNode) {
                int index = ((IntNode) ((ArgumentListNode) node.getValue()).getList().get(0)).getValue();
                if (index < list.size()) {
                    var key = list.keySet().toArray()[index];
                    return list.get(key).get(node.getIdentifierNode().toString());
                } else {
                    throw new RuntimeException("out of bounds");
                }
            } else {
                throw new RuntimeException("parameter must be an int");
            }
        }
        throw new RuntimeException("method call went wrong");
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
        } else if (node.getNode() instanceof ReturnStatementNode) {
            return visit((ReturnStatementNode) node.getNode());
        } else if (node.getNode() instanceof StructureDefinitionNode) {
            visit((StructureDefinitionNode) node.getNode());
        } else if (node.getNode() instanceof PropertyAccessNode) {
            visit((PropertyAccessNode) node.getNode());
        } else if (node.getNode() instanceof PropertyAssignmentNode) {
            visit((PropertyAssignmentNode) node.getNode());
        } else if (node.getNode() instanceof ArrayDefinitionNode) {
            visit((ArrayDefinitionNode) node.getNode());
        } else if (node.getNode() instanceof ArrayAssignmentNode) {
            visit((ArrayAssignmentNode) node.getNode());
        } else {
            throw new RuntimeException("Line 56 of Interpreter.java. Ya got something funky goin' on here, buckaroo.");
        }
        return null;
    }

    public void visit(StructureDefinitionNode node) {
        HashMap<String, AstNode> object = new HashMap<>();
        for (var variable : node.getVariableDeclarations()) {
            if (variable.getValue() instanceof IdentifierNode) {
                object.put(variable.getIdentifier().toString(),
                        getVariable((new IdentifierNode(variable.getValue().toString()))));
            } else {

                object.put(variable.getIdentifier().toString(), variable.getValue());
            }
        }
        if (node.getType().equals("enemy")) {
            tileInformationEnemy.put(node.getIdentifier().toString(), object);
        } else if (node.getType().equals("wall")) {
            tileInformationWall.put(node.getIdentifier().toString(), object);
        } else if (node.getType().equals("floor")) {
            tileInformationFloor.put(node.getIdentifier().toString(), object);
        } else if (node.getType().equals("room")) {
            rooms.add(object);
        }
    }

    public void visit(PropertyAssignmentNode node) {
        replaceValue(visit(node.getPropertyAccessNode()), node.getValue());
    }

    AstNode visit(PropertyAccessNode node) {
        HashMap<String, HashMap<String, AstNode>> list;
        switch (node.getList()) {
            case "enemy" -> list = tileInformationEnemy;
            case "wall" -> list = tileInformationWall;
            case "floor" -> list = tileInformationFloor;
            default -> throw new RuntimeException("list doesnt exist");
        }
        if (list.containsKey(node.getIdentifiers().get(0).toString())) {
            HashMap<String, AstNode> secondList = list.get(node.getIdentifiers().get(0).toString());
            if (node.getIdentifiers().size() > 1 && secondList.containsKey(node.getIdentifiers().get(1).toString())) {
                return secondList.get(node.getIdentifiers().get(1).toString());
            } else {
                throw new RuntimeException("you need 3 arguments");
            }

        }
        throw new RuntimeException("could not find the object");
    }

    public AstNode visit(ReturnStatementNode node) {
        return node;
    }

    public void replaceValue(AstNode node, AstNode node2) {
        AstNode toChange = node2;
        if (toChange instanceof BinaryOperatorNode) {
            toChange = visit((BinaryOperatorNode) toChange);
        }
        if (toChange instanceof FunctionCallNode) {
            toChange = visit((FunctionCallNode) toChange);
        }
        if (toChange instanceof IdentifierNode) {
            toChange = getVariable((IdentifierNode) toChange);
        }
        if (toChange instanceof RelationsAndLogicalOperatorNode) {
            toChange = visit((RelationsAndLogicalOperatorNode) toChange);
        }
        if (toChange instanceof PropertyAccessNode) {
            toChange = visit((PropertyAccessNode) toChange);
        }
        if (toChange instanceof MethodCallNode) {
            toChange = visit((MethodCallNode) toChange);
        }
        if (toChange instanceof ArrayAccessNode)
            toChange = visit((ArrayAccessNode) toChange);

        AstNode finalToChange = toChange;
        switch (node) {
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
    }

    public void visit(IfStatementNode node) {
        HashMap<String, AstNode> localTable = new HashMap<>();
        scopes.add(localTable);
        boolean visited = false;
        for (int i = 0; i < node.getExpressions().size(); i++) {
            AstNode toCheck = node.getExpressions().get(i).getNode();
            if (node.getExpressions().get(i).getNode() instanceof IdentifierNode) {
                toCheck = getVariable((IdentifierNode) node.getExpressions().get(i).getNode());
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

    public AstNode getVariable(IdentifierNode node) {
      
        for (int i = scopes.size()-1; i >= 0; i--) {
            if (scopes.get(i).containsKey(node.getIdentifier())) {
                return scopes.get(i).get(node.getIdentifier());
            }
        }
    
        throw new RuntimeException("could not find the variable " + node.getIdentifier());
    }

    public void visit(AssignmentNode node) {

       
        for (int i = scopes.size() - 1; i >= 0; i--) {
           
            if (scopes.get(i).containsKey(node.getIdentifier().getIdentifier())) {
            
                AstNode nodeToChange = scopes.get(i).get(node.getIdentifier().toString());
                AstNode toChange = node.getValue();
                replaceValue(nodeToChange, toChange);
                return;
            }
        }
        
        throw new RuntimeException("Variable '" + node.getIdentifier() + "' has not been defined yet.");
    }



    public void visit(VariableDeclarationNode node) {

        if (!scopes.getLast().containsKey(node.getIdentifier().getIdentifier())) {
           
            AstNode toChange = node.getValue();
            String type = node.getType().getType();
            if (toChange instanceof MethodCallNode) {
            

                if (type.equals("int")) {
                    wantedType = Type.INT;
                } else if (type.equals("string")) {
                    wantedType = Type.STRING;
                } else if (type.equals("bool")) {
                    wantedType = Type.BOOLEAN;
                } else if (type.equals("float")) {
                    wantedType = Type.FLOAT;
                }

              
                toChange = visit((MethodCallNode) toChange);
                
                Type tochange_Type = Type.UNKNOWN;
                if (toChange instanceof IntNode) {
                    tochange_Type = Type.INT;
                } else if (toChange instanceof FloatNode) {
                    tochange_Type = Type.FLOAT;
                } else if (toChange instanceof StringNode) {
                    tochange_Type = Type.STRING;
                } else if (toChange instanceof BoolNode) {
                    tochange_Type = Type.BOOLEAN;

                }
                
                if (wantedType.equals(tochange_Type)) {
                  
                    scopes.getLast().put(node.getIdentifier().toString(), toChange);
                } else {
                    System.err.println("You tried to assign Type:" + tochange_Type + " To the variable:"
                            + node.getIdentifier().getIdentifier() + " of type:" + wantedType
                            + " in runtime and that is illagal");
                }

            }

            if (toChange instanceof FunctionCallNode) {
                toChange = visit((FunctionCallNode) toChange);
            }
            if (node.getValue() instanceof BinaryOperatorNode) {
                toChange = visit((BinaryOperatorNode) node.getValue());
                scopes.getLast().put(node.getIdentifier().toString(), toChange);
            } else if (toChange instanceof RelationsAndLogicalOperatorNode) {
                toChange = visit((RelationsAndLogicalOperatorNode) node.getValue());
                scopes.getLast().put(node.getIdentifier().toString(), toChange);
            } else if (toChange instanceof IdentifierNode) {
                for (HashMap<String, AstNode> vTable : scopes) {
                    if (vTable.containsKey(toChange.toString())) {
                        AstNode variable = vTable.get(toChange.toString());
                        if (variable instanceof IntNode) {
                            scopes.getLast().put(node.getIdentifier().toString(), new IntNode(variable.toString()));
                        } else if (variable instanceof FloatNode) {
                            scopes.getLast().put(node.getIdentifier().toString(), new FloatNode(variable.toString()));
                        } else if (variable instanceof StringNode) {
                            scopes.getLast().put(node.getIdentifier().toString(), new StringNode(variable.toString()));
                        } else if (variable instanceof BoolNode) {
                            scopes.getLast().put(node.getIdentifier().toString(),
                                    new BoolNode(((BoolNode) variable).getValue()));
                        } else {
                            throw new RuntimeException("haa");
                        }
                    }
                }

            } else if (toChange instanceof PropertyAccessNode) {
                toChange = visit((PropertyAccessNode) toChange);
                scopes.getLast().put(node.getIdentifier().toString(), toChange);

            } else if (toChange instanceof ArrayAccessNode) {
                toChange = visit((ArrayAccessNode) node.getValue());
                scopes.getLast().put(node.getIdentifier().toString(), toChange);
            }  else if (toChange instanceof IntNode) {
                scopes.getLast().put(node.getIdentifier().toString(), new IntNode(toChange.toString()));
            } else if (toChange instanceof FloatNode) {
                scopes.getLast().put(node.getIdentifier().toString(), new FloatNode(toChange.toString()));
            } else if (toChange instanceof StringNode) {
                scopes.getLast().put(node.getIdentifier().toString(), new StringNode(toChange.toString()));
            } else if (toChange instanceof BoolNode) {
                scopes.getLast().put(node.getIdentifier().toString(), new BoolNode(((BoolNode) toChange).getValue()));
            }
        } else {
            wantedType = Type.UNKNOWN;
            throw new RuntimeException("variable " + node.getIdentifier() + " already exists in the current scope");
        }
        wantedType = Type.UNKNOWN;
    }

    private int[] astNodeListToIntArray(List<AstNode> ints) {
        return Arrays.stream(ints.toArray(new AstNode[0])).mapToInt(i -> evaluate_int(i).getValue()).toArray();
    }

    public AstNode visit(ArrayAccessNode node) {
        // Explanation:
        // 1. Get the ArrayNode behind the identifier.
        // 2. Convert the List<AstNode> into int[] (through evaluate_int())
        // 3. ???
        // 4. Profit

        return ((ArrayNode) getVariable(node.getIdentifier()))
                .get(
                        node.getIndices().stream().mapToInt(
                                astNode -> evaluate_int(astNode).getValue()).toArray());
    }

    public void visit(ArrayAssignmentNode node) {

        int[] indices = astNodeListToIntArray(node.getIndices());

        AstNode value;

        if (node.getValue() instanceof BinaryOperatorNode) {
            value = visit((BinaryOperatorNode) node.getValue());
        } else if (node.getValue() instanceof IdentifierNode) {
            value = visit((IdentifierNode) node.getValue());
        } else if (node.getValue() instanceof FunctionCallNode) {
            value = visit((FunctionCallNode) node.getValue());
        } else if (node.getValue() instanceof RelationsAndLogicalOperatorNode) {
            value = visit((RelationsAndLogicalOperatorNode) node.getValue());
        } else {
            // Those were all the checks made by visit(AssignmentNode)
            // Surely there can't be any other checks that need to be made
            value = node.getValue();
        }

        // These are the only checks made in

        ((ArrayNode) getVariable(node.getIdentifier())).set(value, indices);
    }

    private IntNode evaluate_int(AstNode node) {
        if (node instanceof BinaryOperatorNode) {
            return (IntNode) visit((BinaryOperatorNode) node);
        } else if (node instanceof IdentifierNode) {

            var value = getVariable((IdentifierNode) node);
            if (value instanceof IntNode)
                return (IntNode) value;
            else
                throw new RuntimeException("Type mismatch: Expected " + ((IdentifierNode) node).getIdentifier()
                        + " to be an int, got " + value.getClass());
        } else if (node instanceof FunctionCallNode) {
            var value = visit((FunctionCallNode) node);
            if (value instanceof IntNode)
                return (IntNode) value;
            else
                throw new RuntimeException(
                        "Type mismatch: Expected " + ((FunctionCallNode) node).getFunctionName().getIdentifier()
                                + "() to return an int, got " + value.getClass());
        } else if (node instanceof RelationsAndLogicalOperatorNode) {
            var value = visit((RelationsAndLogicalOperatorNode) node);
            if (value instanceof IntNode)
                return (IntNode) value;
            else
                throw new RuntimeException("Type mismatch: " + ((RelationsAndLogicalOperatorNode) node).operator
                        + " operator doesn't return an int");
        } else if (node instanceof IntNode) {
            return (IntNode) node;
        } else if (node instanceof ExpressionNode) {
            return evaluate_int(((ExpressionNode) node).getNode());
        } else {
            throw new RuntimeException("Expected an integer, got " + node.getClass());
        }
    }

    public void visit(ArrayDefinitionNode node) {
        if (idExists(node.getIdentifier().getIdentifier())) {
            throw new RuntimeException("Variable '" + node.getIdentifier() + "' already exists.");
        }

        scopes.getLast()
                .put(node.getIdentifier().getIdentifier(), new ArrayNode(node.getType(), new ArrayList<>(
                        node.getSizes().stream().mapToInt(n -> evaluate_int(n).getValue()).boxed().toList())));
    }

    private boolean idExists(String id) {
        boolean found = false;
        
        for (int i = scopes.size()-1; i >= 0; i--) {
            if (scopes.get(i).containsKey(id)) {
                found = true;
            }
        }
       
    
        return found;
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
            InbuildClasses.print(node, scopes);
        } else if (node.getFunctionName().toString().equals("generateMap")) {
            InbuildClasses.generateGrid(node, scopes, tileInformationWall);

        } else if (node.getFunctionName().toString().equals("generateRooms")) {
            InbuildClasses.generateRooms(node, scopes, tileInformationFloor, rooms);
        } else if (node.getFunctionName().toString().equals("generateCorridors")) {
            InbuildClasses.generateCorridors(node, scopes, tileInformationFloor, rooms);
        } else if (node.getFunctionName().toString().equals("generateSpawns")) {
            InbuildClasses.generateSpawns(node, scopes, tileInformationEnemy, rooms);
        } else if (node.getFunctionName().toString().equals("printMap")) {
            InbuildClasses.printMap(scopes, tileInformationFloor, tileInformationWall, tileInformationEnemy);
        } else if (node.getFunctionName().toString().equals("writeToFile")) {
            try {
                InbuildClasses.writeToFile(node, scopes, tileInformationFloor, tileInformationWall,
                        tileInformationEnemy);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } else if (node.getFunctionName().toString().equals("setSeed")) {
            InbuildClasses.setSeed(node);
        } else {
            // Boolean returnVoidCase = false;
            HashMap<String, AstNode> localTable = new HashMap<>();
            scopes.add(localTable);
            
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
            scopes.remove(localTable);
            
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

    public AstNode visit(BinaryOperatorNode node) {
        AstNode left = node.getLeft();
        AstNode right = node.getRight();
        if (left instanceof MethodCallNode) {
            left = visit((MethodCallNode) left);
        } else if (left instanceof PropertyAccessNode) {
            left = visit((PropertyAccessNode) left);
        }
        if (left instanceof IdentifierNode) {
            left = getVariable((IdentifierNode) left);
        } else if (left instanceof BinaryOperatorNode) {
            left = visit((BinaryOperatorNode) left);
        }
        if (right instanceof PropertyAccessNode) {

            right = visit((PropertyAccessNode) right);
        }
        if (right instanceof MethodCallNode) {
            right = visit((MethodCallNode) right);
        }
        if (right instanceof IdentifierNode) {
            right = getVariable((IdentifierNode) right);
        } else if (right instanceof BinaryOperatorNode) {
            right = visit((BinaryOperatorNode) right);
        }

        if (left instanceof IntNode && right instanceof IntNode) {
            return BinaryOperatorNode.getAstNodeValue(left, right, node.getOperator());
        } else if (left instanceof FloatNode && right instanceof FloatNode) {
            return BinaryOperatorNode.getAstNodeValue(left, right, node.getOperator());
        } else if (left instanceof FloatNode && right instanceof IntNode) {
            // AstNode floatnode =BinaryOperatorNode.getAstNodeValue(left, right,
            // node.getOperator());
            return BinaryOperatorNode.getAstNodeValue(left, right, node.getOperator());
        } else if (left instanceof IntNode && right instanceof FloatNode) {
            return BinaryOperatorNode.getAstNodeValue(left, right, node.getOperator());
        }
        throw new RuntimeException("BinaryOperator not implemented clause " + left.getClass() + " " + right.getClass());
    }

    public AstNode visit(RelationsAndLogicalOperatorNode node) {
        AstNode left = node.getLeft();
        AstNode right = node.getRight();
        if (left instanceof IdentifierNode) {
            left = getVariable((IdentifierNode) left);
        } else if (left instanceof RelationsAndLogicalOperatorNode) {
            left = visit((RelationsAndLogicalOperatorNode) left);
        } else if (left instanceof PropertyAccessNode) {
            left = visit((PropertyAccessNode) left);
        } else if (left instanceof BinaryOperatorNode) {
            left = visit((BinaryOperatorNode) left);
        }
        if (right instanceof IdentifierNode) {
            right = getVariable((IdentifierNode) right);
        } else if (right instanceof RelationsAndLogicalOperatorNode) {
            right = visit((RelationsAndLogicalOperatorNode) right);
        } else if (right instanceof PropertyAccessNode) {
            right = visit((PropertyAccessNode) right);
        } else if (right instanceof BinaryOperatorNode) {
            right = visit((BinaryOperatorNode) right);
        }
        if (left instanceof IntNode && right instanceof IntNode) {
            return RelationsAndLogicalOperatorNode.getAstNodeValue(left, right, node.getOperator());
        } else if (left instanceof FloatNode && right instanceof FloatNode) {
            return RelationsAndLogicalOperatorNode.getAstNodeValue(left, right, node.getOperator());
        } else if (left instanceof BoolNode && right instanceof BoolNode) {
            return RelationsAndLogicalOperatorNode.getAstNodeValue(left, right, node.getOperator());
        } else if (left instanceof StringNode && right instanceof StringNode) {
            return RelationsAndLogicalOperatorNode.getAstNodeValue(left, right, node.getOperator());
        }

        throw new RuntimeException(
                "RelationsAndLogicalOperator not implemented clause " + left.getClass() + " " + right.getClass());
    }

    public AstNode visit(WhileLoopNode node) {
        AstNode toCheck = (node.getExpression()).getNode();
        if (toCheck instanceof IdentifierNode) {
            toCheck = getVariable((IdentifierNode) node.getExpression().getNode());
        } else if (toCheck instanceof RelationsAndLogicalOperatorNode) {
            toCheck = visit((RelationsAndLogicalOperatorNode) toCheck);
        }
        while ((toCheck instanceof BoolNode)) {
            if (((BoolNode) toCheck).getValue()) {
                HashMap<String, AstNode> localTable = new HashMap<>();
                scopes.add(localTable);
                visit(node.getBlock());
                toCheck = visit(node.getExpression().getNode());
                if (toCheck instanceof IdentifierNode) {
                    toCheck = getVariable((IdentifierNode) node.getExpression().getNode());
                } else if (toCheck instanceof RelationsAndLogicalOperatorNode) {
                    toCheck = visit((RelationsAndLogicalOperatorNode) toCheck);
                }
                scopes.remove(localTable);
            } else {
                return node;
            }

        }
        throw new RuntimeException("Did not get into while statement");
    }
}
