package ads.diff.common;

public enum HashcodeBase {
	// Leafs
	CONSTANT, VAR,
	
	// Operators
	NEGATION, ADDITION,
	SUBSTRACTION, PRODUCT,
	DIVISION, POWER,
	
	// Functions
	ABS, EXP, LN,
	LOG, SQRT,
	
	// Trigonometric functions
	ACOS, ACOSH, ACOT,
	ACOTH, ACSC, ACSCH,
	ASEC, ASECH, ASIN,
	ASINH, ATAN, ATANH,
	COS, COSH, COT,
	COTH, CSC, CSCH,
	SEC, SECH, SIN,
	SINH, TAN, TANH,
	
	;
	
	public final long base;
	
	HashcodeBase() {
		this.base = ordinal();
	}
	
	@Override
	public String toString() {
		return String.format("%s(%d)", name(), base);
	}
}
