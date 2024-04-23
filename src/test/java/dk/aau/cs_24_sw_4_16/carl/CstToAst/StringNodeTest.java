package dk.aau.cs_24_sw_4_16.carl.CstToAst;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringNodeTest {

    @Test
    public void testGetValue() {
        StringNode node = new StringNode("Test String");
        assertEquals("Test String", node.getValue());
    }

    @Test
    public void testSetValue() {
        StringNode node = new StringNode("");
        node.setValue("New Test String");
        assertEquals("New Test String", node.getValue());
    }

    @Test
    public void testToString() {
        StringNode node = new StringNode("Test ToString");
        assertEquals("Test ToString", node.toString());
    }
}