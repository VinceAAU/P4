package dk.aau.cs_24_sw_4_16.carl.CstToAst;

import dk.aau.cs_24_sw_4_16.carl.CARLParser;
import org.antlr.v4.runtime.Token;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class AdditionSubtractionTest {
    private CstToAstVisitor visitor;
    private CARLParser.AdditionSubtractionContext ctx;
    private CARLParser.ExpressionContext leftExpr, rightExpr;

    @Before
    public void setUp() {
        visitor = Mockito.spy(new CstToAstVisitor());
        ctx = Mockito.mock(CARLParser.AdditionSubtractionContext.class);
        leftExpr = Mockito.mock(CARLParser.ExpressionContext.class);
        rightExpr = Mockito.mock(CARLParser.ExpressionContext.class);
        Token operatorToken = Mockito.mock(Token.class);

        Mockito.when(ctx.expression(0)).thenReturn(leftExpr);
        Mockito.when(ctx.expression(1)).thenReturn(rightExpr);
        ctx.op = operatorToken;
    }

    @Test
    public void testVisitAdditionSubtraction_Addition() {
        Mockito.when(visitor.visit(leftExpr)).thenReturn(new IntNode("5"));
        Mockito.when(visitor.visit(rightExpr)).thenReturn(new IntNode("3"));
        Mockito.when(ctx.op.getText()).thenReturn("+"); // Set operator to addition

        AstNode result = visitor.visitAdditionSubtraction(ctx);

        Assert.assertNotNull("Result should not be null", result);
        Assert.assertTrue("Result should be an instance of BinaryOperatorNode", result instanceof BinaryOperatorNode);

        BinaryOperatorNode binaryNode = (BinaryOperatorNode) result;
        Assert.assertEquals("Left node value should be 5", 5, ((IntNode) binaryNode.getLeft()).getValue());
        Assert.assertEquals("Right node value should be 3", 3, ((IntNode) binaryNode.getRight()).getValue());
        Assert.assertEquals("Operator should be addition", "+", binaryNode.getOperator());
        Assert.assertEquals("BinaryOperatorNode toString should match calculated value", "8", result.toString());
    }

    @Test
    public void testVisitAdditionSubtraction_Subtraction() {
        // Mock child nodes to return IntNodes with specific values
        Mockito.when(visitor.visit(leftExpr)).thenReturn(new IntNode("10"));
        Mockito.when(visitor.visit(rightExpr)).thenReturn(new IntNode("2"));
        Mockito.when(ctx.op.getText()).thenReturn("-"); // Set operator to subtraction

        AstNode result = visitor.visitAdditionSubtraction(ctx);

        Assert.assertNotNull("Result should not be null", result);
        Assert.assertTrue("Result should be an instance of BinaryOperatorNode", result instanceof BinaryOperatorNode);

        BinaryOperatorNode binaryNode = (BinaryOperatorNode) result;
        Assert.assertEquals("Left node value should be 10", 10, ((IntNode) binaryNode.getLeft()).getValue());
        Assert.assertEquals("Right node value should be 2", 2, ((IntNode) binaryNode.getRight()).getValue());
        Assert.assertEquals("Operator should be subtraction", "-", binaryNode.getOperator());
        Assert.assertEquals("BinaryOperatorNode toString should match calculated value", "8", result.toString());
    }
}
