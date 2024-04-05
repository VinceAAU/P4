package dk.aau.cs_24_sw_4_16.carl;


import dk.aau.cs_24_sw_4_16.carl.Expression.BinaryOperations;
import dk.aau.cs_24_sw_4_16.carl.Expression.Expression;
import dk.aau.cs_24_sw_4_16.carl.Expression.Type;

import java.util.Objects;

public class AntlrToExpression extends CARLBaseVisitor<Object> {
    @Override
    public Object visitMultiplication(CARLParser.MultiplicationContext ctx) {
        return super.visitMultiplication(ctx);
    }

    @Override
    public Object visitAddition(CARLParser.AdditionContext ctx) {
//        Expression left = visit(ctx.getChild(0));
//        Expression right = visit(ctx.getChild(2));
//
//        return new BinaryOperations(left, right,BinaryOperations.operator.ADD);
        return null;
    }

    @Override
    public Object visitOr(CARLParser.OrContext ctx) {
        return super.visitOr(ctx);
    }

    @Override
    public Object visitLessThanOrEqual(CARLParser.LessThanOrEqualContext ctx) {
        return super.visitLessThanOrEqual(ctx);
    }

    @Override
    public Object visitString(CARLParser.StringContext ctx) {
        if (ctx.STRING() != null) {
            return ctx.STRING().getText().substring(1, ctx.STRING().getText().length() - 2);
        }
        return null;    }

    @Override
    public Object visitGreaterThanOrEqual(CARLParser.GreaterThanOrEqualContext ctx) {
        return super.visitGreaterThanOrEqual(ctx);
    }

    @Override
    public Object visitInt(CARLParser.IntContext ctx) {
        if (ctx.INT() != null) {
            return Integer.parseInt(ctx.INT().getText());
        }
        return null;
    }

    @Override
    public Object visitNotEquals(CARLParser.NotEqualsContext ctx) {
        return super.visitNotEquals(ctx);
    }

    @Override
    public Object visitFloat(CARLParser.FloatContext ctx) {
        if (ctx.FLOAT() != null) {
            return Float.parseFloat(ctx.FLOAT().getText());
        }
        return null;    }

    @Override
    public Object visitNot(CARLParser.NotContext ctx) {
        return super.visitNot(ctx);
    }

    @Override
    public Object visitLessThan(CARLParser.LessThanContext ctx) {
        return super.visitLessThan(ctx);
    }

    @Override
    public Object visitEquals(CARLParser.EqualsContext ctx) {
        return super.visitEquals(ctx);
    }

    @Override
    public Object visitRandomBetween(CARLParser.RandomBetweenContext ctx) {
        return super.visitRandomBetween(ctx);
    }

    @Override
    public Object visitIdentifier(CARLParser.IdentifierContext ctx) {
        return super.visitIdentifier(ctx);
    }

    @Override
    public Object visitSubtraction(CARLParser.SubtractionContext ctx) {
        return super.visitSubtraction(ctx);
    }

    @Override
    public Object visitGreaterThan(CARLParser.GreaterThanContext ctx) {
        return super.visitGreaterThan(ctx);
    }

    @Override
    public Object visitModulus(CARLParser.ModulusContext ctx) {
        return super.visitModulus(ctx);
    }

    @Override
    public Object visitAnd(CARLParser.AndContext ctx) {
        return super.visitAnd(ctx);
    }

    @Override
    public Object visitDivision(CARLParser.DivisionContext ctx) {
        return super.visitDivision(ctx);
    }

    @Override
    public Object visitParentheses(CARLParser.ParenthesesContext ctx) {
        return super.visitParentheses(ctx);
    }

    @Override
    public Object visitStructInstantiation(CARLParser.StructInstantiationContext ctx) {
        return super.visitStructInstantiation(ctx);
    }

    @Override
    public Object visitBool(CARLParser.BoolContext ctx) {
        if (ctx.BOOL() != null) {
            return Objects.equals(ctx.BOOL().getText(), "true");
        }
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
    public Object visitArrayAccess(CARLParser.ArrayAccessContext ctx) {
        return super.visitArrayAccess(ctx);
    }

    @Override
    public Object visitPropertyAccess(CARLParser.PropertyAccessContext ctx) {
        return super.visitPropertyAccess(ctx);
    }

    @Override
    public Object visitPrimitiveTypeForArray(CARLParser.PrimitiveTypeForArrayContext ctx) {
        return super.visitPrimitiveTypeForArray(ctx);
    }

    @Override
    public Object visitType(CARLParser.TypeContext ctx) {
        return super.visitType(ctx);
    }
}
