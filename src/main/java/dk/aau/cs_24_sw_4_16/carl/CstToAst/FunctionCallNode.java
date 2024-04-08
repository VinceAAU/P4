package dk.aau.cs_24_sw_4_16.carl.CstToAst;

import java.util.List;

public class FunctionCallNode extends AstNode {
    private String functionName;
    private List<AstNode> arguments;

    public FunctionCallNode(String functionName, List<AstNode> arguments) {
        this.functionName = functionName;
        this.arguments = arguments;
    }

    public String getFunctionName() {
        return functionName;
    }

    public List<AstNode> getArguments() {
        return arguments;
    }

    @Override
    public String toString() {
        return "FunctionCallNode{" +
                "functionName='" + functionName + '\'' +
                ", arguments=" + arguments +
                '}';
    }

}
