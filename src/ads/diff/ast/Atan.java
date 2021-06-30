package ads.diff.ast;

import java.util.Map;

import ads.diff.common.MultivariateExpression;
import ads.diff.common.HashcodeBase;
import ads.diff.common.UnaryOperator;

public class Atan extends UnaryOperator {

	public Atan(MultivariateExpression expression) {
		super(HashcodeBase.ATAN.base, "atan", expression);
	}

	@Override
	public Double eval(Map<String, Double> x) {
		return Math.atan(expression().eval(x));
	}

	@Override
	public MultivariateExpression derivative(String name) {	// datan(u)/du = u'/(1+u^2)
		return division(expression().derivative(name), addition(constant(1), power(expression(), constant(2))));
	}
}
