package dk.aau.cs_24_sw_4_16.carl.CstToAst;

public interface AstVisitor<T> {
    T visitAssignmentNode(AssignmentNode node);

    T visitFloatNode(FloatNode node);

    T visitIntNode(IntNode node);

    T visitProgramNode(ProgramNode node);

    T visitStatementNode(StatementNode node);

    T visitTypeNode(TypeNode node);

    T visitVariableDeclarationNode(VariableDeclarationNode node);

    T visitFunctionCallNode(FunctionCallNode node);

    T visitFunctionDefinitionNode(FunctionDefinitionNode node);

    T visitIfStatementNode(IfStatementNode node);

    T visitWhileLoopNode(WhileLoopNode node);

    T visitReturnStatementNode(ReturnStatementNode node);

    T visitStructureDefinitionNode(StructureDefinitionNode node);

    T visitImportStatementNode(ImportStatementNode node);

    T visitArrayDefinitionNode(ArrayDefinitionNode node);

    T visitCoordinateDeclarationNode(CoordinateDeclarationNode node);

    T visitMethodCallNode(MethodCallNode node);


    T visitBinaryExpressionNode(BinaryExpressionNode node);

    T visitUnaryExpressionNode(UnaryExpressionNode node);
    T visitLiteralNode(LiteralNode node);
    T visitParenthesizedExpressionNode(ParenthesizedExpressionNode node);
    T visitAdditionExpressionNode(AdditionExpressionNode node);
    T visitSubtractionExpressionNode(SubtractionExpressionNode node);
    T visitMultiplicationExpressionNode(MultiplicationExpressionNode node);
    T visitDivisionExpressionNode(DivisionExpressionNode node);
    T visitModulusExpressionNode(ModulusExpressionNode node);
    T visitLessThanExpressionNode(LessThanExpressionNode node);
    T visitLessThanOrEqualExpressionNode(LessThanOrEqualExpressionNode node);
    T visitGreaterThanExpressionNode(GreaterThanExpressionNode node);
    T visitGreaterThanOrEqualExpressionNode(GreaterThanOrEqualExpressionNode node);
    T visitEqualsExpressionNode(EqualsExpressionNode node);
    T visitNotEqualsExpressionNode(NotEqualsExpressionNode node);
    T visitAndExpressionNode(AndExpressionNode node);
    T visitOrExpressionNode(OrExpressionNode node);
    T visitNotExpressionNode(NotExpressionNode node);
    T visitRandomBetweenExpressionNode(RandomBetweenExpressionNode node);
    T visitBlockNode(BlockNode node);
    T visitArrayAccessNode(ArrayAccessNode node);
    T visitPropertyAccessNode(PropertyAccessNode node);
    T visitMethodCallExpressionNode(MethodCallExpressionNode node);
    T visitBooleanLiteralNode(BooleanLiteralNode node);
    T visitStringLiteralNode(StringLiteralNode node);
    T visitIntegerLiteralNode(IntegerLiteralNode node);
    T visitFloatLiteralNode(FloatLiteralNode node);

}