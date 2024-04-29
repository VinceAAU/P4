package dk.aau.cs_24_sw_4_16.carl;

import dk.aau.cs_24_sw_4_16.carl.CstToAst.AstNode;
import dk.aau.cs_24_sw_4_16.carl.CstToAst.CstToAstVisitor;
import dk.aau.cs_24_sw_4_16.carl.Interpreter.Interpreter;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import dk.aau.cs_24_sw_4_16.carl.Semantic_A.SemanticAnalyzer;

import java.io.FileInputStream;
import java.io.IOException;

public class Main {
    public static void main(String... args) {
        try {
            /*
             * Java libary takes in filepath, reads file into memory. Can now be acced
             * throught identifier fileinput
             */
            FileInputStream fileInput = new FileInputStream("test.carl");
            // CARLLexer is a generated lexer class for the CARL language.
            // It tokenizes the input stream (breaks the input into meaningful pieces called
            // tokens).
            CARLLexer lexer = new CARLLexer(CharStreams.fromStream(fileInput));
            // CommonTokenStream is a class from ANTLR that is used to store the tokens
            // generated by the lexer.
            // These tokens are used by the parser to create a parse tree.
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            // CARLParser is a generated parser class for the CARL language.
            // It takes a stream of tokens and builds a parse tree based on the grammar of
            // the language.
            CARLParser parser = new CARLParser(tokens);
            // The program() method is a part of the parser that starts the parsing process.
            // It returns a parse tree representing the entire input file.
            ParseTree tree = parser.program();// Her stopper det som Antler har lavet.


            // CstToAstVisitor is a visitor class that converts the parse tree (CST) into an abstract syntax tree (AST).
            // This is typically used to simplify and optimize the tree structure for further processing.
            CstToAstVisitor visitor = new CstToAstVisitor();
            // The visit method walks the parse tree and constructs the AST
            AstNode astRoot = visitor.visit(tree);
            //System.out.println(astRoot);
            System.out.println(astRoot);
            
            SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer();
            semanticAnalyzer.analyze(astRoot);

            // Interpreter is a class that can traverse the AST and interpret or execute the program based on the AST.
            Interpreter inter = new Interpreter();
            
            inter.visit(astRoot);
        }
         catch (IOException e) {
            System.out.println(e.toString());
        }
         catch (Exception e) {
            // Catches any exception that occurs within the try block.
            // Prints the string representation of the exception to standard output.
            System.out.println();
        }
    }
}
