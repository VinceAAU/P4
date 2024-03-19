package dk.aau.cs_24_sw_4_16.carl;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private String input;
    private int position;
    private char currentChar;

    public Lexer(String input) {
        this.input = input;
        this.position = 0;
        this.currentChar = input.charAt(position);
    }

    private void advance() {
        position++;
        if (position >= input.length()) {
            currentChar = '\0'; // Null character indicates end of input
        } else {
            currentChar = input.charAt(position);
        }
    }

    private void skipWhitespace() {
        while (currentChar != '\0' && Character.isWhitespace(currentChar)) {
            advance();
        }
    }

    private Token number() {
        StringBuilder number = new StringBuilder();
        while (currentChar != '\0' && Character.isDigit(currentChar)) {
            number.append(currentChar);
            advance();
        }
        return new Token(TokenType.NUMBER, number.toString());
    }

    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();

        while (currentChar != '\0') {
            if (Character.isWhitespace(currentChar)) {
                skipWhitespace();
            } else if (Character.isDigit(currentChar)) {
                tokens.add(number());
            } else if (currentChar == '+') {
                tokens.add(new Token(TokenType.PLUS, "+"));
                advance();
            } else if (currentChar == '-') {
                tokens.add(new Token(TokenType.MINUS, "-"));
                advance();
            } else if (currentChar == 'p' && checkKeyword("print")) {
                tokens.add(new Token(TokenType.PRINT, "print"));
            }
            // Continue implementing other tokens like MUL, DIV, LPAREN, RPAREN...
            else {
                // Handle unknown characters or implement more token types
                advance(); // Skip the unknown character for now
            }
        }

        tokens.add(new Token(TokenType.EOF, "")); // Mark the end of input
        return tokens;
    }

    private boolean checkKeyword(String keyword) {
        int end = position + keyword.length();
        if (end <= input.length() && input.substring(position, end).equals(keyword)) {
            // Move the lexer position past the keyword
            position = end;
            currentChar = position < input.length() ? input.charAt(position) : '\0';
            return true;
        }
        return false;
    }

}
