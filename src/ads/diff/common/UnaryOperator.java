package ads.diff.common;

public abstract class UnaryOperator extends MultivariateExpression {
	private MultivariateExpression expression;
	private String operator;
	
	public UnaryOperator(long base, String operator, MultivariateExpression expression) {
		super(base, false);
		this.expression = expression;
		this.operator = operator;
		connectChild(expression);
	}
	
	public MultivariateExpression expression() {
		return expression;
	}
	
	public String operator() {
		return operator;
	}
	
	@Override
	public String toString() {
		return String.format("%s(%s)", operator, expression.toString());
	}
}
