package dk.aau.cs_24_sw_4_16.carl.CstToAst;

public class ArrayDefinitionNode extends AstNode {
    private final TypeNode type;

    // we need this only if dynamic sizing is allowed
    private final int size;

    private  IdentifierNode identifier;

    public ArrayDefinitionNode(TypeNode type, int size, IdentifierNode identifier) {
        this.type = type;
        this.size = size;
        this.identifier = identifier;
    }

    public TypeNode getType() {
        return type;
    }

    public int getSize() {
        return size;
    }

    public String getIdentifier() {
        return identifier.toString();
    }
}
