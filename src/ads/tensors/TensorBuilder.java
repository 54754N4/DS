package ads.tensors;

import ads.common.Numbers;
import ads.contracts.Tensor;

public class TensorBuilder {
	private MemoryLayout layout;
	private int[] shape;
	private double[] data;
	
	public TensorBuilder() {
		this(null, null);
	}
	
	public TensorBuilder(int[] shape, double[] data) {
		rowMajor();	// by default
		this.shape = shape;
		this.data = data;
	}
	
	public TensorBuilder rowMajor() {
		layout = MemoryLayout.ROW_MAJOR;
		return this;
	}
	
	public TensorBuilder colMajor() {
		layout = MemoryLayout.COL_MAJOR;
		return this;
	}
	
	public TensorBuilder sparse() {
		layout = MemoryLayout.SPARSE;
		return this;
	}
	
	public TensorBuilder shape(int...dimensions) {
		shape = dimensions;
		return this;
	}
	
	public TensorBuilder memory(double...data) {
		this.data = data;
		return this;
	}
	
	public Tensor build() {
		if (shape == null)
			throw new IllegalArgumentException("Shape is null");
		if (data == null)
			throw new IllegalArgumentException("Data is null");
		if (Numbers.multiply(shape) != data.length)
			throw new IllegalArgumentException("Not enough data to match shape");
		return layout.tensorConstructor().apply(shape, data);
	}
}