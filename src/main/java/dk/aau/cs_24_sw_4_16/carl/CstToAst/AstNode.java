package dk.aau.cs_24_sw_4_16.carl.CstToAst;
public abstract class AstNode{
    public abstract <T> T accept(AstVisitor<T> visitor);
}