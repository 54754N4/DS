package ads.diff.ast;

import java.util.Map;

import ads.diff.common.MultivariateExpression;
import ads.diff.common.HashcodeBase;
import ads.diff.common.UnaryOperator;

public class Tan extends UnaryOperator {

	public Tan(MultivariateExpression expression) {
		super(HashcodeBase.TAN.base, "tan", expression);
	}

	@Override
	public Double eval(Map<String, Double> x) {
		return Math.tan(expression().eval(x));
	}

	@Override
	public MultivariateExpression derivative(String name) {	// dtan(u)/du = (1 + tan^2(u)) . u'
		return product(
				addition(
					constant(1), 
					product(
							tan(expression()), 
							tan(expression()))),
				expression().derivative(name));
	}

}
