package ads.diff.ast;

import java.util.Map;

import ads.diff.common.MultivariateExpression;
import ads.diff.common.HashcodeBase;
import ads.diff.common.UnaryOperator;

public class Cos extends UnaryOperator {

	public Cos(MultivariateExpression expression) {
		super(HashcodeBase.COS.base, "cos", expression);
	}

	@Override
	public Double eval(Map<String, Double> x) {
		return Math.cos(expression().eval(x));
	}

	@Override
	public MultivariateExpression derivative(String name) {	// dcos(u)/du = -sin(u) . u'
		return product(negate(sin(expression())), expression().derivative(name));
	}

}
