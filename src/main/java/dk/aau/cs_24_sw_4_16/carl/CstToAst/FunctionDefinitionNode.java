package dk.aau.cs_24_sw_4_16.carl.CstToAst;

import java.util.ArrayList;
import java.util.List;

public class FunctionDefinitionNode extends AstNode {
    private final IdentifierNode identifier;
    private final TypeNode returnType;
    private List<AstNode> arguments;
    private List<AstNode> block;


    public FunctionDefinitionNode(AstNode identifier, TypeNode returnType, List<AstNode> arguments, List<AstNode> block) {
        this.identifier = (IdentifierNode) identifier;
        this.returnType = returnType;
        this.arguments = arguments;
        this.block = block;
    }

    public IdentifierNode getIdentifier() {
        return identifier;
    }

    public TypeNode getReturnType() {
        return returnType;
    }

    public List<AstNode> getArguments() {
        return arguments;
    }

    public List<AstNode> getBlock() {
        return block;
    }
}
