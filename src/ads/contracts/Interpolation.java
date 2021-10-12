package ads.contracts;

/**
 * Interpolates a function f based on an initial set of 
 * pairs (x(i), y(i)) where y(i) = f(x(i)).
 */
public interface Interpolation<V> {
	void setPoints(double[][] points);
	int points();
	double[] point(int index);
	double x(int index);
	double y(int index);
	V interpolate();
}
