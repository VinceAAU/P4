package dk.aau.cs_24_sw_4_16.carl.CstToAst;
public interface AstVisitor<T> {
    T visit(AstNode node);
    T visit(StatementNode node);
    T visit(AssignmentNode node);
    T visit(VariableDeclarationNode node);
    T visit(TypeNode node);
    T visit(IntNode node);
    T visit(FloatNode node);
    T visit(ProgramNode node);
}