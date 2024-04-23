package dk.aau.cs_24_sw_4_16.carl.CstToAst;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class IfStatementNodeTest {

    @Test
    void testIfStatementNodeConstructor() {
        ExpressionNode expressionNode1 = new ExpressionNode(new IntNode("10"));
        ExpressionNode expressionNode2 = new ExpressionNode(new IntNode("20"));
        List<ExpressionNode> expressions = List.of(expressionNode1, expressionNode2);
        BlockNode blockNode1 = new BlockNode();
        BlockNode blockNode2 = new BlockNode();
        List<BlockNode> blocks = List.of(blockNode1, blockNode2);
        IfStatementNode node = new IfStatementNode(blocks, expressions);
        assertSame(expressions, node.getExpressions(), "Expressions should match the provided list");
        assertSame(blocks, node.getBlocks(), "Blocks should match the provided list");
    }

    @Test
    void testExpressionsCount() {
        ExpressionNode expressionNode1 = new ExpressionNode(new IntNode("10"));
        ExpressionNode expressionNode2 = new ExpressionNode(new IntNode("20"));
        List<ExpressionNode> expressions = List.of(expressionNode1, expressionNode2);
        BlockNode blockNode1 = new BlockNode();
        List<BlockNode> blocks = List.of(blockNode1);
        IfStatementNode node = new IfStatementNode(blocks, expressions);
        assertEquals(2, node.getExpressions().size(), "There should be two expressions");
    }

    @Test
    void testBlocksCount() {
        BlockNode blockNode1 = new BlockNode();
        BlockNode blockNode2 = new BlockNode();
        List<BlockNode> blocks = List.of(blockNode1, blockNode2);
        ExpressionNode expressionNode1 = new ExpressionNode(new IntNode("10"));
        List<ExpressionNode> expressions = List.of(expressionNode1);
        IfStatementNode node = new IfStatementNode(blocks, expressions);
        assertEquals(2, node.getBlocks().size(), "There should be two blocks");
    }
}
