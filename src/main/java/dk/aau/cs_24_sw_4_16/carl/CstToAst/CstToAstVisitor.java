package dk.aau.cs_24_sw_4_16.carl.CstToAst;

import dk.aau.cs_24_sw_4_16.carl.CARLBaseVisitor;
import dk.aau.cs_24_sw_4_16.carl.CARLParser;
import org.antlr.v4.runtime.Token;

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
        } else if (ctx.functionDefinition() != null) {
            return new StatementNode(visitFunctionDefinition(ctx.functionDefinition()));
        } else if (ctx.whileLoop() != null) {
            return new StatementNode(visitWhileLoop(ctx.whileLoop()));
        } else if (ctx.ifStatement() != null) {
            return new StatementNode(visitIfStatement(ctx.ifStatement()));
        }
        throw new RuntimeException("Unknown statement type: " + ctx.getText());
    }

    @Override
    public AstNode visitImportStatement(CARLParser.ImportStatementContext ctx) {
        return super.visitImportStatement(ctx);
    }

    @Override
    public AstNode visitFunctionDefinition(CARLParser.FunctionDefinitionContext ctx) {

        ParameterListNode argumentList = (ParameterListNode) visitParameterList(ctx.parameterList());
        BlockNode block = (BlockNode) visitBlock(ctx.block());
        TypeNode returntype = (TypeNode) visitType(ctx.type());
        return new FunctionDefinitionNode(new IdentifierNode(ctx.IDENTIFIER().getText()), returntype, argumentList, block);
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
        List<AstNode> arguments = new ArrayList<>();
        for (CARLParser.ExpressionContext expression : ctx.argumentList().expression()) {
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
        List<ParameterNode> parameters = new ArrayList<>();

        for (int i = 0; i < ctx.getChildCount() / 3; i++) {
            IdentifierNode identifier = new IdentifierNode(ctx.IDENTIFIER(i).getText());
            TypeNode type = (TypeNode) visit(ctx.type(i));
            parameters.add(new ParameterNode(identifier, type));
        }

        return new ParameterListNode(parameters);
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
        return new BoolNode(ctx.BOOL().getText());
    }

    @Override
    public AstNode visitDummyMethodCall(CARLParser.DummyMethodCallContext ctx) {
        return super.visitDummyMethodCall(ctx);
    }

    @Override
    public AstNode visitParentheses(CARLParser.ParenthesesContext ctx) {
        AstNode expressionToFocus = visit(ctx.expression());
        if (expressionToFocus instanceof IntNode) {
            return new IntNode(expressionToFocus.toString());
        } else if (expressionToFocus instanceof FloatNode) {
            return new FloatNode(expressionToFocus.toString());
        } else if (expressionToFocus instanceof BoolNode) {
            return new BoolNode(expressionToFocus.toString());
        }
        return null;
    }

    @Override
    public AstNode visitMultiplicationDivisionModulus(CARLParser.MultiplicationDivisionModulusContext ctx) {
        return getAstNode(ctx.expression(0), ctx.expression(1), ctx.op);
    }

    @Override
    public AstNode visitRelation(CARLParser.RelationContext ctx) {
        AstNode left = visit(ctx.expression(0));
        AstNode right = visit(ctx.expression(1));
        String op = ctx.op.getText();
        return new RelationsAndLogicalOperatorNode(left, right, op);
    }

    @Override
    public AstNode visitAdditionSubtraction(CARLParser.AdditionSubtractionContext ctx) {
        return getAstNode(ctx.expression(0), ctx.expression(1), ctx.op);
    }

    private AstNode getAstNode(CARLParser.ExpressionContext expression, CARLParser.ExpressionContext expression2, Token op2) {
        AstNode left = visit(expression);
        AstNode right = visit(expression2);
        String op = op2.getText();
        if (left instanceof IntNode && right instanceof IntNode) {
            AstNode value = new BinaryOperatorNode(left, right, op);
            return new IntNode(String.valueOf(value));
        } else if (left instanceof FloatNode || right instanceof FloatNode) {
            AstNode value = new BinaryOperatorNode(left, right, op);
            return new FloatNode(String.valueOf(value));
        } else if (left instanceof IdentifierNode || right instanceof IdentifierNode) {
            System.out.println(left + " " + right);
            System.out.println(new BinaryOperatorNode(left, right, op) + "weljrgnewjrgkjewngkjewewjk");
            return new BinaryOperatorNode(left, right, op);
        }

        throw new RuntimeException("getAstode unhandled node");
    }

    @Override
    public AstNode visitLogical(CARLParser.LogicalContext ctx) {
        AstNode left = visit(ctx.expression(0));
        AstNode right = visit(ctx.expression(1));
        String op = ctx.op.getText();
        if (left instanceof BoolNode && right instanceof BoolNode) {
            AstNode value = new RelationsAndLogicalOperatorNode(left, right, op);
            System.out.println(value);
            return new BoolNode(value.toString());
        }
        throw new RuntimeException("visitLogical unhandled node");
    }

    @Override
    public AstNode visitFloat(CARLParser.FloatContext ctx) {
        return new FloatNode(ctx.getText());
    }

    @Override
    public AstNode visitNot(CARLParser.NotContext ctx) {
        AstNode left = visit(ctx.expression());
        AstNode right = visit(ctx.expression());
        System.out.println(left);
        if (left instanceof BoolNode) {
            AstNode value = new RelationsAndLogicalOperatorNode(left, right, "!");
            System.out.println(value);
            return new BoolNode(value.toString());
        }
        return null;
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
        List<ExpressionNode> expressionNodes = new ArrayList<>();
        for (CARLParser.ExpressionContext exp : ctx.expression()) {
            if (exp.getChildCount() >= 3) {
                AstNode left;
                if (exp.getChild(0) instanceof CARLParser.IdentifierContext) {
                    left = new IdentifierNode(exp.getChild(0).getText());
                    System.out.println(left);
                } else {
                    left = visit(exp.getChild(0));
                    System.out.println(left);
                }
                AstNode right;
                if (exp.getChild(2) instanceof CARLParser.IdentifierContext) {
                    right = new IdentifierNode(exp.getChild(2).getText());
                    System.out.println(right);
                } else {
                    right = visit(exp.getChild(2));
                    System.out.println(right);
                }
                BinaryOperatorNode bin = new BinaryOperatorNode(left, right, exp.getChild(1).getText());
                System.out.println(bin);
                expressionNodes.add(new ExpressionNode(bin));
            } else {
                expressionNodes.add(new ExpressionNode(visit(exp)));
            }
        }

        System.out.println(expressionNodes.get(0));
        List<BlockNode> blocks = new ArrayList<>();
        for (CARLParser.BlockContext block : ctx.block()) {
            blocks.add((BlockNode) visitBlock(block));
        }
        return new IfStatementNode(blocks, expressionNodes);
    }

    @Override
    public AstNode visitWhileLoop(CARLParser.WhileLoopContext ctx) {
        AstNode expression = visit(ctx.expression());
        BlockNode block = (BlockNode) visitBlock(ctx.block());
        System.out.println(expression + "wer gkwergjnerkgwekeg");
        System.out.println(block);
//        return null;
        return new WhileNode(expression, block);
    }

    @Override
    public AstNode visitReturnStatement(CARLParser.ReturnStatementContext ctx) {
        return super.visitReturnStatement(ctx);
    }

    @Override
    public AstNode visitBlock(CARLParser.BlockContext ctx) {
        BlockNode block = new BlockNode();
        System.out.println("hgahashdfsadgasfas");
        System.out.println(ctx.getText());
        for (CARLParser.StatementContext statementContext : ctx.statement()) {
            block.addStatement((StatementNode) visit(statementContext));
        }
        return block;
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
