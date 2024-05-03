package dk.aau.cs_24_sw_4_16.carl.Semantic_A;

import dk.aau.cs_24_sw_4_16.carl.CstToAst.*;

import java.util.*;

public class TypeChecker {

    HashMap <String, Type> typeOfReturnFunction;
    HashMap <String, List<Type>> functionParameters;

    //    HashMap<String, FunctionDefinitionNode> fTable; // function table, identifier(x) og node

    HashMap<String, Type> eTable;// variable table, identifier(x) og node(int)
    Stack<HashMap<String, Type>> scopes; // scope table, variable identifier(x) og node
    Deque<Integer> activeScope;// Hvilket scope vi er i nu
    int errorNumber = 1;
    Boolean printDebugger = false;
    Boolean hasReturnStatement = false;
    String currentActiveFunction ="";

    public TypeChecker() {
//        fTable = new HashMap<>();
        eTable = new HashMap<>();
        scopes = new Stack<>();
        activeScope = new ArrayDeque<>();
        activeScope.push(0);
        scopes.add(eTable);
        typeOfReturnFunction = new HashMap<>();
        functionParameters = new HashMap<>();
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
        if(printDebugger){
            System.out.println("We get in the vist statement");
            System.out.println(node.getClass());
            System.out.println(node.getNode().getClass());
        }
        if (node.getNode() instanceof VariableDeclarationNode) {
            visitVariableDeclaration((VariableDeclarationNode) node.getNode());
        }
        if (node.getNode() instanceof AssignmentNode) {
            visitAssignNode((AssignmentNode) node.getNode());
        }
        if (node.getNode() instanceof IfStatementNode) {
            visitIfStatement((IfStatementNode) node.getNode());
        }
        if (node.getNode() instanceof WhileLoopNode) {
            visitWhileLoop((WhileLoopNode) node.getNode());
        }
        if (node.getNode() instanceof FunctionDefinitionNode) {
            visitFunctionDefinition((FunctionDefinitionNode) node.getNode());
        }
        if (node.getNode() instanceof FunctionCallNode){
            visitFunctionCall((FunctionCallNode) node.getNode());
        }
        if (node.getNode() instanceof ReturnStatementNode){
            hasReturnStatement = true;

            System.out.println("we get in return statement"+ hasReturnStatement);
            visitReturnNode((ReturnStatementNode) node.getNode());

        }
    }

    public void errorHandler(String errorMessage){
        System.err.println("Error " + errorNumber);
        errorNumber = errorNumber + 1;
        System.err.println(errorMessage);
    }
    public Type relationOperatorTypeCheck(RelationsAndLogicalOperatorNode node) {

        AstNode left = node.getLeft();
        AstNode right = node.getRight();

        Type leftType = Type.VOID;
        Type rightType = Type.VOID;

        leftType = getType(left);
        rightType = getType(right);

        if (leftType == Type.INT && rightType == Type.INT) { // Her at udregning sker, som ikke burde ske.
            return Type.BOOLEAN;
        } else if (leftType == Type.FLOAT && rightType == Type.FLOAT) {
            return Type.BOOLEAN;
        } else if (leftType == Type.BOOLEAN && rightType == Type.BOOLEAN) {
            return Type.BOOLEAN;
        }
        
        errorHandler("Wrong types for relation operation:" + leftType + ":" + left + " And:" + right + ":" + rightType);

        return Type.VOID;
    }

    public Type binaryOperatorTypeCheck(BinaryOperatorNode node) {
        AstNode left = node.getLeft(); // Får venstre x som i result=x+y i node form
        AstNode right = node.getRight();// Får højre y i node form

        Type leftType = Type.VOID;
        Type rightType = Type.VOID;

        leftType = getType(left);
        rightType = getType(right);

        if (leftType == Type.INT && rightType == Type.INT) { // Her at udregning sker, som ikke burde ske.
            return Type.INT;

        } else if (leftType == Type.FLOAT && rightType == Type.FLOAT) {
            return Type.FLOAT;
        }
        errorHandler("Wrong types for binary operation:" + leftType + ":" + left + " And:" + right + ":" + rightType);
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
                    Type typeWeSaveInETable = variableType;
                    scopes.getLast().put(node.getIdentifier().toString(), typeWeSaveInETable);

                } else {
                    errorHandler("Tryied to asssign Type:" + assignmentType + " to the variable:" + identifier
                                                              + " that has the type:" + variableType
                                                              + " And that is hella iligal");

                }
            } else {
                throw new RuntimeException("variable " + node.getIdentifier() + " already exists");
            }
        } catch (Exception e) {
            errorHandler(e.getMessage());
        }

    }

    public Type getVariable(IdentifierNode node) {
        if (scopes.getFirst().containsKey(node.getIdentifier().toString())) {
            return scopes.getFirst().get(node.getIdentifier().toString());
        }
        for (int i = activeScope.getLast(); i < scopes.size(); i++) {
            if (scopes.get(i).containsKey(node.getIdentifier().toString())) {
                return scopes.get(i).get(node.getIdentifier().toString());
            }
        }
        throw new RuntimeException("could not find the variable " + node.getIdentifier());
    }

    // Check if return = type   er det samme som den function den står i.
    public void visitReturnNode(ReturnStatementNode node) {
      Type returnType = getType(node.getReturnValue());
      Type activeFunction = typeOfReturnFunction.get(currentActiveFunction);
      if(Objects.equals(currentActiveFunction, "")){
        errorHandler("You have made return statement outside a function THAT IS illigal");
      }
      if(returnType != activeFunction){
          errorHandler("The return type " + returnType + " Does not match the return statement of the function " + activeFunction.getTypeName());
      }
    }

    public void visitAssignNode(AssignmentNode node) {
        if(printDebugger){

        }
        boolean foundIdentifier = false;
        for (HashMap<String, Type> ETable : scopes) {

             if(true){
                 System.out.println("Identifier"+node.getIdentifier().toString());
                 System.out.println("Etable:"+ETable);
             }
             if (ETable.containsKey(node.getIdentifier().toString())) {// hvis x er i scope
                 foundIdentifier = true;
                // tjekke om det er lovligt.
                // hent gamle type og nye type.
                Type oldType = getType(node.getIdentifier());
                String identifier = node.getIdentifier().toString();

                Type rightType = getType(node.getValue());
               
                // tjekke om det er lovligt.
                if (oldType != rightType) {
                    errorHandler("Tryied to asssign Type:" + rightType + " to the variable:" + identifier
                                                              + " that has the type:" + oldType
                                                              + " And that is hella iligal");

                }
             } else {
                errorHandler("Variable '" + node.getIdentifier() + "' has not been defined yet.");
             }
             if (foundIdentifier){
                 break;
             }
        }
    }

    /*
     * Vi her skal vi sikre af hver if(exp) exp = bool. i if statements har vi if if
     * else flere exp som alle skal være type bool
     *
     */

    public void visitIfStatement(IfStatementNode node) {
        Type expression = Type.VOID;
        if(printDebugger){
            System.out.println("We get in the If statement");
        }
        
        for (int i = 0; i < node.getExpressions().size(); i++) {

            if(printDebugger){
                System.out.println("We get in first for loop");
               System.out.println("Number of expressions:"+node.getExpressions().size()); 
            }
            expression = getType(node.getExpressions().get(i).getNode());
            if (expression != Type.BOOLEAN) {
                errorHandler("If statements expression must resolve to bool expression, and this resolve to Type:"
                        + expression);
            }
        }
        for (int i = 0; i < node.getBlocks().size(); i++) {
            if(printDebugger){
                System.out.println("We get in 2 for loop");
                System.out.println("Number of blocks:"+node.getBlocks().size()); 
            }
            HashMap<String, Type> localETable = new HashMap<>();
            scopes.add(localETable);
            visitBlockNode(node.getBlocks().get(i));

            scopes.remove(localETable);
        }
    }

    public void visitWhileLoop(WhileLoopNode node) {
        Type toCheck = getType((node.getExpression()).getNode());
        if (toCheck != Type.BOOLEAN) {
            errorHandler("While loop expresion must resolve to bool expresion, and this resolve to Type:" + toCheck);

        }
        HashMap<String, Type> localTable = new HashMap<>();
        scopes.add(localTable);
        visitBlockNode(node.getBlock());
        System.out.println(localTable);
        scopes.remove(localTable);
    }

    public void visitFunctionDefinition(FunctionDefinitionNode node) {
    System.out.println(node+"We get in here");
        if (!typeOfReturnFunction.containsKey(node.toString()) && !functionParameters.containsKey(node.toString())) {
            typeOfReturnFunction.put(node.getIdentifier().toString(), getType(node.getReturnType())); // Key identifier // Type
            functionParameters.put(node.getIdentifier().toString(), new ArrayList<>());

            HashMap<String, Type> localTable = new HashMap<>();
            scopes.add(localTable);
            List<ParameterNode> parameters = node.getArguments().getParameters();
            for (ParameterNode parameter : parameters) {
                String identifierParameter = String.valueOf((parameter.getIdentifier()));  // int carl
                Type arguementType = getType(parameter.getType());
                if(localTable.containsKey(identifierParameter) ){
                    errorHandler("The variable:"+identifierParameter+" Already exist in this function parameters.");
                } else {
                    scopes.getLast().put(identifierParameter, arguementType);
                    functionParameters.get(node.getIdentifier().toString()).add(arguementType);// Den tilføjer typen til listen.  Fn x  tilføjet int x, int x, int x
                }
            }
            currentActiveFunction = node.getIdentifier().toString();
            hasReturnStatement = false;
            visitBlockNode(node.getBlock());// Alle statement i fn. En af dem er returnStatement
            currentActiveFunction = "";
            if(!hasReturnStatement && Type.VOID != getType(node.getReturnType())){
                errorHandler("Missing return statement in function declartion:"+node.getIdentifier().toString());
            }
            hasReturnStatement = false;
            scopes.remove(localTable);
        } else {
            errorHandler("function " + node.getIdentifier() + " already exists");
        }
    }
        /*
    * Vi skal sammenligne argumenterne med paremetrene.
    * Vi skal tjekke at der lige mange.
    * Der mangler fx argumenter der er ikke nok.  eller der for mange.
    * Vi skal sige hvis arguemtnet er en forkert type.
    * */
    public void visitFunctionCall(FunctionCallNode node) {
        if(!Objects.equals(node.getFunctionName().toString(), "print")) {
            HashMap<String, Type> localETable = new HashMap<>();
            scopes.add(localETable);
            if (typeOfReturnFunction.containsKey(node.getFunctionName().toString())) {
                List<Type> expectedParameterTypes = functionParameters.get(node.getFunctionName().toString());

                int expectedArgumentsSize = expectedParameterTypes.size();
                int actualArgumentsSize = node.getArguments().size();
                if(expectedArgumentsSize != actualArgumentsSize){
                    errorHandler("Function Expected:"+expectedArgumentsSize+" Arguments but got :"+actualArgumentsSize+" Arguments");
                }

                for (int i = 0; i < expectedArgumentsSize; i++) {
                    if (expectedParameterTypes.get(i) != getType(node.getArgument(i))) {
                        errorHandler("Function Expected type: " + expectedParameterTypes.get(i) + ", as "+ i + " Argument but got " + getType(node.getArgument(i)));
                    }
                }

            } else {
                errorHandler("The function :" + node.getFunctionName().toString() + " May not exist or be out of scope.");
            }
        }
    }

    public void visitBlockNode(BlockNode node) {
        for (AstNode statement : node.getStatements()) {
            visitStatements((StatementNode) statement);
        }
    }

    public Type getType(Object node) {
        Type type = Type.VOID;

        if (node instanceof IdentifierNode) { // true   bool node.    true identifier node
            if ("true".equals(node.toString()) || "false".equals(node.toString())) {
                type = Type.BOOLEAN;
            } else {
                type = getVariable((IdentifierNode) node); // Vis x giv mig x value
            }

        } else if (node instanceof BinaryOperatorNode) {
            type = binaryOperatorTypeCheck((BinaryOperatorNode) node);
        } else if (node instanceof RelationsAndLogicalOperatorNode) {
            type = relationOperatorTypeCheck((RelationsAndLogicalOperatorNode) node);
        } else if (node instanceof  FunctionCallNode){
            String variableName = ((FunctionCallNode) node).getFunctionName().toString();
            type = typeOfReturnFunction.get(variableName);
        }

        else if (node instanceof IntNode) {
            type = Type.INT;
        } else if (node instanceof FloatNode) {
            type = Type.FLOAT;
        } else if (node instanceof BoolNode) {
            type = Type.BOOLEAN;
        } else if (node instanceof ExpressionNode) {

        } else if (node instanceof StringNode) {
            type = Type.STRING;
        } else if (node instanceof TypeNode) {
            String typeAsString = ((TypeNode) node).getType();
            switch (typeAsString) {
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
                return Type.STRING;
            } else if (node instanceof Integer) { // Directly handling raw Integers
                return Type.INT;
            } else if (node instanceof Float) { // Directly handling raw Floats
                return Type.FLOAT;
            } else if (node instanceof Boolean) { // Directly handling raw Booleans
                return Type.BOOLEAN;
            }

        } else {
            errorHandler("The node we get failed to handle:" + node.getClass());
        }
        return type;
    }

}