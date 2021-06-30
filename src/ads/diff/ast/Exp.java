package ads.diff.ast;

import java.util.Map;

import ads.diff.common.MultivariateExpression;
import ads.diff.common.HashcodeBase;
import ads.diff.common.UnaryOperator;

public class Exp extends UnaryOperator {

	public Exp(MultivariateExpression expression) {
		super(HashcodeBase.EXP.base, "exp", expression);
	}

	@Override
	public Double eval(Map<String, Double> x) {
		return Math.exp(expression().eval(x));
	}

	@Override
	public MultivariateExpression derivative(String name) {	// dexp(u)/du = exp(u).u'
		return product(exp(expression()), expression().derivative(name));
	}
}
