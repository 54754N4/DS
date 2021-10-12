package ads.contracts;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import ads.common.Strings;
import ads.tensors.MemoryLayout;

/** All transformation methods have an in-place equivalent 
 *  to allow us to choose when we want to conserve matrices
 *  and memory, or if we don't care about overwriting data
 *  to save resources.
 */
public interface Tensor {
	static final Random RANDOM_GENERATOR = new Random();
	
	Tensor constructor(int[] shape, double...data);	// force a unified construction
	
	/* Access and store methods */
	
	double get(int...indices);
	Tensor set(double value, int...indices);
	
	/* Accessors */
	
	int count();
	int rank();
	int[] shape();
	int[] stride();
	double[] memory();
	MemoryLayout layout();
	
	boolean isFlat();
	
	/* Transformations */
	
	Tensor broadcasti(BiFunction<Double, Double, Double> operation);

	Tensor elementWiseTransformi(Function<Double, Double> map);
	Tensor elementWiseIndexedTransformi(BiFunction<int[], Double, Double> map);
	Tensor elementWiseBiTransformi(BiFunction<Double, Double, Double> map, Tensor t);
	Tensor elementWiseIndexedBiTransformi(TriFunction<int[], Double, Double, Double> map, Tensor t);
	
	/* All methods under this can be generated automatically using AbstractTensor */
	
	Tensor elementWiseTransform(Function<Double, Double> map);
	Tensor elementWiseIndexedTransform(BiFunction<int[], Double, Double> map);
	Tensor elementWiseBiTransform(BiFunction<Double, Double, Double> map, Tensor t);
	Tensor elementWiseIndexedBiTransform(TriFunction<int[], Double, Double, Double> map, Tensor t);
	
	Tensor plus(final Tensor t);
	Tensor plusi(final Tensor t);
	Tensor timesi(final double scalar);
	Tensor times(final double scalar);
	Tensor minus(final Tensor t);
	Tensor minusi(final Tensor t);
	Tensor hadamard(final Tensor t);
	Tensor hadamardi(final Tensor t);
	Tensor divide(final Tensor t);
	Tensor dividei(final Tensor t);
	Tensor outer(final Tensor t);
	Tensor kronecker(final Tensor t);
	Tensor mmul(final Tensor t);
	Tensor broadcast(final BiFunction<Double, Double, Double> operation);

	Tensor copy();
	
	/* Iterators */
	
	Iterator<int[]> coordinates();
	
	Tensor forEach(Consumer<Double> action);
	Tensor forEachIndexed(BiConsumer<int[], Double> action);

	<T> Tensor forEachDimension(IndexedIterationHandler<T> handler);
	<T> Tensor forEachDimension(T element, IndexedIterationHandler<T> handler);
	<T> Tensor forEachDimension(
			T element, 
			IndexedIterationConsumer<T> pre, 
			IndexedIterationHandler<T> handler, 
			IndexedIterationConsumer<T> post);

	/* Extra Transformations */
	
	Tensor flatten();
	Tensor flatteni();
	Tensor arrange(double end);
	Tensor arrange(double start, double end);
	Tensor arrangei(double end);
	Tensor arrangei(double start, double end);
	Tensor filli(double value);
	Tensor filli(Supplier<Double> generator);
	Tensor fill(double value);
	Tensor fill(Supplier<Double> generator);
	Tensor randi();
	
	Tensor reshapei(int...indices);
	Tensor reshape(int...indices);
	Tensor arrangei(double start, double end, double increment);
	Tensor arrange(double start, double end, double increment);
	
	/* Logical transformations */
	
	Tensor where(final Predicate<Double> condition);
	Tensor wherei(final Predicate<Double> condition);
	Tensor where(final Predicate<Double> condition, double ifTrue, double ifFalse);
	Tensor wherei(final Predicate<Double> condition, double ifTrue, double ifFalse);
	Tensor and(final Tensor t);
	Tensor andi(final Tensor t);
	Tensor or(final Tensor t);
	Tensor ori(final Tensor t);
	Tensor not();
	Tensor noti();
	
	/* Comparisons */
	
	Tensor lt(final Tensor t);
	Tensor lti(final Tensor t);
	Tensor lte(final Tensor t);
	Tensor ltei(final Tensor t);
	Tensor gt(final Tensor t);
	Tensor gti(final Tensor t);
	Tensor gte(final Tensor t);
	Tensor gtei(final Tensor t);
	
	/**
	 * Broadcast compatibility only depends on (starting from the end):
	 * - if dimensions are equals
	 * - or if one of them is 1
	 */
	static boolean broadcastingCompatible(int[] shape1, int[] shape2) {
		int biggest = Math.max(shape1.length, shape2.length),
			max1 = shape1.length - 1,
			max2 = shape2.length - 1;
		for (int i=0, u, v; i<biggest; i++) {
			v = i >= shape1.length ? 1 : shape1[max1-i];	// take elements in reverse order
			u = i >= shape2.length ? 1 : shape2[max2-i];	// replace missing dimensions with 1
			if (v != u &&
					u != 1 &&
					v != 1)
				return false;
		}
		return true;
	}
	
	static int[] broadcastedShape(int[] shape1, int[] shape2) {
		int biggest = Math.max(shape1.length, shape2.length);
		int[] dimensions = new int[biggest];
		for (int i=biggest-1, u, v; i>=0; i--) {
			v = i >= shape1.length ? 1 : shape1[i];
			u = i >= shape2.length ? 1 : shape2[i];
			dimensions[i] = Math.max(u, v);
		}
		return dimensions;
	}
	
	default boolean sameDimensions(Tensor t1, Tensor t2) {
		return t1.count() == t2.count() && Arrays.equals(t1.shape(), t2.shape());
	}
	
	/* Default toString printing methods */
	
	static String toString(Tensor t) {
		StringBuilder sb = new StringBuilder();
		t.forEachDimension(sb, Tensor::toStringPre, t::toStringHandler, Tensor::toStringPost);
		return sb.deleteCharAt(sb.length()-2)	// remove last comma
				.toString()
				.trim();
	}
	
	default void toStringHandler(int[] coords, int axis, int count, boolean lastElementInBlock, StringBuilder in) {
		in.append(get(coords))
			.append(lastElementInBlock ? " " : ", ");
	}
	
	public static void toStringPre(int[] coords, int axis, int count, boolean lastElementInBlock, boolean beforeLastBlock, StringBuilder in) {
		in.append(Strings.tabs(axis)+"{")
			.append(beforeLastBlock ? " " : "\n");
	}
	
	public static void toStringPost(int[] coords, int axis, int count, boolean lastElementInBlock, boolean beforeLastBlock, StringBuilder in) {
		in.append(beforeLastBlock ? "" : Strings.tabs(axis))
			.append(lastElementInBlock ? "}\n" : "},\n");
	}
	
	/* Recursive-for-each iteration lambdas (per dimension) */
	
	@FunctionalInterface
	public static interface IndexedIterationConsumer<T> {
		void accept(int[] coords, int axis, int count, boolean lastElementInBlock, boolean beforeLastBlock, T in);
	}
	
	@FunctionalInterface
	public static interface IndexedIterationHandler<T> {
		void handle(int[] coords, int axis, int count, boolean lastElementInBlock, T in);
	}
	
	@FunctionalInterface
	public static interface TriFunction<A, B, C, R> {
		R apply(A a, B b, C c);
	}
}