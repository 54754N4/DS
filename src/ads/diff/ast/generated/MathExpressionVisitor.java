// Generated from MathExpression.g4 by ANTLR 4.9.2

	package ads.diff.ast.generated;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link MathExpressionParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface MathExpressionVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link MathExpressionParser#input}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInput(MathExpressionParser.InputContext ctx);
	/**
	 * Visit a parse tree produced by {@link MathExpressionParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(MathExpressionParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MathExpressionParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTerm(MathExpressionParser.TermContext ctx);
	/**
	 * Visit a parse tree produced by {@link MathExpressionParser#power}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPower(MathExpressionParser.PowerContext ctx);
	/**
	 * Visit a parse tree produced by {@link MathExpressionParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtom(MathExpressionParser.AtomContext ctx);
	/**
	 * Visit a parse tree produced by {@link MathExpressionParser#nested}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNested(MathExpressionParser.NestedContext ctx);
	/**
	 * Visit a parse tree produced by {@link MathExpressionParser#constants}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstants(MathExpressionParser.ConstantsContext ctx);
	/**
	 * Visit a parse tree produced by {@link MathExpressionParser#funcExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncExpression(MathExpressionParser.FuncExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MathExpressionParser#number}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumber(MathExpressionParser.NumberContext ctx);
	/**
	 * Visit a parse tree produced by {@link MathExpressionParser#unary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnary(MathExpressionParser.UnaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link MathExpressionParser#additiveOps}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdditiveOps(MathExpressionParser.AdditiveOpsContext ctx);
	/**
	 * Visit a parse tree produced by {@link MathExpressionParser#multiplicativeOps}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiplicativeOps(MathExpressionParser.MultiplicativeOpsContext ctx);
}