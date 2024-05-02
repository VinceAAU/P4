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
    | arrayDeclaration
    | coordinateDeclaration
    | methodCall
    | propertyAssignment
    ;

importStatement : 'import' STRING ;
functionDefinition : 'fn' IDENTIFIER '(' parameterList? ')' '->' type block ;


structureDefinition : 'var' IDENTIFIER ':' structType '=' '{' variableDeclaration* '}' ;
variableDeclaration : 'var' IDENTIFIER ':' type '=' (expression) ;
arrayDeclaration : 'var' IDENTIFIER ':' legalArrayType arrayOptionalIndex+;


arrayOptionalIndex : '[' expression? ']' ;

legalArrayType :
    'bool'
    | 'coord'
    | 'int'
    | 'string'
    | 'float'
    | IDENTIFIER
    ;


structType
    : 'enemy'
    | 'floor'
    | 'wall'
    ;


type :
    'bool'
    | 'int'
    | 'float'
    | 'coord'
    | 'void'
    | 'string'
    | IDENTIFIER
    | legalArrayType '[' expression? ']' ('[' expression? ']')*
    ;
assignment : (IDENTIFIER | arrayAccess) '=' expression ;
propertyAssignment :  propertyAccess '=' expression ;
functionCall : IDENTIFIER '(' argumentList? ')' ;
methodCall : propertyAccess '(' argumentList? ')' ('.' IDENTIFIER)? ;
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
    | expression '..' expression # RandomBetween
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
    | arrayAccess # DummyArrayAccess
    | propertyAccess # DummyPropertyAccess
    ;




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
block : '{' (statement  | expression)* '}' ;
arrayAccess : IDENTIFIER '[' expression ']' ('[' expression ']')*;
propertyAccess : structType '.' IDENTIFIER ('.' IDENTIFIER)? ;
coordinateDeclaration : 'var' IDENTIFIER ':' 'coord' '=' '(' expression ',' expression ')' ;//Virker ikke nødvendigt, hvorfor ikke bare bruge arrayAcces?

// Lexer rules
INT : [-]?[0-9]+ ;
FLOAT : [-]?[0-9]* '.' [0-9]+ ;
STRING : '"' ~["]* '"' ;
BOOL : ('true' | 'false') ;
IDENTIFIER : [a-zA-Z_][a-zA-Z0-9_]* ;
WS : [ \t\r\n]+ -> skip ;
LINE_COMMENT : '//' ~[\r\n]* -> skip ;
