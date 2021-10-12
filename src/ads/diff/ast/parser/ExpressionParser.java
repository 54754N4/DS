package ads.diff.ast.parser;

import org.antlr.v4.runtime.CharStreams;

import ads.common.Antlr;
import ads.diff.ast.generated.MathExpressionBaseVisitor;
import ads.diff.ast.generated.MathExpressionLexer;
import ads.diff.ast.generated.MathExpressionParser;
import ads.diff.ast.generated.MathExpressionParser.AdditiveOpsContext;
import ads.diff.ast.generated.MathExpressionParser.MultiplicativeOpsContext;
import ads.diff.common.ExpressionProvider;
import ads.diff.common.MultidimensionialExpression;
import ads.diff.common.MultivariateExpression;

public class ExpressionParser extends MathExpressionBaseVisitor<MultivariateExpression> {
	private ExpressionProvider provider;
	
	public ExpressionParser() {
		provider = ExpressionProvider.create();
	}
	
	@Override
	public MultivariateExpression visitInput(MathExpressionParser.InputContext ctx) {
		return visit(ctx.expression());
	}
	
	// expression: unary? term (additiveOps term)* ;
	@Override
	public MultivariateExpression visitExpression(MathExpressionParser.ExpressionContext ctx) {
		int i=0;									// term access count
		MultivariateExpression term = visit(ctx.term(i++)); 	// first is mandatory (hence accessed manually)
		if (ctx.unary() != null && ctx.unary().MINUS() != null)
			term = provider.negate(term);
		for (AdditiveOpsContext c : ctx.additiveOps())
			term = c.MINUS() == null ?
					provider.addition(term, visit(ctx.term(i++))):
					provider.substraction(term, visit(ctx.term(i++)));
		return term;
	}
	
	// term: power (multiplicativeOps power)* ;
	@Override
	public MultivariateExpression visitTerm(MathExpressionParser.TermContext ctx) {
		int i=0;
		MultivariateExpression power = visit(ctx.power(i++));
		for (MultiplicativeOpsContext c : ctx.multiplicativeOps())
			power = c.DIVIDE() == null ?
					provider.product(power, visit(ctx.power(i++))):
					provider.division(power, visit(ctx.power(i++)));
		return power;
	}
	
	// power: atom (POWER atom)* ;
	@Override
	public MultivariateExpression visitPower(MathExpressionParser.PowerContext ctx) {
		int i=0;
		MultivariateExpression atom = visit(ctx.atom(i++));
		for (int c=ctx.POWER().size(); c>0; c--)
			atom = provider.power(atom, visit(ctx.atom(i++)));
		return atom;
	}
	
	@Override
	public MultivariateExpression visitNested(MathExpressionParser.NestedContext ctx) {
		return visit(ctx.expression());
	}
	
	// constants: PI | E | IDENTIFIER ;
	@Override
	public MultivariateExpression visitConstants(MathExpressionParser.ConstantsContext ctx) {
		if (ctx.PI() != null)
			return provider.pi();
		else if (ctx.E() != null)
			return provider.e();
		else
			return provider.variable(ctx.getText());
	}
	
	// funcExpression: FUNC_NAME LPAREN expression RPAREN ;
	@Override
	public MultivariateExpression visitFuncExpression(MathExpressionParser.FuncExpressionContext ctx) {
		String name = ctx.FUNC_NAME().getText();
		MultivariateExpression body = visit(ctx.expression());
		if (name.contains("[") && name.contains("]")) {
			String base = name.split("[")[1].split("]")[0];
			return provider.log(Integer.parseInt(base), body);
		}
		return provider.function(name, body);
	}
	
	// number: FLOATING_POINT | DIGITS ;
	@Override
	public MultivariateExpression visitNumber(MathExpressionParser.NumberContext ctx) {
		if (ctx.FLOATING_POINT() == null) 
			return provider.constant(Integer.parseInt(ctx.getText()));
		return provider.constant(Double.parseDouble(ctx.getText()));
	}
	
	/* Static methods for 1D and n-D expressions */
	
	public static MultivariateExpression parse(String input) throws Exception {
		return Antlr.visit(
				CharStreams.fromString(input),
				MathExpressionLexer.class,
				MathExpressionParser.class,
				ExpressionParser::new,
				"input");
	}
	
	public static MultidimensionialExpression from(String input) throws Exception {
		MultidimensionialExpression m = new MultidimensionialExpression();
		for (String dimension : input.split(","))
			m.add(parse(dimension));
		return m;
	}
}
