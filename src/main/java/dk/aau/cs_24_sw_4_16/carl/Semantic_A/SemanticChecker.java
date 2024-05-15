package dk.aau.cs_24_sw_4_16.carl.Semantic_A;

import dk.aau.cs_24_sw_4_16.carl.CstToAst.*;

import java.util.*;

public class SemanticChecker {

    HashMap<String, Type> typeOfReturnFunction;
    HashMap<String, List<Type>> functionParameters;
    List<String> listOfInbuiltFunctions = new ArrayList<>(Arrays.asList("print", "generateGrid", "generateRooms",
            "generateCorridors", "generateSpawns", "printMap", "generatePrint", "writeToFile", "overlap",
            "tileInformationStringBuilder", "setSeed"));
    HashMap<String, HashMap<String, Type>> structVariablesTable;
    HashMap<String, Type> structTypes;
    HashMap<String, Type> eTable;// variable table, identifier(x) og node(int)
    Stack<HashMap<String, Type>> scopes; // scope table, variable identifier(x) og node
    Deque<Integer> activeScope;// Hvilket scope vi er i nu
    int errorNumber = 1;
    Boolean hasReturnStatement = false;
    String currentActiveFunction = "";
    public Boolean thereWasAnError = false;
    String currentIdentifierCheck = "";
    Boolean struct_variable_declarion_failed = false;

    public SemanticChecker() {
        eTable = new HashMap<>();
        scopes = new Stack<>();
        activeScope = new ArrayDeque<>();
        activeScope.push(0);
        scopes.add(eTable);
        typeOfReturnFunction = new HashMap<>();
        functionParameters = new HashMap<>();
        structVariablesTable = new HashMap<>();
        structTypes = new HashMap<>();
    }

    /**
     * Method for visiting AstNode for program nodes.
     *
     * @param node The AstNode to be visited, which can be of any type that uses AstNode class.
     */
    public void visitor(AstNode node) {
        if (node instanceof ProgramNode) {
            visitProgramNode((ProgramNode) node);
        }
    }

    /**
     * Method for visiting AstNode for statement nodes.
     * Loops through all statement nodes
     *
     * @param node The AstNode to be visited, which is of type ProgramNode.
     */
    public void visitProgramNode(ProgramNode node) {
        for (AstNode statement : node.getStatements()) {
            visitStatements((StatementNode) statement);
        }
    }

    /**
     * Method for visiting correct node of statements.
     * Checks which instance type node is, and type cast to the correct node type before visiting.
     *
     * @param node The AstNode to be visited, which is of type Statement node.
     */
    public void visitStatements(StatementNode node) {

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
        if (node.getNode() instanceof FunctionCallNode) {
            visitFunctionCall((FunctionCallNode) node.getNode());
        }
        if (node.getNode() instanceof ReturnStatementNode) {
            hasReturnStatement = true;
            visitReturnNode((ReturnStatementNode) node.getNode());
        }
        if (node.getNode() instanceof StructureDefinitionNode) {
            visitStruct((StructureDefinitionNode) node.getNode());
        }
        if (node.getNode() instanceof ArrayDefinitionNode) {
            visistarrayDekleration((ArrayDefinitionNode) node.getNode());
        }
        if (node.getNode() instanceof ArrayAssignmentNode) {
            visistArrayAssignment((ArrayAssignmentNode) node.getNode());
        }
        if (node.getNode() instanceof PropertyAssignmentNode) {
            visitPropertyAssignment((PropertyAssignmentNode) node.getNode());
        }
    }

    /**
     * Method error handling.
     * Returns error number and text.
     */
    public void errorHandler(String errorMessage) {
        thereWasAnError = true;
        System.err.println("Error: " + errorNumber);
        errorNumber = errorNumber + 1;
        System.err.println(errorMessage);
    }

    /**
     * Method for looking for identifier of array in scope, and returns boolean if found/!found.
     * @param node The identifier node in string format.
     */
    private boolean checkIdentifierInScopes(String node) {
        boolean found = false;
        for (int i = scopes.size() - 1; i >= 0; i--) {
            if (scopes.get(i).containsKey(node)) {
                found = true;
                break;
            }
        }
        return found;
    }

    /**
     * Private helper method for accessing array indices.
     * Loops through all indices, and checks if the type matches INT type.
     * @param identifier is the array name.
     * @param indicesInNode is the indices of the array.
     * Throws error if wrong type is assigned. This error here is only thrown for visistArrayAccesNodeFn method.
     */
    private void checkArrayIndexTypes(String identifier, List<AstNode> indicesInNode) {
        Type allowedAccesTypesForArrays = Type.INT;
        Type sizeType = Type.UNKNOWN;

        for (int i = 0; i < indicesInNode.size(); i++) {
            AstNode astNode = indicesInNode.get(i);
            sizeType = getType(astNode);
            if (sizeType != allowedAccesTypesForArrays) {
                errorHandler("Tried to assign the array: " + identifier + " but access value: " + i
                        + " is of type: " + sizeType + " and should be INT");
            }
        }
    }

    /**
     * Method for checking type when accessing array indices.
     * Looks for array in scope.
     * Checks if correct type is being assigned to array indices.
     * Throws error if wrong type is assigned.
     */
    public void visistArrayAccesNodeFn(ArrayAccessNode node) {
        boolean found = checkIdentifierInScopes(node.getIdentifier().toString());
        if (found) {
            checkArrayIndexTypes(node.getIdentifier().toString(), node.getIndices());
        }
    }

    /**
     * Method for checking type when assigning value to array indices.
     * Looks for array in scope.
     * Checks if correct type is being assigned to array.
     * Throws error if wrong type is assigned.
     */
    public void visistArrayAssignment(ArrayAssignmentNode node) {
        String identifier = node.getIdentifier().toString();
        boolean found = false;
        Type arrayType = Type.UNKNOWN;

        for (int i = scopes.size() - 1; 0 <= i; i--) {
            if (scopes.get(i).containsKey(identifier)) {
                found = true;
                arrayType = scopes.get(i).get(identifier);
            }
        }

        if (found) {
            checkArrayIndexTypes(identifier, node.getIndices());

            // Tjek venstre mod højre
            Type assignType = getType(node.getValue());
            if (arrayType != assignType) {
                errorHandler("Tried to assign the type: " + assignType + " to the array: " + identifier
                        + " that has the type: " + arrayType);
            }

        } else {
            errorHandler("Array: " + identifier + " Does not exist ");
        }
    }

    /**
     * Method for checking type when declaring array.
     * Looks for array in scope.
     * Checks if correct type is being assigned to array.
     * Throws error if wrong type is assigned.
     */
    public void visistarrayDekleration(ArrayDefinitionNode node) {
        boolean found = checkIdentifierInScopes(node.getIdentifier().toString());

        Type arrayType = getType(node.getType());
        boolean validTypes = true;
        Type sizeType = Type.UNKNOWN;
        int arguementNumber = 0;
        List<AstNode> sizes = node.getSizes();
        for (int i = 0; i < sizes.size(); i++) {
            AstNode astNode = sizes.get(i);
            sizeType = getType(astNode);
            if (sizeType != Type.INT) {
                arguementNumber = i;
                validTypes = false;
                break;
            }
        }
        if (validTypes) {
            scopes.getLast().put(node.getIdentifier().toString(), arrayType);

        } else {
            errorHandler("Tried to declare the array: " + node.getIdentifier().toString() + " but argument: " + arguementNumber
                    + " is of type: " + sizeType + " and should be: " + arrayType);
        }

        if (found) {
            errorHandler("Identifier: " + node.getIdentifier().toString() + " is already used");
        }

    }

    /**
     * Method for checking type when assigning value to properties.
     * Checks if assigned type is not the same as current type.
     * Throws error if wrong type is assigned.
     */
    public void visitPropertyAssignment(PropertyAssignmentNode node) {
        Type oldType = visitPropertyAccessNode(node.getPropertyAccessNode());
        Type newType = getType(node.getValue());
        if (oldType != newType) {
            errorHandler("Type " + oldType + " does not match " + newType);
        }
    }

    /**
     * Method for checking type when accessing properties.
     * Checks if struct for property access does not exist or if property type is not get or size.
     * Checks if struct property access is less than 3 arguments or if property type is not get or size.
     * Throws error if any of these are true and returns an unknown type.
     * Else returns the the type of the property.
     */
    public Type visitPropertyAccessNode(PropertyAccessNode node) {
        List<String> validPropertyAccess = new ArrayList<>(Arrays.asList("size", "get"));

        String firstIdentifier = node.getIdentifiers().get(0).toString();
        if (!structVariablesTable.containsKey(firstIdentifier) && !validPropertyAccess.contains(firstIdentifier)) {
            errorHandler("Could not find the struct variable: " + firstIdentifier);
        }

        HashMap<String, Type> listOfIdentifiers = structVariablesTable.get(firstIdentifier);

        if (!validPropertyAccess.contains(firstIdentifier) && node.getIdentifiers().size() <= 1
                || !listOfIdentifiers.containsKey(node.getIdentifiers().get(1).toString())) {
            errorHandler("You need 3 arguments");
        }

        if (structVariablesTable.containsKey(firstIdentifier) && node.getIdentifiers().size() >= 2
                && listOfIdentifiers.containsKey(node.getIdentifiers().get(1).toString())) {
            Type identifierType = listOfIdentifiers.get(node.getIdentifiers().get(1).toString());
            return getType(identifierType.getTypeName());
        }
        return Type.UNKNOWN;
    }

    /**
     * Method for checking types of operands in relation and logical operations.
     * Checks type of left and right operands.
     * Returns BOOLEAN if both operands are of compatible types.
     * Throws an error if the operand types are incompatible and returns unknown.
     */
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
        } else if (leftType == Type.STRING && rightType == Type.STRING) {
            return Type.BOOLEAN;
        }


        errorHandler("Wrong types for relation operation: " + leftType + ": " + left + " And: " + right + ": " + rightType);

        return Type.UNKNOWN;
    }

    /**
     * Method for checking types of operands in binary operations.
     * Checks type of left and right operands.
     * Returns operand type if both operands are of compatible types.
     * Throws an error if the operand types are incompatible and returns unknown.
     */
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
        } else if (leftType == Type.INT && rightType == Type.FLOAT) {
            return Type.FLOAT;
        } else if (leftType == Type.FLOAT && rightType == Type.INT) {
            return Type.FLOAT;
        }

        errorHandler("Wrong types for binary operation: " + leftType + ": " + left + " And: " + right + ": " + rightType);
        return Type.UNKNOWN;

    }

    /**
     * Method for checking types in variable declarations.
     * Checks if the variable identifier already exists in the scope.
     * If not, it compares the variable type with the assigned value type.
     * If types match, adds the variable to the current scope.
     * Throws an error if types do not match or if the variable already exists.
     */
    private void visitVariableDeclaration(VariableDeclarationNode node) {

        try {
            boolean found = scopes.getLast().containsKey(node.getIdentifier().toString());

            if (!found) {// Vi skal tjekke variable type mod det den type vi assigner til variablen.

                String identifier = node.getIdentifier().toString();

                Type variableType = getType(node.getType()); // Left side type

                AstNode ass = node.getValue(); // THis is right side should be a node
                Type assignmentType = getType(ass); // This should give right side type

                if (variableType == assignmentType) {
                    scopes.getLast().put(node.getIdentifier().toString(), variableType);

                } else {
                    errorHandler("Tried to assign Type: " + assignmentType + " to the variable: " + identifier
                            + " that has the type: " + variableType);

                }
            } else {
                throw new RuntimeException("Variable: " + node.getIdentifier() + " already exists in the scope");
            }
        } catch (Exception e) {
            errorHandler(e.getMessage());
        }

    }

    /**
     * Method for getting the type of identifier.
     * Checks if the identifier exists in scope in the last index, and returns type if found.
     * Otherwise, loops through the whole scope, and returns type if found.
     * Throws an error if the variable is not found in any scope, and returns unknown.
     */
    public Type getVariable(IdentifierNode node) {

        if (scopes.getLast().containsKey(node.getIdentifier())) {
            currentIdentifierCheck = node.getIdentifier();
            return scopes.getLast().get(node.getIdentifier());
        }

        for (int i = scopes.size() - 1; i >= 0; i--) {
            if (scopes.get(i).containsKey(node.getIdentifier())) {
                currentIdentifierCheck = node.getIdentifier();
                return scopes.get(i).get(node.getIdentifier());
            }
        }
        errorHandler("Could not find the variable " + node.getIdentifier());
        return Type.UNKNOWN;
    }

    /**
     * Method for checking return statements.
     * Gets the type from the return value.
     * Checks if the return statement is within a function.
     * Compares the return type with the return type of the current function, and throws an error if not the same.
     */
    public void visitReturnNode(ReturnStatementNode node) {
        Type returnType = getType(node.getReturnValue());

        if (Objects.equals(currentActiveFunction, "")) {
            errorHandler("Return statement should be in a function");
        } else {
            Type activeFunction = typeOfReturnFunction.get(currentActiveFunction);
            if (returnType != activeFunction) {
                errorHandler("The return type " + returnType + " Does not match the return statement of the function "
                        + activeFunction.getTypeName());
            }
        }

    }

    /**
     * Method for checking assignment types.
     * Searches for the identifier in the scopes.
     * Compares the type of the variable with the type of the assigned value.
     * Throws an error if the types do not match or if the variable has not been defined.
     */
    public void visitAssignNode(AssignmentNode node) {

        boolean foundIdentifier = false;

        for (int i = scopes.size() - 1; 0 <= i; i--) {

            HashMap<String, Type> ETable = scopes.get(i);


            if (ETable.containsKey(node.getIdentifier().toString())) {// hvis x er i scope
                foundIdentifier = true;
                Type oldType = getType(node.getIdentifier());
                String identifier = node.getIdentifier().toString();

                Type rightType = getType(node.getValue());

                if (oldType != rightType) {
                    errorHandler("Tried to assign Type: " + rightType + " to the variable: " + identifier
                            + " that has the type: " + oldType);

                }
            }
            if (foundIdentifier) {
                break;
            }
        }
        if (!foundIdentifier) {
            errorHandler("Variable " + node.getIdentifier() + " has not been defined yet.");
        }
    }

    /**
     * Method for checking if statements.
     * Checks all the expressions type in the if statement, and are of boolean type.
     * Throws an error if any expression does not resolve to a boolean type.
     * Creates a local symbol table the blocks in the if statement.
     * Visits each block node within the if statement, and checks the type within the blocks.
     * Removes the local symbol table after visiting each block node.
     */
    public void visitIfStatement(IfStatementNode node) {
        Type expression = Type.VOID;

        for (int i = 0; i < node.getExpressions().size(); i++) {
            expression = getType(node.getExpressions().get(i).getNode());
            if (expression != Type.BOOLEAN) {
                errorHandler("If statements expression must resolve to bool expression, and cannot be of type: "
                        + expression);
            }
        }
        for (int i = 0; i < node.getBlocks().size(); i++) {
            HashMap<String, Type> localETable = new HashMap<>();
            scopes.add(localETable);
            visitBlockNode(node.getBlocks().get(i));

            scopes.remove(localETable);
        }
    }

    /**
     * Method for checking while statements.
     * Checks that the expression type in the while statement are of boolean type.
     * Throws an error if expression does not resolve to a boolean type.
     * Creates a local symbol table for the block in the while statement.
     * Visits block node within the while statement, and checks the type within the block.
     * Removes the local symbol table after visiting the block node.
     */
    public void visitWhileLoop(WhileLoopNode node) {
        Type toCheck = getType((node.getExpression()).getNode());
        if (toCheck != Type.BOOLEAN) {
            errorHandler("While loop expression must resolve to bool expression, and can not be of type: " + toCheck);

        }
        HashMap<String, Type> localTable = new HashMap<>();
        scopes.add(localTable);
        visitBlockNode(node.getBlock());

        scopes.remove(localTable);
    }

    /**
     * Method for checking type in function definitions.
     * Checks if the function is not an inbuilt function.
     * Checks if the function has not been previously defined.
     * Adds the function return type and parameters its own environment.
     * Creates a local symbol table for the function.
     * Adds function parameters to the local symbol table.
     * Sets the current active function used for when checking for return type.
     * Checks the types in the block node within the function.
     * Checks if the function has a return statement if its return type is not VOID.
     * Removes the local symbol table after checking block node.
     */
    public void visitFunctionDefinition(FunctionDefinitionNode node) {
        if (!listOfInbuiltFunctions.contains(node.getIdentifier().toString())) {
            if (!typeOfReturnFunction.containsKey(node.toString())
                    && !functionParameters.containsKey(node.toString())) {
                typeOfReturnFunction.put(node.getIdentifier().toString(), getType(node.getReturnType()));

                functionParameters.put(node.getIdentifier().toString(), new ArrayList<>());

                HashMap<String, Type> localTable = new HashMap<>();
                scopes.add(localTable);
                List<ParameterNode> parameters = node.getArguments().getParameters();
                for (ParameterNode parameter : parameters) {
                    String identifierParameter = String.valueOf((parameter.getIdentifier())); // int carl
                    Type arguementType = getType(parameter.getType());
                    if (localTable.containsKey(identifierParameter)) {
                        errorHandler(
                                "The variable: " + identifierParameter + " Already exist in this function parameters.");
                    } else {
                        scopes.getLast().put(identifierParameter, arguementType);
                        functionParameters.get(node.getIdentifier().toString()).add(arguementType);// Den tilføjer typen
                        // til listen. Fn x
                        // tilføjet int x,
                        // int x, int x
                    }
                }
                currentActiveFunction = node.getIdentifier().toString();
                hasReturnStatement = false;
                visitBlockNode(node.getBlock());// Alle statement i fn. En af dem er returnStatement
                currentActiveFunction = "";
                if (!hasReturnStatement && Type.VOID != getType(node.getReturnType())) {

                    errorHandler("Missing return statement in function declaration: " + node.getIdentifier().toString());
                }
                hasReturnStatement = false;
                scopes.remove(localTable);
            } else {
                errorHandler("function " + node.getIdentifier() + " already exists");
            }
        } else {
            errorHandler("You may not redeclare a inbuilt function. The function you tried to redeclare is: "
                    + node.getIdentifier().toString());
        }
    }

    /**
     * Method for checking type in function calls.
     * Checks if the called function is not an inbuilt function.
     * Checks if the called function has been previously defined.
     * Compares the expected parameter types with the actual arguments provided.
     * Throws an error if the number of arguments does not match the expected number.
     * Throws an error if the type of any argument does not match the expected type.
     */
    public void visitFunctionCall(FunctionCallNode node) {

        if (!listOfInbuiltFunctions.contains(node.getFunctionName().toString())) {
//No need to have local symbol tabel if we do not use it for anything
//            HashMap<String, Type> localETable = new HashMap<>();
//            scopes.add(localETable);
            if (typeOfReturnFunction.containsKey(node.getFunctionName().toString())) {
                List<Type> expectedParameterTypes = functionParameters.get(node.getFunctionName().toString());

                int expectedArgumentsSize = expectedParameterTypes.size();
                int actualArgumentsSize = node.getArguments().size();
                if (expectedArgumentsSize != actualArgumentsSize) {
                    errorHandler("Function Expected: " + expectedArgumentsSize + " Arguments but got: "
                            + actualArgumentsSize + " Arguments");
                }
                for (int i = 0; i < expectedArgumentsSize; i++) {
                    if (expectedParameterTypes.get(i) != getType(node.getArgument(i))) {
                        errorHandler("Function Expected type: " + expectedParameterTypes.get(i) + ", as " + i
                                + " Argument but got " + getType(node.getArgument(i)));
                    }
                }

            } else {
                errorHandler(
                        "The function :" + node.getFunctionName().toString() + " May not exist or be out of scope.");
            }
        }

    }

    /**
     * Method for checking types in struct definitions.
     * Checks if the struct identifier already exists in the structVariablesTable.
     * If not, add the struct to the structVariablesTable.
     * Creates a local symbol table for struct.
     * Checks type of each variable declaration in the struct, and adds it if correctly defined.
     * Removes the local symbol table after visiting variable declarations.
     */
    public void visitStruct(StructureDefinitionNode node) {
        String identifier = node.getIdentifier().toString();
        if (!structVariablesTable.containsKey(identifier)) {

            Type structType = getType(node.getType());

            HashMap<String, Type> localETable = new HashMap<>();

            scopes.add(localETable);

            List<VariableDeclarationNode> declarations = node.getVariableDeclarations();
            struct_variable_declarion_failed = false;
            for (VariableDeclarationNode declaration : declarations) {
                visitVariableDeclarationforStructs(declaration);
            }

                structTypes.put(identifier, structType);
                structVariablesTable.put(identifier, localETable);

            struct_variable_declarion_failed = false;
            scopes.remove(localETable);

        } else {
            errorHandler("Struct " + node.getIdentifier().toString() + " already exists");
        }
    }

    /**
     * Method for checking types variable declarations within structures.
     * Checks if the variable identifier already exists in the current scope.
     * If not found, compares the variable type with the assigned value type.
     * Adds the variable to the current scope if types match.
     * Sets struct_variable_declarion_failed flag to true and throws an error if types do not match.
     * Throws an error if the variable already exists in the current scope.
     */
    private void visitVariableDeclarationforStructs(VariableDeclarationNode node) {
        try {
            boolean found = scopes.getLast().containsKey(node.getIdentifier().toString());

            if (!found) {

                String identifier = node.getIdentifier().toString();

                Type variableType = getType(node.getType()); // Left side type

                AstNode ass = node.getValue();
                Type assignmentType = getType(ass);

                if (variableType == assignmentType) {
                    scopes.getLast().put(node.getIdentifier().toString(), variableType);

                } else {
                    struct_variable_declarion_failed = true;
                    errorHandler("Tried to assign Type: " + assignmentType + " to the variable: " + identifier
                            + " that has the type: " + variableType);

                }
            } else {
                throw new RuntimeException("Variable " + node.getIdentifier() + " already exists in struct");
            }
        } catch (Exception e) {
            struct_variable_declarion_failed = true;
            errorHandler(e.getMessage());
        }

    }

    /**
     * Method for checking the block type to visit.
     * Loops through the block node, and visits its respective statement.
     */
    public void visitBlockNode(BlockNode node) {
        for (AstNode statement : node.getStatements()) {
            visitStatements((StatementNode) statement);
        }
    }

    /**
     * Method for checking method call type.
     * Checks if the property contains size, and returns an int type if true.
     * Checks if the property contains get, and returns an unknown type if true
     */
    public Type resolveMethodCallType(MethodCallNode node) {
//        System.out.println(node.getValue());
//        List<String> structNames = new ArrayList<>();
//        for (String structName : structTypes.keySet()) {
//            structNames.add(structName);
//            System.out.println(structNames);
//        }
//        int value = (Integer.parseInt(node.getValue().toString().replace("[", "").replace("]", "")));
//        String name = structNames.get(value);
//        if (structTypes.containsKey(name)) {
//
//        }
        return Type.INT;
    }

    /**
     * Method to determine the type of a given node.
     * Returns the type of the node.
     */
    public Type getType(Object node) {
        Type type = Type.UNKNOWN;

        // Check the type of the node and assign appropriate type
        if (node instanceof IdentifierNode) { // true bool node. true identifier node
            if ("true".equals(node.toString()) || "false".equals(node.toString())) {
                type = Type.BOOLEAN;
            } else {
                type = getVariable((IdentifierNode) node); // Vis x giv mig x value
            }

        } else if (node instanceof ArrayAccessNode) {
            visistArrayAccesNodeFn(((ArrayAccessNode) node));

            type = getType(((ArrayAccessNode) node).getIdentifier());
        } else if (node instanceof PropertyAccessNode) {
            type = visitPropertyAccessNode((PropertyAccessNode) node);
        } else if (node instanceof MethodCallNode methodCallNode) {
            type = resolveMethodCallType(methodCallNode);
        } else if (node instanceof BinaryOperatorNode) {
            type = binaryOperatorTypeCheck((BinaryOperatorNode) node);
        } else if (node instanceof RelationsAndLogicalOperatorNode) {
            type = relationOperatorTypeCheck((RelationsAndLogicalOperatorNode) node);
        } else if (node instanceof FunctionCallNode) {
            String variableName = ((FunctionCallNode) node).getFunctionName().toString();
            type = typeOfReturnFunction.get(variableName);
        } else if (node instanceof IntNode) {
            type = Type.INT;
        } else if (node instanceof FloatNode) {
            type = Type.FLOAT;
        } else if (node instanceof BoolNode) {
            type = Type.BOOLEAN;
        } else if (node instanceof ExpressionNode) {
            type = getType(((ExpressionNode) node).getNode());
        } else if (node instanceof StringNode) {
            type = Type.STRING;
        } else if (node instanceof TypeNode) {
            // If the node is a type node, retrieve its type from the node
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
                case "void":
                    return Type.VOID;
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

        } else if (node instanceof String) {
            String typeAsString = ((String) node);
            switch (typeAsString) {
                case "int":
                    return Type.INT;
                case "string":
                    return Type.STRING;
                case "bool":
                    return Type.BOOLEAN;
                case "float":
                    return Type.FLOAT;
                case "enemy":
                    return Type.ENEMY;
                case "floor":
                    return Type.FLOOR;
                case "wall":
                    return Type.WALL;
                case "room":
                    return Type.ROOM;
                default:
                    break;
            }
        } else {
            errorHandler("The node we get failed to handle:" + node.getClass());
        }
        return type;
    }

}
