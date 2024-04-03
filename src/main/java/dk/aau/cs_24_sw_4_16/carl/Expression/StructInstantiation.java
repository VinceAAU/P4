package dk.aau.cs_24_sw_4_16.carl.Expression;

import java.util.Map;

public class StructInstantiation extends Expression {
    private String typeName;
    private Map<String, Expression> fields;
}
