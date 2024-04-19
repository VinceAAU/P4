package dk.aau.cs_24_sw_4_16.carl.CstToAst;

import dk.aau.cs_24_sw_4_16.carl.CARLParser;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class AdditionTest {
    private CstToAstVisitor visitor;
    private CARLParser.AdditionSubtractionContext ctx;
    private CARLParser.ExpressionContext leftExpr, rightExpr;

    @Before
    public void setUp() {
        visitor = Mockito.spy(new CstToAstVisitor());
        ctx = Mockito.mock(CARLParser.AdditionSubtractionContext.class);
        leftExpr = Mockito.mock(CARLParser.ExpressionContext.class);
        rightExpr = Mockito.mock(CARLParser.ExpressionContext.class);

        TerminalNode operatorNode = Mockito.mock(TerminalNode.class);
        org.antlr.v4.runtime.Token operatorToken = Mockito.mock(org.antlr.v4.runtime.Token.class);

        Mockito.when(ctx.expression(0)).thenReturn(leftExpr);
        Mockito.when(ctx.expression(1)).thenReturn(rightExpr);
        ctx.op = operatorToken;
        Mockito.when(operatorNode.getSymbol()).thenReturn(operatorToken);
        Mockito.when(operatorToken.getText()).thenReturn("+");
        Mockito.when(visitor.visit(leftExpr)).thenReturn(new IntNode("3"));
        Mockito.when(visitor.visit(rightExpr)).thenReturn(new IntNode("4"));
    }

    @Test
    public void testVisitAdditionSubtraction_Addition() {
        AstNode result = visitor.visitAdditionSubtraction(ctx);
        Assert.assertNotNull(result);
        Assert.assertTrue(result instanceof IntNode);
        Assert.assertEquals(7, ((IntNode) result).getValue());
    }
}
