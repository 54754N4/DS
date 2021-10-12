package ads.diff.ast;

import java.util.Map;

import ads.diff.common.MultivariateExpression;
import ads.diff.common.HashcodeBase;
import ads.diff.common.UnaryOperator;

public class Abs extends UnaryOperator {

	public Abs(MultivariateExpression expression) {
		super(HashcodeBase.ABS.base, "ABS", expression);
	}

	@Override
	public Double eval(Map<String, Double> x) {
		return Math.abs(expression().eval(x));
	}

	@Override
	public MultivariateExpression derivative(String name) {	// d(abs(u))/du == u/|u|
		return division(expression(), abs(expression()));
	}

	@Override
	public String toString() {
		return String.format("|%s|", expression().toString());
	}
}
