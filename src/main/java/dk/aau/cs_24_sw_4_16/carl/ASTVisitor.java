package dk.aau.cs_24_sw_4_16.carl;

public interface ASTVisitor {
    void visit(NumberNode node);
    void visit(BinaryOperationNode node);
    void visit(PrintNode node);
    // Add other visit methods for additional node types
}
