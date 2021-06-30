package run;

import ads.diff.common.MultivariateExpression;
import ads.diff.ast.parser.ExpressionParser;
import ads.diff.common.ExpressionProvider;

@SuppressWarnings("unused")
public class TestDifferentialFunctions {
	public static void main(String[] args) throws Exception {
//		testFunctions();
		testParser();
		testDifferentiation();
		MultivariateExpression result = ExpressionParser.parse("6*x/12");
		System.out.printf("f(x)     = %s%n", result);
	}

	private static void testFunctions() {
		ExpressionProvider p = ExpressionProvider.create();
		MultivariateExpression c1 = p.constant(1), c2 = p.constant(2), c3 = p.constant(3), c4 = p.constant(4);
		MultivariateExpression t1 = p.addition(c1, c2),
				t2 = p.addition(c2,c1),
				d = p.division(t1, t2);
		System.out.printf("left + -> %d%n", t1.graphCode());
		System.out.printf("right + -> %d%n", t2.graphCode());
		System.out.printf("c1 -> %d%n", c1.graphCode());
		System.out.printf("c2 -> %d%n", c2.graphCode());
		System.out.printf("c3 -> %d%n", c3.graphCode());
		System.out.printf("c4 -> %d%n", c4.graphCode());
		System.out.printf("division -> %d%n", d.graphCode());
		System.out.printf("division = %s%n", d);
	}
	
	private static void testParser() throws Exception {
		MultivariateExpression result = ExpressionParser.parse("(cos(x)+2*x)^2");
		System.out.printf("f(x)     = %s%n", result);
		System.out.printf("f'(x)    = %s%n", result.derivative("x"));
		System.out.printf("f''(x)   = %s%n", result.derivative("x").derivative("x"));
		System.out.printf("f'''(x)  = %s%n", result.derivative("x").derivative("x").derivative("x"));
		System.out.printf("f''''(x) = %s%n", result.derivative("x").derivative("x").derivative("x").derivative("x"));
		System.out.println(result.getInputDimensions());
		System.out.println(result.getVariables());
	}
	
	private static void testDifferentiation() throws Exception {
		MultivariateExpression result = ExpressionParser.parse("(cos(x))+2*x");
		System.out.printf("f(x)     = %s%n", result);
		System.out.printf("f'(x)    = %s%n", result.derivative("x"));
		System.out.printf("f''(x)   = %s%n", result.derivative("x").derivative("x"));
		System.out.printf("f'''(x)  = %s%n", result.derivative("x").derivative("x").derivative("x"));
		System.out.printf("f''''(x) = %s%n", result.derivative("x").derivative("x").derivative("x").derivative("x"));
	}
}