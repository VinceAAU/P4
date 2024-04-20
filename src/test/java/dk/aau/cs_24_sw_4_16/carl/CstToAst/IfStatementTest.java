package dk.aau.cs_24_sw_4_16.carl.CstToAst;

import dk.aau.cs_24_sw_4_16.carl.CARLParser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class IfStatementTest {
    private CstToAstVisitor visitor;
    private CARLParser.IfStatementContext ctx;
    private List<CARLParser.ExpressionContext> expressions;
    private List<CARLParser.BlockContext> blocks;

    @Before
    public void setUp() {
        visitor = Mockito.spy(new CstToAstVisitor());
        ctx = Mockito.mock(CARLParser.IfStatementContext.class);
        expressions = new ArrayList<>();
        blocks = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            expressions.add(Mockito.mock(CARLParser.ExpressionContext.class));
            blocks.add(Mockito.mock(CARLParser.BlockContext.class));
        }
        Mockito.when(ctx.expression()).thenReturn(expressions);
        Mockito.when(ctx.block()).thenReturn(blocks);
    }

    @Test
    public void testVisitIfStatement_SimpleIf() {
        IntNode intNode1 = new IntNode("10");
        IntNode intNode2 = new IntNode("20");
        Mockito.doReturn(intNode1).when(visitor).visit(expressions.get(0));
        Mockito.doReturn(intNode2).when(visitor).visit(expressions.get(1));

        // Prepare the blocks
        BlockNode blockNode1 = Mockito.mock(BlockNode.class);
        BlockNode blockNode2 = Mockito.mock(BlockNode.class);
        Mockito.when(visitor.visitBlock(blocks.get(0))).thenReturn(blockNode1);
        Mockito.when(visitor.visitBlock(blocks.get(1))).thenReturn(blockNode2);

        IfStatementNode result = (IfStatementNode) visitor.visitIfStatement(ctx);

        Assert.assertNotNull("Result should not be null", result);
        Assert.assertEquals("Result should have two expressions", 2, result.getExpressions().size());
        Assert.assertEquals("Result should have two blocks", 2, result.getBlocks().size());

        List<ExpressionNode> expressionList = result.getExpressions();

        Assert.assertEquals("First expression should be 10", 10, expressionList.get(0).getValue());
        Assert.assertEquals("Second expression should be 20", 20, expressionList.get(1).getValue());

        Assert.assertNotNull("First block should not be null", result.getBlocks().get(0));
        Assert.assertNotNull("Second block should not be null", result.getBlocks().get(1));
    }
}
