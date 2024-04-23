package dk.aau.cs_24_sw_4_16.carl.CstToAst;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WhileLoopNodeTest {

    @Test
    void testWhileNodeConstructor() {
        ExpressionNode expressionNode = new ExpressionNode(new IntNode("10"));
        BlockNode blockNode = new BlockNode();
        WhileLoopNode whileLoopNode = new WhileLoopNode(expressionNode, blockNode);
        assertSame(expressionNode, whileLoopNode.getExpression(), "Expression should match the provided expression node");
        assertSame(blockNode, whileLoopNode.getBlock(), "Block should match the provided block node");
    }

    @Test
    void testExpressionRetrieval() {
        ExpressionNode expressionNode = new ExpressionNode(new IntNode("20"));
        BlockNode blockNode = new BlockNode();
        WhileLoopNode whileLoopNode = new WhileLoopNode(expressionNode, blockNode);
        assertNotNull(whileLoopNode.getExpression(), "Expression should not be null");
        assertEquals(20, ((IntNode) whileLoopNode.getExpression().getNode()).getValue(), "Expression value should be 20");
    }

    @Test
    void testBlockRetrieval() {
        ExpressionNode expressionNode = new ExpressionNode(new IntNode("10"));
        BlockNode blockNode = new BlockNode();
        WhileLoopNode whileLoopNode = new WhileLoopNode(expressionNode, blockNode);
        assertNotNull(whileLoopNode.getBlock(), "Block should not be null");
    }
}
