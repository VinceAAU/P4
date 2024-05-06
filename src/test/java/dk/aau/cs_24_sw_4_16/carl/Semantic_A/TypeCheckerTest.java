package dk.aau.cs_24_sw_4_16.carl.Semantic_A;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

    TypeChecker typeChecker = new TypeChecker();
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
        BoolNode boolNode = new BoolNode(null);
        StringNode stringNode = new StringNode(null);
        FloatNode floatNode = new FloatNode("2.2");

        BinaryOperatorNode testnode1 = new BinaryOperatorNode(intNode, intNode, null);
        BinaryOperatorNode testnode2 = new BinaryOperatorNode(boolNode, stringNode, null);
        BinaryOperatorNode testnode3 = new BinaryOperatorNode(floatNode, floatNode, null);

        Type testfn1 = typeChecker.binaryOperatorTypeCheck(testnode1);
        Type testfn2 = typeChecker.binaryOperatorTypeCheck(testnode2);
        Type testfn3 = typeChecker.binaryOperatorTypeCheck(testnode3);

        assertEquals(Type.INT, testfn1, "Should have returned int");
        assertEquals(Type.UNKNOWN, testfn2, "Should have returned unknown");
        assertEquals(Type.FLOAT, testfn3, "Should have returned float");

    }

    @Test
    void testErrorHandler() {
        String testString = "test1";
        String testString2 = "test2";
        typeChecker.errorHandler(testString);
        typeChecker.errorHandler(testString2);
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
    void testGetType() {

    }

    @Test
    void testGetVariable() {

    }

    @Test
    void testRelationOperatorTypeCheck() {

        IntNode intNode = new IntNode(0);
        BoolNode boolNode = new BoolNode(null);
        StringNode stringNode = new StringNode(null);
        FloatNode floatNode = new FloatNode("2.2");

        RelationsAndLogicalOperatorNode testnode1 = new RelationsAndLogicalOperatorNode(stringNode, floatNode, null);
        RelationsAndLogicalOperatorNode testnode2 = new RelationsAndLogicalOperatorNode(boolNode, boolNode, null);
        RelationsAndLogicalOperatorNode testnode3 = new RelationsAndLogicalOperatorNode(intNode, intNode, null);
        Type testfn1 = typeChecker.relationOperatorTypeCheck(testnode1);
        Type testfn2 = typeChecker.relationOperatorTypeCheck(testnode2);
        Type testfn3 = typeChecker.relationOperatorTypeCheck(testnode3);

        assertEquals(Type.UNKNOWN, testfn1, "Should return Type unkown");
        assertEquals(Type.BOOLEAN, testfn2, "Should have Type bool");
        assertEquals(Type.BOOLEAN, testfn3, "Should have Type bool");
    }

    @Test
    void testVisitAssignNode() {

    }

    @Test
    void testVisitBlockNode() {

    }

    @Test
    void testVisitFunctionCall() {

    }

    @Test
    void testVisitFunctionDefinition() {

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
        typeChecker.visitIfStatement(node1);
        Type correctType1 = typeChecker.getType(expressionNodeList.get(0));
        Type correctType2 = typeChecker.getType(expressionNodeList.get(1));
        assertEquals(Type.BOOLEAN, correctType1);
        assertEquals(Type.BOOLEAN, correctType2);


        expressionNodeList.add(new ExpressionNode(new IntNode("2")));
        blockNodeList.add(new BlockNode());
        IfStatementNode node2 = new IfStatementNode(blockNodeList, expressionNodeList);
        typeChecker.visitIfStatement(node2);
        Type errorType = typeChecker.getType(expressionNodeList.get(2));
        assertEquals(Type.INT, errorType, "If statements expression must resolve to bool expression, and this resolve to Type:INT");
    }

    @Test
    void testVisitWhileLoop() {
        ExpressionNode expressionNode1 = new ExpressionNode(new BoolNode("true"));
        BlockNode blockNode = new BlockNode();

        typeChecker.visitWhileLoop(new WhileLoopNode(expressionNode1, blockNode));
        Type correctType = typeChecker.getType(expressionNode1);
        assertEquals(Type.BOOLEAN, correctType);

        ExpressionNode expressionNode2 = new ExpressionNode(new StringNode("hello"));
        Type errorType = typeChecker.getType(expressionNode2);
        typeChecker.visitWhileLoop(new WhileLoopNode(expressionNode2, blockNode));
        assertEquals(Type.STRING, errorType, "While loop expresion must resolve to bool expresion, and this resolve to Type:" + errorType);
    }

    @Test
    void testVisitProgramNode() {

    }

    @Test
    void testVisitReturnNode() {

    }

    @Test
    void testVisitStatements() {

    }

    @Test
    void testVisitStruct() {

    }

    @Test
    void testVisitor() {

    }
}
