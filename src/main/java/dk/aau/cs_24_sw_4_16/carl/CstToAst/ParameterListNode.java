package dk.aau.cs_24_sw_4_16.carl.CstToAst;

import java.util.List;

public class ParameterListNode extends AstNode {
    List<ParameterNode> parameters;

    public ParameterListNode(List<ParameterNode> parameters) {
        this.parameters = parameters;
    }

    public List<ParameterNode> getParameters() {
        return parameters;
    }
}
