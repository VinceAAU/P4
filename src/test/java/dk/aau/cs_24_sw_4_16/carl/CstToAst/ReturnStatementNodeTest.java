package dk.aau.cs_24_sw_4_16.carl.CstToAst;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ReturnStatementNodeTest {

    @Test
    void returnStatementWithExpression() {
        AstNode expression = new IntNode("42");
        ReturnStatementNode returnStatement = new ReturnStatementNode(expression);
        assertSame(expression, returnStatement.getReturnValue(), "ReturnStatementNode should return the correct expression node");
    }

    @Test
    void returnStatementWithNullExpression() {
        ReturnStatementNode returnStatement = new ReturnStatementNode(null);
        assertNull(returnStatement.getReturnValue(), "ReturnStatementNode with null should have no return value");
    }

    @Test
    void returnValueConsistency() {
        AstNode initialExpression = new IntNode("100");
        ReturnStatementNode returnStatement = new ReturnStatementNode(initialExpression);
        assertEquals(100, ((IntNode) returnStatement.getReturnValue()).getValue(), "Return value should match the initialized value");
    }

    @Test
    void returnStatementToString() {
        AstNode expression = new IntNode("5");
        ReturnStatementNode returnStatement = new ReturnStatementNode(expression);
        assertTrue(returnStatement.toString().contains("5"), "ReturnStatementNode toString should include expression value");
    }

}
