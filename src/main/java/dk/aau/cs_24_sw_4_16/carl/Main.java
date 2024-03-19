package dk.aau.cs_24_sw_4_16.carl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class Main {
    public static void main(String... args) {
        System.out.println("Hi guys");
        String filePath = "src\\main\\java\\dk\\aau\\cs_24_sw_4_16\\carl\\carl_programs\\print.carl";
        String content = readFile(filePath);
        System.out.println(content);

        // Example code to interpret
        System.out.println("Program executed.");
        String code = "print(1 + 2)";
        Lexer lexer = new Lexer(content);
        List<Token> tokens = lexer.tokenize();
        printTokens(tokens);

        Parser parser = new Parser(tokens);
        ASTNode ast = parser.parse(); // Ensure this method returns the root of your AST

        Evaluator evaluator = new Evaluator();
        if (ast instanceof PrintNode) { // Just as an example, you'd normally handle more gracefully
            ((PrintNode) ast).accept(evaluator);
        }
        if (ast != null) {
            ast.accept(evaluator); // Evaluate the AST starting from its root
        } else {
            System.out.println("No AST generated.");
        }

        System.out.println("Program executed.");
    }

    public static String readFile(String filePath) {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                contentBuilder.append(currentLine).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contentBuilder.toString().trim();
    }
    private static void printTokens(List<Token> tokens) {
        for (Token token : tokens) {
            System.out.println("Token{type=" + token.getType() + ", value='" + token.getValue() + "'}");
        }
    }
}
