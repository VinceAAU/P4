package dk.aau.cs_24_sw_4_16.carl;

import dk.aau.cs_24_sw_4_16.carl.CstToAst.CstToAstVisitor;
import dk.aau.cs_24_sw_4_16.carl.CstToAst.ProgramNode;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

public class TestIfStatement {
    public static void main(String... args) {
        String codeSnippet = "var x : int = 0\n if (x > 0) { \nx = 1\n } else { x = 2\n }";
        CARLLexer lexer = new CARLLexer(CharStreams.fromString(codeSnippet));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CARLParser parser = new CARLParser(tokens);
        CstToAstVisitor visitor = new CstToAstVisitor();
        ProgramNode ast = (ProgramNode) visitor.visit(parser.program());

        System.out.println(ast);
    }
}