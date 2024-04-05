package dk.aau.cs_24_sw_4_16.carl.Expression;

public class BinaryOperations extends Expression {
    Expression left;
    Expression right;
    public enum operator {ADD, SUB, MULTI, DIV, MOD};
    operator op;

    public BinaryOperations(Expression left, Expression right, operator op) {
        this.left = left;
        this.right = right;
        this.op = op;
    }

    /*
    : '+' # Addition
    | '-' # Subtraction
    | '*' # Multiplication
    | '/' # Division
    | '%' # Modulus
    | '==' # Equals
    | '!=' # NotEquals
    | '!' # Not // This prob needs to be a class for itself
    | '<' # LessThan
    | '>' # GreaterThan
    | '<=' # LessThanOrEqual
    | '>=' # GreaterThanOrEqual
     */

}
