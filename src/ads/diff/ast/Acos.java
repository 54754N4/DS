package ads.diff.ast;

import java.util.Map;

import ads.diff.common.MultivariateExpression;
import ads.diff.common.HashcodeBase;
import ads.diff.common.UnaryOperator;

public class Acos extends UnaryOperator {

	public Acos(MultivariateExpression expression) {
		super(HashcodeBase.ACOS.base, "acos", expression);
	}

	@Override
	public Double eval(Map<String, Double> x) {
		return Math.acos(expression().eval(x));
	}

	@Override
	public MultivariateExpression derivative(String name) {	// dacos(u)/du = -u'/sqrt(1-x^2)
		return negate(
				division(
						expression().derivative(name),
						sqrt(substraction(constant(1), power(expression(), constant(2))))));
	}
}
