package ads.diff.ast;

import java.util.Map;

import ads.common.Transforms;
import ads.diff.common.MultivariateExpression;
import ads.diff.common.HashcodeBase;
import ads.diff.common.UnaryOperator;

public class Asinh extends UnaryOperator {

	public Asinh(MultivariateExpression expression) {
		super(HashcodeBase.ASINH.base, "asinh", expression);
	}

	@Override
	public Double eval(Map<String, Double> x) {
		return Transforms.asinh(expression().eval(x));
	}

	@Override
	public MultivariateExpression derivative(String name) {		// dasinh(u)/du = u'/sqrt(u^2+1)
		return division(expression().derivative(name), sqrt(addition(power(expression(), constant(2)), constant(1))));
	}
}
