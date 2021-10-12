package ads.diff.ast;

import java.util.Map;

import ads.diff.common.BinaryOperator;
import ads.diff.common.MultivariateExpression;
import ads.diff.common.HashcodeBase;

public class Division extends BinaryOperator {

	public Division(MultivariateExpression left, MultivariateExpression right) {
		super(HashcodeBase.DIVISION.base, false, left, "/", right);
	}

	@Override
	public Double eval(Map<String, Double> x) {
		return left().eval(x) / right().eval(x);
	}

	@Override
	public MultivariateExpression derivative(String name) {	// d(u/v)du = (u'v-uv')/v^2
		return division(
				substraction(
					product(left().derivative(name), right()), 
					product(left(), right().derivative(name))),
				power(right(), constant(2)));
	}
}
