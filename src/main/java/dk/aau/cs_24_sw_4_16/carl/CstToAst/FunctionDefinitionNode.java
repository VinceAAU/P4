package dk.aau.cs_24_sw_4_16.carl.CstToAst;

public class FunctionDefinitionNode extends AstNode {
    private final String name;
    private final TypeNode returnType;

    public FunctionDefinitionNode(String name, TypeNode returnType) {
        this.name = name;
        this.returnType = returnType;
    }

    public String getName() {
        return name;
    }

    public TypeNode getReturnType() {
        return returnType;
    }

    @Override
    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visitFunctionDefinitionNode(this);
    }
}
