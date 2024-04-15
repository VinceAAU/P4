package dk.aau.cs_24_sw_4_16.carl.CstToAst;

import dk.aau.cs_24_sw_4_16.carl.CARLParser;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;

public class IfStatementTest {
    private CstToAstVisitor visitor;
    private CARLParser.IfStatementContext ctx;
    private CARLParser.ExpressionContext conditionExpr;

    @Before
    public void setUp() {
        visitor = Mockito.spy(new CstToAstVisitor());
        ctx = Mockito.mock(CARLParser.IfStatementContext.class);
        conditionExpr = Mockito.mock(CARLParser.ExpressionContext.class);

        Mockito.when(ctx.expression()).thenReturn((List<CARLParser.ExpressionContext>) conditionExpr);
        Mockito.when(visitor.visit(conditionExpr)).thenReturn(new BoolNode("true")); 
    }


    @Test
    public void testVisitIfStatement() {
        AstNode result = visitor.visitIfStatement(ctx);
        Assert.assertNotNull(result);
        Assert.assertTrue(result instanceof IfNode);
    }
}
