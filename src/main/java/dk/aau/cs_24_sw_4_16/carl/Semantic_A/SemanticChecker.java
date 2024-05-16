package dk.aau.cs_24_sw_4_16.carl.Semantic_A;

import dk.aau.cs_24_sw_4_16.carl.CstToAst.*;

import java.util.*;

import static java.lang.Integer.parseInt;

public class SemanticChecker {

    HashMap<String, Type> typeOfReturnFunction;
    HashMap<String, List<Type>> functionParameters;
    List<String> listOfInbuiltFunctions = new ArrayList<>(Arrays.asList("print", "generateGrid", "generateRooms",
            "generateCorridors", "generateSpawns", "printMap", "generatePrint", "writeToFile", "overlap",
            "tileInformationStringBuilder", "setSeed"));

    HashMap<String, Type> eTable;// variable table, identifier(x) og node(int)
    Stack<HashMap<String, Type>> scopes; // scope table, variable identifier(x) og node
    Deque<Integer> activeScope;// Hvilket scope vi er i nu
    int errorNumber = 1;
    Boolean printDebugger = false;
    Boolean hasReturnStatement = false;
    String currentActiveFunction = "";
    public Boolean thereWasAnError = false;
    String currentIdentifierCheck = "";
    Boolean struct_variable_declarion_failed = false;
    // KUN TIL STRUCTS
    HashMap<String, Type> structTypes;
    HashMap<String, HashMap<String, Type>> tileInformationEnemy;
    HashMap<String, HashMap<String, Type>> tileInformationFloor;
    HashMap<String, HashMap<String, Type>> tileInformationWall;
    List<HashMap<String, Type>> rooms;
    HashMap<String, HashMap<String, Type>> structVariablesTable;
    Type wanted_type =Type.UNKNOWN;

    public SemanticChecker() {

        // fTable = new HashMap<>();
        eTable = new HashMap<>();
        scopes = new Stack<>();
        activeScope = new ArrayDeque<>();
        activeScope.push(0);
        scopes.add(eTable);
        typeOfReturnFunction = new HashMap<>();
        functionParameters = new HashMap<>();
        structVariablesTable = new HashMap<>();
        structTypes = new HashMap<>();
        tileInformationEnemy = new HashMap<>();
        tileInformationFloor = new HashMap<>();
        tileInformationWall = new HashMap<>();
        rooms = new ArrayList<>();
    }

    public void visitor(AstNode node) {
        /// System.out.println(node);
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
        if (node.getNode() instanceof MethodCallNode) {
           // errorHandler("ewjrngkewgjewkejnweklrglewrkngewkrgkwen");
            //visitMethodCall((MethodCallNode) node.getNode());
        }
    }

    public void errorHandler(String errorMessage) {
        thereWasAnError = true;
        
            System.err.println("Error " + errorNumber);
            errorNumber = errorNumber + 1;
            System.err.println(errorMessage);
        

    }

    /*
     * Vi skal tjekke om den er ordenligt skrevet og retunere en type?
     */
    public void visistArrayAccesNodeFn(ArrayAccessNode node) {
        String identifier = node.getIdentifier().toString();
        boolean found = scopes.getFirst().containsKey(identifier);

        for (int i = activeScope.getLast(); i < scopes.size(); i++) {
            if (scopes.get(i).containsKey(identifier)) {
                found = true;
            }
        }
        if (found) {
            Type allowedAccesTypesForArrays = Type.INT;
            Boolean validTypesaccesTypes = true;
            Type sizeType = Type.UNKNOWN;
            int arguementNumber = 0;

            List<AstNode> sizes = node.getIndices();
            for (int i = 0; i < sizes.size(); i++) {
                AstNode astNode = sizes.get(i);
                sizeType = getType(astNode);
                if (sizeType != allowedAccesTypesForArrays) {
                    arguementNumber = i;
                    errorHandler("Tried to assign the array:" + identifier + " but acces value: " + arguementNumber
                            + " is of type:" + sizeType + " and should be INT");
                    validTypesaccesTypes = false;

                    break;
                }
            }

        }
    }

    /*
     * VI skal hente Type fra table
     * Tjekke alle argumenter
     * tjekke højre side
     * tjekke for existens
     */
    public void visistArrayAssignment(ArrayAssignmentNode node) {
        String identifier = node.getIdentifier().toString();
        Boolean found = false;
        Type arrayType = Type.UNKNOWN;
        found = scopes.getLast().containsKey(identifier);

       
        for (int i = scopes.size() - 1; 0 <= i; i--) {

            if (scopes.get(i).containsKey(identifier)) {
                found = true;
                arrayType = scopes.get(i).get(identifier);
                wanted_type=arrayType;
            }
        }
        if (found) {

            Boolean validTypesaccesTypes = true;
            Type sizeType = Type.UNKNOWN;
            int arguementNumber = 0;
            Type allowedAccesTypesForArrays = Type.INT;

            List<AstNode> sizes = node.getIndices();
            for (int i = 0; i < sizes.size(); i++) {
                AstNode astNode = sizes.get(i);
                sizeType = getType(astNode);
                if (sizeType != allowedAccesTypesForArrays) {
                    arguementNumber = i;
                    validTypesaccesTypes = false;

                    break;
                }
            }
            if (!validTypesaccesTypes) {
                errorHandler("Tried to assign the array:" + identifier + " but acces value: " + arguementNumber
                        + " is of type:" + sizeType + " and should be:" + arrayType);
            }

            // Tjek venstre mod højre
            Type assignType = getType(node.getValue());
            if (arrayType != assignType) {
                errorHandler("Tried to assign the type:" + assignType + " to the array:" + identifier
                        + " that has the type:" + arrayType + ", and that is ilegal");
            }

        } else {
            errorHandler("Array:" + identifier + " Does not exist ");
        }
    }

    /*
     * Vi skal hente den dekleerede type
     * VI skal tjekke om den eksistere
     * vi skal tjekke om alle argumenterne er valide.
     */
    public void visistarrayDekleration(ArrayDefinitionNode node) {

        String identifier = node.getIdentifier().toString();
        boolean found = scopes.getFirst().containsKey(identifier);

        for (int i = activeScope.getLast(); i < scopes.size(); i++) {
            if (scopes.get(i).containsKey(identifier)) {
                found = true;
            }
        }

        Type arrayType = getType(node.getType());

        Boolean validTypes = true;
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
            scopes.getLast().put(identifier, arrayType);

        } else {
            errorHandler("Tried to declare the array:" + identifier + " but argument: " + arguementNumber
                    + " is of type:" + sizeType + " and should be:" + arrayType);
        }

        if (found) {
            errorHandler("Identifier:" + identifier + " is alredy used, rename it");
        }

    }

    public void visitPropertyAssignment(PropertyAssignmentNode node) {
        Type oldType = visitPropertyAccessNode(node.getPropertyAccessNode());
        wanted_type = oldType;
        Type newType = getType(node.getValue());
        errorHandler(oldType + "  " + newType);
        if (oldType != newType) {
            errorHandler("Type " + oldType + " does not match " + newType);
        }
    }

   
    public Type visitPropertyAccessNode(PropertyAccessNode node) {
        List<String> validPropertyAccess = new ArrayList<>(Arrays.asList("size", "get"));
        String firstIdentifier = node.getIdentifiers().get(0).toString();
        if (!structVariablesTable.containsKey(firstIdentifier) && !validPropertyAccess.contains(firstIdentifier)) {
            errorHandler("could not find the struct variable: " + firstIdentifier);
        }

        HashMap<String, Type> listOfIdentifiers = structVariablesTable.get(firstIdentifier);

        if (!validPropertyAccess.contains(firstIdentifier) && node.getIdentifiers().size() <= 1
                || !listOfIdentifiers.containsKey(node.getIdentifiers().get(1).toString())) {
            errorHandler("you need 3 arguments");
        }

        if (structVariablesTable.containsKey(firstIdentifier) && node.getIdentifiers().size() >= 2
                && listOfIdentifiers.containsKey(node.getIdentifiers().get(1).toString())) {
            Type identifierType = listOfIdentifiers.get(node.getIdentifiers().get(1).toString());
            return getType(identifierType.getTypeName());
        }
        return Type.UNKNOWN;
    }

    public Type visitMethodCall(MethodCallNode node) { // 
        // Her skal vi først finde ud af vilken type struct vi skal lede i
        // Så skal vi finde ud af om structet eksistere
        // Så skal vi indexe og tjekke dens typer imod den type ting itng
       
        /* String identifier = node.getIdentifierNode().toString();
        System.out.println("Identifier:"+identifier); // Variable vi leder efter i struct

        PropertyAccessNode propertyAccessNode = node.getPropertyAccessContext();
        System.out.println("Propertyascc:"+propertyAccessNode);

        String list = propertyAccessNode.getList();
        List<IdentifierNode> identifiernodes = propertyAccessNode.getIdentifiers();
        System.out.println("Liste:"+list);// Type af struct hasmap vi skal kikke i
        System.out.println("Identifiernodes"+identifiernodes);

        for (int i = identifiernodes.size()-1; i>=0;i--){
            System.out.println("Identifiernode:"+i+":"+identifiernodes.get(i));
        }

        if (node.getValue() instanceof ArgumentListNode) {
            // Cast node to ArgumentListNode
            ArgumentListNode argumentListNode = (ArgumentListNode) node.getValue();
            // You can now use argumentListNode for further operations
            System.out.println(argumentListNode);
            List<AstNode> liste = argumentListNode.getList();
            for (int i = liste.size()-1; i>=0;i--){
                System.out.println("arguments:"+i+":"+liste.get(i));
                System.out.println(liste.get(i).getClass());
            }
        } */

        return wanted_type; // Vi skal ikke tjekke dette, da vi ikke kan vide det. Fordi det er en runtime ting
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
        else if (leftType == Type.STRING && rightType == Type.STRING) {
            return Type.BOOLEAN;
        }
        

        errorHandler("Wrong types for relation operation:" + leftType + ":" + left + " And:" + right + ":" + rightType);

        return Type.UNKNOWN;
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
        } else if (leftType == Type.INT && rightType == Type.FLOAT) {
            return Type.FLOAT;
        } else if (leftType == Type.FLOAT && rightType == Type.INT) {
            return Type.FLOAT;
        }

        errorHandler("Wrong types for binary operation:" + leftType + ":" + left + " And:" + right + ":" + rightType);
        return Type.UNKNOWN;

    }

    private void visitVariableDeclaration(VariableDeclarationNode node) {

        try {
            boolean found = scopes.getLast().containsKey(node.getIdentifier().toString());

            /*
             * for (int i = activeScope.getLast(); i < scopes.size(); i++) {
             * if (scopes.get(i).containsKey(node.getIdentifier().toString())) {
             * found = true;
             * }
             * }
             */
            if (!found) {// Vi skal tjekke variable type mod det den type vi assigner til variablen.

                String identifier = node.getIdentifier().toString();

                Type variableType = getType(node.getType()); // Left side type
                wanted_type = variableType;
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
                wanted_type =Type.UNKNOWN;
            } else {
                throw new RuntimeException("variable: " + node.getIdentifier() + " already exists in the scope");
            }
        } catch (Exception e) {
            errorHandler(e.getMessage());
        }

    }

    public Type getVariable(IdentifierNode node) {

        if (scopes.getLast().containsKey(node.getIdentifier().toString())) {
           
            currentIdentifierCheck = node.getIdentifier().toString();
            return scopes.getLast().get(node.getIdentifier().toString());
        }
      
        for (int i = activeScope.getLast(); i < scopes.size(); i++) {
            if (scopes.get(i).containsKey(node.getIdentifier().toString())) {
                currentIdentifierCheck = node.getIdentifier().toString();
                return scopes.get(i).get(node.getIdentifier().toString());
            }
        }
        errorHandler("could not find the variable " + node.getIdentifier());

        return Type.UNKNOWN;
    }

    // Check if return = type er det samme som den function den står i.
    public void visitReturnNode(ReturnStatementNode node) {
        Type returnType = getType(node.getReturnValue());
       
        if (currentActiveFunction == "") {
            errorHandler("You have made return statement outside a function THAT IS illigal");
        } else {
            Type activeFunction = typeOfReturnFunction.get(currentActiveFunction);
            if (Objects.equals(currentActiveFunction, "")) {
                errorHandler("You have made return statement outside a function THAT IS illigal");
            }
            if (returnType != activeFunction) {
                errorHandler("The return type " + returnType + " Does not match the return statement of the function "
                        + activeFunction.getTypeName());
            }
        }

    }

    public void visitAssignNode(AssignmentNode node) {

        boolean foundIdentifier = false;

        for (int i = scopes.size() - 1; 0 <= i; i--) {
           
            HashMap<String, Type> ETable = scopes.get(i);
          
           
            if (ETable.containsKey(node.getIdentifier().toString())) {// hvis x er i scope
                foundIdentifier = true;
                // tjekke om det er lovligt.
                // hent gamle type og nye type.
                Type oldType = getType(node.getIdentifier());
                String identifier = node.getIdentifier().toString();

                Type rightType = getType(node.getValue());
                wanted_type = oldType;
                // tjekke om det er lovligt.
                if (oldType != rightType) {
                    
                    errorHandler("Tryied to asssign Type:" + rightType + " to the variable:" + identifier
                            + " that has the type:" + oldType
                            + " And that is hella iligal");

                }
            } else {
            }
            if (foundIdentifier) {
                break;
            }
        }
        if (!foundIdentifier) {
            errorHandler("Variable '" + node.getIdentifier() + "' has not been defined yet.");
        }
    }

    /*
     * Vi her skal vi sikre af hver if(exp) exp = bool. i if statements har vi if if
     * else flere exp som alle skal være type bool
     *
     */

    public void visitIfStatement(IfStatementNode node) {
        Type expression = Type.VOID;

        for (int i = 0; i < node.getExpressions().size(); i++) {
            expression = getType(node.getExpressions().get(i).getNode());
            if (expression != Type.BOOLEAN) {
                errorHandler("If statements expression must resolve to bool expression, and this resolve to Type:"
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

    public void visitWhileLoop(WhileLoopNode node) {
        Type toCheck = getType((node.getExpression()).getNode());
        if (toCheck != Type.BOOLEAN) {
            errorHandler("While loop expresion must resolve to bool expresion, and this resolve to Type:" + toCheck);

        }
        HashMap<String, Type> localTable = new HashMap<>();
        scopes.add(localTable);
        visitBlockNode(node.getBlock());
     
        scopes.remove(localTable);
    }

    public void visitFunctionDefinition(FunctionDefinitionNode node) {
       
        if (!listOfInbuiltFunctions.contains(node.getIdentifier().toString())) {
            if (!typeOfReturnFunction.containsKey(node.toString())
                    && !functionParameters.containsKey(node.toString())) {
                typeOfReturnFunction.put(node.getIdentifier().toString(), getType(node.getReturnType())); // Key
                                                                                                          // identifier
                                                                                                          // // Type
                functionParameters.put(node.getIdentifier().toString(), new ArrayList<>());

                HashMap<String, Type> localTable = new HashMap<>();
                scopes.add(localTable);
                List<ParameterNode> parameters = node.getArguments().getParameters();
                for (ParameterNode parameter : parameters) {
                    String identifierParameter = String.valueOf((parameter.getIdentifier())); // int carl
                    Type arguementType = getType(parameter.getType());
                    if (localTable.containsKey(identifierParameter)) {
                        errorHandler(
                                "The variable:" + identifierParameter + " Already exist in this function parameters.");
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
                    
                    errorHandler("Missing return statement in function declartion:" + node.getIdentifier().toString());
                }
                hasReturnStatement = false;
                scopes.remove(localTable);
            } else {
                errorHandler("function " + node.getIdentifier() + " already exists");
            }
        } else {
            errorHandler("You may not redeclare a inbuilt function. The function you tried to redeclare is:"
                    + node.getIdentifier().toString());
        }
    }

    /*
     * Vi skal sammenligne argumenterne med paremetrene.
     * Vi skal tjekke at der lige mange.
     * Der mangler fx argumenter der er ikke nok. eller der for mange.
     * Vi skal sige hvis arguemtnet er en forkert type.
     */
    public void visitFunctionCall(FunctionCallNode node) {
      
        if (!listOfInbuiltFunctions.contains(node.getFunctionName().toString())) {

            HashMap<String, Type> localETable = new HashMap<>();
            scopes.add(localETable);
            if (typeOfReturnFunction.containsKey(node.getFunctionName().toString())) {
                List<Type> expectedParameterTypes = functionParameters.get(node.getFunctionName().toString());

                int expectedArgumentsSize = expectedParameterTypes.size();
                int actualArgumentsSize = node.getArguments().size();
                if (expectedArgumentsSize != actualArgumentsSize) {
                    errorHandler("Function Expected:" + expectedArgumentsSize + " Arguments but got :"
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

    /*
     * Vi skal tjekke alle hashmasps med structs for at se om den eksistere i
     * forvejen
     * Vi skal gemme structet i den rigtige type hashmap.
     * Tjekke alle variable statements
     * Vis nogen fejler skal Struct ikke gemmes i hashmap
     */
    public void visitStruct(StructureDefinitionNode node) {// ! FORKERT SKAL ÆNDRES

        // Først skal vi hente typen som der ønskes at blive deklereet
        String typeStruct = node.getType().toString();
        
        if (!structTypes.containsKey(typeStruct)) {

            if (typeStruct.equals("enemy")) {
                // Nu har vi typen, så skal vi hente identifieren på structet
                String identifier = node.getIdentifier().toString();
               
                // Nu kender vi identiferen, nu skal vi bare tjekke om den eksistere i hashmap
                if (!tileInformationEnemy.containsKey(identifier)) {
                    // Vis den ikke eksisterer skal vi deklere den, efter vi har tjekke variabler

                    // Laver et "Scope" vi kan gemme variablerne i undervejs
                    HashMap<String, Type> localETable = new HashMap<>();
                    scopes.add(localETable);
                    // Henter alle declarations
                    List<VariableDeclarationNode> declarations = node.getVariableDeclarations();
                    struct_variable_declarion_failed = false;// Bool til at holde styr på om en deklaration fejler
                    // Looper over alle declarationerne og gemmer dem i localEtable
                    for (VariableDeclarationNode declaration : declarations) {
                        visitVariableDeclarationforStructs(declaration);
                    }
                    // Vis så ingen declarationer fejler skal vi gemme variablerne(Etable) med
                    // structet
                    if (!struct_variable_declarion_failed) {
                        tileInformationEnemy.put(identifier, localETable);
                        structTypes.put(identifier, Type.ENEMY);
                    }
                    struct_variable_declarion_failed = false;
                    scopes.remove(localETable);
                } else {
                    // Smide fejl vis vi prøver at redeklere
                    errorHandler("Struct " + node.getIdentifier().toString() + " already exists");
                }
            }
            // Repeat bare med andre typper
            if (typeStruct.equals("floor")) {
                String identifier = node.getIdentifier().toString();
                if (!tileInformationFloor.containsKey(identifier)) {
                    HashMap<String, Type> localETable = new HashMap<>();
                    scopes.add(localETable);
                    List<VariableDeclarationNode> declarations = node.getVariableDeclarations();
                    struct_variable_declarion_failed = false;

                    for (VariableDeclarationNode declaration : declarations) {
                        visitVariableDeclarationforStructs(declaration);
                    }

                    if (!struct_variable_declarion_failed) {
                        tileInformationFloor.put(identifier, localETable);
                        structTypes.put(identifier, Type.FLOOR);
                    }
                    struct_variable_declarion_failed = false;
                    scopes.remove(localETable);
                } else {

                    errorHandler("Struct " + node.getIdentifier().toString() + " already exists");
                }
            }
            if (typeStruct.equals("wall")) {
                String identifier = node.getIdentifier().toString();

                if (!tileInformationWall.containsKey(identifier)) {
                    HashMap<String, Type> localETable = new HashMap<>();
                    scopes.add(localETable);

                    List<VariableDeclarationNode> declarations = node.getVariableDeclarations();
                    struct_variable_declarion_failed = false;

                    for (VariableDeclarationNode declaration : declarations) {
                        visitVariableDeclarationforStructs(declaration);
                    }

                    if (!struct_variable_declarion_failed) {
                        tileInformationWall.put(identifier, localETable);
                        structTypes.put(identifier, Type.WALL);
                    }
                    struct_variable_declarion_failed = false;
                    scopes.remove(localETable);
                } else {
                    errorHandler("Struct " + node.getIdentifier().toString() + " already exists");
                }
            }
        } else {
            errorHandler("Struct " + node.getIdentifier().toString() + " already exists");
        }

    }

    private void visitVariableDeclarationforStructs(VariableDeclarationNode node) {
        try {
            boolean found = scopes.getLast().containsKey(node.getIdentifier().toString());

            if (!found) {// Vi skal tjekke variable type mod det den type vi assigner til variablen.

                String identifier = node.getIdentifier().toString();

                Type variableType = getType(node.getType()); // Left side type

                AstNode ass = node.getValue(); // THis is right side should be a node
                Type assignmentType = getType(ass); // This should give right side type

                if (variableType == assignmentType) {
                    Type typeWeSaveInETable = variableType;
                    scopes.getLast().put(node.getIdentifier().toString(), typeWeSaveInETable);

                } else {
                    struct_variable_declarion_failed = true;
                    errorHandler("Tryied to asssign Type:" + assignmentType + " to the variable:" + identifier
                            + " that has the type:" + variableType
                            + " And that is hella iligal");

                }
            } else {
                throw new RuntimeException("variable " + node.getIdentifier() + " already exists in struct");
            }
        } catch (Exception e) {
            struct_variable_declarion_failed = true;
            errorHandler(e.getMessage());
        }

    }

    public void visitBlockNode(BlockNode node) {
        for (AstNode statement : node.getStatements()) {
            visitStatements((StatementNode) statement);
        }
    }

    public Type resolveMethodCallType(MethodCallNode node) {
        Type returnType = Type.INT;

        return returnType;
    }

    public Type getType(Object node) {
        Type type = Type.UNKNOWN;

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
        } else if (node instanceof MethodCallNode) {
            type = visitMethodCall((MethodCallNode) node);
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
        } 
        else {
            errorHandler("The node we get failed to handle:" + node.getClass());
        }
        return type;
    }

}
