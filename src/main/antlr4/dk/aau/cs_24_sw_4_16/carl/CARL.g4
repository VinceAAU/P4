grammar CARL;

// Parser rules
program : statement* ;

statement
    : assignment
    | functionCall
    | functionDefinition
    | ifStatement
    | whileLoop
    | returnStatement
    | structureDefinition
    | importStatement
    | variableDeclaration
    | arrayDefinition
    | coordinateDeclaration
    ;

importStatement : 'import' STRING ;
functionDefinition : 'fn' IDENTIFIER '(' parameterList? ')' '->' type block ;
structureDefinition : 'struct' IDENTIFIER '{' variableDeclaration* '}' ;
variableDeclaration : 'var' IDENTIFIER ':' type '=' (expression | structInstantiation) ;
type
    : 'int'
    | 'float'
    | 'string'
    | 'bool'
    | 'coord'
    | 'void'
    | IDENTIFIER
    ;
assignment : IDENTIFIER '=' expression ;
functionCall : IDENTIFIER '(' argumentList? ')' ;
methodCall : propertyAccess '(' argumentList? ')' ;
argumentList : expression (',' expression)* ;
parameterList : IDENTIFIER ':' type (',' IDENTIFIER ':' type)* ;

expression
    : INT
    | FLOAT
    | STRING
    | IDENTIFIER
    | '(' expression ')'
    | expression operator expression
    | functionCall // Now includes function and method calls
    | expression '..' expression // Incorporating randomFunction directly
    | arrayAccess // Array element access
    | propertyAccess // Property access of structs
    | structInstantiation // Struct instantiation
    ;

structInstantiation : IDENTIFIER '{' (IDENTIFIER ':' expression (',' IDENTIFIER ':' expression)*)? '}' ;

operator
    : '+' # Addition
    | '-' # Subtraction
    | '*' # Multiplication
    | '/' # Division
    | '%' # Modulus
    | '==' # Equals
    | '!=' # NotEquals
    | '<' # LessThan
    | '>' # GreaterThan
    | '<=' # LessThanOrEqual
    | '>=' # GreaterThanOrEqual
    | 'and' # And
    | 'or' # Or
    ;

ifStatement : 'if' expression block ( 'else if' expression block )* ( 'else' block )? ;
whileLoop : 'while' expression block ;
returnStatement : 'return' expression? ;
block : '{' statement* '}' ;
arrayDefinition : type '[' expression? ']' IDENTIFIER ;
arrayAccess : IDENTIFIER '[' expression ']' ;
propertyAccess : IDENTIFIER '.' IDENTIFIER ;
coordinateDeclaration : 'var' IDENTIFIER ':' 'coord' '=' '(' expression ',' expression ')' ;

// Lexer rules
INT : [0-9]+ ;
FLOAT : [0-9]* '.' [0-9]+ ;
STRING : '"' ~["]* '"' ;
IDENTIFIER : [a-zA-Z_][a-zA-Z0-9_]* ;
WS : [ \t\r\n]+ -> skip ;
LINE_COMMENT : '//' ~[\r\n]* -> skip ;
LPAREN : '(' ;
RPAREN : ')' ;
LBRACE : '{' ;
RBRACE : '}' ;

/* code exmaple:
import "basic_library"

struct Vector {
    var x: float = 0.0
    var y: float = 0.0
}

fn addVectors(v1: Vector, v2: Vector) -> Vector {
    var result: Vector = Vector{ x: v1.x + v2.x, y: v1.y + v2.y }
    return result
}

var vectorOne: Vector = Vector{ x: 1.0, y: 2.0 }
var vectorTwo: Vector = Vector{ x: 3.0, y: 4.0 }

var vectorSum: Vector = addVectors(vectorOne, vectorTwo)

if vectorSum.x > 1.0 {
    print("X-coordinate is greater than 1.")
} else {
    print("X-coordinate is 1 or less.")
}

while vectorSum.y < 10.0 {
    var increment: Vector = Vector{ x: 0.0, y: 1.0 }
    vectorSum = addVectors(vectorSum, increment)
}

return vectorSum */
