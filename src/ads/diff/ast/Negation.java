package ads.diff.ast;

import java.util.Map;

import ads.diff.common.MultivariateExpression;
import ads.diff.common.HashcodeBase;
import ads.diff.common.UnaryOperator;

public class Negation extends UnaryOperator {

	public Negation(MultivariateExpression expression) {
		super(HashcodeBase.NEGATION.base, "-", expression);
	}

	@Override
	public Double eval(Map<String, Double> x) {
		return -expression().eval(x);
	}

	@Override
	public MultivariateExpression derivative(String name) {	// d(-u)/du = -u'
		return negate(expression().derivative(name));
	}

}
