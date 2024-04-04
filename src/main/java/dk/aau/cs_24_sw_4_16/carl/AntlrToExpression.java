package dk.aau.cs_24_sw_4_16.carl;


import dk.aau.cs_24_sw_4_16.carl.Expression.Expression;

public class AntlrToExpression extends CARLBaseVisitor<Expression> {
    @Override
    public Expression visitVariableDeclaration(CARLParser.VariableDeclarationContext ctx) {
        return super.visitVariableDeclaration(ctx);
    }

    @Override
    public Expression visitType(CARLParser.TypeContext ctx) {
        return super.visitType(ctx);
    }

    @Override
    public Expression visitAddition(CARLParser.AdditionContext ctx) {
        return super.visitAddition(ctx);
    }

    @Override
    public Expression visitSubtraction(CARLParser.SubtractionContext ctx) {
        return super.visitSubtraction(ctx);
    }

    @Override
    public Expression visitMultiplication(CARLParser.MultiplicationContext ctx) {
        return super.visitMultiplication(ctx);
    }

    @Override
    public Expression visitDivision(CARLParser.DivisionContext ctx) {
        return super.visitDivision(ctx);
    }

    @Override
    public Expression visitModulus(CARLParser.ModulusContext ctx) {
        return super.visitModulus(ctx);
    }

    @Override
    public Expression visitEquals(CARLParser.EqualsContext ctx) {
        return super.visitEquals(ctx);
    }

    @Override
    public Expression visitNotEquals(CARLParser.NotEqualsContext ctx) {
        return super.visitNotEquals(ctx);
    }

    @Override
    public Expression visitLessThan(CARLParser.LessThanContext ctx) {
        return super.visitLessThan(ctx);
    }

    @Override
    public Expression visitGreaterThan(CARLParser.GreaterThanContext ctx) {
        return super.visitGreaterThan(ctx);
    }

    @Override
    public Expression visitLessThanOrEqual(CARLParser.LessThanOrEqualContext ctx) {
        return super.visitLessThanOrEqual(ctx);
    }

    @Override
    public Expression visitGreaterThanOrEqual(CARLParser.GreaterThanOrEqualContext ctx) {
        return super.visitGreaterThanOrEqual(ctx);
    }

    @Override
    public Expression visitAnd(CARLParser.AndContext ctx) {
        return super.visitAnd(ctx);
    }

    @Override
    public Expression visitOr(CARLParser.OrContext ctx) {
        return super.visitOr(ctx);
    }

    @Override
    public Expression visitFunctionCall(CARLParser.FunctionCallContext ctx) {
        return super.visitFunctionCall(ctx);
    }

    @Override
    public Expression visitMethodCall(CARLParser.MethodCallContext ctx) {
        return super.visitMethodCall(ctx);
    }

    @Override
    public Expression visitArrayAccess(CARLParser.ArrayAccessContext ctx) {
        return super.visitArrayAccess(ctx);
    }

    @Override
    public Expression visitPropertyAccess(CARLParser.PropertyAccessContext ctx) {
        return super.visitPropertyAccess(ctx);
    }

    @Override
    public Expression visitStructInstantiation(CARLParser.StructInstantiationContext ctx) {
        return super.visitStructInstantiation(ctx);
    }

    @Override
    public Expression visitRandomBetween(CARLParser.RandomBetweenContext ctx) {
        return super.visitRandomBetween(ctx);
    }
}
