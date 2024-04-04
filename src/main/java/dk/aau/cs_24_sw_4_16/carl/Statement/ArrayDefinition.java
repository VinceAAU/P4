package dk.aau.cs_24_sw_4_16.carl.Statement;

public class ArrayDefinition extends Statement {
    private String id;
    private String type;
    private int anInt;
    private String aString;
    private float aFloat;

    public ArrayDefinition(String id, String type, int anInt) {
        this.id = id;
        this.type = type;
        this.anInt = anInt;
    }

    public ArrayDefinition(String id, String type, String aString) {
        this.id = id;
        this.type = type;
        this.aString = aString;
    }

    public ArrayDefinition(String id, String type, float aFloat) {
        this.id = id;
        this.type = type;
        this.aFloat = aFloat;
    }
}
