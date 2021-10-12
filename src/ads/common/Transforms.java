package ads.common;

public interface Transforms {

	static Double log(Double d, int base) {
		return Math.log(d)/Math.log(base);
	}
	
	static Double sec(Double x) {
		return 1 / Math.cos(x);
	}
	
	static Double csc(Double x) {
		return 1 / Math.sin(x);
	}
	
	static Double cot(Double x) {
		return Math.cos(x) / Math.sin(x);
	}
	
	static Double asec(Double x) {
		return Math.acos(1/x);
	}
	
	static Double acsc(Double x) {
		return Math.asin(1/x);
	}
	
	static Double acot(Double x) {
		return Math.atan(1/x);
	}
	
	static Double cosh(Double x) {
		return (1d + Math.exp(-2*x)) / (2 * Math.exp(-x));
	}
	
	static Double sinh(Double x) {
		return (1d - Math.exp(-2*x)) / (2 * Math.exp(-x)); 
	}
	
	static Double tanh(Double x) {
		return (Math.exp(2*x) - 1) / (Math.exp(2*x) + 1);
	}
	
	static Double sech(Double x) {
		return (2 * Math.exp(x)) / (Math.exp(2*x) + 1);
	}
	
	static Double csch(Double x) {
		return (2 * Math.exp(x)) / (Math.exp(2*x) - 1);
	}
	
	static Double coth(Double x) {
		return (Math.exp(2*x) + 1) / (Math.exp(2*x) - 1);
	}

	static Double acosh(Double x) {
		return Math.log(x + Math.sqrt(x*x - 1));
	}
	
	static Double asinh(Double x) {
		return Math.log(x + Math.sqrt(x*x + 1));
	}
	
	static Double atanh(Double x) {
		return 1d/2 * Math.log((1+x)/(1-x));
	}
	
	static Double acoth(Double x) {
		return 1d/2 * Math.log((x+1)/(x-1));
	}
	
	static Double asech(Double x) {
		return Math.log(1/x + Math.sqrt(1/(x*x) - 1));
	}
	
	static Double acsch(Double x) {
		return Math.log(1/x + Math.sqrt(1/(x*x) + 1));
	}
}
