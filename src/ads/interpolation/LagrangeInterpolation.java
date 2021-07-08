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
	
	public static void main(String[] args) {
		double[][] data = {
				{3, 1},
				{4, 2},
				{5, 4}
		};	// gives: 0.5x^2-2.5x+4.0
//		double[][] data1 = {
//				{0.4, Math.log(0.4)},
//				{0.5, Math.log(0.5)},
//				{0.7, Math.log(0.7)},
//				{0.8, Math.log(0.8)}
//		};	// approximates ln using lagrange
		LagrangeInterpolation l = new LagrangeInterpolation(data);
//		System.out.println(l.points());
//		System.out.println(l.x(0));
//		System.out.println(l.y(0));
		System.out.println(l.interpolate());
//		System.out.println(l.interpolate().eval(0.60));	// prints -0.509976
	}
}
