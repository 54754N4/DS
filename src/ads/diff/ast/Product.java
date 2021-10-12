package ads.diff.ast;

import java.util.Map;

import ads.diff.common.BinaryOperator;
import ads.diff.common.MultivariateExpression;
import ads.diff.common.HashcodeBase;

public class Product extends BinaryOperator {

	public Product(MultivariateExpression left, MultivariateExpression right) {
		super(HashcodeBase.PRODUCT.base, true, left, "*", right);
	}

	@Override
	public Double eval(Map<String, Double> x) {
		return left().eval(x) * right().eval(x);
	}

	@Override
	public MultivariateExpression derivative(String name) {	// d(uv)/du = u'v + uv'
		return addition(product(left().derivative(name), right()), product(left(), right().derivative(name)));
	}

}
