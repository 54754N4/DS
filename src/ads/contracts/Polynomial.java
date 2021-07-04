package ads.contracts;

import java.util.function.BiFunction;

public interface Polynomial {
	double getCoef(int exponent);
	Polynomial setCoef(int exponent, double coef);
	
	int degree();
	
	// Scalar ops
	Polynomial times(double scalar);
	Polynomial divide(double scalar);
	Polynomial timesi(double scalar);
	Polynomial dividei(double scalar);
	
	// Monomial ops
	Polynomial plus(double coef, int exponent);
	Polynomial minus(double coef, int exponent);
	Polynomial times(double coef, int exponent);
	Polynomial plusi(double coef, int exponent);
	Polynomial minusi(double coef, int exponent);
	
	// Generic ops
	Polynomial plus(Polynomial p);
	Polynomial minus(Polynomial p);
	Polynomial times(Polynomial p);
	Polynomial divide(Polynomial p);
	Polynomial power(int exponent);
	
	Polynomial compose(Polynomial p);
	
	// TODO add derivative computation
	
	Polynomial copy();
	
	Polynomial create(double...coefs);
	
	Polynomial forEach(BiFunction<Double, Integer, Polynomial> consumer);
	
	Polynomial plusi(Polynomial p);
	Polynomial minusi(Polynomial p);
	
	Polynomial forEachi(BiFunction<Double, Integer, Polynomial> consumer);
}
