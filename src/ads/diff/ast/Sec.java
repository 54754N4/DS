package ads.diff.ast;

import java.util.Map;

import ads.common.Transforms;
import ads.diff.common.MultivariateExpression;
import ads.diff.common.HashcodeBase;
import ads.diff.common.UnaryOperator;

public class Sec extends UnaryOperator {

	public Sec(MultivariateExpression expression) {
		super(HashcodeBase.SEC.base, "sec", expression);
	}

	@Override
	public Double eval(Map<String, Double> x) {
		return Transforms.sec(expression().eval(x));
	}

	@Override
	public MultivariateExpression derivative(String name) {	// dsec(u)/du = sec(u)tan(u) . u'
		return product(
				product(sec(expression()), tan(expression())),
				expression().derivative(name));
	}
}
