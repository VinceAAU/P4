package dk.aau.cs_24_sw_4_16.carl.Semantic_A;

import dk.aau.cs_24_sw_4_16.carl.CstToAst.*;
public class SemanticAnalyzer {

    public boolean printTest =true;
    public void analyze(AstNode root) throws SemanticException {
      
    }



    
    private void checkBinaryExpression(BinaryOperatorNode node) throws SemanticException {
        AstNode left = node.getLeft();
        AstNode right = node.getRight();

        // Retrieve the types of the left and right operands
        Type leftType = getType(left);
        Type rightType = getType(right);

        // Perform type compatibility check for the specific binary operation
        if (!isOperationTypeCompatible(leftType, rightType, node.getOperator())) {
            throw new SemanticException("Type mismatch for operator '" + node.getOperator() + "': " + leftType + " and " + rightType);
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
            // return getVariableTypeFromSymbolTable(((VariableReferenceNode) node).getName());
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
