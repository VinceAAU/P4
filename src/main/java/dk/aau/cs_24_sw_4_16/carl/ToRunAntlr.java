package dk.aau.cs_24_sw_4_16.carl;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.File;
import java.io.IOException;

public class ToRunAntlr {
    public static void main(String[] args) {
        try {
            File file = new File("C:\\Users\\shadi\\Documents\\GitHub\\P4\\src\\main\\java\\dk\\aau\\cs_24_sw_4_16\\carl\\test.ss");
            CharStream inputStream = CharStreams.fromFileName(file.getAbsolutePath());
            CARLLexer speakLexer = new CARLLexer(inputStream);
            CommonTokenStream commonTokenStream = new CommonTokenStream(speakLexer);
            CARLParser speakParser = new CARLParser(commonTokenStream);
            CARLParser.ProgramContext programContext = speakParser.program();

            // Create a visitor instance
            AntlrToStatement visitor = new AntlrToStatement();

            // Visit the parse tree with the visitor
            visitor.visit(programContext);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
