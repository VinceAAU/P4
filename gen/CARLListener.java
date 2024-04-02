// Generated from C:/Users/shadi/Documents/GitHub/P4/CARL.g4 by ANTLR 4.13.1
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link CARLParser}.
 */
public interface CARLListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link CARLParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(CARLParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link CARLParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(CARLParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link CARLParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(CARLParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CARLParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(CARLParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link CARLParser#importStatement}.
	 * @param ctx the parse tree
	 */
	void enterImportStatement(CARLParser.ImportStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CARLParser#importStatement}.
	 * @param ctx the parse tree
	 */
	void exitImportStatement(CARLParser.ImportStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link CARLParser#functionDefinition}.
	 * @param ctx the parse tree
	 */
	void enterFunctionDefinition(CARLParser.FunctionDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CARLParser#functionDefinition}.
	 * @param ctx the parse tree
	 */
	void exitFunctionDefinition(CARLParser.FunctionDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CARLParser#structureDefinition}.
	 * @param ctx the parse tree
	 */
	void enterStructureDefinition(CARLParser.StructureDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CARLParser#structureDefinition}.
	 * @param ctx the parse tree
	 */
	void exitStructureDefinition(CARLParser.StructureDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CARLParser#variableDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterVariableDeclaration(CARLParser.VariableDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link CARLParser#variableDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitVariableDeclaration(CARLParser.VariableDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link CARLParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(CARLParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link CARLParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(CARLParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link CARLParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(CARLParser.AssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link CARLParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(CARLParser.AssignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link CARLParser#functionCall}.
	 * @param ctx the parse tree
	 */
	void enterFunctionCall(CARLParser.FunctionCallContext ctx);
	/**
	 * Exit a parse tree produced by {@link CARLParser#functionCall}.
	 * @param ctx the parse tree
	 */
	void exitFunctionCall(CARLParser.FunctionCallContext ctx);
	/**
	 * Enter a parse tree produced by {@link CARLParser#methodCall}.
	 * @param ctx the parse tree
	 */
	void enterMethodCall(CARLParser.MethodCallContext ctx);
	/**
	 * Exit a parse tree produced by {@link CARLParser#methodCall}.
	 * @param ctx the parse tree
	 */
	void exitMethodCall(CARLParser.MethodCallContext ctx);
	/**
	 * Enter a parse tree produced by {@link CARLParser#argumentList}.
	 * @param ctx the parse tree
	 */
	void enterArgumentList(CARLParser.ArgumentListContext ctx);
	/**
	 * Exit a parse tree produced by {@link CARLParser#argumentList}.
	 * @param ctx the parse tree
	 */
	void exitArgumentList(CARLParser.ArgumentListContext ctx);
	/**
	 * Enter a parse tree produced by {@link CARLParser#parameterList}.
	 * @param ctx the parse tree
	 */
	void enterParameterList(CARLParser.ParameterListContext ctx);
	/**
	 * Exit a parse tree produced by {@link CARLParser#parameterList}.
	 * @param ctx the parse tree
	 */
	void exitParameterList(CARLParser.ParameterListContext ctx);
	/**
	 * Enter a parse tree produced by {@link CARLParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(CARLParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CARLParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(CARLParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CARLParser#structInstantiation}.
	 * @param ctx the parse tree
	 */
	void enterStructInstantiation(CARLParser.StructInstantiationContext ctx);
	/**
	 * Exit a parse tree produced by {@link CARLParser#structInstantiation}.
	 * @param ctx the parse tree
	 */
	void exitStructInstantiation(CARLParser.StructInstantiationContext ctx);
	/**
	 * Enter a parse tree produced by {@link CARLParser#operator}.
	 * @param ctx the parse tree
	 */
	void enterOperator(CARLParser.OperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link CARLParser#operator}.
	 * @param ctx the parse tree
	 */
	void exitOperator(CARLParser.OperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link CARLParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void enterIfStatement(CARLParser.IfStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CARLParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void exitIfStatement(CARLParser.IfStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link CARLParser#whileLoop}.
	 * @param ctx the parse tree
	 */
	void enterWhileLoop(CARLParser.WhileLoopContext ctx);
	/**
	 * Exit a parse tree produced by {@link CARLParser#whileLoop}.
	 * @param ctx the parse tree
	 */
	void exitWhileLoop(CARLParser.WhileLoopContext ctx);
	/**
	 * Enter a parse tree produced by {@link CARLParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void enterReturnStatement(CARLParser.ReturnStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CARLParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void exitReturnStatement(CARLParser.ReturnStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link CARLParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(CARLParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link CARLParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(CARLParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link CARLParser#arrayDefinition}.
	 * @param ctx the parse tree
	 */
	void enterArrayDefinition(CARLParser.ArrayDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CARLParser#arrayDefinition}.
	 * @param ctx the parse tree
	 */
	void exitArrayDefinition(CARLParser.ArrayDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CARLParser#arrayAccess}.
	 * @param ctx the parse tree
	 */
	void enterArrayAccess(CARLParser.ArrayAccessContext ctx);
	/**
	 * Exit a parse tree produced by {@link CARLParser#arrayAccess}.
	 * @param ctx the parse tree
	 */
	void exitArrayAccess(CARLParser.ArrayAccessContext ctx);
	/**
	 * Enter a parse tree produced by {@link CARLParser#propertyAccess}.
	 * @param ctx the parse tree
	 */
	void enterPropertyAccess(CARLParser.PropertyAccessContext ctx);
	/**
	 * Exit a parse tree produced by {@link CARLParser#propertyAccess}.
	 * @param ctx the parse tree
	 */
	void exitPropertyAccess(CARLParser.PropertyAccessContext ctx);
	/**
	 * Enter a parse tree produced by {@link CARLParser#coordinateDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterCoordinateDeclaration(CARLParser.CoordinateDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link CARLParser#coordinateDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitCoordinateDeclaration(CARLParser.CoordinateDeclarationContext ctx);
}