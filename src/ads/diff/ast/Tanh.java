package ads.diff.ast;

import java.util.Map;

import ads.common.Transforms;
import ads.diff.common.MultivariateExpression;
import ads.diff.common.HashcodeBase;
import ads.diff.common.UnaryOperator;

public class Tanh extends UnaryOperator {

	public Tanh(MultivariateExpression expression) {
		super(HashcodeBase.TANH.base, "Tanh", expression);
	}

	@Override
	public Double eval(Map<String, Double> x) {
		return Transforms.tanh(expression().eval(x));
	}

	@Override
	public MultivariateExpression derivative(String name) {	// dtanh(u)/du = sech(u)^2.u'
		return product(power(sech(expression()), constant(2)), expression().derivative(name));
	}

}
