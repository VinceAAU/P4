package dk.aau.cs_24_sw_4_16.carl.CstToAst;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StatementNodeTest {

    @Test
    void statementNodeHoldsCorrectAstNode() {
        AstNode containedNode = new IntNode("42");
        StatementNode statementNode = new StatementNode(containedNode);
        assertSame(containedNode, statementNode.getNode(), "StatementNode should hold the correct AstNode");
    }

    @Test
    void statementNodeReturnsCorrectToString() {
        AstNode containedNode = new IntNode("99");
        StatementNode statementNode = new StatementNode(containedNode);
        assertEquals(" 99", statementNode.toString(), "StatementNode toString should return a formatted string with node");
    }

    @Test
    void statementNodeConsistencyCheck() {
        AstNode initialNode = new IntNode("5");
        StatementNode statementNode = new StatementNode(initialNode);
        assertEquals(5, ((IntNode) statementNode.getNode()).getValue(), "StatementNode should consistently return the same node value");
    }

    @Test
    void statementNodeNullCheck() {
        StatementNode statementNode = new StatementNode(null);
        assertNull(statementNode.getNode(), "StatementNode initialized with null should return null");
    }
}
