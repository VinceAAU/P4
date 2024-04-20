package dk.aau.cs_24_sw_4_16.carl.CstToAst;

import dk.aau.cs_24_sw_4_16.carl.CARLParser;
import org.antlr.v4.runtime.Token;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class MultiplicationDivisionTest {
    private CstToAstVisitor visitor;
    private CARLParser.MultiplicationDivisionModulusContext ctx;
    private CARLParser.ExpressionContext leftExpr, rightExpr;

    @Before
    public void setUp() {
        visitor = Mockito.spy(new CstToAstVisitor());
        ctx = Mockito.mock(CARLParser.MultiplicationDivisionModulusContext.class);
        leftExpr = Mockito.mock(CARLParser.ExpressionContext.class);
        rightExpr = Mockito.mock(CARLParser.ExpressionContext.class);
        Token operatorToken = Mockito.mock(Token.class);

        Mockito.when(ctx.expression(0)).thenReturn(leftExpr);
        Mockito.when(ctx.expression(1)).thenReturn(rightExpr);
        ctx.op = operatorToken;
    }

    @Test
    public void testVisitMultiplicationDivisionModulus_Multiplication() {
        Mockito.when(visitor.visit(leftExpr)).thenReturn(new IntNode("3"));
        Mockito.when(visitor.visit(rightExpr)).thenReturn(new IntNode("4"));
        Mockito.when(ctx.op.getText()).thenReturn("*");

        AstNode result = visitor.visitMultiplicationDivisionModulus(ctx);

        Assert.assertNotNull("Result should not be null", result);
        Assert.assertTrue("Result should be an instance of BinaryOperatorNode", result instanceof BinaryOperatorNode);

        BinaryOperatorNode binaryNode = (BinaryOperatorNode) result;
        Assert.assertEquals("Left node value should be 3", 3, ((IntNode) binaryNode.left).getValue());
        Assert.assertEquals("Right node value should be 4", 4, ((IntNode) binaryNode.right).getValue());
        Assert.assertEquals("Operator should be multiplication", "*", binaryNode.operator);
        Assert.assertEquals("BinaryOperatorNode toString should match calculated value", "12", result.toString());
    }

    @Test
    public void testVisitMultiplicationDivisionModulus_Division() {
        // Mock child nodes to return IntNodes with specific values
        Mockito.when(visitor.visit(leftExpr)).thenReturn(new IntNode("12"));
        Mockito.when(visitor.visit(rightExpr)).thenReturn(new IntNode("3"));
        Mockito.when(ctx.op.getText()).thenReturn("/");

        AstNode result = visitor.visitMultiplicationDivisionModulus(ctx);

        Assert.assertNotNull("Result should not be null", result);
        Assert.assertTrue("Result should be an instance of BinaryOperatorNode", result instanceof BinaryOperatorNode);

        BinaryOperatorNode binaryNode = (BinaryOperatorNode) result;
        Assert.assertEquals("Left node value should be 12", 12, ((IntNode) binaryNode.left).getValue());
        Assert.assertEquals("Right node value should be 3", 3, ((IntNode) binaryNode.right).getValue());
        Assert.assertEquals("Operator should be division", "/", binaryNode.operator);
        Assert.assertEquals("BinaryOperatorNode toString should match calculated value", "4", result.toString());
    }
}
