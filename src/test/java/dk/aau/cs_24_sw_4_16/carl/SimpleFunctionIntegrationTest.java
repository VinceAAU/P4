package dk.aau.cs_24_sw_4_16.carl;

import dk.aau.cs_24_sw_4_16.carl.CstToAst.AstNode;
import dk.aau.cs_24_sw_4_16.carl.CstToAst.CstToAstVisitor;
import dk.aau.cs_24_sw_4_16.carl.Interpreter.Interpreter;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import static org.junit.jupiter.api.Assertions.*;

public class SimpleFunctionIntegrationTest {

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

        // Assertions can be extended based on the print output or internal state checks
        assertTrue(true, "Expected the interpreter to run without errors.");
    }

    @Test
    public void testingVariable() throws Exception {
        String code = """
                       var x : int = 43
                       var y : int = x
                       print("y")
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
        assertTrue(true, "Expected the interpreter to run without errors.");
    }



    @Test
    public void testingAddition() throws Exception {
        String code = """
                       print(1 + 1)
                       print(1 - 1)
                       print(1 * 1)
                       print(2 / 1)
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
        assertTrue(true, "Expected the interpreter to run without errors.");
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
        assertTrue(true, "Expected the interpreter to run without errors.");
    }




}
