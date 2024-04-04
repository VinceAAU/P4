grammar CARL;

// Parser rules
program : statement* EOF;

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

primitiveTypeForArray
    : 'int'
    | 'float'
    | 'string'
    ;

type
    : primitiveTypeForArray
    | 'bool'
    | 'coord'
    | 'void'
    | IDENTIFIER
    | primitiveTypeForArray '[' ']''[' ']' //Questionable, but maybe?
    ;
assignment : IDENTIFIER '=' expression ;
functionCall : IDENTIFIER '(' argumentList? ')' ;
methodCall : propertyAccess '(' argumentList? ')' ; //Lige nu så bliver den slet ikke brugt
argumentList : expression (',' expression)* ;
parameterList : IDENTIFIER ':' type (',' IDENTIFIER ':' type)* ;

//Should probably define visitor method for each type. Might make it easier when implementing.
//Need to create visitMethod for for exmaple (expression operator expression) operator expression (Parenthese method).
//Once the methods are set up and the CFG is correct, we can probably start coding.
//Saving expression values in hashmap with functions?
expression
    : INT
    | FLOAT
    | STRING
    | IDENTIFIER
    | '(' expression ')' //A bit ambigous?
    | expression operator expression
    | functionCall
//    | expression '..' expression // Virker ikke rigtigt, for her vil man kunne lave mærkelige som ID..Float
//    | randomExpression //Man kunne også definere den under operator. Hvilket jeg har valgt at gøre her
    | arrayAccess
    | propertyAccess
    | structInstantiation
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
    | '!' # Not
    | '<' # LessThan
    | '>' # GreaterThan
    | '<=' # LessThanOrEqual
    | '>=' # GreaterThanOrEqual
    | 'AND' # And //Not capital?
    | 'OR' # Or
    | '..' # RandomBetween
    ;

ifStatement : 'if' expression block ( 'else if' expression block )* ( 'else' block )? ;
whileLoop : 'while' expression block ;
returnStatement : 'return' expression? ;
block : '{' statement* '}' ;
arrayDefinition : primitiveTypeForArray '[' expression? ']' ('[' expression ']')? IDENTIFIER ;
arrayAccess : IDENTIFIER '[' INT ']' ('[' INT ']') '=' primitiveTypeForArray?;
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
