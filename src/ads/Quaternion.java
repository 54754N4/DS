package ads;

import ads.common.StringBuilders;
import ads.errors.QuaternionException.CannotInverseQuaternionZeroException;

/**
 * Class representing elements of the form :
 * q = a + b.i + c.j + d.k (for all (a,b,c,d) belonging to R
 * 
 * N.b. : QUATERNIONS ARE NOT COMMUTATIVE !
 */
public class Quaternion {
	public static final Quaternion
		ZERO = new Quaternion(),
		ONE = new Quaternion(1),
		I = new Quaternion(0,1,0,0), 
		J = new Quaternion(0,0,1,0), 
		K = new Quaternion(0,0,0,1);
	
	public static final String PLUS_OPERATOR = " + ", MINUS_OPERATOR = " - ";
	public static final String[] UNIT_VECTORS = { "", "i", "j", "k" };
	public static final int DIMENSIONS = UNIT_VECTORS.length;
	
	// N.B.: Representation of Quaternions in R^4 is not unique, other matrix unit vectors exist
	public static Matrix[] REAL_MATRIX_UNIT_VECTORS = {
			Matrix.identity(DIMENSIONS, DIMENSIONS),
			new Matrix(new double[][] {
				{0,-1,0,0},
				{1,0,0,0},
				{0,0,0,-1},
				{0,0,1,0}
			}),
			new Matrix(new double[][] {
				{0,0,-1,0},
				{0,0,0,1},
				{1,0,0,0},
				{0,-1,0,0}
			}),
			new Matrix(new double[][] {
				{0,0,0,-1},
				{0,0,-1,0},
				{0,1,0,0},
				{1,0,0,0}
			})
	};
	
	/* Attributes */
	
	private final double a, b, c, d;
	
	public Quaternion(double a, double b, double c, double d) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}
	
	// copy constructor
	public Quaternion(Quaternion q) {
		this(q.a, q.b, q.c, q.d);
	}
	
	// real number constructor
	public Quaternion(double a) {
		this(a,0,0,0);
	}
	
	// zero constructor
	public Quaternion() {
		this(0);
	}
	
	/* Accessors */
	
	public double real() {
		return a;
	}
	
	public double[] imaginary() {
		return new double[] {b, c, d};
	}
	
	/* Conditions */
	
	public boolean isScalar() {
		return b == 0 && c == 0 && d == 0;
	}
	
	public boolean isVector() {
		return a == 0;
	}
	
	public boolean isReal() {
		return isScalar();
	}
	
	public boolean isImaginary() {
		return isVector();
	}
	
	public double norm() {
		return Math.sqrt(a*a + b*b + c*c + d*d);
	}
	
	/* Elementary operation */
	
	public Quaternion unitary() {
		double mod = norm();
		return new Quaternion(a/mod, b/mod, c/mod, d/mod);
	}
	
	public Quaternion versor() {
		return unitary();
	}
	
	public Quaternion conjugate() {
		return new Quaternion(a, -b, -c, -d);
	}
	
	public Quaternion plus(double scalar) {
		return new Quaternion(a+scalar, b, c, d);
	}
	
	public Quaternion plus(Quaternion q) {
		return new Quaternion(a+q.a, b+q.b, c+q.c, d+q.d);
	}
	
	public Quaternion minus(double scalar) {
		return plus(-scalar);
	}
	
	public Quaternion minus(Quaternion q) {
		return new Quaternion(a-q.a, b-q.b, c-q.c, d-q.d);
	}
	
	public Quaternion times(double scalar) {
		return new Quaternion(a*scalar, b*scalar, c*scalar, d*scalar);
	}
	
	public Quaternion times(Quaternion q) {	// hamilton product
		return new Quaternion(
			a*q.a - b*q.b - c*q.c - d*q.d,
			a*q.b + b*q.a + c*q.d - d*q.c,
			a*q.c - b*q.d + c*q.a + d*q.b,
			a*q.d + b*q.c - c*q.b + d*q.a
		);
	}
	
	public Quaternion inverse() {
		if (norm() == 0)
			throw new CannotInverseQuaternionZeroException();
		return conjugate().times(1d/(norm()*norm()));	// q* / |q|^2
	}
	
	public Quaternion divide(Quaternion q) {
		return times(q.inverse());
	}
	
	public Quaternion power(int degree) {
		Quaternion q = ONE;
		while (degree-->0)
			q = q.times(this);
		return q;
	}
	
	/* Object overriden methods */
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(a);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(b);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(c);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(d);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
	
	public boolean equals(Quaternion q) {
		return a==q.a && b==q.b && c==q.c && d==q.d;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Quaternion other = (Quaternion) obj;
		if (Double.doubleToLongBits(a) != Double.doubleToLongBits(other.a))
			return false;
		if (Double.doubleToLongBits(b) != Double.doubleToLongBits(other.b))
			return false;
		if (Double.doubleToLongBits(c) != Double.doubleToLongBits(other.c))
			return false;
		if (Double.doubleToLongBits(d) != Double.doubleToLongBits(other.d))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		double[] components = {a, b, c, d};
		for (int i=0; i<DIMENSIONS; i++)
			sb.append(handleRepresenting(components[i], i));
		if (StringBuilders.matches(sb, PLUS_OPERATOR, 0))
			StringBuilders.removeHead(sb, PLUS_OPERATOR);
		return sb.toString().trim();
	}
	
	public String handleRepresenting(double component, int index) {
		String prefix = component >= 0 ? PLUS_OPERATOR : MINUS_OPERATOR,
			postfix = UNIT_VECTORS[index];
		if (component == 0) {
			if (isReal() && index == 0)
				return "0"; 
			return "";
		} else if (component == 1 && index != 0)
			return prefix + postfix;
		return prefix + Math.abs(component) + postfix;
	}
	
	/* Static convenience and conversion methods */
	
	public static Quaternion of(double real) {
		return new Quaternion(real);
	}
	
	public Matrix toRealMatrix() {
		Matrix[] unit = REAL_MATRIX_UNIT_VECTORS; 
		return unit[0].times(a)
				.plus(unit[1].times(b))
				.plus(unit[2].times(c))
				.plus(unit[3].times(d));
	}
	
	/* Test */
	
	public static void main(String[] args) {
		System.out.printf("i1 = 1i = i -> %b%n", I.times(1).equals(I) && ONE.times(I).equals(I));
		System.out.printf("i*i = -1 -> %b%n", I.power(2).equals(of(-1)));
		System.out.printf("j*j = -1 -> %b%n", J.power(2).equals(of(-1)));
		System.out.printf("k*k = -1 -> %b%n", K.power(2).equals(of(-1)));
		System.out.printf("i*j = k -> %b%n", I.times(J).equals(K));
		System.out.printf("j*i = -k -> %b%n", J.times(I).equals(K.times(-1)));
		System.out.printf("i*j*k = -1 -> %b%n", I.times(J).times(K).equals(of(-1)));
	}
}
