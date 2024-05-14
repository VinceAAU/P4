package dk.aau.cs_24_sw_4_16.carl.Semantic_A;

public enum Type {
    INT("int"), // Integer type
    FLOAT("float"), // Floating-point type
    STRING("string"), // String type
    BOOLEAN("boolean"), // Boolean type
    VOID("void"), // Type for functions that don't return a value
    // ... add other types as needed
    COORD("coord"),
    ENEMY("enemy"),
    WALL("wall"),
    FLOOR("floor"),
    ROOM("room"),

    UNKNOWN("unknown"); // Used for uninitialized or error states

    private final String typeName;

    Type(String typeName) {
        this.typeName = typeName;
    }

    // This method returns the type name as a string.
    public String getTypeName() {
        return typeName;
    }

    // This method might be used to check if two types are compatible.
    // For simplicity, it's just checking for equality here.
    public boolean isCompatible(Type otherType) {
        return this == otherType;
    }

    // ... you can add more methods to handle type-specific logic
}