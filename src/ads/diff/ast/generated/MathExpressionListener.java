// Generated from MathExpression.g4 by ANTLR 4.9.2

	package ads.diff.ast.generated;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link MathExpressionParser}.
 */
public interface MathExpressionListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link MathExpressionParser#input}.
	 * @param ctx the parse tree
	 */
	void enterInput(MathExpressionParser.InputContext ctx);
	/**
	 * Exit a parse tree produced by {@link MathExpressionParser#input}.
	 * @param ctx the parse tree
	 */
	void exitInput(MathExpressionParser.InputContext ctx);
	/**
	 * Enter a parse tree produced by {@link MathExpressionParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(MathExpressionParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MathExpressionParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(MathExpressionParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MathExpressionParser#term}.
	 * @param ctx the parse tree
	 */
	void enterTerm(MathExpressionParser.TermContext ctx);
	/**
	 * Exit a parse tree produced by {@link MathExpressionParser#term}.
	 * @param ctx the parse tree
	 */
	void exitTerm(MathExpressionParser.TermContext ctx);
	/**
	 * Enter a parse tree produced by {@link MathExpressionParser#power}.
	 * @param ctx the parse tree
	 */
	void enterPower(MathExpressionParser.PowerContext ctx);
	/**
	 * Exit a parse tree produced by {@link MathExpressionParser#power}.
	 * @param ctx the parse tree
	 */
	void exitPower(MathExpressionParser.PowerContext ctx);
	/**
	 * Enter a parse tree produced by {@link MathExpressionParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterAtom(MathExpressionParser.AtomContext ctx);
	/**
	 * Exit a parse tree produced by {@link MathExpressionParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitAtom(MathExpressionParser.AtomContext ctx);
	/**
	 * Enter a parse tree produced by {@link MathExpressionParser#nested}.
	 * @param ctx the parse tree
	 */
	void enterNested(MathExpressionParser.NestedContext ctx);
	/**
	 * Exit a parse tree produced by {@link MathExpressionParser#nested}.
	 * @param ctx the parse tree
	 */
	void exitNested(MathExpressionParser.NestedContext ctx);
	/**
	 * Enter a parse tree produced by {@link MathExpressionParser#constants}.
	 * @param ctx the parse tree
	 */
	void enterConstants(MathExpressionParser.ConstantsContext ctx);
	/**
	 * Exit a parse tree produced by {@link MathExpressionParser#constants}.
	 * @param ctx the parse tree
	 */
	void exitConstants(MathExpressionParser.ConstantsContext ctx);
	/**
	 * Enter a parse tree produced by {@link MathExpressionParser#funcExpression}.
	 * @param ctx the parse tree
	 */
	void enterFuncExpression(MathExpressionParser.FuncExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MathExpressionParser#funcExpression}.
	 * @param ctx the parse tree
	 */
	void exitFuncExpression(MathExpressionParser.FuncExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MathExpressionParser#number}.
	 * @param ctx the parse tree
	 */
	void enterNumber(MathExpressionParser.NumberContext ctx);
	/**
	 * Exit a parse tree produced by {@link MathExpressionParser#number}.
	 * @param ctx the parse tree
	 */
	void exitNumber(MathExpressionParser.NumberContext ctx);
	/**
	 * Enter a parse tree produced by {@link MathExpressionParser#unary}.
	 * @param ctx the parse tree
	 */
	void enterUnary(MathExpressionParser.UnaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link MathExpressionParser#unary}.
	 * @param ctx the parse tree
	 */
	void exitUnary(MathExpressionParser.UnaryContext ctx);
	/**
	 * Enter a parse tree produced by {@link MathExpressionParser#additiveOps}.
	 * @param ctx the parse tree
	 */
	void enterAdditiveOps(MathExpressionParser.AdditiveOpsContext ctx);
	/**
	 * Exit a parse tree produced by {@link MathExpressionParser#additiveOps}.
	 * @param ctx the parse tree
	 */
	void exitAdditiveOps(MathExpressionParser.AdditiveOpsContext ctx);
	/**
	 * Enter a parse tree produced by {@link MathExpressionParser#multiplicativeOps}.
	 * @param ctx the parse tree
	 */
	void enterMultiplicativeOps(MathExpressionParser.MultiplicativeOpsContext ctx);
	/**
	 * Exit a parse tree produced by {@link MathExpressionParser#multiplicativeOps}.
	 * @param ctx the parse tree
	 */
	void exitMultiplicativeOps(MathExpressionParser.MultiplicativeOpsContext ctx);
}