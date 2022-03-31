package run;

import java.util.Arrays;

import ads.solvers.LinearSolver;

public class TestLinearSystemSolver {
	public static void main(String[] args) {
		double[][] A = {{2,2,-1,1}, {4,3,-1,2}, {8,5,-3,4}, {3,3,-2,2}};
		double[] b = {4,6,12,6};
		LinearSolver l = new LinearSolver(A, b);
		double[] x = l.getSolution();
		System.out.println(Arrays.toString(x));	// solution is 1,1,-1,-1
	}
}
