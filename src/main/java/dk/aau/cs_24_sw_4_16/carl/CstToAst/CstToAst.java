package dk.aau.cs_24_sw_4_16.carl.CstToAst;


import dk.aau.cs_24_sw_4_16.carl.CARLLexer;
import dk.aau.cs_24_sw_4_16.carl.CARLParser;
import dk.aau.cs_24_sw_4_16.carl.Interpreter.Interpreter;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.FileInputStream;
import java.io.IOException;

public class CstToAst {

    public static void main(String[] args) throws IOException {
        try {
            FileInputStream fileInput = new FileInputStream("test.ss");

            CARLLexer lexer = new CARLLexer(CharStreams.fromStream(fileInput));
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            CARLParser parser = new CARLParser(tokens);

            ParseTree tree = parser.program();
            CstToAstVisitor visitor = new CstToAstVisitor();
            AstNode astRoot = visitor.visit(tree);
            Interpreter inter = new Interpreter();
            inter.visit(astRoot);
        }
        catch (Exception e){
            System.out.println(e.toString());
        }
    }
}
