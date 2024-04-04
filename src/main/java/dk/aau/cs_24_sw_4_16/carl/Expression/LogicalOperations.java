package dk.aau.cs_24_sw_4_16.carl.Expression;

public class LogicalOperations extends Expression {
    Expression left;
    Expression right;

    public LogicalOperations(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /*
    | 'and' # And
    | 'or' # Or
     */
}
