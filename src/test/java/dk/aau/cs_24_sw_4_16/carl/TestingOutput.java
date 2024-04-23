package dk.aau.cs_24_sw_4_16.carl;

import dk.aau.cs_24_sw_4_16.carl.CstToAst.AstNode;
import dk.aau.cs_24_sw_4_16.carl.CstToAst.CstToAstVisitor;
import dk.aau.cs_24_sw_4_16.carl.Interpreter.Interpreter;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;

public class TestingOutput {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
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

        // Assertions can be extended based on the print output or internal state checks
        assertEquals("\"test\"".trim(), outContent.toString().trim());
    }
    @Test
    public void testingVariable() throws Exception {
        String code = """
                       var x : int = 43
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
        assertEquals("43".trim(), outContent.toString().trim());
    }
}
