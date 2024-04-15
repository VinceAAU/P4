package dk.aau.cs_24_sw_4_16.carl.Interpreter;

import dk.aau.cs_24_sw_4_16.carl.CstToAst.*;

import java.util.HashMap;
import java.util.Stack;

public class InbuildClasses {
    public static void print(FunctionCallNode node, Stack<HashMap<String, AstNode>> scopes)
    {
        StringBuilder toPrint = new StringBuilder();
        for (AstNode argument : node.getArguments()) {
            if (argument instanceof StatementNode) {
                toPrint.append(((StatementNode) argument).getNode()).append(" ");
            } else if (argument instanceof IdentifierNode) {
                for (HashMap<String, AstNode> vTable : scopes) {
                    if (vTable.containsKey(argument.toString())) {
                        toPrint.append(vTable.get(argument.toString()).toString()).append(" ");
                    }
                }
            } else if (argument instanceof FloatNode) {
                toPrint.append(((FloatNode) argument).getValue());
            } else if (argument instanceof IntNode) {
                toPrint.append(((IntNode) argument).getValue());
            } else if (argument instanceof StringNode) {
                toPrint.append(((StringNode) argument).getValue());
            } else if (argument instanceof BoolNode) {
                toPrint.append(((BoolNode) argument).getValue());
            }
        }
        System.out.println(toPrint);
    }
}
