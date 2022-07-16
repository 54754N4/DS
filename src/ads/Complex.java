package ads;

import ads.common.StringBuilders;
import ads.errors.ComplexException.InverseZeroComplexException;
import ads.errors.ComplexException.PhaseZeroComplexException;

public class Complex {
	public static Complex ZERO = new Complex(),		// identity element for +
			ONE = new Complex(1),					// identity element for *
			I = new Complex(0, 1);
	
	public static final String PLUS_OPERATOR = " + ", MINUS_OPERATOR = " - ";
	public static final String[] UNIT_VECTORS = { "", "i" };
	public static final int DIMENSIONS = UNIT_VECTORS.length;
	
	private final double x, y;
	
	public Complex(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	// Creates real numbers
	public Complex(double x) {
		this(x,0);
	}
	
	// Creates complex 0
	public Complex() {
		this(0,0);
	}
	
	// Copy constructor
		public Complex(Complex c) {
			this(c.x, c.y);
		}
	
	/* Accessors */
	
	public double real() {
		return x;
	}
	
	public double imaginary() {
		return y;
	}
	
	public boolean isReal() {
		return y == 0;
	}
	
	public boolean isImaginary() {
		return x == 0;
	}
	
	public double norm() {
		return Math.sqrt(x*x+y*y);
	}
	
	/*	Elementary Operations */
		
	public Complex conjugate() {
		return new Complex(x, -y);
	}
	
	public Complex plus(double d) {
		return new Complex(x+d, y);
	}
	
	public Complex plus(Complex c) {
		return new Complex(x+c.x, y+c.y);
	}
	
	public Complex minus(double d) {
		return plus(-d);
	}
	
	public Complex minus(Complex c) {
		return new Complex(x-c.x, y-c.y);
	}
	
	public Complex times(double d) {
		return new Complex(x*d, y*d);
	}

	public Complex times(Complex c) {
		return new Complex(x * c.x - y * c.y, x * c.y + y * c.x);
	}
	
	public Complex inverse() {
		if (equals(new Complex()))
			throw new InverseZeroComplexException();
		return new Complex(x/(x*x+y*y), -y/(x*x+y*y));
	}
	
	public Complex divide(Complex c) {
		return times(c.inverse());
	}
	
	public Complex power(int degree) {
		Complex c = ONE;
		while (degree-->0)
			c = c.times(this);
		return c;
	}
	
	/* Polar form */
	
	public double modulus() {
		return norm();
	}
	
	public double phase() {
		if (x>0) 
			return Math.atan(y/x);
		else if (x<0 && y>=0) 
			return Math.atan(y/x)+Math.PI;
		else if (x<0 && y<0)
			return Math.atan(y/x)-Math.PI;
		else if (x==0 && y>0)
			return Math.PI/2;
		else if (x==0 && y<0)
			return -Math.PI/2;
		else 
			throw new PhaseZeroComplexException();
	}
	
	/*	Complex Transformations */
	
	public Complex translation(double dx) {
		double nX = x + dx;
		double nY = y;
		return new Complex(nX,nY);
	}
	
	/**
	 * Returns the complex image of a rotation of angle theta.
	 * @param theta	- the angle
	 * @return	complex image
	 */
	public Complex rotation(double theta) {
		return rotation(theta,0,0);
	}
	
	/**
	 * Returns the complex image of a rotation characterized by origin (oX,oY) and angle theta.
	 * @param theta	- the angle
	 * @param oX	- abscissa of origin
	 * @param oY	- ordinate of origin
	 * @return	complex image
	 */
	public Complex rotation(double theta, double oX, double oY) {
		double nX = oX + Math.cos(theta)*(x-oX) + Math.sin(theta)*(oY-y);
		double nY = oY + Math.cos(theta)*(y-oY) + Math.sin(theta)*(x-oX);
		return new Complex(nX,nY);
	}
	
	/**
	 * Returns the complex image of a homethetic transformation
	 * characterized by origin (oX,oY) and ratio k.
	 * @param k		- ratio
	 * @param oX	- abscissa of origin
	 * @param oY	- ordinate of origin
	 * @return	complex image
	 */
	public Complex homothety(double k, double oX, double oY) {
		double nX = oX + k*(x-oX);
		double nY = oY + k*(y-oY);
		return new Complex(nX,nY);
	}
	
	public Complex directSimilarity(double k, double theta, double oX, double oY) {
		Complex a = new Complex(k*Math.cos(theta), k*Math.sin(theta)),		// a = k*[cos(t)+i*sin(t)]
				w = new Complex(oX,oY), 									// origin w
				b = a.times(-1).plus(1).times(w);							// b = (1-a)*w
		return a.times(this).plus(b);										// returns a.z + b
	}
	
	/**
	 * Returns z' such as z' = a*z + b with (a,b) belonging to the complex space and a!=0
	 */
	public Complex directSimilarity(Complex a, Complex b) {
		// with ratio k = |a|
		// and angle theta = arg(a)
		// and origin exists iff a!=1 with origin = b/(1-a) 
		return times(a).plus(b);
	}
	
	/**
	 * Returns z^n = a^n * z + Sigma[c=0; n-1](a^c * b)
	 * Using tex commands: z^{(n)}=a^nz+\sum_{c=0}^{n-1}a^cb
	 */
	public Complex composedDirectSimilarity(int n, Complex a, Complex b) {
		Complex result = a.power(n).times(this);
		for (int c=0; c<n-1; c++)
			result = result.plus(a.power(c).times(b));
		return result;
	}
	
	/**
	 * Returns z' = a * z.conjugate() + b
	 */
	public Complex indirectSimilarity(Complex a, Complex b) {
		return conjugate().times(a).plus(b);
	}
	
	/**
	 * Using tex commands:
	 * z^{(n)}=\begin{cases}
		|a|^nz & \text{if n even}\\
		a|a|^{n-1}\bar{z} & \text{if n odd}
		\end{cases}
		+\sum_{c=0}^{n-1}\begin{cases}
		(|a|^2)^{floor(\frac{c}{2})}b & \text{if c even}\\
		a(|a|^2)^{floor(\frac{c}{2})}\bar{b} & \text{if c odd}
		\end{cases}
	 */
	public Complex composedIndirectSimilarity(int n, Complex a, Complex b) {
		Complex result;
		if (n%2 == 0)
			result = times(Math.pow(a.modulus(), n));
		else
			result = conjugate().times(a).times(Math.pow(a.modulus(), n-1));
		double a2 = Math.pow(a.modulus(), 2);
		for (int c=0; c<n-1; c++) {
			double coef = Math.pow(a2, c/2);
			if (c%2 == 0)
				result = result.plus(b.times(coef));
			else
				result = result.plus(b.conjugate().times(a).times(coef));
		}
		return result;
	}
	
	/*	Conversions */
	
	public Complex unitary() {
		double mod = modulus();
		return new Complex(x/mod, y/mod);
	}
	
	public double[][] matrixify() {
		return new double[][]{
			{x,-y},
			{y,x}
		};
	}
	
	public static Complex of(double i) {
		return new Complex(i);
	}
	
	public static Complex from(double r, double i) {
		return new Complex(r, i);
	}
	
	/* Object overriden methods */
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
	
	public boolean equals(Complex c) {
		return x==c.x && y==c.y;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Complex other = (Complex) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		double[] components = {x, y};
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
	
	/* Static methods */
	
	public static Complex exp(Complex c) {	// e^(x+iy) = e^x . cos(y) + e^x . sin(y) . i (using euler's decomposition)
		return new Complex(
				Math.exp(c.x) * Math.cos(c.y), 
				Math.exp(c.x) * Math.sin(c.y));
	}
	
	/* Test */
	
	public static void main(String[] args) {	//debug
		Complex c = new Complex(1,2);
		System.out.println(c);
		System.out.println(c.times(c).times(c));
		System.out.println(c.power(3));
	}
}