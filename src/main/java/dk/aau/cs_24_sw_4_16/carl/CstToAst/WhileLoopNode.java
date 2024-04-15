package dk.aau.cs_24_sw_4_16.carl.CstToAst;

import java.util.List;

public class WhileLoopNode extends AstNode {
    private final ExpressionNode condition;
    private final List<StatementNode> body;

    public WhileLoopNode(ExpressionNode condition, List<StatementNode> body) {
        this.condition = condition;
        this.body = body;
    }

    public ExpressionNode getCondition() {
        return condition;
    }

    public List<StatementNode> getBody() {
        return body;
    }


    @Override
    public String toString() {
        return "WhileLoopNode{" +
                "condition=" + condition +
                ", body=" + body +
                '}';
    }
}
