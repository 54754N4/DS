package ads;

import java.awt.Dimension;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Function;

import ads.common.Numbers;
import ads.contracts.lambdas.Indexed2DVisitor;
import ads.errors.MatrixException.MatricesNotMultipliableException;
import ads.errors.MatrixException.MatricesNotSameSizeException;
import ads.errors.MatrixException.NotInvertibleMatrixException;
import ads.errors.MatrixException.NotSquareMatrixException;

public class Matrix {
	private int rows;
	private int cols;
	private double[][] model;
	
	public Matrix(int m, int n) {
		this(new double[m][n]);
	}
	
	public Matrix(double[][] matrix) {
		setRows(matrix.length);
		setCols(matrix[0].length);
		model = matrix;
	}
	
	public Matrix(){
		this(new double[][]{{0d}});
	}

	/* Accessors */
	
	public int getRows() {
		return rows;
	}

	public void setRows(int m) {
		this.rows = m;
	}

	public int getCols() {
		return cols;
	}

	public void setCols(int n) {
		this.cols = n;
	}
	
	public double[][] getMatrix() {
		return model;
	}

	public double[] getCol(int n) {
		double[] column = new double[rows];
		for (int i=0; i<rows; i++) 
			column[i] = model[i][n];
		return column;
	}
	
	public double[] getRow(int n) {
		double[] row = new double[cols];
		for (int i=0; i<cols; i++)
			row[i] = model[n][i];
		return row;
	}
	
	public void setMatrix(double[][] matrix) {
		this.model = matrix;
	}
	
	public Dimension getDimensions() {
		return new Dimension(rows, cols);
	}
	
	/* Conditions */
	
	public boolean isSquare() {
		return rows == cols;
	}
	
	public boolean isSingular() {
		return isSquare() && det(this) == 0;
	}
	
	public boolean isDegenerate() {
		return isSingular();
	}
	
	public static boolean notSameSize(Matrix a, Matrix b) {
		return a.rows != b.rows || a.cols != b.cols;
	}
	
	public static boolean areMultipliable(Matrix a, Matrix b) {
		return a.cols == b.rows;
	}
	
	/* Norms */
	
	public double norm1() {
		double sum, max = 0;
		for (int i=0; i<cols; i++) {
			sum = Numbers.sum(Numbers.transform(Math::abs, getCol(i)));
			max = Numbers.max(max, sum);
		}
		return max;
	}

	public double norm2() {
		return normEuclidean();
	}
	
	public double normEuclidean() {
		return Math.sqrt(Numbers.sum(Numbers.transform(v -> Math.pow(v, 2), flatten())));
	}
	
	public double normInfinity() {
		double sum, max = 0;
		for (int i=0; i<rows; i++) {
			sum = Numbers.sum(Numbers.transform(Math::abs, getRow(i)));
			max = Numbers.max(max, sum);
		}
		return max;
	}
	
	/* Elementary Operations */
	
	public double[] flatten() {
		double[] flattened = new double[rows*cols];
		for (int i=0; i<rows; i++)
			for (int j=0; j<cols; j++)
				flattened[i * cols + j] = model[i][j];
		return flattened;
	}
	
	public Matrix transpose() {
		double[][] result = new double[cols][rows];
		for (int row=0; row<rows; row++)
			for (int col=0; col<cols; col++)
				result[col][row] = model[row][col];
		return new Matrix(result);
	}
	
	public Matrix plus(Matrix matrix) {
		if (notSameSize(this,matrix))
			throw new MatricesNotSameSizeException();
		double[][] result = new double[matrix.rows][matrix.cols];
		for (int i=0; i<matrix.rows; i++) 
			for (int j=0; j<matrix.cols; j++) 
				result[i][j] = model[i][j] + matrix.model[i][j];
		return new Matrix(result);
	}
	
	public Matrix times(double lambda) {
		double[][] result = new double[rows][cols];
		for (int i=0; i<rows; i++) 
			for (int j=0; j<cols; j++) 
				result[i][j] = model[i][j]*lambda;
		return new Matrix(result);
	}
	
	public Matrix opposite() {
		return times(-1);						
	}
	
	public Matrix minus(Matrix matrix) {
		if (notSameSize(this, matrix))
			throw new MatricesNotSameSizeException();
		return plus(matrix.opposite());
	}
	
	public Matrix times(Matrix matrix) {
		if (!areMultipliable(this, matrix))
			throw new MatricesNotMultipliableException();
		double[][] result = new double[rows][matrix.cols];
		for (int i=0; i<rows; i++) 
			for (int j=0; j<matrix.cols; j++) {
				double sum = 0;
				for (int k=0; k<matrix.rows; k++)
					sum = sum + (model[i][k] * matrix.model[k][j]);
				result[i][j] = sum;
			}
		return new Matrix(result);
	}
	
	public Matrix timesHadamard(Matrix matrix) {
		if (notSameSize(this, matrix))
			throw new MatricesNotSameSizeException();
		double[][] result = new double[rows][cols];
		for (int i=0; i<rows; i++)
			for (int j=0; j<cols; j++)
				result[i][j] = model[i][j] * matrix.model[i][j];
		return new Matrix(result);
	}
	
	public Matrix timesSchur(Matrix matrix) {
		return timesHadamard(matrix);
	}
	
	public Matrix cofactor() {
		if (!isSquare())
			throw new NotSquareMatrixException("Matrix is not square "+getDimensions()+".");
		double[][] result = new double[rows][cols];
		for (int i=0; i<rows; i++) 
			for (int j=0; j<cols; j++)
				result[i][j] = Math.pow(-1, i+j) * minor(i, j).det();
		return new Matrix(result);
	}
	
	public Matrix adjugate() {
		return cofactor().transpose();
	}
	
	public Matrix inverse() {
		if (isSingular())
			throw new NotInvertibleMatrixException(isSingular() ? 
						"Determenant is 0 and hence not invertible" : 
						"Matrix is not square and hence not invertible");
		return adjugate().times(1/det());	// A^-1 = 1/det*C_t
	}
	
	public Matrix power(int power) {
		Matrix matrix = identity(rows, cols);
		while (power-->0) 
			matrix = matrix.times(this);
		return matrix;
	}
	
	public Matrix minor(int row, int column) {
		Matrix minor = new Matrix(rows-1, cols-1);	//because we're deleting 1 row + 1 column
		for (int i=0; i<getRows(); i++)
			for (int j=0; j<getCols(); j++)
				if (!(i==row || j==column))
					minor.model[i>row ? i-1 : i][j>column ? j-1 : j] = model[i][j];
		return minor;	
	}
	
	public double trace() {
		if (!isSquare()) 
			throw new NotSquareMatrixException("Matrix is not square "+getDimensions()+".");
		double trace = 0;
		for (int i=0; i<rows; i++)
			trace += model[i][i];
		return trace;		
	}
	
	public double det() {
		if (!isSquare())
			throw new NotSquareMatrixException("Matrix is not square "+getDimensions()+".");
		return det(this);
	}
	
	/**
	 * Recursively calculate the matrix's determinant :
	 * - Stop Condition	| square matrix size 2 
	 * - Heredity 		| use formula : det(A) = Σ(σ*pivot*det(minor))
	 * Hypothesis: matrix is a Square Matrix.
	 * @param matrix
	 * @return complex determinant
	 */
	private static double det(Matrix matrix) {
		if (matrix.getRows()==2) {	//if square matrix size 2
			double a = matrix.model[0][0],
					b = matrix.model[0][1],
					c = matrix.model[1][0],
					d = matrix.model[1][1];
			return (a*d)-(b*c);
		}
		double determinant = 0;
		for (int i=0; i<matrix.getRows(); i++) 
			/* we'll use the first column as pivot, and the following formula : det(A) = Σ(σ*pivot*det(minor))
			 * with σ the sign deduced by (-1)^i,
			 * i representing the current pivot,
			 * pivot being the i'th coefficient from the first column,
			 * minor being the minor matrix after removing the pivot cell.
			 */
			determinant += matrix.model[i][0] * Math.pow(-1, i) * det(matrix.minor(i, 0));
		return determinant;
	}
	
	/* Functional for each loop methods */
	
	public Matrix forEachVisit(Consumer<Double> action) {
		for (int i=0; i<rows; i++) 
			for (int j=0; j<cols; j++)
				action.accept(model[i][j]);
		return this;
	}
	
	public Matrix forEachVisitIndexed(Indexed2DVisitor<Double> indexedAction) {
		for (int i=0; i<rows; i++)
			for (int j=0; j<cols; j++) 
				indexedAction.visit(i, j, model[i][j]);
		return this;
	}
	
	public Matrix forEachApply(Function<Double, Double> action) {
		Matrix applied = new Matrix(rows, cols);
		for (int i=0; i<applied.rows; i++)
			for (int j=0; j<applied.cols; j++) 
				applied.model[i][j] = action.apply(model[i][j]);
		return applied;	// return the new result matrix
	}
	
	public Matrix forEachApplySelf(Function<Double, Double> action) {
		for (int i=0; i<rows; i++)
			for (int j=0; j<cols; j++) 
				model[i][j] = action.apply(model[i][j]);
		return this;
	}
	
	/* Static helper/convenience methods */

	public static Matrix identity(int m, int n) {
		double[][] model = new double[m][n];
		for (int i=0; i<m; i++)
			for (int j=0; j<n; j++)
				model[i][j] = i == j ? 1d : 0d;
		return new Matrix(model);
	}
	
	public static Matrix ones(int m, int n) {
		return fill(m, n, 1);
	}
	
	public static Matrix fill(int m, int n, double with) {
		return new Matrix(m, n).forEachApplySelf(d -> with);
	}
	
	/* Object overriden methods */
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		DecimalFormat df = new DecimalFormat("#.##");
		for (int i=0; i<rows; i++) {
			sb.append("(");
			for (int j=0; j<cols; j++)
				sb.append(df.format(model[i][j])+((j!=cols-1)?"\t":""));
			sb.append("\t)\n");
		}
		return sb.toString();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cols;
		result = prime * result + Arrays.deepHashCode(model);
		result = prime * result + rows;
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
		Matrix other = (Matrix) obj;
		if (cols != other.cols)
			return false;
		if (!Arrays.deepEquals(model, other.model))
			return false;
		if (rows != other.rows)
			return false;
		return true;
	}
	
	public boolean equals(Matrix matrix) {
		return rows == matrix.rows
				&& cols == matrix.cols
				&& Arrays.deepEquals(model, matrix.model);
	}
	
	/* Test */
	
	public static void main(String[] args) {
		Matrix m = new Matrix();
		/* Empty matrix : m = new Matrix(3,3); */
		double[][] mModel = new double[][] {
			{ 1,2,3 },
			{ 2,0,1 },
			{ -1,1,2 }
		};	
		m = new Matrix(mModel);
		// Verification Purposes : det(m) = 0 and if u replace first complex 1+i to 1 det(m) = -6
//		System.out.println(m.det());
//		System.out.println(m.forEachVisit((elem) -> System.err.println(elem)));
//		System.out.println(m.forEachApply((elem) -> -elem));
		System.out.println(m.cofactor());
		System.out.println(m.inverse());
	}
}