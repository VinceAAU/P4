package dk.aau.cs_24_sw_4_16.carl.Interpreter;

import dk.aau.cs_24_sw_4_16.carl.CstToAst.*;

import java.util.Deque;
import java.util.HashMap;
import java.util.Stack;

public class InbuildClasses {
    public static void print(FunctionCallNode node, Stack<HashMap<String, AstNode>> scopes, Deque<Integer> activeScope) {
        StringBuilder toPrint = new StringBuilder();
        for (AstNode argument : node.getArguments()) {
            if (argument instanceof StatementNode) {
                toPrint.append(((StatementNode) argument).getNode()).append(" ");
            } else if (argument instanceof IdentifierNode) {
                if (scopes.getFirst().containsKey(argument.toString())) {
                    toPrint.append(scopes.getFirst().get(argument.toString()).toString()).append(" ");
                } else {
                    for (int i = activeScope.getLast(); i < scopes.size(); i++) {
                        if (scopes.get(i).containsKey(argument.toString())) {
                            toPrint.append(scopes.get(i).get(argument.toString()).toString()).append(" ");
                        }
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
