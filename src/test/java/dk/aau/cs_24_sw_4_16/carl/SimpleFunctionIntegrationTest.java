package dk.aau.cs_24_sw_4_16.carl;

import dk.aau.cs_24_sw_4_16.carl.CstToAst.AstNode;
import dk.aau.cs_24_sw_4_16.carl.CstToAst.CstToAstVisitor;
import dk.aau.cs_24_sw_4_16.carl.Interpreter.EvaluatorExecutor;
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

        EvaluatorExecutor interpreter = new EvaluatorExecutor();
        interpreter.visit(astRoot);

        assertEquals("test".trim(), outContent.toString().trim(), "expected the output to be test");
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

        EvaluatorExecutor interpreter = new EvaluatorExecutor();
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
        // System.out.println(astRoot);
        EvaluatorExecutor interpreter = new EvaluatorExecutor();
        interpreter.visit(astRoot);

        assertEquals("2".trim(), outContent.toString().trim(),
                "Expected the output to be 2 because 'l' was incremented once.");
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

        EvaluatorExecutor interpreter = new EvaluatorExecutor();
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

        EvaluatorExecutor interpreter = new EvaluatorExecutor();
        interpreter.visit(astRoot);

        // Assertions can be extended based on the print output or internal state checks
        assertEquals("1".trim(), outContent.toString().trim(),
                "Expected the output to be 1 because x > 5 and therefore, y should be set to 1.");
    }

    @Test
    public void testingIfElseChain() {
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
        AstNode astRoot = treemaker(code);
        EvaluatorExecutor interpreter = new EvaluatorExecutor();
        interpreter.visit(astRoot);
        assertEquals("3".trim(), outContent.toString().trim(),
                "Expected the output to be 3 because x does not meet the other conditions and therefore, y should be set to 3.");
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

        EvaluatorExecutor interpreter = new EvaluatorExecutor();
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

        EvaluatorExecutor interpreter = new EvaluatorExecutor();
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

        EvaluatorExecutor interpreter = new EvaluatorExecutor();
        interpreter.visit(astRoot);

        // Assertions can be extended based on the print output or internal state checks
        assertEquals("42".trim(), outContent.toString().trim(), "Expected the output to be 42");
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

        EvaluatorExecutor interpreter = new EvaluatorExecutor();
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

        EvaluatorExecutor interpreter = new EvaluatorExecutor();
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

        EvaluatorExecutor interpreter = new EvaluatorExecutor();
        interpreter.visit(astRoot);

        // Assertions can be extended based on the print output or internal state checks
        assertEquals("1".trim(), outContent.toString().trim(), "Expected the output to be 1 because 10 % 3 equals 1.");
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
    public void testingInt() throws Exception {
        String code = """
                var x : int = 42
                print(x)
                """;

        InputStream stream = new ByteArrayInputStream(code.getBytes(StandardCharsets.UTF_8));
        CARLLexer lexer = new CARLLexer(CharStreams.fromStream(stream));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CARLParser parser = new CARLParser(tokens);
        ParseTree tree = parser.program();
        CstToAstVisitor visitor = new CstToAstVisitor();
        AstNode astRoot = visitor.visit(tree);

        EvaluatorExecutor interpreter = new EvaluatorExecutor();
        interpreter.visit(astRoot);

        assertEquals("42".trim(), outContent.toString().trim());
    }

    @Test
    public void testingFloat() throws Exception {
        String code = """
                var x : float = 42.5
                print(x)
                """;

        InputStream stream = new ByteArrayInputStream(code.getBytes(StandardCharsets.UTF_8));
        CARLLexer lexer = new CARLLexer(CharStreams.fromStream(stream));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CARLParser parser = new CARLParser(tokens);
        ParseTree tree = parser.program();
        CstToAstVisitor visitor = new CstToAstVisitor();
        AstNode astRoot = visitor.visit(tree);

        EvaluatorExecutor interpreter = new EvaluatorExecutor();
        interpreter.visit(astRoot);

        assertEquals("42.5".trim(), outContent.toString().trim());
    }

    @Test
    public void testingString() throws Exception {
        String code = """
                var x : string = "test"
                print(x)
                """;

        InputStream stream = new ByteArrayInputStream(code.getBytes(StandardCharsets.UTF_8));
        CARLLexer lexer = new CARLLexer(CharStreams.fromStream(stream));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CARLParser parser = new CARLParser(tokens);
        ParseTree tree = parser.program();
        CstToAstVisitor visitor = new CstToAstVisitor();
        AstNode astRoot = visitor.visit(tree);

        EvaluatorExecutor interpreter = new EvaluatorExecutor();
        interpreter.visit(astRoot);

        assertEquals("test".trim(), outContent.toString().trim());
    }

    @Test
    public void testingBool() throws Exception {
        String code = """
                var x : bool = true
                print(x)
                """;

        InputStream stream = new ByteArrayInputStream(code.getBytes(StandardCharsets.UTF_8));
        CARLLexer lexer = new CARLLexer(CharStreams.fromStream(stream));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CARLParser parser = new CARLParser(tokens);
        ParseTree tree = parser.program();
        CstToAstVisitor visitor = new CstToAstVisitor();
        AstNode astRoot = visitor.visit(tree);

        EvaluatorExecutor interpreter = new EvaluatorExecutor();
        interpreter.visit(astRoot);

        assertEquals("true".trim(), outContent.toString().trim());
    }
    // @Test
    // public void testingRelationalOperators() throws Exception {
    // String code = """
    // var f : int = 3
    // var g : int = 4
    // var h : bool = f < g
    // print(h)
    // """;
    //
    // InputStream stream = new
    // ByteArrayInputStream(code.getBytes(StandardCharsets.UTF_8));
    // CARLLexer lexer = new CARLLexer(CharStreams.fromStream(stream));
    // CommonTokenStream tokens = new CommonTokenStream(lexer);
    // CARLParser parser = new CARLParser(tokens);
    // ParseTree tree = parser.program();
    // CstToAstVisitor visitor = new CstToAstVisitor();
    // AstNode astRoot = visitor.visit(tree);
    //
    // Interpreter interpreter = new Interpreter();
    // interpreter.visit(astRoot);
    //
    // assertEquals("true", outContent.toString().trim(), "Expected the output to be
    // true because 3 < 4.");
    // }

    @Test
    public void testingSeed() throws Exception {
        String code = """
                setSeed(42069)
                var test : int = 1..20
                print(test)
                """;

        InputStream stream = new ByteArrayInputStream(code.getBytes(StandardCharsets.UTF_8));
        CARLLexer lexer = new CARLLexer(CharStreams.fromStream(stream));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CARLParser parser = new CARLParser(tokens);
        ParseTree tree = parser.program();
        CstToAstVisitor visitor = new CstToAstVisitor();
        AstNode astRoot = visitor.visit(tree);

        EvaluatorExecutor interpreter = new EvaluatorExecutor();
        interpreter.visit(astRoot);

        assertEquals("14".trim(), outContent.toString().trim());
    }

    @Test
    public void testingSeed2() throws Exception {
        String code = """
                setSeed(555)
                var test : int = 1..20
                print(test)
                """;

        InputStream stream = new ByteArrayInputStream(code.getBytes(StandardCharsets.UTF_8));
        CARLLexer lexer = new CARLLexer(CharStreams.fromStream(stream));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CARLParser parser = new CARLParser(tokens);
        ParseTree tree = parser.program();
        CstToAstVisitor visitor = new CstToAstVisitor();
        AstNode astRoot = visitor.visit(tree);

        EvaluatorExecutor interpreter = new EvaluatorExecutor();
        interpreter.visit(astRoot);

        assertEquals("6".trim(), outContent.toString().trim());
    }

    @Test
    public void testingEntireProcedure() throws Exception {
        String code = """
                setSeed(42069)
                generateMap(20,25)
                generateRooms(3,5,7)
                generateCorridors()
                generateSpawns()
                printMap()
                """;

        InputStream stream = new ByteArrayInputStream(code.getBytes(StandardCharsets.UTF_8));
        CARLLexer lexer = new CARLLexer(CharStreams.fromStream(stream));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CARLParser parser = new CARLParser(tokens);
        ParseTree tree = parser.program();
        CstToAstVisitor visitor = new CstToAstVisitor();
        AstNode astRoot = visitor.visit(tree);

        EvaluatorExecutor interpreter = new EvaluatorExecutor();
        interpreter.visit(astRoot);

        // do not touch the indentation of the check it will break
        assertEquals(
                """
                        {"map" : {
                            "size":{
                            "height" : 10,
                            "width" : 10
                             },
                             "tiles": [

                        {"row":["w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w"]},
                        {"row":["w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w"]},
                        {"row":["w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","f","f","f","f","f","w","w","w"]},
                        {"row":["w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","f","f","f","f","f","w","w","w"]},
                        {"row":["w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","f","f","f","p","f","w","w","w"]},
                        {"row":["w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","f","f","f","f","f","f","w","w","w"]},
                        {"row":["w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","f","f","f","f","f","f","w","w","w"]},
                        {"row":["w","w","w","w","w","w","f","f","f","f","f","w","w","w","w","w","f","f","f","f","f","f","w","w","w"]},
                        {"row":["w","w","w","w","w","w","f","f","f","f","f","w","w","w","w","w","f","w","w","w","w","w","w","w","w"]},
                        {"row":["w","w","w","w","w","w","f","f","f","f","f","w","w","w","f","f","f","f","f","w","w","w","w","w","w"]},
                        {"row":["w","w","w","w","w","w","f","f","f","f","f","w","w","w","f","f","f","f","f","w","w","w","w","w","w"]},
                        {"row":["w","w","w","w","w","w","f","f","f","f","f","w","w","w","f","f","f","f","f","w","w","w","w","w","w"]},
                        {"row":["w","w","w","w","w","w","f","f","f","f","f","f","f","f","f","f","f","f","f","w","w","w","w","w","w"]},
                        {"row":["w","w","w","w","w","w","w","w","w","w","w","w","w","w","f","f","f","f","f","w","w","w","w","w","w"]},
                        {"row":["w","w","w","w","w","w","w","w","w","w","w","w","w","w","f","f","f","f","f","w","w","w","w","w","w"]},
                        {"row":["w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w"]},
                        {"row":["w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w"]},
                        {"row":["w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w"]},
                        {"row":["w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w"]},
                        {"row":["w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w"]}],
                        "level" : 1,
                        "type" : "Room",
                        "tileInformation":[
                        {
                        "symbol" : "w" ,
                        "info" : {
                            "name" : "Wall"
                           }
                        },
                        {
                        "symbol" : "f" ,
                        "info" : {
                            "name" : "Floor"
                           }
                        },
                        {
                        "symbol" : "p" ,
                        "info" : {
                            "name" : "Player"
                           }
                        }
                        ]}}
                                        """
                        .trim(),
                outContent.toString().trim());
    }

    // * +++++++++++++++++++++++ DONT YOU DARE TOUCH THIS TEST +++++++++++++++++++++
    // *//
    @Test
    public void testingEntireProcedure2() throws Exception {
        String code = """
                setSeed(242)
                generateMap(20,25)
                generateRooms(3,5,7)
                generateCorridors()
                generateSpawns()
                printMap()
                                """;

        InputStream stream = new ByteArrayInputStream(code.getBytes(StandardCharsets.UTF_8));
        CARLLexer lexer = new CARLLexer(CharStreams.fromStream(stream));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CARLParser parser = new CARLParser(tokens);
        ParseTree tree = parser.program();
        CstToAstVisitor visitor = new CstToAstVisitor();
        AstNode astRoot = visitor.visit(tree);

        EvaluatorExecutor interpreter = new EvaluatorExecutor();
        interpreter.visit(astRoot);

        assertEquals(
                """
                        {"map" : {
                            "size":{
                            "height" : 10,
                            "width" : 10
                             },
                             "tiles": [

                        {"row":["w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w"]},
                        {"row":["w","w","w","w","w","w","w","f","f","f","f","f","f","w","w","w","w","w","w","w","w","w","w","w","w"]},
                        {"row":["w","w","w","w","w","w","w","f","f","f","f","f","f","w","w","w","w","w","w","w","w","w","w","w","w"]},
                        {"row":["w","w","w","w","w","w","w","f","f","f","f","f","f","w","w","w","w","w","w","w","w","w","w","w","w"]},
                        {"row":["w","w","w","w","w","w","w","f","f","f","f","f","f","w","w","w","w","w","w","w","w","w","w","w","w"]},
                        {"row":["w","w","w","w","w","w","w","f","f","f","f","f","f","w","w","w","w","w","w","w","w","w","w","w","w"]},
                        {"row":["w","w","w","w","w","w","w","w","w","w","f","w","w","w","f","f","f","f","f","w","w","w","w","w","w"]},
                        {"row":["w","w","w","w","w","w","w","w","w","w","f","w","w","w","f","f","f","f","f","w","w","w","w","w","w"]},
                        {"row":["w","w","w","w","w","w","w","w","w","w","f","w","w","w","f","f","f","f","f","w","w","w","w","w","w"]},
                        {"row":["w","w","w","w","w","w","w","w","w","w","f","f","f","f","f","f","f","f","f","w","w","w","w","w","w"]},
                        {"row":["w","w","w","w","w","w","w","w","w","w","w","w","w","w","f","f","f","f","f","w","w","w","w","w","w"]},
                        {"row":["w","w","w","w","w","w","w","w","w","w","w","w","w","w","f","f","f","f","f","w","w","w","w","w","w"]},
                        {"row":["w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","f","w","w","w","w","w","w","w","w"]},
                        {"row":["w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","f","w","w","w","w","w","w","w","w"]},
                        {"row":["w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","f","f","f","f","f","w","w","w","w","w"]},
                        {"row":["w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","f","f","f","f","f","w","w","w","w","w"]},
                        {"row":["w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","f","f","f","f","f","w","w","w","w","w"]},
                        {"row":["w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","f","f","f","f","p","w","w","w","w","w"]},
                        {"row":["w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","f","f","f","f","f","w","w","w","w","w"]},
                        {"row":["w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w"]}],
                        "level" : 1,
                        "type" : "Room",
                        "tileInformation":[
                        {
                        "symbol" : "w" ,
                        "info" : {
                            "name" : "Wall"
                           }
                        },
                        {
                        "symbol" : "f" ,
                        "info" : {
                            "name" : "Floor"
                           }
                        },
                        {
                        "symbol" : "p" ,
                        "info" : {
                            "name" : "Player"
                           }
                        }
                        ]}}
                        """
                        .trim(),
                outContent.toString().trim());
    }

    @Test
    public void testingStruct1() throws Exception {
        String code = """
                var Goblin : enemy ={
                        var difficulty : int = 1
                        var health : int = 5300
                        var symbol : string= "O"
                    }
                        var test : int = 5
                    test = enemy.Goblin.health
                    print(test)

                            """;

        InputStream stream = new ByteArrayInputStream(code.getBytes(StandardCharsets.UTF_8));
        CARLLexer lexer = new CARLLexer(CharStreams.fromStream(stream));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CARLParser parser = new CARLParser(tokens);
        ParseTree tree = parser.program();
        CstToAstVisitor visitor = new CstToAstVisitor();
        AstNode astRoot = visitor.visit(tree);

        EvaluatorExecutor interpreter = new EvaluatorExecutor();
        interpreter.visit(astRoot);

        assertEquals("5300".trim(), outContent.toString().trim());
    }

    @Test
    public void testingStruct2() throws Exception {
        String code = """
                var Goblin : enemy ={
                        var difficulty : int = 1
                        var health : int = 5300
                        var symbol : string= "O"
                        var gender : string= "orcacnian"
                    }
                        var test : string = "ssssssss"
                    test = enemy.Goblin.gender
                    print(test)

                            """;

        InputStream stream = new ByteArrayInputStream(code.getBytes(StandardCharsets.UTF_8));
        CARLLexer lexer = new CARLLexer(CharStreams.fromStream(stream));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CARLParser parser = new CARLParser(tokens);
        ParseTree tree = parser.program();
        CstToAstVisitor visitor = new CstToAstVisitor();
        AstNode astRoot = visitor.visit(tree);

        EvaluatorExecutor interpreter = new EvaluatorExecutor();
        interpreter.visit(astRoot);

        assertEquals("orcacnian".trim(), outContent.toString().trim());
    }

    @Test
    public void testingStruct3() throws Exception {
        String code = """
                var Goblin : enemy ={
                        var difficulty : int = 1
                        var health : int = 5300
                        var symbol : string= "O"
                        var gender : string= "orcacnian"
                    }
                        var test : string = "ssssssss"
                    test = enemy.Goblin.gender
                    print(test)

                            """;

        InputStream stream = new ByteArrayInputStream(code.getBytes(StandardCharsets.UTF_8));
        CARLLexer lexer = new CARLLexer(CharStreams.fromStream(stream));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CARLParser parser = new CARLParser(tokens);
        ParseTree tree = parser.program();
        CstToAstVisitor visitor = new CstToAstVisitor();
        AstNode astRoot = visitor.visit(tree);

        EvaluatorExecutor interpreter = new EvaluatorExecutor();
        interpreter.visit(astRoot);

        assertEquals("orcacnian".trim(), outContent.toString().trim());
    }

    @Test
    public void testingStruct4() throws Exception {
        String code = """
                var Goblin : enemy ={
                        var difficulty : int = 1
                        var health : int = 5300
                        var symbol : string= "O"
                        var gender : string= "orcacnian"
                    }
                        var test : string = "ssssssss"
                    test = enemy.get(0).gender
                    print(test)

                            """;

        InputStream stream = new ByteArrayInputStream(code.getBytes(StandardCharsets.UTF_8));
        CARLLexer lexer = new CARLLexer(CharStreams.fromStream(stream));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CARLParser parser = new CARLParser(tokens);
        ParseTree tree = parser.program();
        CstToAstVisitor visitor = new CstToAstVisitor();
        AstNode astRoot = visitor.visit(tree);

        EvaluatorExecutor interpreter = new EvaluatorExecutor();
        interpreter.visit(astRoot);

        assertEquals("orcacnian".trim(), outContent.toString().trim());
    }

    @Test
    public void testingStruct5() throws Exception {
        String code = """
                var Goblin : enemy ={
                        var difficulty : int = 1
                        var health : int = 5300
                        var symbol : string= "O"
                        var gender : string= "orcacnian"
                    }
                        var test : int =44
                    test = enemy.size()
                    print(test)

                            """;

        InputStream stream = new ByteArrayInputStream(code.getBytes(StandardCharsets.UTF_8));
        CARLLexer lexer = new CARLLexer(CharStreams.fromStream(stream));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CARLParser parser = new CARLParser(tokens);
        ParseTree tree = parser.program();
        CstToAstVisitor visitor = new CstToAstVisitor();
        AstNode astRoot = visitor.visit(tree);

        EvaluatorExecutor interpreter = new EvaluatorExecutor();
        interpreter.visit(astRoot);

        assertEquals("1".trim(), outContent.toString().trim());
    }
}
