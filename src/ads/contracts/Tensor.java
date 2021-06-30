package ads.contracts;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
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
	Tensor elementWiseBiTransformi(BiFunction<Double, Double, Double> map, Tensor t);
	
	Tensor reshapei(int...indices);
	Tensor arrangei(double start, double end, double increment);

	/* All methods under this can be generated automatically using AbstractTensor */
	
	Tensor elementWiseTransform(Function<Double, Double> map);
	Tensor elementWiseBiTransform(BiFunction<Double, Double, Double> map, Tensor t);
	
	Tensor plus(Tensor t);
	Tensor plusi(Tensor t);
	Tensor timesi(double scalar);
	Tensor times(double scalar);
	Tensor minus(Tensor t);
	Tensor minusi(Tensor t);
	Tensor hadamard(Tensor t);
	Tensor hadamardi(Tensor t);
	Tensor divide(Tensor t);
	Tensor dividei(Tensor t);
	Tensor product(Tensor t);
	
	Tensor broadcast(BiFunction<Double, Double, Double> operation);
	
	Tensor arrange(double end);
	Tensor arrangei(double end);
	Tensor arrange(double start, double end);
	Tensor arrangei(double start, double end);
	Tensor arrange(double start, double end, double increment);
	Tensor reshape(int...indices);
	Tensor flatten();
	Tensor flatteni();
	Tensor fill(double value);
	Tensor fill(Supplier<Double> generator);
	Tensor filli(double value);
	Tensor filli(Supplier<Double> generator);
	Tensor randi();
	Tensor copy();
	
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
}