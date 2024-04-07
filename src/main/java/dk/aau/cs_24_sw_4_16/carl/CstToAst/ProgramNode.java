package dk.aau.cs_24_sw_4_16.carl.CstToAst;

import dk.aau.cs_24_sw_4_16.carl.Statement.Statement;

import java.util.ArrayList;
import java.util.List;

public class ProgramNode extends AstNode {
    List<StatementNode> statements;

    public void addStatement(StatementNode node) {
        statements.add(node);
    }

    ProgramNode() {
        statements = new ArrayList<>();
    }

    public List<StatementNode> getStatements() {
        return statements;
    }

    @Override
    public  <T> T accept(AstVisitor<T> visitor) {return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "ProgramNode{" +
                "statements=" + statements +
                '}';
    }
}
