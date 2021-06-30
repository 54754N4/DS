package ads.diff.ast;

import java.util.Map;

import ads.common.Transforms;
import ads.diff.common.MultivariateExpression;
import ads.diff.common.HashcodeBase;
import ads.diff.common.UnaryOperator;

public class Asech extends UnaryOperator {

	public Asech(MultivariateExpression expression) {
		super(HashcodeBase.ASECH.base, "asech", expression);
	}

	@Override
	public Double eval(Map<String, Double> x) {
		return Transforms.asech(expression().eval(x));
	}

	@Override
	public MultivariateExpression derivative(String name) {		// dasech(u)/du = -u'/(u . sqrt(1 - u^2)
		return negate(
				division(
						expression().derivative(name), 
						product(
								expression(),
								sqrt(
										substraction(
												constant(1), 
												power(expression(), constant(2)))))));
	}

}
