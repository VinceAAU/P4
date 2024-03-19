package dk.aau.cs_24_sw_4_16.carl;

public class Evaluator implements ASTVisitor {
    private int result; // Assuming all evaluations result in an integer for simplicity

    @Override
    public void visit(NumberNode node) {
        result = Integer.parseInt(node.token.getValue());
    }

    @Override
    public void visit(BinaryOperationNode node) {
        node.left.accept(this);
        int leftResult = result;
        node.right.accept(this);
        int rightResult = result;

        switch (node.opToken.getType()) {
            case PLUS:
                result = leftResult + rightResult;
                break;
            case MINUS:
                result = leftResult - rightResult;
                break;
            // Add cases for other operations
            default:
                throw new RuntimeException("Unsupported operation: " + node.opToken.getType());
        }
    }

    @Override
    public void visit(PrintNode node) {
        node.expression.accept(this);
        System.out.println(result); // Now printing the evaluated result
    }

    // Optionally, provide a method to get the final result, if needed
    public int getResult() {
        return result;
    }
}
