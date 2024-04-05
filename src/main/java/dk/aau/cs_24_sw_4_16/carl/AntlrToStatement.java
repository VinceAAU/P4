package dk.aau.cs_24_sw_4_16.carl;


import dk.aau.cs_24_sw_4_16.carl.Statement.Statement;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

public class AntlrToStatement extends CARLBaseVisitor<Object> {

    @Override
    public Object visitImportStatement(CARLParser.ImportStatementContext ctx) {
        return super.visitImportStatement(ctx);
    }

    @Override
    public Object visitFunctionDefinition(CARLParser.FunctionDefinitionContext ctx) {
        return super.visitFunctionDefinition(ctx);
    }

    @Override
    public Object visitStructureDefinition(CARLParser.StructureDefinitionContext ctx) {
        return super.visitStructureDefinition(ctx);
    }

    @Override
    public Object visitVariableDeclaration(CARLParser.VariableDeclarationContext ctx) {
        return super.visitVariableDeclaration(ctx);
    }

    private Map<String, Object> variables = new HashMap<>();

    @Override
    public Object visitAssignment(CARLParser.AssignmentContext ctx) {
        String varName = ctx.IDENTIFIER().getText();
        Object value = new AntlrToExpression().visit(ctx.expression());
        variables.put(varName, value);
        return null;
    }

    @Override
    public Object visitFunctionCall(CARLParser.FunctionCallContext ctx) {
        return super.visitFunctionCall(ctx);
    }

    @Override
    public Object visitMethodCall(CARLParser.MethodCallContext ctx) {
        return super.visitMethodCall(ctx);
    }

    @Override
    public Object visitIfStatement(CARLParser.IfStatementContext ctx) {
        return super.visitIfStatement(ctx);
    }

    @Override
    public Object visitWhileLoop(CARLParser.WhileLoopContext ctx) {
        return super.visitWhileLoop(ctx);
    }

    @Override
    public Object visitReturnStatement(CARLParser.ReturnStatementContext ctx) {
        return super.visitReturnStatement(ctx);
    }

    @Override
    public Object visitCoordinateDeclaration(CARLParser.CoordinateDeclarationContext ctx) {
        return super.visitCoordinateDeclaration(ctx);
    }

    @Override
    public Object visitArrayDefinition(CARLParser.ArrayDefinitionContext ctx) {
        return super.visitArrayDefinition(ctx);
    }

    @Override
    public Object visitArgumentList(CARLParser.ArgumentListContext ctx) {
        return super.visitArgumentList(ctx);
    }

    @Override
    public Object visitParameterList(CARLParser.ParameterListContext ctx) {
        return super.visitParameterList(ctx);
    }

    @Override
    public Object visitBlock(CARLParser.BlockContext ctx) {
        return super.visitBlock(ctx);
    }
}
