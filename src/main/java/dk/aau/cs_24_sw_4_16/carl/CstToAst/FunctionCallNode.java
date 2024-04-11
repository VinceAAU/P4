package dk.aau.cs_24_sw_4_16.carl.CstToAst;

import java.util.List;

public class FunctionCallNode extends AstNode {
    private final IdentifierNode identifier;
    private final List<AstNode> arguments;

    public FunctionCallNode(AstNode identifier, List<AstNode> arguments) {
        this.identifier = (IdentifierNode) identifier;
        this.arguments = arguments;
    }

    public IdentifierNode getFunctionName() {
        return identifier;
    }

    public List<AstNode> getArguments() {
        return arguments;
    }

    @Override
    public String toString() {
        return "identifier='" + identifier + '\'' +
                ", arguments=" + arguments;
    }

}