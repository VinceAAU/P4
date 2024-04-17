package dk.aau.cs_24_sw_4_16.carl.CstToAst;

public class ReturnStatementNode extends AstNode{
    AstNode returnValue;
    public ReturnStatementNode(AstNode returnValue) {
        this.returnValue = returnValue;
    }

    public AstNode getReturnValue() {
        return returnValue;
    }
}
