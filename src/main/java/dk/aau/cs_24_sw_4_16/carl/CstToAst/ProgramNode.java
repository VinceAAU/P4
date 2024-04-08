package dk.aau.cs_24_sw_4_16.carl.CstToAst;

import dk.aau.cs_24_sw_4_16.carl.Statement.Statement;

import java.util.ArrayList;
import java.util.List;

public class ProgramNode extends AstNode {
    List<AstNode> statements;

    public void addStatement(StatementNode node) {
        statements.add(node);
    }

    ProgramNode() {
        statements = new ArrayList<>();
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
