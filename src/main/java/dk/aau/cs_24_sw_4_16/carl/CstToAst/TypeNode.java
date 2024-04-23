package dk.aau.cs_24_sw_4_16.carl.CstToAst;

import lombok.Getter;

public class TypeNode extends AstNode {
    @Getter
    String type;

    public TypeNode(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return " type: " + type;
    }



}
