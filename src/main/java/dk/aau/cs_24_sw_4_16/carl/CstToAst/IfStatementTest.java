package dk.aau.cs_24_sw_4_16.carl.CstToAst;

import dk.aau.cs_24_sw_4_16.carl.CARLParser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class IfStatementTest {

    private CstToAstVisitor visitor;
    private CARLParser.IfStatementContext ctx;

    @Before
    public void setUp() {
        visitor = Mockito.spy(new CstToAstVisitor());
        ctx = Mockito.mock(CARLParser.IfStatementContext.class);
        CARLParser.ExpressionContext conditionExpr = Mockito.mock(CARLParser.ExpressionContext.class);
        CARLParser.BlockContext thenBlock = Mockito.mock(CARLParser.BlockContext.class);
        CARLParser.BlockContext elseBlock = Mockito.mock(CARLParser.BlockContext.class);
        List<CARLParser.ExpressionContext> conditions = Collections.singletonList(conditionExpr);
        List<CARLParser.BlockContext> blocks = Arrays.asList(thenBlock, elseBlock);
        Mockito.when(ctx.expression()).thenReturn(conditions);
        Mockito.when(ctx.block()).thenReturn(blocks);
        Mockito.when(ctx.expression(0)).thenReturn(conditionExpr);
        Mockito.when(ctx.block(0)).thenReturn(thenBlock);
        Mockito.when(ctx.block(1)).thenReturn(elseBlock);
    }



    @Test
    public void testVisitIfStatement() {
        AstNode condition = Mockito.mock(ExpressionNode.class);
        BlockNode thenBranch = Mockito.mock(BlockNode.class);
        BlockNode elseBranch = Mockito.mock(BlockNode.class);

        Mockito.when(visitor.visit(Mockito.any())).thenAnswer(invocation -> {
            Object argument = invocation.getArgument(0);
            if (argument == ctx.expression(0)) {
                return condition;
            } else if (argument == ctx.block(0)) {
                return thenBranch;
            } else if (argument == ctx.block(1)) {
                return elseBranch;
            } else {
                throw new RuntimeException("Unexpected visit call");
            }
        });
        List<AstNode> elseIfConditions = new ArrayList<>();
        List<BlockNode> elseIfBlocks = new ArrayList<>();

        IfStatementNode expectedNode = new IfStatementNode((ExpressionNode) condition, thenBranch, elseBranch, elseIfConditions, elseIfBlocks);

        AstNode result = visitor.visitIfStatement(ctx);

        Assert.assertNotNull(result);
        Assert.assertTrue(result instanceof StatementNode);
        Assert.assertEquals(expectedNode, ((StatementNode) result).getNode());
    }
}
