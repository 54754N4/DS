package ads.diff.ast;

import java.util.Map;

import ads.diff.common.MultivariateExpression;
import ads.diff.common.HashcodeBase;
import ads.diff.common.UnaryOperator;

public class Sqrt extends UnaryOperator {

	public Sqrt(MultivariateExpression expression) {
		super(HashcodeBase.SQRT.base, "sqrt", expression);
	}

	@Override
	public Double eval(Map<String, Double> x) {
		return Math.sqrt(expression().eval(x));
	}

	@Override
	public MultivariateExpression derivative(String name) {	// dsqrt(u)/du = 1/2 . u^(-1/2) . u' = u'/(2 . sqrt(u)^2)
		return division(expression().derivative(name), product(constant(2), power(sqrt(expression()), constant(2))));
	}

}
