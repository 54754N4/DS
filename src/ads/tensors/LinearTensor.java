package ads.tensors;

import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Function;

import ads.contracts.Tensor;
import ads.errors.TensorException.DifferentDimensionTensorsException;

public abstract class LinearTensor extends AbstractTensor {
	protected double[] memory;
	
	protected LinearTensor(MemoryLayout layout, int[] shape, double...data) {
		super(layout, shape);
		memory = new double[count()];
		for (int i=0; i<data.length; i++)
			memory[i] = data[i];
	}
	
	/* Access and store operations */
	
	@Override
	public double get(int...indices) {
		if (indices.length != rank())
			 throw new IllegalArgumentException("No subview has been implemented yet.");	// Look at `dope vector` for this
		return memory[offset(indices)];
	}
	
	@Override
	public Tensor set(double value, int...indices) {
		if (indices.length != rank())
			 throw new IllegalArgumentException("No subview manipulation has been implemented yet.");
		memory[offset(indices)] = value;
		return this;
	}
	
	@Override
	public double[] memory() {
		return memory;
	}
	
	/* Transformation methods */
	
	public Tensor elementWiseTransformi(Function<Double, Double> map) {
		for (int i=0; i<count(); i++)
			memory[i] = map.apply(memory[i]);
		return this;
	}
	
	public Tensor elementWiseBiTransformi(BiFunction<Double, Double, Double> map, Tensor t) {
		if (!sameDimensions(this, t))
			throw new DifferentDimensionTensorsException();
		double[] current = memory(), other = t.memory();
		for (int i=0; i<count(); i++)
			memory[i] = map.apply(current[i], other[i]);
		return this;
	}
	
	/* Object overridden methods */
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + super.hashCode();
		result = prime * result + Arrays.hashCode(memory);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj))
			return false;
		LinearTensor other = (LinearTensor) obj;
		return Arrays.equals(memory, other.memory);
	}
}
