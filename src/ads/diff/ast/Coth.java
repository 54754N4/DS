package ads.diff.ast;

import java.util.Map;

import ads.common.Transforms;
import ads.diff.common.MultivariateExpression;
import ads.diff.common.HashcodeBase;
import ads.diff.common.UnaryOperator;

public class Coth extends UnaryOperator {

	public Coth(MultivariateExpression expression) {
		super(HashcodeBase.COTH.base, "coth", expression);
	}

	@Override
	public Double eval(Map<String, Double> x) {
		return Transforms.coth(expression().eval(x));
	}

	@Override
	public MultivariateExpression derivative(String name) {	// dcoth(u)/du = -csch(u)^2.u'
		return negate(product(power(csch(expression()), constant(2)), expression().derivative(name)));
	}

}
