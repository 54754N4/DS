package ads.polynomial;

import java.util.Arrays;
import java.util.function.BiFunction;

import ads.common.Numbers;
import ads.contracts.Polynomial;

public abstract class AbstractPolynomial implements Polynomial {
	
	@Override
	public int degree() {
		double[] coefs = getCoefs();
		if (coefs.length == 1 && coefs[0] == 0)	// guard in case 0
			return 0;
		int degree = coefs.length - 1;
		while (degree != -1 && coefs[degree] == 0)
			degree--;
		return degree;
	}

	@Override
	public Polynomial times(Polynomial p) {
		int max = degree() + p.degree();
		Polynomial result = create(Numbers.zerosD(max));
		for (int i=0; i<=p.degree(); i++)
			result = result.plusi(times(p.getCoef(i), i));
		return result;
	}

	@Override
	public Polynomial power(int exponent) {
		if (exponent == 0)
			return create(1);
		Polynomial result = this;
		while (exponent-->1)
			result = result.times(this);
		return result;
	}
	
	/**
	 * Polynomial division using Extended Synthetic Division because 
	 * it's faster and more efficient than polynomial long division.
	 * @return first element is remainder while second is quotient
	 * Refs:
	 * https://en.wikipedia.org/wiki/Polynomial_long_division 
	 * https://en.wikipedia.org/wiki/Synthetic_division
	 */
	@Override
	public Polynomial[] divide(Polynomial p) {
		double[] dividend = getCoefs(), 
				divisor = p.getCoefs(),
				out = Arrays.copyOf(dividend, dividend.length);
		double normalizer = divisor[divisor.length - 1], coef;
		for (int i=0; i < dividend.length - divisor.length + 1; i++) {
			out[dividend.length-1-i] /= normalizer;
			coef = out[dividend.length-1-i];
			if (coef != 0)
				for (int j=1; j<divisor.length; j++)
					out[dividend.length - 1 - (i + j)] += -divisor[divisor.length - 1 - j] * coef;
		}
		int separator = divisor.length - 1;
		return new Polynomial[] {
			new ArrayPolynomial(Arrays.copyOfRange(out, 0, separator)),
			new ArrayPolynomial(Arrays.copyOfRange(out, separator, out.length))
		};
	}

	@Override
	public Polynomial compose(Polynomial p) {
		Polynomial result = create(0);
		for (int i=0; i<=degree(); i++)
			result = result.plusi(p.power(i).times(getCoef(i)));	// coef * g(x) ^ i
		return result;
	}

	@Override
	public Polynomial derivative() {
		double[] coefs = getCoefs(),
			derived = new double[coefs.length-1];
		for (int i=1; i<coefs.length; i++)
			derived[i-1] = coefs[i]*i;
		return create(derived);
	}
	
	@Override
	public Polynomial forEachi(BiFunction<Double, Integer, Polynomial> consumer) {
		Polynomial result = this;
		for (int i=0, max=degree(); i<=max; i++)
			result = consumer.apply(getCoef(i), i);
		return result;
	}
	
	@Override
	public Polynomial copy() {
		return create(getCoefs());
	}
	
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
	
	/* Object overridden methods */
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(getCoefs());
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
		ArrayPolynomial other = (ArrayPolynomial) obj;
		if (!Arrays.equals(getCoefs(), other.getCoefs()))
			return false;
		return true;
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
