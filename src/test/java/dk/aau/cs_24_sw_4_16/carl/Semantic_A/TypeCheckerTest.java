package dk.aau.cs_24_sw_4_16.carl.Semantic_A;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import dk.aau.cs_24_sw_4_16.carl.CstToAst.*;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dk.aau.cs_24_sw_4_16.carl.CARLLexer;
import dk.aau.cs_24_sw_4_16.carl.CARLParser;

public class TypeCheckerTest {

    SemanticChecker SemanticChecker = new SemanticChecker();
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalErr = System.err;

    // Redirect System.err before each test
    @BeforeEach
    void setUpStreams() {
        System.setErr(new PrintStream(errContent));
    }

    // Restore original System.err after each test
    @AfterEach
    void restoreStreams() {
        System.setErr(originalErr);
    }

    public AstNode treemaker(String fileInput) {// Denne her skal vi bruge til intergrations testing af Typechecker
        AstNode astNode;
        if (fileInput != null) {
            try {
                InputStream stream = new ByteArrayInputStream(fileInput.getBytes(StandardCharsets.UTF_8));
                CARLLexer lexer = new CARLLexer(CharStreams.fromStream(stream));
                CommonTokenStream tokens = new CommonTokenStream(lexer);

                CARLParser parser = new CARLParser(tokens);

                ParseTree tree = parser.program();

                CstToAstVisitor visitor = new CstToAstVisitor();

                astNode = visitor.visit(tree);
                return astNode;
            } catch (Exception e) {
                System.out.println("Error happened:" + e);
            }

        }
        return null;
    }

    @Test
    void testBinaryOperatorTypeCheck() {

        IntNode intNode = new IntNode(0);
        BoolNode boolNode = new BoolNode("yes");
        StringNode stringNode = new StringNode("String");
        FloatNode floatNode = new FloatNode("2.2");

        BinaryOperatorNode testnode1 = new BinaryOperatorNode(intNode, intNode, "+");
        BinaryOperatorNode testnode2 = new BinaryOperatorNode(boolNode, stringNode, "+");
        BinaryOperatorNode testnode3 = new BinaryOperatorNode(floatNode, floatNode, "+");

        Type testfn1 = SemanticChecker.binaryOperatorTypeCheck(testnode1);
        Type testfn2 = SemanticChecker.binaryOperatorTypeCheck(testnode2);
        Type testfn3 = SemanticChecker.binaryOperatorTypeCheck(testnode3);

        assertEquals(Type.INT, testfn1, "Should have returned int");
        String correct_error = """
                            Error 1
                Wrong types for binary operation:BOOLEAN: false And:String:STRING
                                                """;
        ;

        String terminal_Errors = normalizeOutput();
        System.out.println(terminal_Errors);
        assertEquals(correct_error.trim(), terminal_Errors);
        assertEquals(Type.UNKNOWN, testfn2, "Should have returned unknown");
        assertEquals(Type.FLOAT, testfn3, "Should have returned float");

    }

    @Test
    void testTypeCheker47() {
        String code = """
                var integer:int =20
                var test_string:string="string"
                var result:int = integer+test_string
                                    """;
        AstNode astTree = treemaker(code);

        String correct_error = """
                Error 1
                Wrong types for binary operation:INT:integer And:test_string:STRING
                Error 2
                Tryied to declare Type:UNKNOWN to the variable:result that has the type:INT And that is hella iligal
                                    """;
        SemanticChecker.visitor(astTree);
        String terminal_Errors = normalizeOutput();
        assertEquals(correct_error.trim(), terminal_Errors);
    }

    @Test
    void testErrorHandler() {
        String testString = "test1";
        String testString2 = "test2";
        SemanticChecker.errorHandler(testString);
        SemanticChecker.errorHandler(testString2);
        String expectedOutput = "Error 1\n" +
                "test1\n" +
                "Error 2\n" +
                "test2\n";
        // System.out.println(outContent.toString().trim());
        // System.err.println(expectedOutput.length()+"actual;"+errContent.toString().length());
        String normalizedActualOutput = errContent.toString().trim().replace("\r\n", "\n").replace("\r", "\n");
        assertEquals(expectedOutput.trim(), normalizedActualOutput);
    }

    @Test
    void testRelationOperatorTypeCheck() {

        IntNode intNode = new IntNode(0);
        BoolNode boolNode = new BoolNode("ee");
        StringNode stringNode = new StringNode("ee");
        FloatNode floatNode = new FloatNode("2.2");

        RelationsAndLogicalOperatorNode testnode1 = new RelationsAndLogicalOperatorNode(stringNode, floatNode, null);
        RelationsAndLogicalOperatorNode testnode2 = new RelationsAndLogicalOperatorNode(boolNode, boolNode, null);
        RelationsAndLogicalOperatorNode testnode3 = new RelationsAndLogicalOperatorNode(intNode, intNode, null);
        Type testfn1 = SemanticChecker.relationOperatorTypeCheck(testnode1);
        Type testfn2 = SemanticChecker.relationOperatorTypeCheck(testnode2);
        Type testfn3 = SemanticChecker.relationOperatorTypeCheck(testnode3);

        assertEquals(Type.UNKNOWN, testfn1, "Should return Type unkown");
        assertEquals(Type.BOOLEAN, testfn2, "Should have Type bool");
        assertEquals(Type.BOOLEAN, testfn3, "Should have Type bool");
    }

    @Test
    void testVisitIfStatement() {
        List<ExpressionNode> expressionNodeList = new ArrayList<>();
        expressionNodeList.add(new ExpressionNode(new BoolNode("true")));
        expressionNodeList.add(new ExpressionNode(new BoolNode("false")));

        List<BlockNode> blockNodeList = new ArrayList<>();
        blockNodeList.add(new BlockNode());
        blockNodeList.add(new BlockNode());

        IfStatementNode node1 = new IfStatementNode(blockNodeList, expressionNodeList);
        SemanticChecker.visitIfStatement(node1);
        Type correctType1 = SemanticChecker.getType(expressionNodeList.get(0));
        Type correctType2 = SemanticChecker.getType(expressionNodeList.get(1));
        assertEquals(Type.BOOLEAN, correctType1);
        assertEquals(Type.BOOLEAN, correctType2);

        expressionNodeList.add(new ExpressionNode(new IntNode("2")));
        blockNodeList.add(new BlockNode());
        IfStatementNode node2 = new IfStatementNode(blockNodeList, expressionNodeList);
        SemanticChecker.visitIfStatement(node2);
        Type errorType = SemanticChecker.getType(expressionNodeList.get(2));
        assertEquals(Type.INT, errorType,
                "If statements expression must resolve to bool expression, and this resolve to Type:INT");
    }

    @Test
    void testVisitWhileLoop() {
        ExpressionNode expressionNode1 = new ExpressionNode(new BoolNode("true"));
        BlockNode blockNode = new BlockNode();

        SemanticChecker.visitWhileLoop(new WhileLoopNode(expressionNode1, blockNode));
        Type correctType = SemanticChecker.getType(expressionNode1);
        assertEquals(Type.BOOLEAN, correctType);

        ExpressionNode expressionNode2 = new ExpressionNode(new StringNode("hello"));
        Type errorType = SemanticChecker.getType(expressionNode2);
        SemanticChecker.visitWhileLoop(new WhileLoopNode(expressionNode2, blockNode));
        assertEquals(Type.STRING, errorType,
                "While loop expresion must resolve to bool expresion, and this resolve to Type:" + errorType);
    }

    @Test
    void testTypeCheker1() {
        String code = """
                var array: int[3][3]
                var array: int[v][3]
                                    """;
        AstNode astTree = treemaker(code);

        String correct_error = """
                Error 1
                could not find the variable v
                Error 2
                Tried to declare the array:array but argument: 0 is of type:UNKNOWN and should be:INT
                Error 3
                Identifier:array is alredy used, rename it
                    """;
        SemanticChecker.visitor(astTree);

        String terminal_Errors = normalizeOutput();
        System.out.println(terminal_Errors + "We get here");
        assertEquals(correct_error.trim(), terminal_Errors);
    }

    @Test
    void testTypeCheker2() {
        String code = """
                var array: int[3][3]
                array[1][1]=10
                array[2][1]="string"
                array["string"][2] = 2
                                    """;
        AstNode astTree = treemaker(code);

        String correct_error = """
                            Error 1
                Tried to assign the type:STRING to the array:array that has the type:INT, and that is illegal
                Error 2
                Tried to assign the array:array but acces value: 0 is of type:STRING and should be:Int
                                """;
        SemanticChecker.visitor(astTree);

        String terminal_Errors = normalizeOutput();
        System.out.println(terminal_Errors + "We get here");
        assertEquals(correct_error.trim(), terminal_Errors);
        // assertTrue( terminal_Errors.contains(correct_error));
    }

    @Test
    void testTypeCheker9() {
        String code = """
                var Goblin : enemy ={
                    var difficulty : int = 1
                    var health : int = 500
                    var symbol : string= "O"
                    var difficulty:int = 20
                }
                var Goblin2 : enemy ={
                    var difficulty : int = 1
                    var health : int = 500
                    var symbol : string= "O"
                }
                var Goblin2 : enemy ={
                    var difficulty : int = 1
                    var health : int = 500
                    var symbol : string= "O"
                }


                    """;
        AstNode astTree = treemaker(code);

        String correct_error = """
                            Error 1
                variable difficulty already exists in struct
                Error 2
                Struct Goblin2 already exists
                                """;
        ;
        SemanticChecker.visitor(astTree);
        String terminal_Errors = normalizeOutput();
        assertEquals(correct_error.trim(), terminal_Errors);
        // assertTrue( terminal_Errors.contains(correct_error));
    }

    @Test
    void testTypeCheker10() {
        String code = """

                fn print (y:int)-> int{
                    return 5
                    }

                """;
        AstNode astTree = treemaker(code);

        String correct_error = """
                Error 1
                You may not redeclare a inbuilt function. The function you tried to redeclare is:print
                    """;
        ;
        SemanticChecker.visitor(astTree);
        String terminal_Errors = normalizeOutput();
        assertEquals(correct_error.trim(), terminal_Errors);
        // assertTrue( terminal_Errors.contains(correct_error));
    }

    public String normalizeOutput() {
        String normalizedActualOutput = errContent.toString().trim().replace("\r\n", "\n").replace("\r", "\n");
        return normalizedActualOutput;
    }

    @Test
    void testTypeCheker11() {
        /*
         * Variable declaration rigtig type.
         */
        String code = """
                var test_variable:int = 20
                var test_variable2:string=test_variable
                """;
        AstNode astTree = treemaker(code);

        String correct_error = """
                Error 1
                Tryied to declare Type:INT to the variable:test_variable2 that has the type:STRING And that is hella iligal""";
        SemanticChecker.visitor(astTree);
        String terminal_Errors = normalizeOutput();
        assertEquals(correct_error.trim(), terminal_Errors);

    }

    @Test
    void testTypeCheker12() {
        /*
         * Må ikke deklere variable i samme scope.
         */

        String code = """
                var test_variable:int = 20
                var test_variable:int =40
                """;
        AstNode astTree = treemaker(code);

        String correct_error = """
                Error 1
                variable: test_variable already exists in the scope""";
        SemanticChecker.visitor(astTree);
        String terminal_Errors = normalizeOutput();
        assertEquals(correct_error.trim(), terminal_Errors);
    }

    @Test
    void testTypeCheker13() {
        /*
         * Try to use variable that does not exist
         * Vi får flere fejl, men den først fejl burde være could not found variable
         */

        String code = """
                var test_variable:int = 20
                test_variable=y
                """;
        AstNode astTree = treemaker(code);

        String correct_error = """
                Error 1
                could not find the variable y""";
        SemanticChecker.visitor(astTree);
        String terminal_Errors = normalizeOutput();
        // assertEquals(correct_error.trim(), terminal_Errors);
        assertTrue(terminal_Errors.contains(correct_error));
    }

    @Test
    void testTypeCheker14() {
        /*
         * Giver fejl vis return er ude for function
         */
        String code = """
                var variable:int =20
                return 10
                """;
        AstNode astTree = treemaker(code);

        String correct_error = """
                        Error 1
                You have made return statement outside a function THAT IS illigal""";
        SemanticChecker.visitor(astTree);
        String terminal_Errors = normalizeOutput();
        assertEquals(correct_error.trim(), terminal_Errors);
        // assertTrue( terminal_Errors.contains(correct_error));
    }

    @Test
    void testTypeCheker15() {
        /*
         * Giver ikke fejl vis return er inde i en funktion
         */
        String code = """
                var variable:int =4
                var y:int =3
                var x:int =6
                fn plus (y:int)-> int{
                    return y+x
                    }

                """;
        AstNode astTree = treemaker(code);

        String correct_error = "";
        SemanticChecker.visitor(astTree);
        String terminal_Errors = normalizeOutput();
        assertEquals(correct_error.trim(), terminal_Errors);
        // assertTrue( terminal_Errors.contains(correct_error));
    }

    @Test
    void testTypeCheker16() {
        /*
         * Tester at den smider fejl vis man prøver at retunere den forkerte type.
         */
        String code = """
                var variable:int =4
                var y:int =3
                var x:int =6
                fn plus (y:int)-> int{
                    var variable_string:string="hej"
                    return variable_string
                    }

                """;
        AstNode astTree = treemaker(code);

        String correct_error = """
                        Error 1
                The return type STRING Does not match the return statement of the function int
                       """;
        SemanticChecker.visitor(astTree);
        String terminal_Errors = normalizeOutput();
        assertEquals(correct_error.trim(), terminal_Errors);
        // assertTrue( terminal_Errors.contains(correct_error));
    }

    @Test
    void testTypeCheker17() {
        /*
         * Smid fejl vis :Valider at argumenterne i functions call stemmer overens med
         * de forventede //! VIKRER IKKE ÅBENBART
         * typer i funktionsdeklaration.
         */
        String code = """

                 fn plus (y:int)-> int{
                      return y+2
                     }
                //   var false_result:int =plus("string")

                   var true_result:int=plus(5)

                                     """;
        /*
         * AstNode astTree = treemaker(code);
         * 
         * String correct_error = """
         * Error 1
         * Function Expected type: , as Argument but got
         * """;
         * typeChecker.visitor(astTree);
         * 
         * String terminal_Errors = normalizeOutput();
         * System.out.println(terminal_Errors + "We get here");
         * assertEquals(correct_error.trim(), terminal_Errors);
         */
        // assertTrue( terminal_Errors.contains(correct_error));
    }

}
