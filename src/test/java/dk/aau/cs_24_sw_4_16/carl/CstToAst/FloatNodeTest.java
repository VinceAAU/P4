package dk.aau.cs_24_sw_4_16.carl.CstToAst;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FloatNodeTest {

    @Test
    void ensureValueIsParsedCorrectly() {
        String testValue = "11.2";
        FloatNode floatNode = new FloatNode(testValue);
        assertEquals(11.2f, floatNode.getValue(), "FloatNode parsed float value incorrect.");
    }

    @Test
    void ensureValueIsSetCorrectly() {
        FloatNode floatNode = new FloatNode("0");
        floatNode.setValue(22.3f);
        assertEquals(22.3f, floatNode.getValue(),"FloatNode set float value incorrect.");
    }

    @Test
    void ensureToStringWorksCorrectly() {
        FloatNode floatNode = new FloatNode("44.2");
        assertEquals("44.2", floatNode.toString(), "FloatNode toString method incorrect.");
    }
}