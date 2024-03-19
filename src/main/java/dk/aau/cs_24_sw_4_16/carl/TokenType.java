package dk.aau.cs_24_sw_4_16.carl;

public enum TokenType {
    NUMBER, // Represents a numeric literal
    PLUS, // '+'
    MINUS, // '-'
    MUL, // '*'
    DIV, // '/'
    LPAREN, // '('
    RPAREN, // ')'
    PRINT, // 'print' keyword
    EOF, // End of file/input marker
    UNKNOWN // For unrecognized symbols
}
