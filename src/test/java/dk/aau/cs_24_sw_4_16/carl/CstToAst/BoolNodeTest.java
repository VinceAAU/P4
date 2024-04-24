package dk.aau.cs_24_sw_4_16.carl.CstToAst;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BoolNodeTest {
    
    @Test
    public void testValueParsingTrue(){
        BoolNode node = new BoolNode("True");
        Assertions.assertEquals(true, node.getValue());
    }
    
    @Test
    public void testValueParsingFalse(){
        BoolNode node = new BoolNode("false");
        Assertions.assertEquals(false, node.getValue());
    }

    @Test
    public void testValueSettingTrue(){
        BoolNode node = new BoolNode("false");
        node.setValue(true);
        Assertions.assertEquals(true, node.getValue());
    }

    @Test
    public void testValueSettingFalse(){
        BoolNode node = new BoolNode("true");
        node.setValue(false);
        Assertions.assertEquals(false, node.getValue());
    }

    @Test
    public void testToStringTrue(){
        BoolNode node = new BoolNode("true");
        Assertions.assertEquals(" true", node.toString());
    }

    @Test
    public void testToStringFalse(){
        BoolNode node = new BoolNode("false");
        Assertions.assertEquals(" false", node.toString());
    }
}