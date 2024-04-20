package dk.aau.cs_24_sw_4_16.carl.CstToAst;

import java.util.List;

public class IfStatementNode extends AstNode {
    List<BlockNode> blocks;
    List<ExpressionNode> expressions;

    public IfStatementNode(List<BlockNode> blocks, List<ExpressionNode> expressions) {
        this.blocks = blocks;
        this.expressions = expressions;
    }

    public List<BlockNode> getBlocks() {
        return blocks;
    }

    public List<ExpressionNode> getExpressions() {
        return expressions;
    }
}