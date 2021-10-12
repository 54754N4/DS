package ads.diff.ast;

import java.util.Map;

import ads.diff.common.BinaryOperator;
import ads.diff.common.MultivariateExpression;
import ads.diff.common.HashcodeBase;

public class Addition extends BinaryOperator {
	
	public Addition(MultivariateExpression left, MultivariateExpression right) {
		super(HashcodeBase.ADDITION.base, true, left, "+", right);
	}

	@Override
	public Double eval(Map<String, Double> x) {
		return left().eval(x) + right().eval(x);
	}

	@Override
	public MultivariateExpression derivative(String name) {	// d(u+v)/du = u' + v'
		return addition(left().derivative(name), right().derivative(name));
	}
}
