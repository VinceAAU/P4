package dk.aau.cs_24_sw_4_16.carl.CstToAst;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AdditionSubtractionTest {

    @Test
    public void testAdditionWithIntNodes() {
        IntNode left = new IntNode("3");
        IntNode right = new IntNode("5");
        BinaryOperatorNode node = new BinaryOperatorNode(left, right, "+");
        assertEquals(8, ((IntNode)BinaryOperatorNode.getAstNodeValue(left, right, "+")).getValue(), "Addition should return the correct value.");
    }

    @Test
    public void testSubtractionWithIntNodes() {
        IntNode left = new IntNode("10");
        IntNode right = new IntNode("6");
        BinaryOperatorNode node = new BinaryOperatorNode(left, right, "-");
        assertEquals(4, ((IntNode)BinaryOperatorNode.getAstNodeValue(left, right, "-")).getValue(), "Subtraction should return the correct value.");
    }

    @Test
    public void testToString() {
        IntNode left = new IntNode("12");
        IntNode right = new IntNode("8");
        BinaryOperatorNode node = new BinaryOperatorNode(left, right, "+");
        assertEquals("20", node.toString(), "The toString method should return the correct string representation of the operation result.");
    }
}
