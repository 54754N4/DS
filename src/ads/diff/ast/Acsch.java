package ads.diff.ast;

import java.util.Map;

import ads.common.Transforms;
import ads.diff.common.MultivariateExpression;
import ads.diff.common.HashcodeBase;
import ads.diff.common.UnaryOperator;

public class Acsch extends UnaryOperator {

	public Acsch(MultivariateExpression expression) {
		super(HashcodeBase.ACSCH.base, "acsch", expression);
	}

	@Override
	public Double eval(Map<String, Double> x) {
		return Transforms.acsch(expression().eval(x));
	}

	@Override
	public MultivariateExpression derivative(String name) {		// dacsch(u)/du = -u'/(abs(u)sqrt(1+u^2))
		return negate(
					division(
							expression().derivative(name),
							product(
									abs(expression()), 
									sqrt(
											addition(
													constant(1), 
													power(expression(), constant(2)))))));
	}

}
