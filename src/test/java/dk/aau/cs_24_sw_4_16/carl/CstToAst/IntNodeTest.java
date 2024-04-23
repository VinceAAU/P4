package dk.aau.cs_24_sw_4_16.carl.CstToAst;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntNodeTest {

    @Test
    void ensureValueIsParsedCorrectly() {
        String testValue = "11";
        IntNode intNode = new IntNode(testValue);
        assertEquals(11, intNode.getValue(), "IntNode parsed int value incorrect.");
    }

    @Test
    void ensureValueIsSetCorrectly() {
        IntNode intNode = new IntNode("0");
        intNode.setValue(22);
        assertEquals(22, intNode.getValue(),"IntNode set int value incorrect.");
    }

    @Test
    void ensureToStringWorksCorrectly() {
        IntNode intNode = new IntNode("44");
        assertEquals("44", intNode.toString(), "IntNode toString method incorrect.");
    }
}
