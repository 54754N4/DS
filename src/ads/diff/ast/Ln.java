package ads.diff.ast;

import java.util.Map;

import ads.diff.common.MultivariateExpression;
import ads.diff.common.HashcodeBase;
import ads.diff.common.UnaryOperator;

public class Ln extends UnaryOperator {

	public Ln(MultivariateExpression expression) {
		super(HashcodeBase.LN.base, "ln", expression);
	}

	@Override
	public Double eval(Map<String, Double> x) {
		return Math.log(expression().eval(x));
	}

	@Override
	public MultivariateExpression derivative(String name) {	// d(ln(u))/du = u'/u
		return division(expression().derivative(name), expression());
	}
}
