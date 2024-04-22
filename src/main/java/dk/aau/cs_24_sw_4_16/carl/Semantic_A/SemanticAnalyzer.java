package dk.aau.cs_24_sw_4_16.carl.Semantic_A;

import dk.aau.cs_24_sw_4_16.carl.CstToAst.*;
// ... (other necessary imports)

public class SemanticAnalyzer {

    public void analyze(AstNode root) throws SemanticException {
        if (root != null) {
            visit(root);
        }
    }

    private void visit(AstNode node) throws SemanticException {
        // Implement type checking logic for each relevant node type
        if (node instanceof BinaryExpressionNode) {
            checkBinaryExpression((BinaryExpressionNode) node);
        }
        // You can add more checks here for different types of nodes as needed

        // Recursively visit all children of the current node
        for (AstNode child : node.getChildren()) {
            visit(child);
        }
    }

    private void checkBinaryExpression(BinaryExpressionNode node) throws SemanticException {
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
        } else if (node instanceof VariableReferenceNode) {
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
