package dk.aau.cs_24_sw_4_16.carl.CstToAst;

import java.util.ArrayList;
import java.util.List;

public class BlockNode extends AstNode{
    List<AstNode> statements;



    public BlockNode() {
        statements = new ArrayList<>();
    }
    public void addStatement(StatementNode node) {
        statements.add(node);
    }
    public List<AstNode> getStatements() {
        return statements;
    }



    @Override
    public String toString() {
        return "ProgramNode{" +
                "statements=" + statements +
                '}';
    }
}

