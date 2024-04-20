package dk.aau.cs_24_sw_4_16.carl.CstToAst;

import dk.aau.cs_24_sw_4_16.carl.CstToAst.TypeNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TypeNodeTest {

    @Test
    public void testTypeNode() {
        String expectedType = "int";
        TypeNode typeNode = new TypeNode(expectedType);

        Assertions.assertEquals(" type: " + expectedType, typeNode.toString());
    }
}