package ads.diff.ast;

import java.util.Map;

import ads.common.Transforms;
import ads.diff.common.MultivariateExpression;
import ads.diff.common.HashcodeBase;
import ads.diff.common.UnaryOperator;

public class Sech extends UnaryOperator {

	public Sech(MultivariateExpression expression) {
		super(HashcodeBase.SECH.base, "sech", expression);
	}

	@Override
	public Double eval(Map<String, Double> x) {
		return Transforms.sech(expression().eval(x));
	}

	@Override
	public MultivariateExpression derivative(String name) {	// dsech(u)/du = -tanh(u)sech(u).u'
		return negate(product(tanh(expression()), product(sech(expression()), expression().derivative(name))));
	}

}
