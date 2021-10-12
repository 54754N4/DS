package ads.interpolation;

import ads.contracts.Polynomial;
import ads.polynomial.ArrayPolynomial;

public class LagrangeInterpolation extends BaseInterpolation<Polynomial> {

	public LagrangeInterpolation(double[]...data) {
		super(data);
	}
	
	// L[i] = PI(j: 0->n | i != j) (x - x[j]) / (x[i] - x[j])
	public Polynomial basis(int i) {
		Polynomial basis = create(1);
		double divisor = 1, xi = x(i);
		for (int j=0, n=points(); j<n; j++) {
			if (i != j) {
				basis = basis.times(monome(j));
				divisor *= xi - x(j); 
			}
		}
		return basis.dividei(divisor);
	}

	/**
	 * Lagrange interpolation polynomial is:
	 * 		P(X) = SIGMA(i: 0->n)  f[i] . L[i]
	 * where:
	 * - f[i] are the y[i] images of each x[i] respectively
	 * - L[i] are the Lagrange basis polynomials (check basis method)
	 */
	@Override
	public Polynomial interpolate() {
		Polynomial interpolation = create(0);
		for (int i=0, n=points(); i<n; i++)
			interpolation = interpolation.plusi(basis(i).timesi(y(i)));
		return interpolation;
	}

	private Polynomial create(double...coefs) {
		return new ArrayPolynomial(coefs);
	}
	
	public Polynomial monome(int index) {
		return create(-x(index), 1);	// x - xi
	}
}
