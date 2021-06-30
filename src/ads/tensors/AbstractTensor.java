package ads.tensors;

import java.util.Arrays;
import java.util.Iterator;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import ads.common.Numbers;
import ads.common.Utils.Bag;
import ads.contracts.Tensor;
import ads.errors.TensorException.ElementsCountInvalidException;

public abstract class AbstractTensor implements Tensor {
	private int count, rank;
	private int[] shape, stride;
	private final MemoryLayout layout;
	
	public AbstractTensor(MemoryLayout layout, int[] shape) {
		this.layout = layout;
		setupDopeVector(shape);
	}
	
	private void setupDopeVector(int[] shape) {			// artificial "dope vector"
		this.shape = shape;
		rank = shape.length;
		count = Numbers.multiply(shape);
		stride = layout.stridesProcessor().apply(shape);
	}
	
	protected int offset(int...indices) {
		return layout.offsetProcessor().apply(stride, indices);
	}
	
	@Override
	public Tensor constructor(int[] shape, double...data) {
		return layout.tensorConstructor().apply(shape, data);
	}
	
	@Override
	public Iterator<int[]> coordinates() {
		return layout.coordinatesIterator(shape);
	}
	
	/* Accessors */
	
	@Override
	public int count() {
		return count;
	}
	
	@Override
	public int rank() {
		return rank;
	}
	
	@Override
	public int[] shape() {
		return shape;
	}
	
	@Override
	public int[] stride() {
		return stride;
	}
	
	@Override
	public MemoryLayout layout() {
		return layout;
	}
	
	/* Conditions */
	
	@Override
	public boolean isFlat() {
		return rank == 1;
	}
	
	public static void main(String[] args) {
		System.out.println(Tensor.broadcastingCompatible(new int[] {1,2,3}, new int[] {3,3}));
		System.out.println(Arrays.toString(Tensor.broadcastedShape(new int[] {1,3}, new int[] {})));
	}
	
	/* Transformations */
	
	@Override
	public Tensor broadcasti(BiFunction<Double, Double, Double> operation) {
		// TODO
		return this;
	}
	
	@Override
	public Tensor broadcast(BiFunction<Double, Double, Double> operation) {
		return copy().broadcasti(operation);
	}
	
	@Override
	public Tensor elementWiseTransform(Function<Double, Double> map) {
		return copy().elementWiseTransformi(map);
	}
	
	@Override
	public Tensor elementWiseBiTransform(BiFunction<Double, Double, Double> map, Tensor t) {
		return copy().elementWiseBiTransformi(map, t);
	}
	
	/* Default implementations */
	
	@Override 
	public Tensor plus(final Tensor t) {
		return copy().plusi(t);
	}
	
	@Override
	public Tensor plusi(final Tensor t) {
		return elementWiseBiTransformi(Numbers.SUM_DOUBLE, t);
	}
	
	@Override
	public Tensor times(final double scalar) {
		return copy().timesi(scalar);
	}

	@Override
	public Tensor timesi(final double scalar) {
		return elementWiseTransformi(a -> scalar * a);
	}
	
	@Override
	public Tensor minus(final Tensor t) {
		return copy().minusi(t);
	}
	
	@Override
	public Tensor minusi(final Tensor t) {
		return elementWiseBiTransformi(Numbers.MINUS_DOUBLE, t);
	}
	
	@Override
	public Tensor hadamard(final Tensor t) {
		return copy().hadamardi(t);
	}
	
	@Override
	public Tensor hadamardi(final Tensor t) {
		return elementWiseBiTransformi(Numbers.TIMES_DOUBLE, t);
	}
	
	@Override
	public Tensor divide(final Tensor t) {
		return copy().dividei(t);
	}
	
	@Override
	public Tensor dividei(final Tensor t) {
		return elementWiseBiTransformi(Numbers.DIVIDE_DOUBLE, t);
	}
	
	// TODO Check correctness of this (and kronecker, outer product, inner product, matrix mul, direct sum etc)
	@Override
	public Tensor product(final Tensor t) {
		int rank = rank(), 
			trank = t.rank(), 
			newRank = rank + trank;
		int[] newShape = new int[newRank],
			shape = shape(), 
			tshape = t.shape();
		for (int i=0; i<newRank; i++)
			newShape[i] = i<rank ? shape[i] : tshape[i-rank];
		Tensor result = constructor(newShape);
		return result.forEachDimension(Bag.of(this, t), 
				(resultCoords, axis, count, lastElementInBlock, bag) -> {
			Tensor u = bag.get(0),
				v = bag.get(1);
			int[] ucoords = Numbers.sublist(0, u.rank(), resultCoords),
					vcoords = Numbers.sublist(u.rank(), resultCoords.length, resultCoords);
			result.set(u.get(ucoords) * v.get(vcoords), resultCoords);
		});
	}
	
//	public Tensor directSum(final Tensor t) {
//		
//	}
//	
//	public Tensor outer(final Tensor t) {
//		int rank = rank(),
//			trank = t.rank(),
//			newRank = rank + trank;
//		int[] newShape = new int[newRank],
//			shape = shape(), 
//			tshape = t.shape();
//		for (int i=0; i<newRank; i++)
//			newShape[i] = i<rank ? shape[i] : tshape[i-rank];
//		Tensor result = constructor(newShape);
//		return result.forEachDimension(Bag.of(this, t), 
//				(resultCoords, axis, count, lastElementInBlock, bag) -> {
//			Tensor u = bag.get(0),
//				v = bag.get(1);
//			int[] ucoords = Numbers.sublist(0, u.rank(), resultCoords),
//					vcoords = Numbers.sublist(u.rank(), resultCoords.length, resultCoords);
//			result.set(u.get(ucoords) * v.get(vcoords), resultCoords);
//		});
//	}
	
	@Override
	public Tensor randi() {
		return elementWiseTransformi((value) -> RANDOM_GENERATOR.nextDouble());
	}
	
	@Override
	public Tensor fill(double value) {
		return copy().filli(value);
	}
	
	@Override
	public Tensor fill(Supplier<Double> generator) {
		return copy().filli(generator);
	}
	
	@Override
	public Tensor filli(double value) {
		return elementWiseTransformi((idc) -> value);
	}

	@Override
	public Tensor filli(Supplier<Double> generator) {
		return elementWiseTransformi((value) -> generator.get());
	}
	
	@Override
	public Tensor reshape(int...indices) {
		return reshapei(indices);
	} 
	
	@Override
	public Tensor copy() {
		return constructor(shape(), memory());
	}
	
	@Override
	public Tensor flatten() {
		return constructor(new int[] {count()}, memory());
	}
	
	@Override
	public Tensor flatteni() {
		return reshapei(count());
	}
	
	@Override
	public Tensor reshapei(int...indices) {
		int newCount = Numbers.multiply(indices); 
		if (newCount != count) {
			String message = String.format("Product of all dimensions %d should be the same as the current total index count %d", newCount, count);
			throw new ElementsCountInvalidException(message);	
		}
		setupDopeVector(indices);
		return this;
	}
	
	@Override
	public Tensor arrange(double end) {
		return arrange(0, end);
	}
	
	@Override
	public Tensor arrangei(double end) {
		return arrangei(0, end);
	}
	
	@Override
	public Tensor arrange(double start, double end) {
		return arrange(start, end, 1);
	}
	
	@Override
	public Tensor arrangei(double start, double end) {
		double temp;
		if (end < start) {
			temp = end;
			end = start;
			start = temp;
		}
		return arrangei(start, end, 1);
	}
	
	@Override
	public Tensor arrange(double start, double end, double increment) {
		int count = (int) (Math.abs(end - start)/increment);  
		return constructor(new int[] {count})
				.arrangei(start, end, increment);
	}
	
	@Override
	public Tensor arrangei(double start, double end, double di) {
		Stream<Double> stream = Stream.iterate(start,  i -> i + di);
		int count = (int) (Math.abs(end - start)/di);
		if (count < this.count)
			throw new ElementsCountInvalidException("Not enough elements to fill tensor. (Expected +"+this.count+" but got "+count);
		return filli(stream.limit(count).iterator()::next);
	}
	
	/* Iterative for each loops */
	
	@Override
	public Tensor forEach(Consumer<Double> action) {
		return forEachIndexed((coords, value) -> action.accept(value));
	}
	
	@Override
	public Tensor forEachIndexed(BiConsumer<int[], Double> action) {
		int[] coords = Numbers.zerosI(rank()),
			shape = shape(),
			stride = stride();
		for (int count = 0, axis = 0, total = count(), rank = rank(), max = rank-1;; count++) {
			action.accept(coords, get(coords));
			if (coords[max-axis]+1 != shape[axis])
				coords[max-axis]++;
			else {
				// Find axis with unfulfilled dimension
				while (axis+1 < rank && (count+1)%stride[axis+1] == 0) 
					axis++;
				if (count+1 == total)
					break;
				// Increment current biggest dimension
				coords[max-axis]++;
				// Reset previous dimension increments
				while (axis != 0 && axis != rank) 
					coords[max-(--axis)] = 0;
			}
		}
		return this;
	}
	
	/* Recursive for loops (per dimension) */
	
	@Override
	public <T> Tensor forEachDimension(IndexedIterationHandler<T> handler) {
		return forEachDimension(null, handler);
	}
	
	@Override
	public <T> Tensor forEachDimension(T element, IndexedIterationHandler<T> handler) {
		return forEachDimension(element, null, handler, null);
	}
	
	@Override
	public <T> Tensor forEachDimension(T element, IndexedIterationConsumer<T> pre, IndexedIterationHandler<T> handler, IndexedIterationConsumer<T> post) {
		return forEachDimension(Numbers.zerosI(rank()), 0, 0, false, element, pre, handler, post);
	}
	
	private <T> Tensor forEachDimension(int[] coords, int axis,	int count, boolean lastElementInBlock, T element, IndexedIterationConsumer<T> pre, IndexedIterationHandler<T> handler, IndexedIterationConsumer<T> post) {
		int[] shape = shape(), stride = stride();
		boolean beforeLastBlock = axis == shape.length - 1;
		if (axis == shape.length) { 
			handler.handle(coords, axis, count, lastElementInBlock, element);
			return this;
		}
		if (pre != null)
			pre.accept(coords, axis, count, lastElementInBlock, beforeLastBlock, element);
		for (int delta=0; delta < shape[axis]; delta++, count += stride[axis]) {
			coords[axis] = delta;
			forEachDimension(coords, axis+1, count, delta+1 == shape[axis], element, pre, handler, post);
		}
		if (post != null)
			post.accept(coords, axis, count, lastElementInBlock, beforeLastBlock, element);
		return this;
	}

	/* Object overriden methods */
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + count;
		result = prime * result + rank;
		result = prime * result + Arrays.hashCode(shape);
		result = prime * result + Arrays.hashCode(stride);
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractTensor other = (AbstractTensor) obj;
		if (count != other.count)
			return false;
		if (rank != other.rank)
			return false;
		if (!Arrays.equals(shape, other.shape))
			return false;
		if (!Arrays.equals(stride, other.stride))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return Tensor.toString(this);
	}
}
