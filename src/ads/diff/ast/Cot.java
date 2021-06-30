package ads.diff.ast;

import java.util.Map;

import ads.common.Transforms;
import ads.diff.common.MultivariateExpression;
import ads.diff.common.HashcodeBase;
import ads.diff.common.UnaryOperator;

public class Cot extends UnaryOperator {

	public Cot(MultivariateExpression expression) {
		super(HashcodeBase.COT.base, "cot", expression);
	}

	@Override
	public Double eval(Map<String, Double> x) {
		return Transforms.cot(expression().eval(x));
	}

	@Override
	public MultivariateExpression derivative(String name) {	// dcot(u)/du = -csc(u)^2 . u'
		return product(negate(power(csc(expression()), constant(2))), expression().derivative(name));
	}
}
