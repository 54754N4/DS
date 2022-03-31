package ads.solvers;

public class LinearSolver implements LinearSystemSolver {
	private double[][] incompleteMatrix;
	private double[] coefficientVector;
	private int rows, cols;
	
	public LinearSolver(double[][] system, double[] coefficients) {
		if (system==null || coefficients==null)
			throw new NullPointerException();
		if (system.length != coefficients.length)
			throw new IllegalArgumentException();
		incompleteMatrix = system;
		coefficientVector = coefficients;
		rows = system.length;
		cols = system[0].length;
	}
	
	/**
	 * Solutions exist IFF the rank of the incomplete
	 * matrix equals the rank of the complete one
	 */
	@Override
	public boolean hasSolutions() {
		return getIncompleteRank() == getCompleteRank();
	}

	/**
	 * Possible solutions:
	 * - 0 if the ranks are !=
	 * - 1 if the ranks are equal to the unkowns count
	 * - infinite if the the ranks are not equal to unkowns
	 */
	@Override
	public int getSolutionsCount() {
		if (!hasSolutions())
			return 0;
		else if (getIncompleteRank() == cols)
			return 1;
		else
			return Integer.MAX_VALUE;
	}

	@Override
	public double[] getSolution() {
		if (!hasSolutions() || getSolutionsCount() == Integer.MAX_VALUE)
			throw new IllegalStateException("Impossible system or has infinite solutions.");
		double [][] A = gaussElimination(getCompleteMatrix());
		double[] x = new double[getCompleteRank()];
		for (int i=0; i<x.length; i++)
			x[i] = 0;
		for (int i=A.length-1; i>= 0; i--) {
			double s = A[i][A[0].length -1];
			for (int j=i+1; j<A[0].length; j++)
				if (j<x.length)
					s -= A[i][j] * x[j];
			if (i < x.length)
				x[i] = s/A[i][i];
		}
		return x;
	}

	@Override
	public double[][] getCompleteMatrix() {
		double[][] complete = new double[rows][cols+1];
		for (int i=0; i<rows; i++)
			for (int j=0; j<cols; j++)
				complete[i][j] = incompleteMatrix[i][j];
		for (int i=0; i<rows; i++)
			complete[i][cols] = coefficientVector[i];
		return complete;
	}

	@Override
	public double[][] getIncompleteMatrix() {
		double[][] incomplete = new double[rows][cols];
		for (int i=0; i<rows; i++)
			for (int j=0; j<cols; j++)
				incomplete[i][j] = incompleteMatrix[i][j];
		return incomplete;
	}

	private int getIncompleteRank() {
		return getRank(getIncompleteMatrix());
	}
	
	private int getCompleteRank() {
		return getRank(getCompleteMatrix());
	}
	
	public static int getRank(double[][] matrix) {
		double[][] reduced = gaussElimination(matrix);
		int rank = 0;
		for (int i=0; i<reduced.length; i++)
			for (int j=0; j<reduced[0].length; j++)
				if (reduced[i][j] != 0) {
					rank++;
					break;
				}
		return rank;
	}
	
	// Algorithm taken from Gordon College
	public static double[][] gaussElimination(double[][] A) {
		for (int k=0; k<A.length; k++) {
			if (A[k][k] == 0 && k != A.length - 1)
				swap(A, k);
			for (int i=k+1; i<A.length; i++) {
				double aik = A[i][k]/A[k][k];
				A[i][k] = 0;
				for (int j = k+1; j<A[0].length; j++)
					A[i][j] = A[i][j] - (aik * A[k][j]);
			}
		}
		finalReduce(A);
		return A;
	}

	private static void finalReduce(double[][] A) {
		for (int i=0; i<A.length; i++)
			for (int j=0; j<A[0].length; j++)
				if (A[i][j] != 0)
					break;
				else if (j == A[0].length-1)
					for (int k=0; k<A[0].length; k++) {
						A[i][k] = A[i+1][k];
						A[i+1][k] = 0;
					}
	}

	private static void swap(double[][] A, int k) {
		for (int i=0; i<A[0].length; i++) {
			double tmp = A[k][i];
			A[k][i] = A[k+1][i];
			A[k+1][i] = tmp;
		}
	}
}
