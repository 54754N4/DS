package ads.diff.ast;

import java.util.Map;

import ads.common.Transforms;
import ads.diff.common.MultivariateExpression;
import ads.diff.common.HashcodeBase;
import ads.diff.common.UnaryOperator;

public class Sinh extends UnaryOperator {

	public Sinh(MultivariateExpression expression) {
		super(HashcodeBase.SINH.base, "sinh", expression);
	}

	@Override
	public Double eval(Map<String, Double> x) {
		return Transforms.sinh(expression().eval(x));
	}

	@Override
	public MultivariateExpression derivative(String name) {	// dsinh(u)/du = cosh(u).u' 
		return product(cosh(expression()), expression().derivative(name));
	}

}
