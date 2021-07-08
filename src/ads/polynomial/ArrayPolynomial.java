package ads.polynomial;

import java.util.Arrays;

import ads.contracts.Polynomial;

public class ArrayPolynomial extends AbstractPolynomial {
	private double[] coefs;
	
	public ArrayPolynomial(double...coefs) {
		this(false, coefs);
	}
	
	public ArrayPolynomial(boolean reverse, double...coefs) {
		if (reverse) {
			for (int left = 0, right = coefs.length - 1; 
					left < right; 
					left++, right--) {
		        double temp = coefs[left];
		        coefs[left]  = coefs[right];
		        coefs[right] = temp;
		    }
		}
		this.coefs = Arrays.copyOf(coefs, coefs.length);
	}

	@Override
	public ArrayPolynomial create(double... coefs) {
		return new ArrayPolynomial(coefs);
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
	
	/* Accessors */
	
	@Override
	public double getCoef(int exponent) {
		return coefs[exponent];
	}

	@Override
	public double[] getCoefs() {
		return coefs;
	}
	
	@Override
	public Polynomial setCoef(int exponent, double coef) {
		coefs[exponent] = coef;
		return this;
	}
	
	/* Operators */
	
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
}