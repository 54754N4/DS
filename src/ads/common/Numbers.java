package ads.common;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import ads.Complex;

public class Numbers {
	
	public static BiFunction<Double, Double, Double> 
		SUM_DOUBLE = (a,b) -> a+b,
		MINUS_DOUBLE = (a,b) -> a-b,
		TIMES_DOUBLE = (a,b) -> a*b,
		DIVIDE_DOUBLE = (a,b) -> a/b;
	
	/* Maximum */
	
	public static int max(int initial, int...values) {
		int max = initial;
		for (int value : values) 
			max = Math.max(max, value);
		return max;
	}
	
	public static Integer maxo(Integer initial, Integer...values) {
		int max = initial;
		for (int value : values) 
			max = Math.max(max, value);
		return max;
	}
	
	public static long max(long initial, long...values) {
		long max = initial;
		for (long value : values) 
			max = Math.max(max, value);
		return max;
	}
	
	public static Long maxo(Long initial, Long...values) {
		long max = initial;
		for (long value : values) 
			max = Math.max(max, value);
		return max;
	}
	
	public static double max(double initial, double...values) {
		double max = initial;
		for (double value : values) 
			max = Math.max(max, value);
		return max;
	}
	
	public static Double maxo(Double initial, Double...values) {
		double max = initial;
		for (double value : values) 
			max = Math.max(max, value);
		return max;
	}
	
	/* Sums */
	
	public static double sum(double...values) {
		double result = 0;
		for (double value : values)
			result += value;
		return result;
	}
	/* Fuck how java can't unbox things itself..
	 * I'd rather duplicate rather than make code slower
	 */
	public static Double sumo(Double...values) {
		double result = 0;
		for (double value : values)
			result += value;
		return result;
	}
	
	public static int sum(int...values) {
		int result = 0;
		for (int value : values)
			result += value;
		return result;
	}
	
	public static Integer sumo(Integer...values) {
		int result = 0;
		for (int value : values)
			result += value;
		return result;
	}
	
	public static long sum(long...values) {
		long result = 0;
		for (long value : values)
			result += value;
		return result;
	}
	
	public static Long sumo(Long...values) {
		long result = 0;
		for (long value : values)
			result += value;
		return result;
	}
	
	/* Multiplications */
	
	public static int multiply(int...values) {
		int result = 1;
		for (int value : values)
			result *= value;
		return result;
	}
	
	public static Integer multiplyo(Integer...values) {
		int result = 1;
		for (int value : values)
			result *= value;
		return result;
	}
	
	public static long multiply(long...values) {
		long result = 0;
		for (long value : values)
			result *= value;
		return result;
	}
	
	public static Long multiplyo(Long...values) {
		long result = 1;
		for (long value : values)
			result *= value;
		return result;
	}
	
	public static double multiply(double...values) {
		double result = 1;
		for (double value : values)
			result *= value;
		return result;
	}
	
	public static Double multiplyo(Double...values) {
		double result = 1;
		for (double value : values)
			result *= value;
		return result;
	}
	
	/* Transformations */
	
	public static double[] transform(Function<Double, Double> transformation, double...values) {
		for (int i=0; i<values.length; i++) 
			values[i] = transformation.apply(values[i]);
		return values;
	}
	
	public static Double[] transform(Function<Double, Double> transformation, Double...values) {
		for (int i=0; i<values.length; i++) 
			values[i] = transformation.apply(values[i]);
		return values;
	}
	
	@SafeVarargs
	public static <T, R> R[] transform(Function<T, R> transformation, Class<R> type, T...values) {
		@SuppressWarnings("unchecked")
		R[] array = (R[]) Array.newInstance(type, values.length);	// use java.lang.reflect.Array::newInstance
		for (int i=0; i<values.length; i++) 
			array[i] = transformation.apply(values[i]);
		return array;
	}
	
	public static int[] bitransform(BiFunction<Integer, Integer, Integer> transformation, int[] v1, int[] v2) {
		int[] result = new int[v1.length];
		for (int i=0; i<v1.length; i++)
			result[i] = transformation.apply(v1[i], v2[i]);
		return result;
	}
	
	/* Arrays handling */
	
	public static int[] sublist(int start, int count, int[] elements) {
		return Arrays.copyOfRange(elements, start, count);
	}
	
	public static long[] sublist(int start, int count, long[] elements) {
		return Arrays.copyOfRange(elements, start, count);
	}
	
	public static double[] sublist(int start, int count, double[] elements) {
		return Arrays.copyOfRange(elements, start, count);
	}
	
	public static <T> T[] sublist(int start, int count, T[] elements) {
		return Arrays.copyOfRange(elements, start, start+count);
	}
	
	public static int[][] split(int after, int...elements) {
		return Arrays.asList(sublist(0, after, elements), sublist(after, elements.length, elements)).toArray(new int[2][]);
	} 
	
	public static long[][] split(int after, long...elements) {
		return Arrays.asList(sublist(0, after, elements), sublist(after, elements.length, elements)).toArray(new long[2][]);
	}
	
	public static double[][] split(int after, double...elements) {
		return Arrays.asList(sublist(0, after, elements), sublist(after, elements.length, elements)).toArray(new double[2][]);
	}
	
	public static <T> List<T[]> split(int after, T[] elements) {
		return Arrays.asList(sublist(0, after, elements), sublist(after, elements.length, elements));
	}
	
	/* Generators */
	
	public static int[] fillI(int count, int value) {
		int[] result = new int[count];
		for (int i=0; i<count; i++)
			result[i] = value;
		return result;
	}
	
	public static int[] zerosI(int count) {
		return fillI(count, 0);
	}
	
	public static int[] onesI(int count) {
		return fillI(count, 1);
	}
	
	public static long[] fillL(int count, long value) {
		long[] result = new long[count];
		for (int i=0; i<count; i++)
			result[i] = value;
		return result;
	}
	
	public static long[] zerosL(int count) {
		return fillL(count, 0);
	}
	
	public static long[] onesL(int count) {
		return fillL(count, 1);
	}
	
	public static double[] fillD(int count, double value) {
		double[] result = new double[count];
		for (int i=0; i<count; i++)
			result[i] = value;
		return result;
	}
	
	public static double[] zerosD(int count) {
		return fillD(count, 0);
	}
	
	public static double[] onesD(int count) {
		return fillD(count, 1);
	}
	
	/* Ranges */
	
	public static void main(String[] args) {
		Complex[] a = {Complex.ONE, Complex.ZERO, Complex.I };
		System.out.println(Arrays.deepToString(transform(Complex::norm, Double.class, a)));
	}
	
}