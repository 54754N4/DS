package ads.diff.ast;

import java.util.Map;

import ads.common.Transforms;
import ads.diff.common.MultivariateExpression;
import ads.diff.common.HashcodeBase;
import ads.diff.common.UnaryOperator;

public class Csc extends UnaryOperator {

	public Csc(MultivariateExpression expression) {
		super(HashcodeBase.CSC.base, "csc", expression);
	}

	@Override
	public Double eval(Map<String, Double> x) {
		return Transforms.csc(expression().eval(x));
	}

	@Override
	public MultivariateExpression derivative(String name) {	// dcsc(u)/du = -csc(u)cot(u) . u'
		return product(
				negate(product(csc(expression()), cot(expression()))),
				expression().derivative(name));
	}
}
