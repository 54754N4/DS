package ads.diff.ast;

import java.util.Map;

import ads.common.Transforms;
import ads.diff.common.MultivariateExpression;
import ads.diff.common.HashcodeBase;
import ads.diff.common.UnaryOperator;

public class Csch extends UnaryOperator {

	public Csch(MultivariateExpression expression) {
		super(HashcodeBase.CSCH.base, "csch", expression);
	}

	@Override
	public Double eval(Map<String, Double> x) {
		return Transforms.csch(expression().eval(x));
	}

	@Override
	public MultivariateExpression derivative(String name) {	// dcsch(u)/du = -coth(u)csch(u)u'
		return negate(product(coth(expression()), product(csch(expression()), expression().derivative(name))));
	}

}
