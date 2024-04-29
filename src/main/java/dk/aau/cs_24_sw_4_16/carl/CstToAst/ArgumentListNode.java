package dk.aau.cs_24_sw_4_16.carl.CstToAst;

import java.util.List;

public class ArgumentListNode extends AstNode{
    List<AstNode> list;

    public ArgumentListNode(List<AstNode> list) {
        this.list = list;
    }

    public List<AstNode> getList() {
        return list;
    }
}
