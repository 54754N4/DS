package ads.diff.common;

import java.util.Map;

public class Constant extends MultivariateExpression {
	private final double value;
	
	public Constant(double value) {
		super(HashcodeBase.CONSTANT.base, false);
		this.value = value;
	}
	
	public double value() {
		return value;
	}
	
	@Override
	public Double eval(Map<String, Double> x) {
		return value;
	}

	@Override
	public MultivariateExpression derivative(String name) {	// dc/du = 0
		return constant(0);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(value);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Constant other = (Constant) obj;
		if (Double.doubleToLongBits(value) != Double.doubleToLongBits(other.value))
			return false;
		return true;
	}
	
	@Override
	public long graphCode() {
		return base + Double.valueOf(value).hashCode();
	}
	
	@Override
	public String toString() {
		return String.valueOf(value);
	}
}
