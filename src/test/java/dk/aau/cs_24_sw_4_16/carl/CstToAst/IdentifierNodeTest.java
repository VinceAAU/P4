package dk.aau.cs_24_sw_4_16.carl.CstToAst;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IdentifierNodeTest {

    @Test
    public void testIdentifierNodeConstructorAndGetter() {
        String identifier = "TestIdentifier";
        IdentifierNode identifierNode = new IdentifierNode(identifier);
        assertEquals(identifier, identifierNode.getIdentifier());
    }

    @Test
    public void testIdentifierNodeToString() {
        String identifier = "TestIdentifier";
        IdentifierNode identifierNode = new IdentifierNode(identifier);
        assertEquals(identifier, identifierNode.toString());
    }

}