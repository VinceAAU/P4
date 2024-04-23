package dk.aau.cs_24_sw_4_16.carl.CstToAst;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BinaryOperatorNodeTest {

    @Test
    public void testPerformOperationInt() {
        AstNode node = BinaryOperatorNode.performOperation(3, 2, "+");
        Assertions.assertTrue(node instanceof IntNode);
        Assertions.assertEquals("5", node.toString());
    }

    @Test
    public void testPerformOperationFloat() {
        AstNode node = BinaryOperatorNode.performOperation(3.0f, 2.0f, "+");
        Assertions.assertTrue(node instanceof FloatNode);
        Assertions.assertEquals("5.0", node.toString());
    }

    @Test
    public void testGetAstNodeValueInt() {
        AstNode left = new IntNode("3");
        AstNode right = new IntNode("2");
        AstNode node = BinaryOperatorNode.getAstNodeValue(left, right, "+");
        Assertions.assertEquals("5", node.toString());
    }

    @Test
    public void testGetAstNodeValueFloat() {
        AstNode left = new FloatNode("3.0");
        AstNode right = new FloatNode("2.0");
        AstNode node = BinaryOperatorNode.getAstNodeValue(left, right, "+");
        Assertions.assertEquals("5.0", node.toString());
    }

    @Test
    public void testGetLeft() {
        AstNode left = new IntNode("3");
        BinaryOperatorNode node = new BinaryOperatorNode(left, null, "+");
        Assertions.assertEquals(left, node.getLeft());
    }

    @Test
    public void testGetRight() {
        AstNode right = new IntNode("2");
        BinaryOperatorNode node = new BinaryOperatorNode(null, right, "+");
        Assertions.assertEquals(right, node.getRight());
    }

    @Test
    public void testGetOperator() {
        String operator = "+";
        BinaryOperatorNode node = new BinaryOperatorNode(null, null, operator);
        Assertions.assertEquals(operator, node.getOperator());
    }

}