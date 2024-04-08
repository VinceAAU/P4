package dk.aau.cs_24_sw_4_16.carl.CstToAst;

public class ImportStatementNode extends AstNode {
    private final String path;

    public ImportStatementNode(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    @Override
    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visitImportStatementNode(this);
    }
}
