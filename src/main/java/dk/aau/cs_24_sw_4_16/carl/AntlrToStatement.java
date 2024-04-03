package dk.aau.cs_24_sw_4_16.carl;

import java.beans.Statement;

public class AntlrToStatement extends CARLBaseVisitor<Statement> {
//statement
//    : assignment
//    | functionCall
//    | functionDefinition
//    | ifStatement
//    | whileLoop
//    | returnStatement
//    | structureDefinition
//    | importStatement
//    | variableDeclaration
//    | arrayDefinition
//    | coordinateDeclaration
//    ;


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


}
