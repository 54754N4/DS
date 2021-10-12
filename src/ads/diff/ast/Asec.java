package ads.diff.ast;

import java.util.Map;

import ads.common.Transforms;
import ads.diff.common.MultivariateExpression;
import ads.diff.common.HashcodeBase;
import ads.diff.common.UnaryOperator;

public class Asec extends UnaryOperator {

	public Asec(MultivariateExpression expression) {
		super(HashcodeBase.ASEC.base, "asec", expression);
	}

	@Override
	public Double eval(Map<String, Double> x) {
		return Transforms.asec(expression().eval(x));
	}

	@Override
	public MultivariateExpression derivative(String name) {	// dasec(u)/du = u'/(|u|sqrt(u^2-1))
		return division(
				expression().derivative(name),
				product(
						abs(expression()),
						sqrt(substraction(power(expression(), constant(2)), constant(1)))));
	}
}
