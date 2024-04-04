package dk.aau.cs_24_sw_4_16.carl;


import dk.aau.cs_24_sw_4_16.carl.Statement.Statement;

public class AntlrToStatement extends CARLBaseVisitor<Statement> {

    @Override
    public Statement visitImportStatement(CARLParser.ImportStatementContext ctx) {
        return super.visitImportStatement(ctx);
    }

    @Override
    public Statement visitFunctionDefinition(CARLParser.FunctionDefinitionContext ctx) {
        return super.visitFunctionDefinition(ctx);
    }

    @Override
    public Statement visitStructureDefinition(CARLParser.StructureDefinitionContext ctx) {
        return super.visitStructureDefinition(ctx);
    }

    @Override
    public Statement visitVariableDeclaration(CARLParser.VariableDeclarationContext ctx) {
        return super.visitVariableDeclaration(ctx);
    }

    @Override
    public Statement visitAssignment(CARLParser.AssignmentContext ctx) {
        return super.visitAssignment(ctx);
    }

    @Override
    public Statement visitFunctionCall(CARLParser.FunctionCallContext ctx) {
        return super.visitFunctionCall(ctx);
    }

    @Override
    public Statement visitMethodCall(CARLParser.MethodCallContext ctx) {
        return super.visitMethodCall(ctx);
    }

    @Override
    public Statement visitIfStatement(CARLParser.IfStatementContext ctx) {
        return super.visitIfStatement(ctx);
    }

    @Override
    public Statement visitWhileLoop(CARLParser.WhileLoopContext ctx) {
        return super.visitWhileLoop(ctx);
    }

    @Override
    public Statement visitReturnStatement(CARLParser.ReturnStatementContext ctx) {
        return super.visitReturnStatement(ctx);
    }

    @Override
    public Statement visitCoordinateDeclaration(CARLParser.CoordinateDeclarationContext ctx) {
        return super.visitCoordinateDeclaration(ctx);
    }

    @Override
    public Statement visitArrayDefinition(CARLParser.ArrayDefinitionContext ctx) {
        return super.visitArrayDefinition(ctx);
    }

    @Override
    public Statement visitArgumentList(CARLParser.ArgumentListContext ctx) {
        return super.visitArgumentList(ctx);
    }

    @Override
    public Statement visitParameterList(CARLParser.ParameterListContext ctx) {
        return super.visitParameterList(ctx);
    }

    @Override
    public Statement visitBlock(CARLParser.BlockContext ctx) {
        return super.visitBlock(ctx);
    }
}
