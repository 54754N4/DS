package ads.diff.ast;

import java.util.Map;

import ads.diff.common.BinaryOperator;
import ads.diff.common.MultivariateExpression;
import ads.diff.common.HashcodeBase;

public class Power extends BinaryOperator {

	public Power(MultivariateExpression left, MultivariateExpression right) {
		super(HashcodeBase.POWER.base, false, left, "^", right);
	}
	
	@Override
	public Double eval(Map<String, Double> x) {
		return Math.pow(left().eval(x), right().eval(x));
	}

	@Override
	public MultivariateExpression derivative(String name) {	// https://www.wikiwand.com/en/Differentiation_rules#/Generalized_power_rule
		return product(
				power(left(), right()),
				addition(
						product(right().derivative(name), ln(left())), 
						division(product(right(), left().derivative(name)), left())));
	}
}
