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
    public void testSimpleFunction() throws Exception { // Declare the exception
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
}
