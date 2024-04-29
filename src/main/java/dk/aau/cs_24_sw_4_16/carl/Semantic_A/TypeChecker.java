package dk.aau.cs_24_sw_4_16.carl.Semantic_A;

import dk.aau.cs_24_sw_4_16.carl.CstToAst.*;

import java.util.*;

public class TypeChecker {

    HashMap <String, Type> TypeOfReturnFunction;
    HashMap <String, List<Type>> FunctionParameters;

    //    HashMap<String, FunctionDefinitionNode> fTable; // function table, identifier(x) og node

    HashMap<String, Type> ETable;// variable table, identifier(x) og node(int)
    Stack<HashMap<String, Type>> scopes; // scope table, variable identifier(x) og node
    Deque<Integer> activeScope;// Hvilket scope vi er i nu
    int errorNumber = 1;
    Boolean print_debugger = false;
    Boolean has_return_statement = false;
    String current_active_function ="";

    public TypeChecker() {
//        fTable = new HashMap<>();
        ETable = new HashMap<>();
        scopes = new Stack<>();
        activeScope = new ArrayDeque<>();
        activeScope.push(0);
        scopes.add(ETable);
        TypeOfReturnFunction = new HashMap<>();
        FunctionParameters = new HashMap<>();
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
        if(print_debugger){
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
            has_return_statement = true;

            System.out.println("we get in return statement"+has_return_statement);
            visitReturnNode((ReturnStatementNode) node.getNode());

        }
    }

    public void error_handler(String errorMessage){
        System.err.println("Error " + errorNumber);
        errorNumber = errorNumber + 1;
        System.err.println(errorMessage);
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
        
        error_handler("Wrong types for relation operation:" + left_type + ":" + left + " And:" + right + ":" + right_Type);

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
        error_handler("Wrong types for binnary operation:" + left_type + ":" + left + " And:" + right + ":" + right_Type);
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
                    error_handler("Tryied to asssign Type:" + assignmentType + " to the variable:" + identifier
                                                              + " that has the type:" + variableType
                                                              + " And that is hella iligal");

                }
            } else {
                throw new RuntimeException("variable " + node.getIdentifier() + " already exists");
            }
        } catch (Exception e) {
            error_handler(e.getMessage());
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
      Type activeFunction = TypeOfReturnFunction.get(current_active_function);
      if(Objects.equals(current_active_function, "")){
        error_handler("You have made return statement outside a function THAT IS illigal");
      }
      if(returnType != activeFunction){
          error_handler("The return type " + returnType + " Does not match the return statement of the function " + activeFunction.getTypeName());
      }
    }

    public void visitAssignNode(AssignmentNode node) {
        if(print_debugger){

        }
        boolean found_identifier = false;
        for (HashMap<String, Type> ETable : scopes) {

             if(true){
                 System.out.println("Identifier"+node.getIdentifier().toString());
                 System.out.println("Etable:"+ETable);
             }
             if (ETable.containsKey(node.getIdentifier().toString())) {// hvis x er i scope
                 found_identifier = true;
                // tjekke om det er lovligt.
                // hent gamle type og nye type.
                Type oldType = getType(node.getIdentifier());
                String identifier = node.getIdentifier().toString();

                Type rightType = getType(node.getValue());
               
                // tjekke om det er lovligt.
                if (oldType != rightType) {
                    error_handler("Tryied to asssign Type:" + rightType + " to the variable:" + identifier
                                                              + " that has the type:" + oldType
                                                              + " And that is hella iligal");

                }
             } else {
                error_handler("Variable '" + node.getIdentifier() + "' has not been defined yet.");
             }
             if (found_identifier){
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
        if(print_debugger){
            System.out.println("We get in the If statement");
        }
        
        for (int i = 0; i < node.getExpressions().size(); i++) {

            if(print_debugger){
                System.out.println("We get in first for loop");
               System.out.println("Number of expressions:"+node.getExpressions().size()); 
            }
            expression = getType(node.getExpressions().get(i).getNode());
            if (expression != Type.BOOLEAN) {
                error_handler("If statements expression must resolve to bool expression, and this resolve to Type:"
                        + expression);
            }
        }
        for (int i = 0; i < node.getBlocks().size(); i++) {
            if(print_debugger){
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
            error_handler("While loop expresion must resolve to bool expresion, and this resolve to Type:" + toCheck);

        }
        HashMap<String, Type> localTable = new HashMap<>();
        scopes.add(localTable);
        visitBlockNode(node.getBlock());
        System.out.println(localTable);
        scopes.remove(localTable);
    }

    public void visitFunctionDefinition(FunctionDefinitionNode node) {
    System.out.println(node+"We get in here");
        if (!TypeOfReturnFunction.containsKey(node.toString()) && !FunctionParameters.containsKey(node.toString())) {
            TypeOfReturnFunction.put(node.getIdentifier().toString(), getType(node.getReturnType())); // Key identifier // Type
            FunctionParameters.put(node.getIdentifier().toString(), new ArrayList<>());

            HashMap<String, Type> localTable = new HashMap<>();
            scopes.add(localTable);
            List<ParameterNode> parameters = node.getArguments().getParameters();
            for (ParameterNode parameter : parameters) {
                String identifierParameter = String.valueOf((parameter.getIdentifier()));  // int carl
                Type arguementType = getType(parameter.getType());
                if(localTable.containsKey(identifierParameter) ){
                    error_handler("The variable:"+identifierParameter+" Already exist in this function parameters.");
                } else {
                    scopes.getLast().put(identifierParameter, arguementType);
                    FunctionParameters.get(node.getIdentifier().toString()).add(arguementType);// Den tilføjer typen til listen.  Fn x  tilføjet int x, int x, int x
                }
            }
            current_active_function = node.getIdentifier().toString();
            has_return_statement = false;
            visitBlockNode(node.getBlock());// Alle statement i fn. En af dem er returnStatement
            current_active_function = "";
            if(!has_return_statement){
                error_handler("Missing return statement in function declartion:"+node.getIdentifier().toString());
            }
            has_return_statement = false;
            scopes.remove(localTable);
        } else {
            error_handler("function " + node.getIdentifier() + " already exists");
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
            if (TypeOfReturnFunction.containsKey(node.getFunctionName().toString())) {
                List<Type> expected_Parameter_Types = FunctionParameters.get(node.getFunctionName().toString());

                int expected_arguments_size = expected_Parameter_Types.size();
                int actual_arguments_size = node.getArguments().size();
                if(expected_arguments_size != actual_arguments_size){
                    error_handler("Function Expected:"+expected_arguments_size+" Arguments but got :"+actual_arguments_size+" Arguments");
                }

                for (int i = 0; i < expected_arguments_size; i++) {
                    if (expected_Parameter_Types.get(i) != getType(node.getArgument(i))) {
                        error_handler("Function Expected type: " + expected_Parameter_Types.get(i) + ", as "+ i + " Argument but got " + getType(node.getArgument(i)));
                    }
                }

            } else {
                error_handler("The function :" + node.getFunctionName().toString() + " May not exist or be out of scope.");
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
            type = binaryoperator_type_check((BinaryOperatorNode) node);
        } else if (node instanceof RelationsAndLogicalOperatorNode) {
            type = relationOperator_Type_check((RelationsAndLogicalOperatorNode) node);
        } else if (node instanceof  FunctionCallNode){
            String variable_name = ((FunctionCallNode) node).getFunctionName().toString();
            type = TypeOfReturnFunction.get(variable_name);
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
            String type_fuck_me_why_did_we_save_types_as_String = ((TypeNode) node).getType();
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
                return Type.STRING;
            } else if (node instanceof Integer) { // Directly handling raw Integers
                return Type.INT;
            } else if (node instanceof Float) { // Directly handling raw Floats
                return Type.FLOAT;
            } else if (node instanceof Boolean) { // Directly handling raw Booleans
                return Type.BOOLEAN;
            }

        } else {
            error_handler("The node we get failed to handle:" + node.getClass());
        }
        return type;
    }

}
