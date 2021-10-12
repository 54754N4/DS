package ads.diff.ast;

import java.util.Map;

import ads.common.Transforms;
import ads.diff.common.MultivariateExpression;
import ads.diff.common.HashcodeBase;
import ads.diff.common.UnaryOperator;

public class Acot extends UnaryOperator {

	public Acot(MultivariateExpression expression) {
		super(HashcodeBase.ACOT.base, "acot", expression);
	}

	@Override
	public Double eval(Map<String, Double> x) {
		return Transforms.acot(expression().eval(x));
	}

	@Override
	public MultivariateExpression derivative(String name) {	// dacot(u)/du = -u'/(1+u^2)
		return negate(division(expression().derivative(name), addition(constant(1), power(expression(), constant(2)))));
	}
}
