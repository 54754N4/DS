package run;

import ads.interpolation.LagrangeInterpolation;

public class TestInterpolation {
	public static void main(String[] args) {
//		interpolation();
//		lnApproximation();
//		wikipediaExample1();
		wikipediaExample2();
	}
	
	public static void interpolation() {
		double[][] data = {
				{3, 1},
				{4, 2},
				{5, 4}
		};
		LagrangeInterpolation l = new LagrangeInterpolation(data);
		System.out.println(l.interpolate()); 	// prints: 0.5x^2-2.5x+4.0
	}
	
	public static void lnApproximation() {	// approximates ln using lagrange
		double[][] data1 = {
				{0.4, Math.log(0.4)},
				{0.5, Math.log(0.5)},
				{0.7, Math.log(0.7)},
				{0.8, Math.log(0.8)}
		};
		LagrangeInterpolation l = new LagrangeInterpolation(data1);
		System.out.println(l.interpolate().eval(0.60));	// prints -0.509976
	}
	
	// https://en.wikipedia.org/wiki/Lagrange_polynomial
	public static void wikipediaExample1() {	// approximates x^2 using lagrange
		System.out.println(new LagrangeInterpolation(new double[][] {
			{1, 1},
			{2, 4},
			{3, 9}
		}).interpolate());	// prints x^2
	}
	
	public static void wikipediaExample2() {	// approximates x^3 using lagrange
		System.out.println(new LagrangeInterpolation(new double[][] {
			{1, 1},
			{2, 8},
			{3, 27},
			{4, 64}
		}).interpolate());	// prints x^3
	}
}
