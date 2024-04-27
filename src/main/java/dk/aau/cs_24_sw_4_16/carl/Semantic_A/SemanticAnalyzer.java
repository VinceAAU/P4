package dk.aau.cs_24_sw_4_16.carl.Semantic_A;

import dk.aau.cs_24_sw_4_16.carl.CstToAst.*;
public class SemanticAnalyzer {

    public boolean printTest = false;

    public void analyze(AstNode root) throws SemanticException {
        if (printTest) {
         //   System.out.println("Hej jeg kommer her ind");
        }
       // System.out.println("Her starter visitor class");

        TypeChecker typeChecker = new TypeChecker();
        typeChecker.visitor(root);

        System.out.println("Her stopper visitor class");

        if (root != null) {
            visit(root);
        }
    }

    
    private void visit(AstNode node) throws SemanticException {
        if (node instanceof ProgramNode) {
            visitProgram((ProgramNode) node);
            if (printTest) {
                System.out.println("Hej jeg kommer ind i programnode");
            }
        } else if (node instanceof BinaryOperatorNode) {
            visitBinaryOperator((BinaryOperatorNode) node);
            if (printTest) {
                System.out.println("Hej jeg kommer ind i binary");
            }
        } else if (node instanceof StatementNode) {
            visitStatement((StatementNode) node);
            if (printTest) {
                System.out.println("Hej jeg kommer ind i statement");
            }
        } else if (node instanceof ExpressionNode) {
            visitExpression((ExpressionNode) node);
            if (printTest) {
                System.out.println("Hej jeg kommer ind i expression");
            }
        } else if (node instanceof BlockNode) {
            visitBlock((BlockNode) node);
            if (printTest) {
                System.out.println("Hej jeg kommer ind i block");
            }
        } else if (node instanceof IfStatementNode) {
            visitIfStatement((IfStatementNode) node);
            if (printTest) {
                System.out.println("Hej jeg kommer ind i if statement");
            }
        } else if (node instanceof WhileLoopNode) {
            visitWhileLoop((WhileLoopNode) node);
            if (printTest) {
                System.out.println("Hej jeg kommer ind i while loop");
            }
        } else if (node instanceof FunctionDefinitionNode) {
            visitFunctionDefinition((FunctionDefinitionNode) node);
            if (printTest) {
                System.out.println("Hej jeg kommer ind i function definition");
            }
        } else if (node instanceof ReturnStatementNode) {
            visitReturnStatement((ReturnStatementNode) node);
            if (printTest) {
                System.out.println("Hej jeg kommer ind i return statement");
            }
        } else if (node instanceof FunctionCallNode) {
            visitFunctionCall((FunctionCallNode) node);
            if (printTest) {
                System.out.println("Hej jeg kommer ind i function call");
            }
        } else if (node instanceof VariableDeclarationNode) {
            visitVariableDeclaration((VariableDeclarationNode) node);
            if (printTest) {
                System.out.println("Hej jeg kommer ind i variable");
            }
        }
        // Add more as necessary
    }

    private void visitVariableDeclaration(VariableDeclarationNode node) throws SemanticException {
        System.out.println(node.toString());
        visit(node.getValue());
    }

    private void visitProgram(ProgramNode node) throws SemanticException {
        for (AstNode statement : node.getStatements()) {
            visit(statement);
        }
    }

    private void visitStatement(StatementNode node) throws SemanticException {
        // Assuming StatementNode holds a single statement or compound statements
        visit(node.getNode());
    }

    private void visitIfStatement(IfStatementNode node) throws SemanticException {
        // visit(node.getCondition());
        // visit(node.getThenBlock());
        // if (node.getElseBlock() != null) {
        // visit(node.getElseBlock());
        // }
    }

    private void visitWhileLoop(WhileLoopNode node) throws SemanticException {
        // visit(node.getCondition());
        // visit(node.getBody());
    }

    private void visitFunctionDefinition(FunctionDefinitionNode node) throws SemanticException {
        // for (ParameterNode param : node.getParameters()) {
        // visit(param);
        // }
        // visit(node.getBody());
    }

    private void visitReturnStatement(ReturnStatementNode node) throws SemanticException {
        // if (node.getExpression() != null) {
        // visit(node.getExpression());
        // }
    }

    private void visitFunctionCall(FunctionCallNode node) throws SemanticException {
        // for (ExpressionNode argument : node.getArguments()) {
        // visit(argument);
        // }
    }

    private void visitExpression(ExpressionNode node) throws SemanticException {
        // Assuming ExpressionNode wraps an expression
        visit(node.getNode());
    }

    private void visitBlock(BlockNode node) throws SemanticException {
        for (AstNode statement : node.getStatements()) {
            visit(statement);
        }
    }

    private void visitBinaryOperator(BinaryOperatorNode node) throws SemanticException {
        // Specific checks for BinaryOperatorNode
        checkBinaryExpression(node);
        if (printTest) {
            System.out.println("Hej jeg kommer int i visitbinary opertor");
        }
        // Recursively visit left and right children
        visit(node.getLeft());
        visit(node.getRight());
    }

    private void checkBinaryExpression(BinaryOperatorNode node) throws SemanticException {
        AstNode left = node.getLeft();
        AstNode right = node.getRight();

        // Retrieve the types of the left and right operands
        Type leftType = getType(left);
        Type rightType = getType(right);
        try {
            // Perform type compatibility check for the specific binary operation
            if (!isOperationTypeCompatible(leftType, rightType, node.getOperator())) {
                throw new SemanticException(
                        "Type mismatch for operator '" + node.getOperator() + "': " + leftType + " and " + rightType);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

        // If necessary, set the type for the binary expression itself
        // e.g., node.setType(resultingTypeOfOperation(leftType, rightType));
    }

    // Check if the operation is compatible with the given types
    private boolean isOperationTypeCompatible(Type leftType, Type rightType, String operator) {
        // Example for addition operation
        if (operator.equals("+")) {
            // Let's assume for simplicity that we can only add numbers
            if (leftType == Type.INT && rightType == Type.INT) {
                return true;
            }
            // Add more rules as per your language specification
        }
        // Include checks for other operators

        return false; // Default to false if not compatible
    }

    // Retrieve the type of a given AST node
    private Type getType(AstNode node) {
        if (node instanceof IntNode) {
            return Type.INT;
        } else if (node instanceof FloatNode) {
            return Type.FLOAT;
        } else if (node instanceof StringNode) {
            return Type.STRING;
        } else if (node instanceof IdentifierNode) {
            // Lookup variable type in symbol table
            // Assuming you have a method to get the variable type
            // return getVariableTypeFromSymbolTable(((VariableReferenceNode)
            // node).getName());
        }
        // Handle more node types as necessary

        return Type.UNKNOWN; // Or any other default you want to assign
    }

    // Define an exception class for semantic errors
    public static class SemanticException extends Exception {
        public SemanticException(String message) {
            super(message);
        }
    }
}
