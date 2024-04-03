package dk.aau.cs_24_sw_4_16.carl.Expression;

public class Type <T> extends Expression{
    private T typeName;

    public Type(T typeName) {
        this.typeName = typeName;
    }
}
