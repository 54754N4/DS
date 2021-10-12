package ads;

import java.awt.Dimension;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Function;

import ads.common.Numbers;
import ads.contracts.lambdas.Indexed2DVisitor;
import ads.errors.MatrixException.MatricesNotMultipliableException;
import ads.errors.MatrixException.MatricesNotSameSizeException;
import ads.errors.MatrixException.NotInvertibleMatrixException;
import ads.errors.MatrixException.NotSquareMatrixException;

public class ComplexMatrix {
	private int rows;
	private int cols;
	private Complex[][] model;
	
	public ComplexMatrix(int m, int n) {
		this(new Complex[m][n]);
	}
	
	public ComplexMatrix(Complex[][] matrix) {
		setRows(matrix.length);
		setCols(matrix[0].length);
		model = matrix;
	}
	
	public ComplexMatrix(){
		this(new Complex[][]{{Complex.ZERO}});
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
	
	public Complex[][] getMatrix() {
		return model;
	}

	public Complex[] getCol(int n) {
		Complex[] column = new Complex[rows];
		for (int i=0; i<rows; i++) 
			column[i] = model[i][n];
		return column;
	}
	
	public Complex[] getRow(int n) {
		Complex[] row = new Complex[cols];
		for (int i=0; i<cols; i++)
			row[i] = model[n][i];
		return row;
	}
	
	public void setMatrix(Complex[][] matrix) {
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
		return isSquare() && det(this).equals(Complex.ZERO);
	}
	
	public boolean isDegenerate() {
		return isSingular();
	}
	
	public static boolean notSameSize(ComplexMatrix a, ComplexMatrix b) {
		return a.rows != b.rows || a.cols != b.cols;
	}
	
	public static boolean areMultipliable(ComplexMatrix a, ComplexMatrix b) {
		return a.cols == b.rows;
	}
	
	/* Norms */
	
	public double norm1() {
		double sum, max = 0;
		for (int i=0; i<cols; i++) {
			sum = Numbers.sumo(
					Numbers.transform(
							Math::abs, 
							Numbers.transform(Complex::norm, Double.class, getCol(i))));
			max = Numbers.max(max, sum);
		}
		return max;
	}

	public double norm2() {
		return normEuclidean();
	}
	
	public double normEuclidean() {
		return Math.sqrt(
				Numbers.sumo(
						Numbers.transform(
								v -> Math.pow(v, 2),
								Numbers.transform(Complex::norm, Double.class, flatten()))));
	}
	
	public double normInfinity() {
		double sum, max = 0;
		for (int i=0; i<rows; i++) {
			sum = Numbers.sumo(
					Numbers.transform(
							Math::abs,
							Numbers.transform(Complex::norm, Double.class, getRow(i))));
			max = Numbers.max(max, sum);
		}
		return max;
	}
	
	/* Elementary Operations */
	
	public Complex[] flatten() {
		Complex[] flattened = new Complex[rows*cols];
		for (int i=0; i<rows; i++)
			for (int j=0; j<cols; j++)
				flattened[i * cols + j] = model[i][j];
		return flattened;
	}
	
	public ComplexMatrix transpose() {
		Complex[][] result = new Complex[cols][rows];
		for (int row=0; row<rows; row++)
			for (int col=0; col<cols; col++)
				result[col][row] = model[row][col];
		return new ComplexMatrix(result);
	}
	
	public ComplexMatrix plus(ComplexMatrix matrix) {
		if (notSameSize(this,matrix))
			throw new MatricesNotSameSizeException();
		Complex[][] result = new Complex[matrix.rows][matrix.cols];
		for (int i=0; i<matrix.rows; i++) 
			for (int j=0; j<matrix.cols; j++) 
				result[i][j] = model[i][j].plus(matrix.model[i][j]);
		return new ComplexMatrix(result);
	}
	
	public ComplexMatrix times(double lambda) {
		return times(Complex.of(lambda));
	}
	
	public ComplexMatrix times(Complex lambda) {
		Complex[][] result = new Complex[rows][cols];
		for (int i=0; i<rows; i++) 
			for (int j=0; j<cols; j++) 
				result[i][j] = model[i][j].times(lambda);
		return new ComplexMatrix(result);
	}
	
	public ComplexMatrix opposite() {
		return times(-1);						
	}
	
	public ComplexMatrix minus(ComplexMatrix matrix) {
		if (notSameSize(this, matrix))
			throw new MatricesNotSameSizeException();
		return plus(matrix.opposite());
	}
	
	public ComplexMatrix times(ComplexMatrix matrix) {
		if (!areMultipliable(this, matrix))
			throw new MatricesNotMultipliableException();
		Complex[][] result = new Complex[rows][matrix.cols];
		for (int i=0; i<rows; i++) 
			for (int j=0; j<matrix.cols; j++) {
				Complex sum = Complex.ZERO;
				for (int k=0; k<matrix.rows; k++)
					sum = sum.plus(model[i][k].times(matrix.model[k][j]));
				result[i][j] = sum;
			}
		return new ComplexMatrix(result);
	}
	
	public ComplexMatrix timesHadamard(ComplexMatrix matrix) {
		if (notSameSize(this, matrix))
			throw new MatricesNotSameSizeException();
		Complex[][] result = new Complex[rows][cols];
		for (int i=0; i<rows; i++)
			for (int j=0; j<cols; j++)
				result[i][j] = model[i][j].times(matrix.model[i][j]);
		return new ComplexMatrix(result);
	}
	
	public ComplexMatrix timesSchur(ComplexMatrix matrix) {
		return timesHadamard(matrix);
	}
	
	public ComplexMatrix cofactor() {
		if (!isSquare())
			throw new NotSquareMatrixException("Matrix is not square "+getDimensions()+".");
		Complex[][] result = new Complex[rows][cols];
		for (int i=0; i<rows; i++) 
			for (int j=0; j<cols; j++)
				result[i][j] = minor(i, j).det().times(Math.pow(-1, i+j));
		return new ComplexMatrix(result);
	}
	
	public ComplexMatrix adjugate() {
		return cofactor().transpose();
	}
	
	public ComplexMatrix inverse() {
		if (isSingular())
			throw new NotInvertibleMatrixException(isSingular() ? 
					"Determenant is 0 and hence not invertible" : 
					"Matrix is not square and hence not invertible");
		return adjugate().times(det().inverse());	// A^-1 = 1/det*C_t
	}
	
	public ComplexMatrix power(int power) {
		ComplexMatrix matrix = identity(rows, cols);
		while (power-->0) 
			matrix = matrix.times(this);
		return matrix;
	}
	
	public ComplexMatrix minor(int row, int column) {
		ComplexMatrix minor = new ComplexMatrix(rows-1, cols-1);	//because we're deleting 1 row + 1 column
		for (int i=0; i<getRows(); i++)
			for (int j=0; j<getCols(); j++)
				if (!(i==row || j==column))
					minor.model[i>row ? i-1 : i][j>column ? j-1 : j] = model[i][j];
		return minor;	
	}
	
	public Complex trace() {
		if (!isSquare()) 
			throw new NotSquareMatrixException("Matrix is not square "+getDimensions()+".");
		Complex trace = Complex.ZERO;
		for (int i=0; i<rows; i++)
			trace = trace.plus(model[i][i]);
		return trace;		
	}
	
	public Complex det() {
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
	private static Complex det(ComplexMatrix matrix) {
		if (matrix.getRows()==2) {	//if square matrix size 2
			Complex a = matrix.model[0][0],
					b = matrix.model[0][1],
					c = matrix.model[1][0],
					d = matrix.model[1][1];
			return (a.times(d)).minus(b.times(c));
		}
		Complex determinant = Complex.ZERO;
		for (int i=0; i<matrix.getRows(); i++) 
			/* we'll use the first column as pivot, and the following formula : det(A) = Σ(σ*pivot*det(minor))
			 * with σ the sign deduced by (-1)^i,
			 * i representing the current pivot,
			 * pivot being the i'th coefficient from the first column,
			 * minor being the minor matrix after removing the pivot cell.
			 */
			determinant = determinant.plus(matrix.model[i][0].times(Math.pow(-1, i)).times(det(matrix.minor(i, 0))));
		return determinant;
	}
	
	/* Functional for each loop methods */
	
	public ComplexMatrix forEachVisit(Consumer<Complex> action) {
		for (int i=0; i<rows; i++) 
			for (int j=0; j<cols; j++)
				action.accept(model[i][j]);
		return this;
	}
	
	public ComplexMatrix forEachVisitIndexed(Indexed2DVisitor<Complex> indexedAction) {
		for (int i=0; i<rows; i++)
			for (int j=0; j<cols; j++) 
				indexedAction.visit(i, j, model[i][j]);
		return this;
	}
	
	public ComplexMatrix forEachApply(Function<Complex, Complex> action) {
		ComplexMatrix applied = new ComplexMatrix(rows, cols);
		for (int i=0; i<applied.rows; i++)
			for (int j=0; j<applied.cols; j++) 
				applied.model[i][j] = action.apply(model[i][j]);
		return applied;	// return the new result matrix
	}
	
	public ComplexMatrix forEachApplySelf(Function<Complex, Complex> action) {
		for (int i=0; i<rows; i++)
			for (int j=0; j<cols; j++) 
				model[i][j] = action.apply(model[i][j]);
		return this;
	}
	
	/* Static helper/convenience methods */

	public static ComplexMatrix identity(int m, int n) {
		Complex[][] model = new Complex[m][n];
		for (int i=0; i<m; i++)
			for (int j=0; j<n; j++)
				model[i][j] = i == j ? Complex.ONE : Complex.ZERO;
		return new ComplexMatrix(model);
	}
	
	public static ComplexMatrix ones(int m, int n) {
		return fill(m, n, Complex.ONE);
	}
	
	public static ComplexMatrix fill(int m, int n, Complex with) {
		return new ComplexMatrix(m, n).forEachApplySelf(d -> with);
	}
	
	/* Object overriden methods */
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<rows; i++) {
			sb.append("(");
			for (int j=0; j<cols; j++)
				sb.append(model[i][j]+((j!=cols-1)?"\t":""));
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
		ComplexMatrix other = (ComplexMatrix) obj;
		if (cols != other.cols)
			return false;
		if (!Arrays.deepEquals(model, other.model))
			return false;
		if (rows != other.rows)
			return false;
		return true;
	}
	
	public boolean equals(ComplexMatrix matrix) {
		return rows == matrix.rows
				&& cols == matrix.cols
				&& Arrays.deepEquals(model, matrix.model);
	}
	
	/* Test */
	
	public static void main(String[] args) {
		ComplexMatrix m = new ComplexMatrix();
		/* Empty matrix : m = new Matrix(3,3); */
		Complex[][] mModel = new Complex[][] {
			{ Complex.of(1),Complex.of(2),Complex.of(3) },
			{ Complex.of(2),Complex.of(0),Complex.of(1) },
			{ Complex.of(-1),Complex.of(1),Complex.of(2) }
		};	
		m = new ComplexMatrix(mModel);
		// Verification Purposes : det(m) = 0 and if u replace first complex 1+i to 1 det(m) = -6
//		System.out.println(m.det());
//		System.out.println(m.forEachVisit((elem) -> System.err.println(elem)));
//		System.out.println(m.forEachApply((elem) -> -elem));
		System.out.println(m.cofactor());
		System.out.println(m.inverse());
	}
}