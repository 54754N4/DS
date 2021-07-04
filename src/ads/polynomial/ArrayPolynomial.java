package ads.polynomial;

import java.util.Arrays;
import java.util.function.BiFunction;

import ads.contracts.Polynomial;

public class ArrayPolynomial extends AbstractPolynomial {
	public static final ArrayPolynomial 
		ZERO = new ArrayPolynomial(0), 
		ONE = new ArrayPolynomial(1);
	
	private double[] coefs;
	
	public ArrayPolynomial(double...coefs) {
		this.coefs = Arrays.copyOf(coefs, coefs.length);
	}

	@Override
	public double getCoef(int exponent) {
		return coefs[exponent];
	}

	@Override
	public Polynomial setCoef(int exponent, double coef) {
		coefs[exponent] = coef;
		return this;
	}

	@Override
	public int degree() {
		if (coefs.length == 1 && coefs[0] == 0)	// guard in case 0
			return 0;
		int degree = coefs.length - 1;
		while (degree != -1 && coefs[degree] == 0)
			degree--;
		return degree;
	}
	
	@Override
	public Polynomial timesi(double scalar) {
		for (int i=0; i<=degree(); i++)
			coefs[i] *= scalar;
		return this;
	}

	@Override
	public Polynomial dividei(double scalar) {
		for (int i=0; i<=degree(); i++)
			coefs[i] /= scalar;
		return this;
	}

	@Override
	public Polynomial plusi(double coef, int exponent) {
		ensureSize(exponent);
		coefs[exponent] += coef;
		return this;
	}

	@Override
	public Polynomial minusi(double coef, int exponent) {
		ensureSize(exponent);
		coefs[exponent] -= coef;
		return this;
	}
	
	@Override
	public Polynomial times(double coef, int exponent) {
		int newDegree = degree() + exponent;
		ArrayPolynomial result = new ArrayPolynomial()
				.ensureSize(newDegree);
		for (int i = newDegree; i >= 0; i--)
			result.coefs[i] = i < exponent ? 0 : coefs[i-exponent] * coef;
		return result;
	}

	@Override
	public Polynomial plusi(Polynomial p) {
		int max = Math.max(degree(), p.degree());
		ensureSize(max);
		p.forEach(this::plusi);
		return this;
	}

	@Override
	public Polynomial minusi(Polynomial p) {
		int max = Math.max(degree(), p.degree());
		ensureSize(max);
		p.forEach(this::minusi);
		return this;
	}

	@Override
	public Polynomial times(Polynomial p) {
		int max = degree() + p.degree();
		Polynomial result = ZERO.copy().ensureSize(max);
		for (int i=0; i<=p.degree(); i++)
			result = result.plusi(times(p.getCoef(i), i));
		return result;
	}

	@Override
	public Polynomial divide(Polynomial p) {
		// TODO https://en.wikipedia.org/wiki/Polynomial_long_division
		throw new IllegalStateException("Not implemented yet.");
	}

	@Override
	public Polynomial power(int exponent) {
		if (exponent == 0)
			return ONE;
		Polynomial result = this;
		while (exponent-->1)
			result = result.times(this);
		return result;
	}

	@Override
	public Polynomial compose(Polynomial p) {
		Polynomial result = ZERO.copy();
		for (int i=0; i<=degree(); i++)
			result = result.plusi(p.power(i).times(coefs[i]));	// coef * g(x) ^ i
		return result;
	}

	@Override
	public Polynomial forEachi(BiFunction<Double, Integer, Polynomial> consumer) {
		Polynomial result = this;
		for (int i=0, max=degree(); i<=max; i++)
			result = consumer.apply(coefs[i], i);
		return result;
	}
	
	@Override
	public ArrayPolynomial copy() {
		return create(coefs);
	}
	
	@Override
	public ArrayPolynomial create(double... coefs) {
		return new ArrayPolynomial(coefs);
	}
	
	/* Object overridden methods */
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(coefs);
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
		if (!Arrays.equals(coefs, other.coefs))
			return false;
		return true;
	}
	
	/* Helper methods */
	
	private ArrayPolynomial ensureSize(int degree) {
		if (degree >= coefs.length) {
			double[] bigger = new double[degree+1];
			for (int i=0; i<degree+1; i++)
				bigger[i] = i >= coefs.length ? 0 : coefs[i];
			coefs = bigger;
		}
		return this;
	}	
}
