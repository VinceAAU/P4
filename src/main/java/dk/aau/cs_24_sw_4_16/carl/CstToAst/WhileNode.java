package dk.aau.cs_24_sw_4_16.carl.CstToAst;

public class WhileNode extends AstNode {
    private AstNode expression;
    private BlockNode block;

    public WhileNode(AstNode expression, BlockNode block) {
        this.expression = expression;
        this.block = block;
    }

    public AstNode getExpression() {
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
