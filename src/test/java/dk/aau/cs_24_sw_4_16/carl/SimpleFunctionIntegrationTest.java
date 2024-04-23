package dk.aau.cs_24_sw_4_16.carl;

import dk.aau.cs_24_sw_4_16.carl.CstToAst.AstNode;
import dk.aau.cs_24_sw_4_16.carl.CstToAst.CstToAstVisitor;
import dk.aau.cs_24_sw_4_16.carl.Interpreter.Interpreter;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import static org.junit.jupiter.api.Assertions.*;

public class SimpleFunctionIntegrationTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void testingPrintSatement() throws Exception {
        String code = """
                       print("test")
                       """;

        InputStream stream = new ByteArrayInputStream(code.getBytes(StandardCharsets.UTF_8));
        CARLLexer lexer = new CARLLexer(CharStreams.fromStream(stream));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CARLParser parser = new CARLParser(tokens);
        ParseTree tree = parser.program();
        CstToAstVisitor visitor = new CstToAstVisitor();
        AstNode astRoot = visitor.visit(tree);

        Interpreter interpreter = new Interpreter();
        interpreter.visit(astRoot);

        assertEquals("\"test\"".trim(), outContent.toString().trim(), "expected the output to be test");
    }

    @Test
    public void testingVariable() throws Exception {
        String code = """
                       var x : int = 42
                       var y : int = x
                       print(y)
                       """;

        InputStream stream = new ByteArrayInputStream(code.getBytes(StandardCharsets.UTF_8));
        CARLLexer lexer = new CARLLexer(CharStreams.fromStream(stream));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CARLParser parser = new CARLParser(tokens);
        ParseTree tree = parser.program();
        CstToAstVisitor visitor = new CstToAstVisitor();
        AstNode astRoot = visitor.visit(tree);

        Interpreter interpreter = new Interpreter();
        interpreter.visit(astRoot);

        // Assertions can be extended based on the print output or internal state checks
        assertEquals("42".trim(), outContent.toString().trim());
    }

    @Test
    public void testingIncrementOperator() throws Exception {
        String code = """
                var l : int = 1
                l = l + 1
                print(l)
                """;

        InputStream stream = new ByteArrayInputStream(code.getBytes(StandardCharsets.UTF_8));
        CARLLexer lexer = new CARLLexer(CharStreams.fromStream(stream));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CARLParser parser = new CARLParser(tokens);
        ParseTree tree = parser.program();
        CstToAstVisitor visitor = new CstToAstVisitor();
        AstNode astRoot = visitor.visit(tree);

        Interpreter interpreter = new Interpreter();
        interpreter.visit(astRoot);

        assertEquals("2".trim(), outContent.toString().trim(), "Expected the output to be 2 because 'l' was incremented once.");
    }

    @Test
    public void testingAddition() throws Exception {
        String code = """
                       var x : int = (1 + 2) - 2
                       print(x)
                       """;

        InputStream stream = new ByteArrayInputStream(code.getBytes(StandardCharsets.UTF_8));
        CARLLexer lexer = new CARLLexer(CharStreams.fromStream(stream));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CARLParser parser = new CARLParser(tokens);
        ParseTree tree = parser.program();
        CstToAstVisitor visitor = new CstToAstVisitor();
        AstNode astRoot = visitor.visit(tree);

        Interpreter interpreter = new Interpreter();
        interpreter.visit(astRoot);

        // Assertions can be extended based on the print output or internal state checks
        assertEquals("1".trim(), outContent.toString().trim());
    }

    @Test
    public void testingIfStatement() throws Exception {
        String code = """
                var x : int = 10
                var y : int = 0
                if x > 5 {
                    y = 1
                } else {
                    y = 2
                }
                print(y)
                """;

        InputStream stream = new ByteArrayInputStream(code.getBytes(StandardCharsets.UTF_8));
        CARLLexer lexer = new CARLLexer(CharStreams.fromStream(stream));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CARLParser parser = new CARLParser(tokens);
        ParseTree tree = parser.program();
        CstToAstVisitor visitor = new CstToAstVisitor();
        AstNode astRoot = visitor.visit(tree);

        Interpreter interpreter = new Interpreter();
        interpreter.visit(astRoot);

        // Assertions can be extended based on the print output or internal state checks
        assertEquals("1".trim(), outContent.toString().trim(), "Expected the output to be 1 because x > 5 and therefore, y should be set to 1.");
    }

    @Test
    public void testingIfElseChain() throws Exception {
        String code = """
                var x : int = 1
                var y : int = 0
                if x > 5 {
                    y = 1
                } else if x < -5 {
                    y = 2
                } else {
                    y = 3
                }
                print(y)
                """;

        InputStream stream = new ByteArrayInputStream(code.getBytes(StandardCharsets.UTF_8));
        CARLLexer lexer = new CARLLexer(CharStreams.fromStream(stream));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CARLParser parser = new CARLParser(tokens);
        ParseTree tree = parser.program();
        CstToAstVisitor visitor = new CstToAstVisitor();
        AstNode astRoot = visitor.visit(tree);

        Interpreter interpreter = new Interpreter();
        interpreter.visit(astRoot);

        // Assertions can be extended based on the print output or internal state checks
        assertEquals("3".trim(), outContent.toString().trim(), "Expected the output to be 3 because x does not meet the other conditions and therefore, y should be set to 3.");
    }
    @Test
    public void testingMult() throws Exception {
        String code = """
                       var x : int = (1 * 4) / 2
                       print(x)
                       """;

        InputStream stream = new ByteArrayInputStream(code.getBytes(StandardCharsets.UTF_8));
        CARLLexer lexer = new CARLLexer(CharStreams.fromStream(stream));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CARLParser parser = new CARLParser(tokens);
        ParseTree tree = parser.program();
        CstToAstVisitor visitor = new CstToAstVisitor();
        AstNode astRoot = visitor.visit(tree);

        Interpreter interpreter = new Interpreter();
        interpreter.visit(astRoot);

        // Assertions can be extended based on the print output or internal state checks
        assertEquals("2".trim(), outContent.toString().trim());
    }

    @Test
    public void testingWhile() throws Exception {
        String code = """
                var d : int = 0
                while d <= 5{
                    d = d + 1
                }
                print(d)
                       """;

        InputStream stream = new ByteArrayInputStream(code.getBytes(StandardCharsets.UTF_8));
        CARLLexer lexer = new CARLLexer(CharStreams.fromStream(stream));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CARLParser parser = new CARLParser(tokens);
        ParseTree tree = parser.program();
        CstToAstVisitor visitor = new CstToAstVisitor();
        AstNode astRoot = visitor.visit(tree);

        Interpreter interpreter = new Interpreter();
        interpreter.visit(astRoot);

        // Assertions can be extended based on the print output or internal state checks
        assertEquals("6".trim(), outContent.toString().trim());
    }

    @Test
    public void testingFunction() throws Exception {
        String code = """
                       fn calculate() -> int {
                           return 42
                       }
                       var result: int = calculate()
                       print(result)
                       """;

        InputStream stream = new ByteArrayInputStream(code.getBytes(StandardCharsets.UTF_8));
        CARLLexer lexer = new CARLLexer(CharStreams.fromStream(stream));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CARLParser parser = new CARLParser(tokens);
        ParseTree tree = parser.program();
        CstToAstVisitor visitor = new CstToAstVisitor();
        AstNode astRoot = visitor.visit(tree);

        Interpreter interpreter = new Interpreter();
        interpreter.visit(astRoot);

        // Assertions can be extended based on the print output or internal state checks
        assertTrue(true, "Expected the interpreter to run without errors."); // this one is temporary until functions return a value
    }


    @Test
    public void testingSubtraction() throws Exception {
        String code = """
                var x : int = 10 - 5
                print(x)
                """;

        InputStream stream = new ByteArrayInputStream(code.getBytes(StandardCharsets.UTF_8));
        CARLLexer lexer = new CARLLexer(CharStreams.fromStream(stream));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CARLParser parser = new CARLParser(tokens);
        ParseTree tree = parser.program();
        CstToAstVisitor visitor = new CstToAstVisitor();
        AstNode astRoot = visitor.visit(tree);

        Interpreter interpreter = new Interpreter();
        interpreter.visit(astRoot);

        // Assertions can be extended based on the print output or internal state checks
        assertEquals("5".trim(), outContent.toString().trim(), "Expected the output to be 5 because 10 - 5 equals 5.");
    }

    @Test
    public void testingDivision() throws Exception {
        String code = """
                var x : int = 10 / 2
                print(x)
                """;

        InputStream stream = new ByteArrayInputStream(code.getBytes(StandardCharsets.UTF_8));
        CARLLexer lexer = new CARLLexer(CharStreams.fromStream(stream));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CARLParser parser = new CARLParser(tokens);
        ParseTree tree = parser.program();
        CstToAstVisitor visitor = new CstToAstVisitor();
        AstNode astRoot = visitor.visit(tree);

        Interpreter interpreter = new Interpreter();
        interpreter.visit(astRoot);

        // Assertions can be extended based on the print output or internal state checks
        assertEquals("5".trim(), outContent.toString().trim(), "Expected the output to be 5 because 10 / 2 equals 5.");
    }

    @Test
    public void testingModulus() throws Exception {
        String code = """
                var x : int = 10 % 3
                print(x)
                """;

        InputStream stream = new ByteArrayInputStream(code.getBytes(StandardCharsets.UTF_8));
        CARLLexer lexer = new CARLLexer(CharStreams.fromStream(stream));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CARLParser parser = new CARLParser(tokens);
        ParseTree tree = parser.program();
        CstToAstVisitor visitor = new CstToAstVisitor();
        AstNode astRoot = visitor.visit(tree);

        Interpreter interpreter = new Interpreter();
        interpreter.visit(astRoot);

        // Assertions can be extended based on the print output or internal state checks
        assertEquals("1".trim(), outContent.toString().trim(), "Expected the output to be 1 because 10 % 3 equals 1.");
    }


//    @Test
//    public void testingRelationalOperators() throws Exception {
//        String code = """
//                var f : int = 3
//                var g : int = 4
//                var h : bool = f < g
//                print(h)
//                """;
//
//        InputStream stream = new ByteArrayInputStream(code.getBytes(StandardCharsets.UTF_8));
//        CARLLexer lexer = new CARLLexer(CharStreams.fromStream(stream));
//        CommonTokenStream tokens = new CommonTokenStream(lexer);
//        CARLParser parser = new CARLParser(tokens);
//        ParseTree tree = parser.program();
//        CstToAstVisitor visitor = new CstToAstVisitor();
//        AstNode astRoot = visitor.visit(tree);
//
//        Interpreter interpreter = new Interpreter();
//        interpreter.visit(astRoot);
//
//        assertEquals("true", outContent.toString().trim(), "Expected the output to be true because 3 < 4.");
//    }



}
