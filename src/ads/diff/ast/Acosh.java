package ads.diff.ast;

import java.util.Map;

import ads.common.Transforms;
import ads.diff.common.MultivariateExpression;
import ads.diff.common.HashcodeBase;
import ads.diff.common.UnaryOperator;

public class Acosh extends UnaryOperator {

	public Acosh(MultivariateExpression expression) {
		super(HashcodeBase.ACOSH.base, "acosh", expression);
	}

	@Override
	public Double eval(Map<String, Double> x) {
		return Transforms.acosh(expression().eval(x));
	}

	@Override
	public MultivariateExpression derivative(String name) {	// dacosh(u)/du = u'/sqrt(u^2-1)
		return division(expression().derivative(name), sqrt(substraction(power(expression(), constant(2)), constant(1))));
	}

}
