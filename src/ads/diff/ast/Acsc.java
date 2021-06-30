package ads.diff.ast;

import java.util.Map;

import ads.common.Transforms;
import ads.diff.common.MultivariateExpression;
import ads.diff.common.HashcodeBase;
import ads.diff.common.UnaryOperator;

public class Acsc extends UnaryOperator {

	public Acsc(MultivariateExpression expression) {
		super(HashcodeBase.ACSC.base, "acsc", expression);
	}

	@Override
	public Double eval(Map<String, Double> x) {
		return Transforms.acsc(expression().eval(x));
	}

	@Override
	public MultivariateExpression derivative(String name) {		// dacsc(u)/du = -u'/(|u|sqrt(u^2-1))
		return negate(division(
				expression().derivative(name),
				product(abs(expression()), sqrt(substraction(power(expression(), constant(2)), constant(1))))));
	}

}
