package dk.aau.cs_24_sw_4_16.carl.CstToAst;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MultiplicationDivisionTest {

    @Test
    public void testMultiplicationWithIntNodes() {
        IntNode left = new IntNode("4");
        IntNode right = new IntNode("3");
        BinaryOperatorNode node = new BinaryOperatorNode(left, right, "*");
        assertEquals(12, ((IntNode)BinaryOperatorNode.getAstNodeValue(left, right, "*")).getValue(), "Multiplication should return the correct value.");
    }

    @Test
    public void testDivisionWithIntNodes() {
        IntNode left = new IntNode("12");
        IntNode right = new IntNode("4");
        BinaryOperatorNode node = new BinaryOperatorNode(left, right, "/");
        assertEquals(3, ((IntNode)BinaryOperatorNode.getAstNodeValue(left, right, "/")).getValue(), "Division should return the correct value.");
    }

    @Test
    public void testModulusWithIntNodes() {
        IntNode left = new IntNode("10");
        IntNode right = new IntNode("4");
        BinaryOperatorNode node = new BinaryOperatorNode(left, right, "%");
        assertEquals(2, ((IntNode)BinaryOperatorNode.getAstNodeValue(left, right, "%")).getValue(), "Modulus should return the correct value.");
    }

    @Test
    public void testToString() {
        IntNode left = new IntNode("15");
        IntNode right = new IntNode("5");
        BinaryOperatorNode node = new BinaryOperatorNode(left, right, "/");
        assertEquals("3", node.toString(), "The toString method should return the correct string representation of the division result.");
    }
}
