package ads.diff.common;

public abstract class BinaryOperator extends MultivariateExpression {
	private MultivariateExpression left, right;
	private String operator;
	
	public BinaryOperator(long base, boolean commutative, MultivariateExpression left, String operator, MultivariateExpression right) {
		super(base, commutative);
		this.left = left;
		this.operator = operator;
		this.right = right;
		connectChild(left);
		connectChild(right);
	}

	public MultivariateExpression left() {
		return left;
	}
	
	public MultivariateExpression right() {
		return right;
	}
	
	public String operator() {
		return operator;
	}
	
	@Override
	public String toString() {
		return String.format("%s%s%s", left.toString(), operator, right.toString());
	}
}
