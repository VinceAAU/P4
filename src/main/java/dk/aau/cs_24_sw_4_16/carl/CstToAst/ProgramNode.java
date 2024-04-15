package dk.aau.cs_24_sw_4_16.carl.CstToAst;

import java.util.ArrayList;
import java.util.List;

public class ProgramNode extends AstNode {
    List<AstNode> statements;



    ProgramNode() {
        statements = new ArrayList<>();
    }

    public List<AstNode> getStatements() {
        return statements;
    }

    public void addStatement(StatementNode node) {
        statements.add(node);
    }

    @Override
    public String toString() {
        return "ProgramNode{" +
                "statements=" + statements +
                '}';
    }
}
