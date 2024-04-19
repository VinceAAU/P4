package dk.aau.cs_24_sw_4_16.carl.CstToAst;

import lombok.Getter;

import java.util.List;

@Getter
public class IfStatementNode extends AstNode {
    private final AstNode condition;
    private final AstNode thenBranch;
    private final AstNode elseBranch;
    private final List<AstNode> elseIfConditions;
    private final List<BlockNode> elseIfBlocks;

    public IfStatementNode(AstNode condition, AstNode thenBranch, AstNode elseBranch, List<AstNode> elseIfConditions, List<BlockNode> elseIfBlocks) {
        this.condition = condition;
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;
        this.elseIfConditions = elseIfConditions;
        this.elseIfBlocks = elseIfBlocks;
    }



    @Override
    public String toString() {
        return "IfStatementNode" +
                "condition=" + condition +
                ", thenBranch=" + thenBranch +
                ", elseBranch=" + elseBranch +
                ", elseIfConditions=" + elseIfConditions +
                ", elseIfBlocks=" + elseIfBlocks;
    }


}
