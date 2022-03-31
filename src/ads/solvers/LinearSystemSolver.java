package ads.solvers;

public interface LinearSystemSolver {
	boolean hasSolutions();
	int getSolutionsCount();
	double[] getSolution();
	double[][] getCompleteMatrix();
	double[][] getIncompleteMatrix();
}
