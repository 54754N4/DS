package ads.polynomial;

import java.util.function.BiFunction;

import ads.contracts.Polynomial;

public abstract class AbstractPolynomial implements Polynomial {

	@Override
	public Polynomial times(double scalar) {
		return copy().timesi(scalar);
	}

	@Override
	public Polynomial divide(double scalar) {
		return copy().dividei(scalar);
	}

	@Override
	public Polynomial plus(double coef, int exponent) {
		return copy().plusi(coef, exponent);
	}

	@Override
	public Polynomial minus(double coef, int exponent) {
		return copy().minusi(coef, exponent);
	}

	@Override
	public Polynomial plus(Polynomial p) {
		return copy().plusi(p);
	}

	@Override
	public Polynomial minus(Polynomial p) {
		return copy().minusi(p);
	}
	
	@Override
	public Polynomial forEach(BiFunction<Double, Integer, Polynomial> consumer) {
		return copy().forEachi(consumer);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int exponent = degree(); exponent >= 0; exponent--)
			sb.append(monomial(getCoef(exponent), exponent));
		if (sb.length() == 0)
			return "0";
		else if (sb.charAt(0) == '+')	// remove leading +
			sb.deleteCharAt(0);
		return sb.toString();
	}
	
	private final String monomial(double coef, int exp) { 
		if (coef == -1) {
			if (exp == 0) 
				return String.valueOf(coef);
			else if (exp == 1)
				return "-x";
			return "-x^" + exp;
		} else if (coef == 0) {
			return "";
		} else if (coef == 1) {
			if (exp == 0) 
				return "+"+String.valueOf(coef);
			else if (exp == 1)
				return "+x";
			return "+x^" + exp;
		} else if (coef < 0) {
			if (exp == 0)
				return String.valueOf(coef);
			else if (exp == 1)
				return coef + "x";
			return coef + "x^" + exp;
		} else {	// coef > 0
			if (exp == 0)
				return "+" + coef;
			else if (exp == 1)
				return "+" + coef + "x";
			return "+" + coef + "x^" + exp;
		}
	}
}
