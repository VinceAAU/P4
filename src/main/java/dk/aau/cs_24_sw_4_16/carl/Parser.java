package dk.aau.cs_24_sw_4_16.carl;

import java.util.List;

public class Parser {
    private List<Token> tokens;
    private int position = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    private Token currentToken() {
        return tokens.get(position);
    }

    private void advance() {
        position++;
    }

    private ASTNode parseNumber() {
        Token token = currentToken();
        advance();
        return new NumberNode(token); // Ensure NumberNode implements ASTNode
    }

    // Parse expressions, starting with the lowest precedence (e.g., binary operations)
    private ASTNode parseExpression() {
        return parseTerm(); // Start with terms and add more rules for higher precedence operations
    }

    // Parse terms in an expression (e.g., addition, subtraction)
    private ASTNode parseTerm() {
        ASTNode left = parseFactor();

        while (currentToken().getType() == TokenType.PLUS || currentToken().getType() == TokenType.MINUS) {
            Token opToken = currentToken();
            advance();
            ASTNode right = parseFactor();
            left = new BinaryOperationNode(left, opToken, right); // Ensure this constructor exists and matches
        }

        return left;
    }

    // Parse factors in a term (e.g., multiplication, division)
    private ASTNode parseFactor() {
        Token token = currentToken();

        if (token.getType() == TokenType.NUMBER) {
            advance();
            return new NumberNode(token); // Ensure NumberNode implements ASTNode
        } else if (token.getType() == TokenType.LPAREN) {
            advance();
            ASTNode node = parseExpression();
            if (currentToken().getType() != TokenType.RPAREN) {
                // Handle error: expected ')'
            }
            advance(); // Consume the ')'
            return node;
        }

        return null; // Placeholder for error handling
    }

    public ASTNode parse() {
        if (currentToken().getType() == TokenType.PRINT) {
            return parsePrintStatement(); // Parse a print statement
        } else {
            return parseExpression(); // Default to parsing an expression
        }
    }
    
    private ASTNode parsePrintStatement() {
        advance(); // Skip the PRINT token
        ASTNode expression = parseExpression(); // Parse the expression to be printed
        if (currentToken().getType() != TokenType.RPAREN) {
            // Assuming the print statement ends with a right parenthesis, e.g., print(expr);
            // Handle error: expected ')'
            System.err.println("Error: Expected ')' after expression in print statement.");
        } else {
            advance(); // Consume the ')'
        }
        return new PrintNode(expression); // Construct and return a PrintNode
    }
    
}
