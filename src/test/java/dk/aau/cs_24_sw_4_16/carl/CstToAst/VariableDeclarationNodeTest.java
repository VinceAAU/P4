package dk.aau.cs_24_sw_4_16.carl.CstToAst;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class VariableDeclarationNodeTest {

    @Test
    void testVariableDeclarationNodeConstructor() {
        IdentifierNode identifier = new IdentifierNode("x");
        TypeNode type = new TypeNode("int");
        IntNode value = new IntNode("100");

        VariableDeclarationNode node = new VariableDeclarationNode(identifier, type, value);

        assertSame(identifier, node.getIdentifier(), "Identifier should match");
        assertSame(type, node.getType(), "Type should match");
        assertSame(value, node.getValue(), "Value should match");
    }

    @Test
    void testVariableDeclarationNodeToString() {
        IdentifierNode identifier = new IdentifierNode("x");
        TypeNode type = new TypeNode("int");
        IntNode value = new IntNode("100");

        VariableDeclarationNode node = new VariableDeclarationNode(identifier, type, value);
        String expected = "Identifier: x type: int100";

        assertEquals(expected, node.toString(), "toString should format correctly");
    }
}
