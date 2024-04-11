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
    | methodCall
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
    | primitiveTypeForArray '[' ']'('[' ']')*
    ;
assignment : (IDENTIFIER | arrayAccess) '=' expression ;
functionCall : IDENTIFIER '(' argumentList? ')' ;
methodCall : propertyAccess '(' argumentList? ')' ;
argumentList : expression (',' expression)* ;
parameterList : IDENTIFIER ':' type (',' IDENTIFIER ':' type)* ;

//Only way to get precedence in terms of *+- etc.
expression
    : primary # DummyPrimary
    | '!' expression # Not
    | expression op=('*' | '/' | '%') expression # MultiplicationDivisionModulus
    | expression op=('+' | '-') expression # AdditionSubtraction
    | expression op=('<' | '<=' | '>' | '>=' | '==' | '!=') expression # Relation
    | expression op=('AND' | 'OR') expression # Logical
    | expression '..' primary # RandomBetween
    ;


primary
    : INT # Int
    | FLOAT # Float
    | STRING # String
    | IDENTIFIER # Identifier
    | BOOL # Bool
    | '(' expression ')' # Parentheses
    | functionCall # DummyFunctionCallExpr
    | methodCall # DummyMethodCall
    | arrayAccess # DummyArrayAccessExpr
    | propertyAccess # DummyPropertyAccess
    | structInstantiation # DummyStructInstantiationExpr
    ;


structInstantiation : IDENTIFIER '{' (IDENTIFIER ':' expression (',' IDENTIFIER ':' expression)*)? '}' ;

//operator
//    : '!' # Not
//    | '*' # Multiplication
//    | '-' # Subtraction
//    | '+' # Addition
//    | '/' # Division
//    | '%' # Modulus
//    | '<' # LessThan
//    | '<=' # LessThanOrEqual
//    | '>' # GreaterThan
//    | '>=' # GreaterThanOrEqual
//    | '==' # Equals
//    | '!=' # NotEquals
//    | 'AND' # And
//    | 'OR' # Or
//    | '..' # RandomBetween
//    ;

//Maybe wrong since we already have it defined earlier, but we need true false values and to not be able to write anything in the condition
//booleanExpression : expression ('==' | '!=' | '<' | '>' | '<=' | '>=') expression | 'true' | 'false';
ifStatement : 'if' expression block ( 'else if' expression block )* ( 'else' block )? ;
whileLoop : 'while' expression block ;
returnStatement : 'return' expression? ;
block : '{' statement*  | expression* '}' ;
arrayDefinition : primitiveTypeForArray '[' INT? ']' ('[' INT ']')* IDENTIFIER ;
arrayAccess : IDENTIFIER '[' INT ']' ('[' INT ']')*;
propertyAccess : IDENTIFIER '.' IDENTIFIER ;
coordinateDeclaration : 'var' IDENTIFIER ':' 'coord' '=' '(' expression ',' expression ')' ;//Virker ikke nødvendigt, hvorfor ikke bare bruge arrayAcces?

// Lexer rules
INT : [0-9]+ ;
FLOAT : [0-9]* '.' [0-9]+ ;
STRING : '"' ~["]* '"' ;
IDENTIFIER : [a-zA-Z_][a-zA-Z0-9_]* ;
BOOL : ('true' | 'false') ;
WS : [ \t\r\n]+ -> skip ;
LINE_COMMENT : '//' ~[\r\n]* -> skip ;

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
