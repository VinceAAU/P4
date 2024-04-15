package dk.aau.cs_24_sw_4_16.carl.CstToAst;

public class WhileNode extends AstNode {
    private ExpressionNode expression;
    private BlockNode block;

    public WhileNode(ExpressionNode expression, BlockNode block) {
        this.expression = expression;
        this.block = block;
    }

    public ExpressionNode getExpression() {
        return expression;
    }

    public BlockNode getBlock() {
        return block;
    }

    @Override
    public String toString() {
        return "" + getBlock();
    }
}
