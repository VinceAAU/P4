package dk.aau.cs_24_sw_4_16.carl.CstToAst;

import dk.aau.cs_24_sw_4_16.carl.CARLBaseVisitor;
import dk.aau.cs_24_sw_4_16.carl.CARLParser;

import java.util.ArrayList;
import java.util.List;

public class CstToAstVisitor extends CARLBaseVisitor<AstNode> {

    @Override
    public AstNode visitProgram(CARLParser.ProgramContext ctx) {
        ProgramNode programNode = new ProgramNode();
        for (CARLParser.StatementContext statementContext : ctx.statement()) {
            programNode.addStatement((StatementNode) visit(statementContext));
        }
        return programNode;
    }

    @Override
    public AstNode visitStatement(CARLParser.StatementContext ctx) {
        if (ctx.assignment() != null) {
            return new StatementNode(visitAssignment(ctx.assignment()));
        } else if (ctx.variableDeclaration() != null) {
            return new StatementNode(visitVariableDeclaration(ctx.variableDeclaration()));
        } else if (ctx.functionCall() != null) {
            return new StatementNode(visitFunctionCall(ctx.functionCall()));
        }
        throw new RuntimeException("Unknown statement type: " + ctx.getText());
    }

    @Override
    public AstNode visitImportStatement(CARLParser.ImportStatementContext ctx) {
        return super.visitImportStatement(ctx);
    }

    @Override
    public AstNode visitFunctionDefinition(CARLParser.FunctionDefinitionContext ctx) {
        return super.visitFunctionDefinition(ctx);
    }

    @Override
    public AstNode visitStructureDefinition(CARLParser.StructureDefinitionContext ctx) {
        return super.visitStructureDefinition(ctx);
    }

    @Override
    public AstNode visitVariableDeclaration(CARLParser.VariableDeclarationContext ctx) {
        String identifier = ctx.IDENTIFIER().getText();

        IdentifierNode identifierNode = new IdentifierNode(identifier);

        TypeNode type = (TypeNode) visit(ctx.type());
        AstNode value = visit(ctx.expression());

        return new VariableDeclarationNode(identifierNode, type, value);
    }

    @Override
    public AstNode visitPrimitiveTypeForArray(CARLParser.PrimitiveTypeForArrayContext ctx) {
        return super.visitPrimitiveTypeForArray(ctx);
    }

    @Override
    public AstNode visitType(CARLParser.TypeContext ctx) {
        return new TypeNode(ctx.getText());

    }

    @Override
    public AstNode visitAssignment(CARLParser.AssignmentContext ctx) {
        AstNode value = visit(ctx.expression());
        return new AssignmentNode(new IdentifierNode(ctx.IDENTIFIER().getText()), value);
    }

    @Override
    public AstNode visitFunctionCall(CARLParser.FunctionCallContext ctx) {
        //        for (CARLParser.StatementContext statementContext : ctx.statement()) {
        //            programNode.addStatement((StatementNode) visit(statementContext));
        //        }
        List<AstNode> arguments = new ArrayList<>();
        for (CARLParser.ExpressionContext expression : ctx.argumentList().expression()) {
            System.out.println("hello");
            arguments.add(visit(expression));
        }
        return new FunctionCallNode(new IdentifierNode(ctx.IDENTIFIER().getText()), arguments);
    }

    @Override
    public AstNode visitMethodCall(CARLParser.MethodCallContext ctx) {
        return super.visitMethodCall(ctx);
    }

    @Override
    public AstNode visitArgumentList(CARLParser.ArgumentListContext ctx) {
        return super.visitArgumentList(ctx);
    }

    @Override
    public AstNode visitParameterList(CARLParser.ParameterListContext ctx) {
        return super.visitParameterList(ctx);
    }


    @Override
    public AstNode visitString(CARLParser.StringContext ctx) {
        return new StringNode(ctx.getText());
    }

    @Override
    public AstNode visitInt(CARLParser.IntContext ctx) {
        return new IntNode(ctx.getText());
    }

    @Override
    public AstNode visitDummyFunctionCallExpr(CARLParser.DummyFunctionCallExprContext ctx) {
        return super.visitDummyFunctionCallExpr(ctx);
    }

    @Override
    public AstNode visitIdentifier(CARLParser.IdentifierContext ctx) {
        return new IdentifierNode(ctx.IDENTIFIER().getText());
    }

    @Override
    public AstNode visitBool(CARLParser.BoolContext ctx) {
        return super.visitBool(ctx);
    }

    @Override
    public AstNode visitDummyMethodCall(CARLParser.DummyMethodCallContext ctx) {
        return super.visitDummyMethodCall(ctx);
    }

    @Override
    public AstNode visitParentheses(CARLParser.ParenthesesContext ctx) {
        return super.visitParentheses(ctx);
    }

    @Override
    public AstNode visitMultiplicationDivisionModulus(CARLParser.MultiplicationDivisionModulusContext ctx) {
        AstNode left = visit(ctx.expression(0));
        AstNode right = visit(ctx.expression(1));
        String op = ctx.op.getText();
        AstNode value = new BinaryOperatorNode(left, right, op);
        if(left instanceof IntNode && right instanceof IntNode) {
            return new IntNode(String.valueOf(value));
        } else if (left instanceof FloatNode || right instanceof FloatNode) {
            return new FloatNode(String.valueOf(value));
        }
        return null;
    }

    @Override
    public AstNode visitRelation(CARLParser.RelationContext ctx) {
        return super.visitRelation(ctx);
    }

    @Override
    public AstNode visitAdditionSubtraction(CARLParser.AdditionSubtractionContext ctx) {
        AstNode left = visit(ctx.expression(0));
        AstNode right = visit(ctx.expression(1));
        String op = ctx.op.getText();
        AstNode value = new BinaryOperatorNode(left, right, op);
        if(left instanceof IntNode && right instanceof IntNode) {
            return new IntNode(String.valueOf(value));
        } else if (left instanceof FloatNode || right instanceof FloatNode) {
            return new FloatNode(String.valueOf(value));
        }
        return null;
    }

    @Override
    public AstNode visitLogical(CARLParser.LogicalContext ctx) {
        return super.visitLogical(ctx);
    }

    @Override
    public AstNode visitFloat(CARLParser.FloatContext ctx) {
        return new FloatNode(ctx.getText());
    }

    @Override
    public AstNode visitNot(CARLParser.NotContext ctx) {
        return super.visitNot(ctx);
    }

    @Override
    public AstNode visitRandomBetween(CARLParser.RandomBetweenContext ctx) {
        return super.visitRandomBetween(ctx);
    }

    @Override
    public AstNode visitStructInstantiation(CARLParser.StructInstantiationContext ctx) {
        return super.visitStructInstantiation(ctx);
    }

    @Override
    public AstNode visitIfStatement(CARLParser.IfStatementContext ctx) {

        return super.visitIfStatement(ctx);
    }

    @Override
    public AstNode visitWhileLoop(CARLParser.WhileLoopContext ctx) {
        return super.visitWhileLoop(ctx);
    }

    @Override
    public AstNode visitReturnStatement(CARLParser.ReturnStatementContext ctx) {
        return super.visitReturnStatement(ctx);
    }

    @Override
    public AstNode visitBlock(CARLParser.BlockContext ctx) {
        return super.visitBlock(ctx);
    }

    @Override
    public AstNode visitArrayDefinition(CARLParser.ArrayDefinitionContext ctx) {
        return super.visitArrayDefinition(ctx);
    }

    @Override
    public AstNode visitArrayAccess(CARLParser.ArrayAccessContext ctx) {
        return super.visitArrayAccess(ctx);
    }

    @Override
    public AstNode visitPropertyAccess(CARLParser.PropertyAccessContext ctx) {
        return super.visitPropertyAccess(ctx);
    }

    @Override
    public AstNode visitCoordinateDeclaration(CARLParser.CoordinateDeclarationContext ctx) {
        return super.visitCoordinateDeclaration(ctx);
    }
}
