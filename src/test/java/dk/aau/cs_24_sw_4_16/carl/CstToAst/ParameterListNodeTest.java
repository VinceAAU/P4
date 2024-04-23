package dk.aau.cs_24_sw_4_16.carl.CstToAst;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ParameterListNodeTest {

    @Test
    void notNull() {
        ParameterListNode node = new ParameterListNode(List.of(createNode("int")));
        assertNotNull(node.getParameters(), "Should not be null");
    }

    @Test
    void sizeTwo() {
        ParameterListNode node = new ParameterListNode(List.of(createNode("int"), createNode("float")));
        assertEquals(2, node.getParameters().size(), "Should have two parameters");
    }

    @Test
    void firstParameter() {
        ParameterNode first = createNode("int");
        ParameterListNode node = new ParameterListNode(List.of(first));
        assertSame(first, node.getParameters().get(0), "First should match");
    }

    @Test
    void secondParameter() {
        ParameterNode second = createNode("float");
        ParameterListNode node = new ParameterListNode(List.of(createNode("int"), second));
        assertSame(second, node.getParameters().get(1), "Second should match");
    }

    private ParameterNode createNode(String type) {
        return new ParameterNode(new IdentifierNode("param"), new TypeNode(type));
    }
}
