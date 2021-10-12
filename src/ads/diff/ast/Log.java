package ads.diff.ast;

import java.util.Map;

import ads.diff.common.MultivariateExpression;
import ads.diff.common.HashcodeBase;
import ads.diff.common.UnaryOperator;

public class Log extends UnaryOperator {
	private int base;

	public Log(int base, MultivariateExpression expression) {
		super(HashcodeBase.LOG.base, String.format("Log[%s]", base), expression);
		this.base = base;
	}

	public int base() {
		return base;
	}
	
	@Override
	public Double eval(Map<String, Double> x) {
		return Math.log(expression().eval(x)) / Math.log(base);
	}

	@Override
	public MultivariateExpression derivative(String name) {		// d(log[b](u))/du = 1/ln(b) . u'/u
		return product(
				division(
						constant(1), 
						ln(constant(base))), 
				division(
						expression().derivative(name), 
						expression()));
	}
}
