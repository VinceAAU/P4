package dk.aau.cs_24_sw_4_16.carl.CstToAst;

import java.util.ArrayList;
import java.util.List;

public class FunctionDefinitionNode extends AstNode {
    private final IdentifierNode identifier;
    private final TypeNode returnType;
    private final ParameterListNode arguments;
    private final BlockNode block;


    public FunctionDefinitionNode(AstNode identifier, TypeNode returnType, ParameterListNode arguments, BlockNode block) {
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

    public ParameterListNode getArguments() {
        return arguments;
    }

    public BlockNode getBlock() {
        return block;
    }
}
