package ads.diff.ast;

import java.util.Map;

import ads.common.Transforms;
import ads.diff.common.MultivariateExpression;
import ads.diff.common.HashcodeBase;
import ads.diff.common.UnaryOperator;

public class Acoth extends UnaryOperator {

	public Acoth(MultivariateExpression expression) {
		super(HashcodeBase.ACOTH.base, "acoth", expression);
	}

	@Override
	public Double eval(Map<String, Double> x) {
		return Transforms.acoth(expression().eval(x));
	}

	@Override
	public MultivariateExpression derivative(String name) {		// dacoth(u)/du = u'/(1-u^2)
		return division(expression().derivative(name), substraction(constant(1), power(expression(), constant(2))));
	}

}
