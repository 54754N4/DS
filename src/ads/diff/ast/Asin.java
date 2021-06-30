package ads.diff.ast;

import java.util.Map;

import ads.diff.common.MultivariateExpression;
import ads.diff.common.HashcodeBase;
import ads.diff.common.UnaryOperator;

public class Asin extends UnaryOperator {

	public Asin(MultivariateExpression expression) {
		super(HashcodeBase.ASIN.base, "asin", expression);
	}

	@Override
	public Double eval(Map<String, Double> x) {
		return Math.asin(expression().eval(x));
	}

	@Override
	public MultivariateExpression derivative(String name) {	// dasin(u)/du = u'/sqrt(1-u^2)
		return division(
				expression().derivative(name),
				sqrt(
						substraction(
								constant(1),
								power(expression(), constant(2)))));
	}
}
