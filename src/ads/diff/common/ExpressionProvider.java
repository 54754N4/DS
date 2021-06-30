package ads.diff.common;

import java.util.function.BiFunction;

import ads.common.Utils;
import ads.diff.ast.Abs;
import ads.diff.ast.Acos;
import ads.diff.ast.Acosh;
import ads.diff.ast.Acot;
import ads.diff.ast.Acoth;
import ads.diff.ast.Acsc;
import ads.diff.ast.Acsch;
import ads.diff.ast.Addition;
import ads.diff.ast.Asec;
import ads.diff.ast.Asech;
import ads.diff.ast.Asin;
import ads.diff.ast.Asinh;
import ads.diff.ast.Atan;
import ads.diff.ast.Atanh;
import ads.diff.ast.Cos;
import ads.diff.ast.Cosh;
import ads.diff.ast.Cot;
import ads.diff.ast.Coth;
import ads.diff.ast.Csc;
import ads.diff.ast.Csch;
import ads.diff.ast.Division;
import ads.diff.ast.E;
import ads.diff.ast.Exp;
import ads.diff.ast.Ln;
import ads.diff.ast.Log;
import ads.diff.ast.Negation;
import ads.diff.ast.PI;
import ads.diff.ast.Power;
import ads.diff.ast.Product;
import ads.diff.ast.Sec;
import ads.diff.ast.Sech;
import ads.diff.ast.Sin;
import ads.diff.ast.Sinh;
import ads.diff.ast.Sqrt;
import ads.diff.ast.Substraction;
import ads.diff.ast.Tan;
import ads.diff.ast.Tanh;

/* Provider interface that allows us to implement simple mathematical
 * simplifications that can be done on the fly.
 * All methods are default to allow each Expression user/builder to be 
 * able to create expressions without any kind of adapter class or 
 * reference.
 */
public interface ExpressionProvider {
	
	static ExpressionProvider create() {
		return new ExpressionProvider() {};	// creates anonymous object
	}
	
	default MultivariateExpression e() {
		return new E();
	}
	
	default MultivariateExpression pi() {
		return new PI();
	}
		
	default MultivariateExpression constant(double d) {
		return new Constant(d);
	}
	
	default MultivariateExpression variable(String name) {
		return new Variable(name);
	}
	
	default MultivariateExpression addition(MultivariateExpression a, MultivariateExpression b) {
		if (isConst(b, 0))							// a + 0 = a
			return a;
		if (isConst(a, 0))							// 0 + b = b
			return b;
		if (bothConst(a, b))						// a + b = c
			return constant(reduce(a, b, (o1, o2) -> o1 + o2));
		if (a.equals(b))							// a + a = 2a
			return product(constant(2), a);
		if (is(Negation.class, b))					// a + -b = a - b
			return substraction(a, Negation.class.cast(b).expression());
		if (is(Product.class, a) && is(Product.class, b)) {
			Product p1 = Product.class.cast(a), p2 = Product.class.cast(b);
			if (p1.right().equals(p2.right()))		// b * a + c * a = a * (b + c)
				return product(addition(p1.left(), p2.left()), p1.right());
			if (p1.right().equals(p2.left()))		// b * a + a * c = a * (b + c)
				return product(addition(p1.left(), p2.right()), p1.right());
			if (p1.left().equals(p2.left()))		// a * b + a * c = a * (b + c)
				return product(addition(p1.right(), p2.right()), p1.left());
			if (p1.left().equals(p2.right()))		// a * b + c * a = a * (b + c)
				return product(addition(p1.right(), p2.right()), p1.left());
		}
		return new Addition(a, b);
	}

	default MultivariateExpression substraction(MultivariateExpression a, MultivariateExpression b) {
		if (isConst(b, 0))							// a - 0 = a
			return a;
		if (isConst(a, 0))							// 0 - b = -b
			return negate(b);
		if (bothConst(a, b))						// a - b = c
			return constant(reduce(a, b, (o1, o2) -> o1 - o2));
		if (a.equals(b))							// a - a = 0
			return constant(0);	
		if (is(Negation.class, b))					// a - -b = a + b
			return addition(a, Negation.class.cast(b).expression());
		if (is(Product.class, a) && is(Product.class, b)) {
			Product p1 = Product.class.cast(a), p2 = Product.class.cast(b);
			if (p1.right().equals(p2.right()))		// b * a - c * a = a * (b - c)
				return product(substraction(p1.left(), p2.left()), p1.right());
			if (p1.right().equals(p2.left()))		// b * a - a * c = a * (b - c)
				return product(substraction(p1.left(), p2.right()), p1.right());
			if (p1.left().equals(p2.left()))		// a * b - a * c = a * (b - c)
				return product(substraction(p1.right(), p2.right()), p1.left());
			if (p1.left().equals(p2.right()))		// a * b - c * a = a * (b - c)
				return product(substraction(p1.right(), p2.right()), p1.left());
		}
		return new Substraction(a, b);
	}

	default MultivariateExpression product(MultivariateExpression a, MultivariateExpression b) {
		if (isConst(b, -1))									// a * -1 = -a
			return negate(a);
		if (isConst(a, -1))									// -1 * b = -b
			return negate(b);
		if (isConst(b, 1))									// a * 1 = a
			return a;
		if (isConst(a, 1))									// 1 * b = b
			return b;
		if (isConst(b, 0))									// a * 0 = 0
			return constant(0);
		if (isConst(a, 0))									// 0 * b = 0
			return constant(0);
		if (bothConst(a, b))								// a * b = c
			return constant(reduce(a, b, (o1, o2) -> o1 * o2));
		if (a.equals(b))									// a * a = a^2
			return power(a, constant(2));
		if (is(Power.class, a) && is(Power.class, b)) {		// a^b * a^c = a^(b+c) 
			Power p1 = Power.class.cast(a),
					p2 = Power.class.cast(b);
			return power(p1.left(), addition(p1.right(), p2.right()));
		}if (is(Power.class, b)) {	 
			Power p = Power.class.cast(b);
			if (a.equals(p.left()))							// a * a^c = a^(c+1)
				return power(a, addition(p.right(), constant(1)));
		}
		if (is(Power.class, a)) {
			Power p = Power.class.cast(a);
			if (b.equals(p.left()))							// a^c * a = a^(c+1);
				return power(b, addition(p.right(), constant(1)));
		}
		return new Product(a, b);
	}
	
	default MultivariateExpression division(MultivariateExpression a, MultivariateExpression b) {
		if (isConst(b, -1))										// a / -1 = -a
			return negate(a);
		if (isConst(a, 0) && !isConst(b, 0))					// 0 / b = 0
			return constant(0);
		if (isConst(b, 1))										// a / 1 = a
			return a;
		if (a.equals(b))										// a / a = 1
			return constant(1);
		if (bothConst(a, b)) {									// a / b = c (only if it's integer, otherwise kept as fraction)
			double left = Constant.class.cast(a).value(),
				right = Constant.class.cast(b).value(),
				value = left/right;
			int trunc = (int) value;							// modulo would've rounded, i want to trunc
			double after = trunc;
			if (value == after)									// no loss, so it's divisible
				return constant(value);
			if (isInteger(left) && isInteger(right)) {			// 35/50 = 7/6
				long gcd = Utils.Maths.gcd((long) left, (long) right);
				if (gcd != 1)
					return division(constant(left/gcd), constant(right/gcd));
			}
		}
		if (is(Division.class, a) && is(Division.class, b)) {	// (a/b)/(c/d) = (a*d)/(b*c)
			Division d1 = Division.class.cast(a),
					d2 = Division.class.cast(b);
			return division(product(d1.left(), d2.right()), product(d1.right(), d2.left()));
		}
		return new Division(a, b);
	}
	
	default MultivariateExpression power(MultivariateExpression a, MultivariateExpression b) {
		if (isConst(b, 0))												// a^0 = 1
			return constant(1);
		if (isConst(b, 1))												// a^1 = a
			return a;
		if (is(Log.class, b) && isConst(a, Log.class.cast(b).base()))	// a^log[a](c) = c
			return Log.class.cast(b).expression();
		if (is(Power.class, b)) {										// a^b^c = a^(b*c)
			Power p = Power.class.cast(b);
			return power(a, product(p.left(), p.right()));
		}
		return new Power(a, b);
	}
	
	default MultivariateExpression negate(MultivariateExpression e) {
		if (isConst(e))					// Negation(Constant(x)) = Constant(-x)
			return constant(-Constant.class.cast(e).value());
		if (is(Negation.class, e))		// --e = e
			return Negation.class.cast(e).expression();
		return new Negation(e);
	}
	
	default MultivariateExpression abs(MultivariateExpression e) {
		if (isConst(e))					// |+-c| = c
			return constant(Math.abs(Constant.class.cast(e).value()));
		if (is(Abs.class, e))			// ||e|| = |e|
			return e;
		if (is(Negation.class, e))		// |-e| = |e|
			return abs(Negation.class.cast(e).expression());
		return new Abs(e);
	}
	
	default MultivariateExpression sqrt(MultivariateExpression e) {
		if (isConst(e)) {					// sqrt(c^2) = c
			double c = Constant.class.cast(e).value();
			int sq = (int) Math.sqrt(c);
			if (sq * sq == c)
				return constant(sq);
		}
		if (is(Power.class, e)) {
			Power p = Power.class.cast(e);
			if (isConst(p.right(), 2))		// sqrt(u^2) = u
				return p.left();
		}
		return new Sqrt(e);
	}
	
	default MultivariateExpression exp(MultivariateExpression e) {
		if (is(Ln.class, e))				// exp(ln(u)) = u
			return Ln.class.cast(e).expression();
		if (is(Addition.class, e)) {		// exp(a+b) = exp(a) . exp(b)
			Addition a = Addition.class.cast(e);
			return product(exp(a.left()), exp(a.right()));
		} if (is(Substraction.class, e)) {	// exp(a-b) = exp(a) / exp(b)
			Substraction s = Substraction.class.cast(e);
			return division(exp(s.left()), exp(s.right()));
		} if (isConst(e, 0)) 				// exp(0) = 1
			return constant(1);
		return new Exp(e);
	}
	
	default MultivariateExpression ln(MultivariateExpression e) {
		if (is(Product.class, e)) {			// ln(uv) = ln(u) + ln(v)
			Product p = Product.class.cast(e);
			return addition(ln(p.left()), ln(p.right()));
		} if (is(Division.class, e)) {		// ln(u/v) = ln(u) - ln(v)
			Division d = Division.class.cast(e);
			return substraction(ln(d.left()), ln(d.right()));
		} if (is(Power.class, e)) {			// ln(u^v) = v . ln(u)
			Power d = Power.class.cast(e);
			return product(d.right(), ln(d.left()));
		} if (is(Exp.class, e))				// ln(exp(u)) = u
			return e;
		if (is(E.class, e))					// ln(e) = 1
			return constant(1);
		return new Ln(e);
	}
	
	default MultivariateExpression log(int base, MultivariateExpression e) {
		if (is(Product.class, e)) {			// log[b](uv) = log[b](u) + log[b](v)
			Product p = Product.class.cast(e);
			return addition(ln(p.left()), ln(p.right()));
		} else if (is(Division.class, e)) {	// log[b](u/v) = log[b](u) - log[b](v)
			Division d = Division.class.cast(e);
			return substraction(ln(d.left()), ln(d.right()));
		} else if (is(Power.class, e)) {	// log[b](u^v) = v . log[b](u)
			Power d = Power.class.cast(e);
			if (isConst(d.left(), base))	// log[b](b^u) = u
				return d.right();
			return product(d.right(), ln(d.left()));
		} else if (isConst(e, base))		// log[b](b) = 1
			return constant(1);
		return new Log(10, e);
	}
	
	default MultivariateExpression cos(MultivariateExpression e) {
		if (isConst(e, 0) || isConst(e, 2*Math.PI))		// cos(0) = 1 or cos(2.pi) = 1
			return constant(1);
		if (isConst(e, Math.PI))						// cos(pi) = -1
			return negate(constant(1));
		if (is(Negation.class, e))						// cos(-x) = cos(x)
			return cos(Negation.class.cast(e).expression());
		if (is(Acos.class, e))							// cos(acos(x)) = x
			return Acos.class.cast(e).expression();
		if (is(Asin.class, e))							// cos(asin(x)) = sqrt(1-x^2)
			return sqrt(substraction(constant(1), power(Asin.class.cast(e).expression(), constant(2))));
		if (is(Atan.class, e))							// cos(atan(x)) = 1/sqrt(1+x^2)
			return division(
					constant(1), 
					sqrt(addition(constant(1), power(Atan.class.cast(e).expression(), constant(2)))));
		if (is(Acot.class, e)) {						// cos(acot(x)) = x/sqrt(1+x^2)
			MultivariateExpression x = Acot.class.cast(e).expression();
			return division(x, sqrt(addition(constant(1), power(x, constant(2)))));
		} if (is(Asec.class, e))						// cos(asec(x)) = 1/x
			return division(constant(1), Asec.class.cast(e).expression());
		if (is(Acsc.class, e)) {						// cos(acsc(x)) = sqrt(x^2-1)/abs(x)
			MultivariateExpression x = Acsc.class.cast(e).expression();
			return division(sqrt(substraction(power(x, constant(2)), constant(1))), abs(x));
		}
		return new Cos(e);
	}
	
	default MultivariateExpression sin(MultivariateExpression e) {
		if (isConst(e, 0) || isConst(e, Math.PI))		// sin(0) = 0
			return constant(0);
		if (is(Negation.class, e))						// sin(-x) = -sin(x)
			return negate(sin(Negation.class.cast(e).expression()));
		if (is(Asin.class, e))							// sin(asin(x)) = x
			return Asin.class.cast(e).expression();
		if (is(Acos.class, e)) {						// sin(acos(x)) = sqrt(1-x^2)
			MultivariateExpression x = Acos.class.cast(e).expression();
			return sqrt(substraction(constant(1), power(x, constant(2))));
		} if (is(Atan.class, e)) {						// sin(atan(x)) = x/sqrt(1+x^2)
			MultivariateExpression x = Atan.class.cast(e).expression();
			return division(x, sqrt(addition(constant(1), power(x, constant(2)))));
		} if (is(Acot.class, e))						// sin(acot(x)) = 1/sqrt(1+x^2)
			return division(
					constant(1),
					sqrt(addition(constant(1), power(Acot.class.cast(e).expression(), constant(2)))));
		if (is(Asec.class, e)) {						// sin(asec(x)) = sqrt(x^2-1)/|x|
			MultivariateExpression x = Asec.class.cast(e).expression(); 
			return division(sqrt(substraction(power(x, constant(2)), constant(1))),	abs(x));
		} if (is(Acsc.class, e))						// sin(acsc(x)) = 1/x
			return division(constant(1), Acsc.class.cast(e).expression());
		return new Sin(e);
	}
	
	default MultivariateExpression tan(MultivariateExpression e) {
		if (is(Negation.class, e))			// tan(-x) = -tan(x)
			return negate(tan(Negation.class.cast(e).expression()));
		if (is(Asin.class, e)) {			// tan(asin(x)) = x/sqrt(1-x^2)
			MultivariateExpression x = Asin.class.cast(e).expression();
			return division(x, sqrt(substraction(constant(1), power(x, constant(2)))));
		} if (is(Acos.class, e)) {			// tan(acos(x)) = sqrt(1-x^2)/x
			MultivariateExpression x = Acos.class.cast(e).expression();
			return division(sqrt(substraction(constant(1), power(x, constant(2)))), x);
		} if (is(Atan.class, e)) 			// tan(atan(x)) = x
			return Atan.class.cast(e).expression();
		if (is(Acot.class, e))				// tan(acot(x)) = 1/x
			return division(constant(1), Acot.class.cast(e).expression());
		return new Tan(e);
	}
	
	default MultivariateExpression cot(MultivariateExpression e) {
		if (is(Negation.class, e))			// cot(-x) = -cot(x)
			return negate(cot(Negation.class.cast(e).expression()));
		// TODO Auto-generated method stub
		return new Cot(e);
	}
	
	default MultivariateExpression sec(MultivariateExpression e) {
		if (is(Negation.class, e))			// sec(-x) = sec(x)
			return sec(Negation.class.cast(e).expression());
		// TODO Auto-generated method stub
		return new Sec(e);
	}

	default MultivariateExpression csc(MultivariateExpression e) {
		if (is(Negation.class, e))			// csc(-x) = -csc(x)
			return negate(csc(Negation.class.cast(e).expression()));
		// TODO Auto-generated method stub
		return new Csc(e);
	}
	
	// https://www.wikiwand.com/en/Inverse_trigonometric_functions#Relationships_among_the_inverse_trigonometric_functions
	default MultivariateExpression acos(MultivariateExpression e) {
		if (is(Negation.class, e))			// acos(-x) = pi - acos(x)
			return substraction(pi(), acos(Negation.class.cast(e).expression()));
		if (is(Division.class, e)) {		// acos(1/x) = asec(x) 
			Division x = Division.class.cast(e);  	
			if (isConst(x.left(), 1))
				return asec(x.right());
		}
		return new Acos(e);
	}

	default MultivariateExpression asin(MultivariateExpression e) {
		if (is(Negation.class, e))			// asin(-x) = -asin(x)
			return negate(asin(Negation.class.cast(e).expression()));
		if (is(Division.class, e)) {		// asin(1/x) = acsc(x) 
			Division x = Division.class.cast(e);  	
			if (isConst(x.left(), 1))
				return acsc(x.right());
		}
		return new Asin(e);
	}
	
	default MultivariateExpression atan(MultivariateExpression e) {
		if (is(Negation.class, e))			// atan(-x) = -atan(x)
			return negate(atan(Negation.class.cast(e).expression()));
		return new Atan(e);
	}
	
	default MultivariateExpression acot(MultivariateExpression e) {
		if (is(Negation.class, e))			// acot(-x) = pi - acot(x)
			return substraction(pi(), acot(Negation.class.cast(e).expression()));
		return new Acot(e);
	}

	default MultivariateExpression asec(MultivariateExpression e) {
		if (is(Negation.class, e))			// asec(-x) = pi - asec(x)
			return substraction(pi(), asec(Negation.class.cast(e).expression()));
		if (is(Division.class, e)) {		// asec(1/x) = acos(x) 
			Division x = Division.class.cast(e);  	
			if (isConst(x.left(), 1))
				return acos(x.right());
		}
		return new Asec(e);
	}

	default MultivariateExpression acsc(MultivariateExpression e) {
		if (is(Negation.class, e))			// acsc(-x) = -acsc(x)
			return negate(acsc(Negation.class.cast(e).expression()));
		if (is(Division.class, e)) {		// acsc(1/x) = asin(x) 
			Division x = Division.class.cast(e);  	
			if (isConst(x.left(), 1))
				return asin(x.right());
		}
		return new Acsc(e);
	}
	
	default MultivariateExpression cosh(MultivariateExpression e) {
		if (is(Asinh.class, e))				// cosh(asinh(x)) = sqrt(1+x^2)
			return sqrt(addition(constant(1), power(Asinh.class.cast(e).expression(), constant(2))));
		if (is(Atanh.class, e))				// cosh(atanh(x)) = 1/sqrt(1-x^2)
			return division(
					constant(1),
					sqrt(substraction(
							constant(1),
							power(Atanh.class.cast(e).expression(), constant(2)))));
		return new Cosh(e);
	}

	default MultivariateExpression sinh(MultivariateExpression e) {
		if (is(Acosh.class, e)) 			// sinh(acosh(x)) = sqrt(x^2-1)
			return sqrt(substraction(power(Acosh.class.cast(e).expression(), constant(2)), constant(1)));
		if (is(Atanh.class, e)) { 			// sinh(atanh(x)) = x/sqrt(1-x^2)
			MultivariateExpression x = Atanh.class.cast(e).expression();
			return division(x, sqrt(substraction(constant(1), power(x, constant(2)))));
		}
		return new Sinh(e);
	}
	
	default MultivariateExpression tanh(MultivariateExpression e) {
		if (is(Asinh.class, e)) {			// tanh(asinh(x)) = x/sqrt(1+x^2)
			MultivariateExpression x = Asinh.class.cast(e).expression();
			return division(x, sqrt(addition(constant(1), power(x, constant(2)))));
		} if (is(Acosh.class, e)) {			// tanh(acosh(x)) = sqrt(x^2-1)/x
			MultivariateExpression x = Acosh.class.cast(e).expression();
			return division(sqrt(substraction(power(x, constant(2)), constant(1))), x);
		}
		return new Tanh(e);
	}
	
	default MultivariateExpression coth(MultivariateExpression e) {
		// TODO Auto-generated method stub
		return new Coth(e);
	}

	default MultivariateExpression sech(MultivariateExpression e) {
		// TODO Auto-generated method stub
		return new Sech(e);
	}

	default MultivariateExpression csch(MultivariateExpression e) {
		// TODO Auto-generated method stub
		return new Csch(e);
	}

	default MultivariateExpression acosh(MultivariateExpression e) {
		// TODO Auto-generated method stub
		return new Acosh(e);
	}

	default MultivariateExpression asinh(MultivariateExpression e) {
		// TODO Auto-generated method stub
		return new Asinh(e);
	}
	
	default MultivariateExpression atanh(MultivariateExpression e) {
		// TODO Auto-generated method stub
		return new Atanh(e);
	}
	
	default MultivariateExpression acoth(MultivariateExpression e) {
		// TODO Auto-generated method stub
		return new Acoth(e);
	}
	
	default MultivariateExpression asech(MultivariateExpression e) {
		// TODO Auto-generated method stub
		return new Asech(e);
	}
	
	default MultivariateExpression acsch(MultivariateExpression e) {
		// TODO Auto-generated method stub
		return new Acsch(e);
	}
	
	default MultivariateExpression function(String name, MultivariateExpression body) {
		switch (name) {
			case "abs": return abs(body);
			case "sqrt": return sqrt(body);
			case "exp": return exp(body);
			case "ln": return ln(body);
			case "cos": return cos(body);
			case "sin": return sin(body);
			case "tan": return tan(body);
			case "cot": return cot(body);
			case "sec": return sec(body);
			case "csc": return csc(body);
			case "acos": return acos(body);
			case "asin": return asin(body);
			case "atan": return atan(body);
			case "acot": return acot(body);
			case "asec": return asec(body);
			case "acsc": return acsc(body);
			case "cosh": return cosh(body);
			case "sinh": return sinh(body);
			case "tanh": return tanh(body);
			case "coth": return coth(body);
			case "sech": return sech(body);
			case "csch": return csch(body);
			case "acosh": return acosh(body);
			case "asinh": return asinh(body);
			case "atanh": return atanh(body);
			case "acoth": return acoth(body);
			case "asech": return asech(body);
			case "acsch": return acsch(body);
			default: throw new IllegalArgumentException("Unrecognized function named "+name);
		}
	}
	
	/* Helper methods */
	
	private boolean isInteger(double v) {
		return v == Math.floor(v) && !Double.isInfinite(v);
	}
	
	private <K extends MultivariateExpression> boolean is(Class<K> cls, MultivariateExpression e) {
		return cls.isInstance(e);
	}
	
	private boolean isConst(MultivariateExpression e) {
		return is(Constant.class, e);
	}
	
	private boolean isConst(MultivariateExpression e, double value) {
		return isConst(e) && Constant.class.cast(e).value() == value;
	}
	
	private boolean bothConst(MultivariateExpression a, MultivariateExpression b) {
		return isConst(a) && isConst(b);
	}
	
	private double reduce(MultivariateExpression a, MultivariateExpression b, BiFunction<Double, Double, Double> f) {
		return f.apply(Constant.class.cast(a).value(), Constant.class.cast(b).value());
	}
}
