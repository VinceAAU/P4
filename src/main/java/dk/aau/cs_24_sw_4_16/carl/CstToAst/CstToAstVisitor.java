package dk.aau.cs_24_sw_4_16.carl.CstToAst;

import dk.aau.cs_24_sw_4_16.carl.CARLBaseVisitor;
import dk.aau.cs_24_sw_4_16.carl.CARLParser;
import org.antlr.v4.runtime.tree.TerminalNode;

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
        } else if (ctx.returnStatement() != null) {
            return new StatementNode(visitReturnStatement(ctx.returnStatement()));
        } else if (ctx.structureDefinition() != null) {
            return new StatementNode(visitStructureDefinition(ctx.structureDefinition()));
        } else if (ctx.propertyAssignment() != null) {
            return new StatementNode(visitPropertyAssignment(ctx.propertyAssignment()));
        } else if (ctx.arrayDeclaration() != null) {
            return new StatementNode(visitArrayDeclaration(ctx.arrayDeclaration()));
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
        String type = ctx.structType().getText();
        List<VariableDeclarationNode> variableDeclarationNodes = new ArrayList<>();
        for (var variableDeclaration : ctx.variableDeclaration()) {
            variableDeclarationNodes.add((VariableDeclarationNode) visit(variableDeclaration));
        }
        return new StructureDefinitionNode(new IdentifierNode(ctx.IDENTIFIER().getText()), type, variableDeclarationNodes);
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
    public AstNode visitArrayDeclaration(CARLParser.ArrayDeclarationContext ctx) {
        String identifier = ctx.IDENTIFIER().getText();

        IdentifierNode identifierNode = new IdentifierNode(identifier);

        TypeNode type = (TypeNode) /*This is the part where we pray to God that it does indeed return a TypeNode */ visit(ctx.legalArrayType());

        /*List<Integer> sizes = ctx.arrayOptionalIndex().stream()
                .map(arrayOptionalIndexContext -> {
                    if (arrayOptionalIndexContext.INT() == null)
                        return -1;
                    else
                        return Integer.parseInt(arrayOptionalIndexContext.INT().getText()); //And on this line, we pray that the int is indeed an int...
                })
                .toList();*/

        List<AstNode> sizes = ctx.arrayOptionalIndex().stream()
                .map(arrayOptionalIndexContext -> {
                    if (arrayOptionalIndexContext.expression() == null)
                        return new IntNode(-1);
                    else
                        return new ExpressionNode(visit(arrayOptionalIndexContext.expression()));
                })
                .toList();

        return new ArrayDefinitionNode(type, sizes, identifierNode);


    }

    @Override
    public AstNode visitLegalArrayType(CARLParser.LegalArrayTypeContext ctx) {
        return new TypeNode(ctx.getText());
    }

    @Override
    public AstNode visitType(CARLParser.TypeContext ctx) {
        return new TypeNode(ctx.getText());

    }

    @Override
    public AstNode visitAssignment(CARLParser.AssignmentContext ctx) {
        AstNode value = visit(ctx.expression());

        if (ctx.IDENTIFIER() != null)
            return new AssignmentNode(new IdentifierNode(ctx.IDENTIFIER().getText()), value);
        else if (ctx.arrayAccess() != null)
            return new ArrayAssignmentNode((ArrayAccessNode) visit(ctx.arrayAccess()), value);
        else
            throw new Error("Neither an array access nor an identifier");
    }

    @Override
    public AstNode visitArrayAccess(CARLParser.ArrayAccessContext ctx) {
        IdentifierNode id = new IdentifierNode(ctx.IDENTIFIER().getText());

        //List<Integer> indices = ctx.INT().stream().map(tn -> Integer.parseInt(tn.getText())).toList();

        List<AstNode> indices = ctx.expression().stream().map(e -> new ExpressionNode(visit(e)).getNode()).toList();

        return new ArrayAccessNode(id, indices);
    }

    @Override
    public AstNode visitFunctionCall(CARLParser.FunctionCallContext ctx) {
        List<AstNode> arguments = new ArrayList<>();
        if (ctx.argumentList() != null) {
            for (CARLParser.ExpressionContext expression : ctx.argumentList().expression()) {
                arguments.add(visit(expression));
            }
        }
        return new FunctionCallNode(new IdentifierNode(ctx.IDENTIFIER().getText()), arguments);
    }

    @Override
    public AstNode visitMethodCall(CARLParser.MethodCallContext ctx) {
        ctx.argumentList();
        if (ctx.IDENTIFIER() != null) {
            return new MethodCallNode((PropertyAccessNode) visitPropertyAccess(ctx.propertyAccess()), visitArgumentList(ctx.argumentList()), new IdentifierNode(ctx.IDENTIFIER().getText()));
        }
        return new MethodCallNode((PropertyAccessNode) visitPropertyAccess(ctx.propertyAccess()), visitArgumentList(ctx.argumentList()));
    }

    @Override
    public AstNode visitArgumentList(CARLParser.ArgumentListContext ctx) {
        List<AstNode> list = new ArrayList<>();
        if (ctx != null) {
            for (CARLParser.ExpressionContext exp : ctx.expression()) {
                list.add(visit(exp));
            }
        }
        return new ArgumentListNode(list);
    }

    @Override
    public AstNode visitParameterList(CARLParser.ParameterListContext ctx) {
        List<ParameterNode> parameters = new ArrayList<>();
        if (ctx != null) {
                for (int i = 0; i < Math.ceilDiv(ctx.getChildCount(), 4); i++) {
                    //System.out.println(ctx.IDENTIFIER().size());
                    IdentifierNode identifier = new IdentifierNode(ctx.IDENTIFIER(i).getText());
                    TypeNode type = (TypeNode) visit(ctx.type(i));
                    parameters.add(new ParameterNode(identifier, type));
                }
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
    public AstNode visitInteger(CARLParser.IntegerContext ctx) {
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

    //This does not work
    @Override
    public AstNode visitParentheses(CARLParser.ParenthesesContext ctx) {
        AstNode expressionToFocus = visit(ctx.expression());
        AstNode left;
        AstNode right;
        String op;

        if (expressionToFocus instanceof BinaryOperatorNode) {
            left = ((BinaryOperatorNode) expressionToFocus).getLeft();
            right = ((BinaryOperatorNode) expressionToFocus).getRight();
            op = ((BinaryOperatorNode) expressionToFocus).getOperator();
            if (left instanceof IntNode && right instanceof IntNode) {
                AstNode value = new BinaryOperatorNode(left, right, op);
                return new IntNode(String.valueOf(value));
            } else if (left instanceof FloatNode && right instanceof FloatNode) {
                AstNode value = new BinaryOperatorNode(left, right, op);
                return new FloatNode(String.valueOf(value));
            } else if (left instanceof IdentifierNode || right instanceof IdentifierNode) {
                return new BinaryOperatorNode(left, right, op);
            }
        } else if (expressionToFocus instanceof RelationsAndLogicalOperatorNode) {
            left = ((RelationsAndLogicalOperatorNode) expressionToFocus).getLeft();
            right = ((RelationsAndLogicalOperatorNode) expressionToFocus).getRight();
            op = ((RelationsAndLogicalOperatorNode) expressionToFocus).getOperator();

            return new RelationsAndLogicalOperatorNode(left, right, op);

        }
        throw new RuntimeException("Parentheses expression does not match the correct type.");
    }

    @Override
    public AstNode visitMultiplicationDivisionModulus(CARLParser.MultiplicationDivisionModulusContext ctx) {
        AstNode left = visit(ctx.expression(0));
        AstNode right = visit(ctx.expression(1));
        String op = ctx.op.getText();
        return new BinaryOperatorNode(left, right, op);
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
        AstNode left = visit(ctx.expression(0));
        AstNode right = visit(ctx.expression(1));
        String op = ctx.op.getText();
        return new BinaryOperatorNode(left, right, op);
    }

    @Override
    public AstNode visitLogical(CARLParser.LogicalContext ctx) {
        AstNode left = visit(ctx.expression(0));
        AstNode right = visit(ctx.expression(1));
        String op = ctx.op.getText();
        return new RelationsAndLogicalOperatorNode(left, right, op);
//        if (left instanceof BoolNode && right instanceof BoolNode) {
//            AstNode value = new RelationsAndLogicalOperatorNode(left, right, op);
//            return new BoolNode(value.toString());
//        } else if (left instanceof IdentifierNode || right instanceof IdentifierNode) {
//            AstNode value = new RelationsAndLogicalOperatorNode(left, right, op);
//            return value;
//        } else if (left instanceof RelationsAndLogicalOperatorNode || right instanceof RelationsAndLogicalOperatorNode){
//            return new RelationsAndLogicalOperatorNode(left,right,op);
//        }
//        throw new RuntimeException("visitLogical unhandled node");
    }

    @Override
    public AstNode visitFloat(CARLParser.FloatContext ctx) {
        return new FloatNode(ctx.getText());
    }

    @Override
    public AstNode visitFpNum(CARLParser.FpNumContext ctx) {
        return new FloatNode(ctx.getText());
    }

    @Override
    public AstNode visitNot(CARLParser.NotContext ctx) {
        AstNode left = visit(ctx.expression()); //Why do we need both a left and a right if they're the same? -Vincent
        AstNode right = visit(ctx.expression());
        AstNode value = new RelationsAndLogicalOperatorNode(left, right, "!");
        return value;
    }

    @Override
    public AstNode visitRandomBetween(CARLParser.RandomBetweenContext ctx) {
        AstNode left = visit(ctx.expression(0));
        AstNode right = visit(ctx.expression(1));
        return new BinaryOperatorNode(left, right, "..");
    }


    @Override
    public AstNode visitIfStatement(CARLParser.IfStatementContext ctx) {
        List<ExpressionNode> expressionNodes = new ArrayList<>();
        for (CARLParser.ExpressionContext exp : ctx.expression()) {
            if (exp.getChildCount() >= 3) {
                AstNode left;
                if (exp.getChild(0) instanceof CARLParser.IdentifierContext) {
                    left = new IdentifierNode(exp.getChild(0).getText());
                } else {
                    left = visit(exp.getChild(0));
                }
                AstNode right;
                if (exp.getChild(2) instanceof CARLParser.IdentifierContext) {
                    right = new IdentifierNode(exp.getChild(2).getText());
                } else {
                    right = visit(exp.getChild(2));
                }
                RelationsAndLogicalOperatorNode bin = new RelationsAndLogicalOperatorNode(left, right, exp.getChild(1).getText());
                expressionNodes.add(new ExpressionNode(bin));
            } else {
                expressionNodes.add(new ExpressionNode(visit(exp)));
            }
        }
        List<BlockNode> blocks = new ArrayList<>();
        for (CARLParser.BlockContext block : ctx.block()) {
            blocks.add((BlockNode) visitBlock(block));
        }
        return new IfStatementNode(blocks, expressionNodes);
    }

    @Override
    public AstNode visitWhileLoop(CARLParser.WhileLoopContext ctx) {
        ExpressionNode expression;
        if (ctx.expression().getChildCount() >= 3) {
            AstNode left;
            if (ctx.expression().getChild(0) instanceof CARLParser.IdentifierContext) {
                left = new IdentifierNode(ctx.expression().getChild(0).getText());
            } else {
                left = visit(ctx.expression().getChild(0));
            }

            AstNode right;
            if (ctx.expression().getChild(2) instanceof CARLParser.IdentifierContext) {
                right = new IdentifierNode(ctx.expression().getChild(2).getText());
            } else {
                right = visit(ctx.expression().getChild(2));
            }
            RelationsAndLogicalOperatorNode bin = new RelationsAndLogicalOperatorNode(left, right, ctx.expression().getChild(1).getText());
            expression = (new ExpressionNode(bin));
        } else {
            expression = (new ExpressionNode(visit(ctx.expression())));

        }
        BlockNode block = (BlockNode) visitBlock(ctx.block());
        return new WhileLoopNode(expression, block);

    }

    @Override
    public AstNode visitReturnStatement(CARLParser.ReturnStatementContext ctx) {

        AstNode expression;
        if (ctx.expression() != null) {
            expression = visit(ctx.expression());
        } else {
            expression = null;
        }
        return new ReturnStatementNode(expression);
    }

    @Override
    public AstNode visitBlock(CARLParser.BlockContext ctx) {
        BlockNode block = new BlockNode();
        for (CARLParser.StatementContext statementContext : ctx.statement()) {
            block.addStatement((StatementNode) visit(statementContext));
        }
        return block;
    }

    @Override
    public AstNode visitPropertyAccess(CARLParser.PropertyAccessContext ctx) {
        List<IdentifierNode> identifiers = new ArrayList<>();
        for (TerminalNode identifier : ctx.IDENTIFIER()) {
            identifiers.add(new IdentifierNode(identifier.toString()));
        }
        return new PropertyAccessNode(ctx.structType().getText(), identifiers);
    }

    @Override
    public AstNode visitPropertyAssignment(CARLParser.PropertyAssignmentContext ctx) {
        AstNode property = visitPropertyAccess(ctx.propertyAccess());
        AstNode value = visit(ctx.expression());
        return new PropertyAssignmentNode((PropertyAccessNode) property, value);
    }

    @Override
    public AstNode visitCoordinateDeclaration(CARLParser.CoordinateDeclarationContext ctx) {
        return super.visitCoordinateDeclaration(ctx);
    }
}