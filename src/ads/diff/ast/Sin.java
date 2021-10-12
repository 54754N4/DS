package ads.diff.ast;

import java.util.Map;

import ads.diff.common.MultivariateExpression;
import ads.diff.common.HashcodeBase;
import ads.diff.common.UnaryOperator;

public class Sin extends UnaryOperator {

	public Sin(MultivariateExpression expression) {
		super(HashcodeBase.SIN.base, "sin", expression);
	}

	@Override
	public Double eval(Map<String, Double> x) {
		return Math.sin(expression().eval(x));
	}

	@Override
	public MultivariateExpression derivative(String name) {		// dsin(u)/du = cos(u) . u'
		return product(cos(expression()), expression().derivative(name));
	}
}
