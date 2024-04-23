package dk.aau.cs_24_sw_4_16.carl.CstToAst;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class ProgramNodeTest {

    @Test
    void emptyProgramNode() {
        ProgramNode program = new ProgramNode();
        assertTrue(program.getStatements().isEmpty(), "New ProgramNode should have no statements");
    }

    @Test
    void addStatementToProgram() {
        ProgramNode program = new ProgramNode();
        StatementNode statement = new StatementNode(new IntNode("5"));
        program.addStatement(statement);
        assertEquals(1, program.getStatements().size(), "ProgramNode should have one statement");
        assertSame(statement, program.getStatements().get(0), "Statement should match added one");
    }

    @Test
    void multipleStatements() {
        ProgramNode program = new ProgramNode();
        StatementNode first = new StatementNode(new IntNode("5"));
        StatementNode second = new StatementNode(new IntNode("10"));
        program.addStatement(first);
        program.addStatement(second);
        assertEquals(List.of(first, second), program.getStatements(), "ProgramNode should contain both statements");
    }

    @Test
    void programToString() {
        ProgramNode program = new ProgramNode();
        program.addStatement(new StatementNode(new IntNode("5")));
        assertEquals("ProgramNode{statements=[ 5]}", program.toString(), "Program toString should match expected format");
    }
}
