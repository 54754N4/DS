package ads.diff.ast;

import java.util.Map;

import ads.common.Transforms;
import ads.diff.common.MultivariateExpression;
import ads.diff.common.HashcodeBase;
import ads.diff.common.UnaryOperator;

public class Cosh extends UnaryOperator {

	public Cosh(MultivariateExpression expression) {
		super(HashcodeBase.COSH.base, "cosh", expression);
	}

	@Override
	public Double eval(Map<String, Double> x) {
		return Transforms.cosh(expression().eval(x));
	}

	@Override
	public MultivariateExpression derivative(String name) {	// dcosh(u)/du = sinh(u).u'
		return product(sinh(expression()), expression().derivative(name));
	}

}
