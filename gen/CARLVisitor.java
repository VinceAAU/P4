// Generated from C:/Users/shadi/Documents/GitHub/P4/CARL.g4 by ANTLR 4.13.1
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link CARLParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface CARLVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link CARLParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(CARLParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link CARLParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(CARLParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link CARLParser#importStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImportStatement(CARLParser.ImportStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link CARLParser#functionDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionDefinition(CARLParser.FunctionDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CARLParser#structureDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStructureDefinition(CARLParser.StructureDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CARLParser#variableDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableDeclaration(CARLParser.VariableDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link CARLParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(CARLParser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link CARLParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment(CARLParser.AssignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link CARLParser#functionCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionCall(CARLParser.FunctionCallContext ctx);
	/**
	 * Visit a parse tree produced by {@link CARLParser#methodCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodCall(CARLParser.MethodCallContext ctx);
	/**
	 * Visit a parse tree produced by {@link CARLParser#argumentList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgumentList(CARLParser.ArgumentListContext ctx);
	/**
	 * Visit a parse tree produced by {@link CARLParser#parameterList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameterList(CARLParser.ParameterListContext ctx);
	/**
	 * Visit a parse tree produced by {@link CARLParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(CARLParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CARLParser#structInstantiation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStructInstantiation(CARLParser.StructInstantiationContext ctx);
	/**
	 * Visit a parse tree produced by {@link CARLParser#operator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperator(CARLParser.OperatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link CARLParser#ifStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStatement(CARLParser.IfStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link CARLParser#whileLoop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileLoop(CARLParser.WhileLoopContext ctx);
	/**
	 * Visit a parse tree produced by {@link CARLParser#returnStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStatement(CARLParser.ReturnStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link CARLParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(CARLParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link CARLParser#arrayDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayDefinition(CARLParser.ArrayDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CARLParser#arrayAccess}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayAccess(CARLParser.ArrayAccessContext ctx);
	/**
	 * Visit a parse tree produced by {@link CARLParser#propertyAccess}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPropertyAccess(CARLParser.PropertyAccessContext ctx);
	/**
	 * Visit a parse tree produced by {@link CARLParser#coordinateDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCoordinateDeclaration(CARLParser.CoordinateDeclarationContext ctx);
}