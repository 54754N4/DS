package ads.tensors;

import java.util.Iterator;
import java.util.function.BiFunction;
import java.util.function.Function;

import ads.contracts.Tensor;

public enum MemoryLayout {
	ROW_MAJOR(RowMajorTensor::new, RowMajorTensor::computeStride, RowMajorTensor::computeOffset),
	COL_MAJOR(ColMajorTensor::new, ColMajorTensor::computeStride, ColMajorTensor::computeOffset),
	SPARSE(SparseTensor::new, COL_MAJOR);
	
	private final BiFunction<int[], double[], Tensor> constructor;
	private final BiFunction<int[], int[], Integer> offsetCalculator;
	private final Function<int[], int[]> strideCalculator;
	
	MemoryLayout(
			BiFunction<int[], double[], Tensor> constructor, 
			Function<int[], int[]> strideCalculator, 
			BiFunction<int[], int[], Integer> offsetCalculator) {
		this.constructor = constructor;
		this.strideCalculator = strideCalculator;
		this.offsetCalculator = offsetCalculator;
	}
	
	MemoryLayout(BiFunction<int[], double[], Tensor> constructor, MemoryLayout copy) {
		this(constructor, copy.strideCalculator, copy.offsetCalculator);
	}
	
	public BiFunction<int[], double[], Tensor> tensorConstructor() {
		return constructor;
	}
	
	public Function<int[], int[]> stridesProcessor() {
		return strideCalculator;
	}
	
	public BiFunction<int[], int[], Integer> offsetProcessor() {
		return offsetCalculator;
	}
	
	public Iterator<int[]> coordinatesIterator(final int...shape) {
		return new CoordinatesIterator(this, shape);
	}
}