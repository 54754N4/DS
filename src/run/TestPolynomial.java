package run;

import ads.contracts.Polynomial;
import ads.polynomial.ArrayPolynomial;

public class TestPolynomial {
	public static void main(String[] args) {
//		testCreation();
//		testMultiplication();
//		testComposition();
//		testDivision();
		testDerivative();
	}
	
	public static void testCreation() {
		Polynomial p = new ArrayPolynomial(1,2,3),
				r = new ArrayPolynomial(2,1,0);
//		System.out.println(new ArrayPolynomial(-1,-1,-1));
		System.out.println(p);
		System.out.println(r);
		System.out.println(p.plus(r).minus(r).equals(p));
	}
	
	public static void testMultiplication() {
		Polynomial p = new ArrayPolynomial(3,2,1),
				r = new ArrayPolynomial(2,1,0);
//		System.out.println(ArrayPolynomial.ZERO.degree());
//		System.out.println(ArrayPolynomial.ONE.degree());
		System.out.println(p.plus(r));
		System.out.println(r);
		System.out.println(r.power(4));
		System.out.println(r.times(r).times(r).equals(r.power(3)));
	}
	
	public static void testComposition() {
//		Polynomial f = new ArrayPolynomial(-1,-2,3),
//		g = new ArrayPolynomial(2,-5);
//		System.out.println(f);
//		System.out.println(g);
//		System.out.println(f.compose(g));
		
		Polynomial f = new ArrayPolynomial(3,2),
				g = new ArrayPolynomial(5,0,-1);
		System.out.println(f);
		System.out.println(g);
		System.out.println(f.compose(g));
	}
	
	public static void testDivision() {
		Polynomial f = new ArrayPolynomial(-4,0,-2,1),
				g = new ArrayPolynomial(-3, 1);
		System.out.println(f);
		System.out.println(g);
		Polynomial[] division = f.divide(g);
		System.out.println("Remainder " + division[0]);
		System.out.println("Quotient " + division[1]);
	}
	
	public static void testDerivative() {
		Polynomial f = new ArrayPolynomial(true, -4, -1, 3);	// true means reversed
		System.out.println(f);
		System.out.println(f.derivative());
		System.out.println(f.derivative().derivative());
	}
}