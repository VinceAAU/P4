package dk.aau.cs_24_sw_4_16.carl.CstToAst;

import dk.aau.cs_24_sw_4_16.carl.CARLParser;
import org.antlr.v4.runtime.tree.ParseTree;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;



import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;

public class WhileTest {


    // Declare the visitor instance
    private CstToAstVisitor visitor;

    @Before
    public void setUp() {
        visitor = Mockito.spy(new CstToAstVisitor());  // Initialize the visitor
    }

    @Test
    public void testVisitWhileLoop_SimpleWhile() {
        CARLParser.WhileLoopContext ctx = Mockito.mock(CARLParser.WhileLoopContext.class);
        CARLParser.ExpressionContext expressionCtx = Mockito.mock(CARLParser.ExpressionContext.class);
        CARLParser.BlockContext blockCtx = Mockito.mock(CARLParser.BlockContext.class);
        Mockito.when(ctx.expression()).thenReturn(expressionCtx);
        Mockito.when(ctx.block()).thenReturn(blockCtx);

        String identifier = "x";
        Mockito.when(expressionCtx.getChildCount()).thenReturn(3);
        Mockito.when(expressionCtx.getChild(0)).thenReturn(Mockito.mock(CARLParser.IdentifierContext.class));
        Mockito.when(expressionCtx.getChild(0).getText()).thenReturn(identifier);

        Mockito.when(expressionCtx.getChild(1)).thenReturn((ParseTree) Mockito.mock(Object.class));
        Mockito.when(expressionCtx.getChild(1).getText()).thenReturn("<=");

        Mockito.when(expressionCtx.getChild(2)).thenReturn(Mockito.mock(CARLParser.IdentifierContext.class));
        Mockito.when(expressionCtx.getChild(2).getText()).thenReturn(identifier);
        BlockNode blockNode = Mockito.mock(BlockNode.class);
        Mockito.when(visitor.visitBlock(blockCtx)).thenReturn(blockNode);

        WhileNode result = (WhileNode) visitor.visitWhileLoop(ctx);


        assertNotNull("Result should not be null", result);
        assertEquals("WhileNode should have the correct expression", identifier + " <= " + identifier, result.getExpression().toString());
        assertEquals("WhileNode should have the visited block", blockNode, result.getBlock());
    }
}
