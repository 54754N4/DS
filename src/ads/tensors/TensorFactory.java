package ads.tensors;

import java.util.Iterator;
import java.util.function.Supplier;
import java.util.stream.Stream;

import ads.common.Transforms;
import ads.contracts.Tensor;

public class TensorFactory {
	private static final TensorFactory FACTORY = new TensorFactory();
	private static MemoryLayout layout = MemoryLayout.ROW_MAJOR;
	
	public static TensorFactory getInstance() {
		return FACTORY;
	}
	
	/* Methods that set tensor type to create */
	
	public TensorFactory rowMajor() {
		layout = MemoryLayout.ROW_MAJOR;
		return this;
	}
	
	public TensorFactory colMajor() {
		layout = MemoryLayout.COL_MAJOR;
		return this;
	}
	
	public TensorFactory sparse() {
		layout = MemoryLayout.SPARSE;
		return this;
	}
	
	/* Factory methods */
	
	public Tensor create(int[] shape, double...data) {
		return layout.tensorConstructor().apply(shape, data);
	}
	
	public Tensor identity(int...shape) {
		return create(shape)
			.elementWiseIndexedTransform((coords, val) -> 
				Stream.of(coords).distinct().count() == 1 ? 1d : 0);	// if all coords are equal ? 1 : 0
	}
	
	public Tensor arrange(double end, int...shape) {
		return create(shape).arrangei(end);
	}
	
	public Tensor arrange(double start, double end, int...shape) {
		return create(shape).arrangei(start, end);
	}
	
	public Tensor arrange(double start, double end, double increment, int...shape) {
		return create(shape).arrangei(start, end, increment);
	}
	
	public Tensor zeros(int...shape) {
		return fill(0, shape);
	}
	
	public Tensor ones(int...shape) {
		return fill(1, shape);
	}
	
	public Tensor fill(double value, int...shape) {
		return create(shape).filli(value);
	}
	
	public Tensor fill(Supplier<Double> generator, int...shape) {
		return create(shape).filli(generator);
	}
	
	public Tensor rand(int...shape) {
		return create(shape).randi();
	}
	
	/* Scale dependent factory methods */

	public Tensor linspace(double start, double stop, int samples) {
		return linspace(start, stop, samples, true, samples);
	}
	
	public Tensor linspace(double start, double stop, int samples, boolean endpoint) {
		return linspace(start, stop, samples, endpoint, samples);
	}
	
	public Tensor linspace(double start, double stop, int samples, boolean endpoint, int...shape) {
		int count = endpoint ? samples-1 : samples;
		final double distance = stop-start,
			dx = distance/count;
		Iterator<Double> iterator = Stream.iterate(start, x -> x + dx)
				.limit(samples)
				.iterator();
		return create(shape).filli(iterator::next);
	}
	
	public Tensor logspace(double start, double stop, int samples) {
		return logspace(start, stop, samples, true);
	}
	
	public Tensor logspace(double start, double stop, int samples, boolean endpoint) {
		return logspace(start, stop, samples, endpoint, 10);
	}
	
	public Tensor logspace(double start, double stop, int samples, int base) {
		return logspace(start, stop, samples, true, base, samples);
	}
	
	public Tensor logspace(double start, double stop, int samples, boolean endpoint, int base) {
		return logspace(start, stop, samples, endpoint, base, samples);
	}
	
	public Tensor logspace(double start, double stop, int samples, boolean endpoint, int base, int...shape) {
		int count = endpoint ? samples-1 : samples;
		final double
			first = Math.pow(base, start),
			end = Math.pow(base, stop),
			logDistance = Transforms.log(end, base) - Transforms.log(first, base),
			logStep = logDistance/count,
			dx = Math.pow(base, logStep);
		Iterator<Double> iterator = Stream.iterate(first, x -> x * dx)
				.limit(samples)
				.iterator();
		return create(shape).filli(iterator::next);
	}
	
	public Tensor geomspace(double start, double stop, int samples) {
		return geomspace(start, stop, samples, true);
	}
	
	public Tensor geomspace(double start, double stop, int samples, boolean endpoint) {
		return geomspace(start, stop, samples, endpoint, samples);
	}
	
	public Tensor geomspace(double start, double stop, int samples, boolean endpoint, int...shape) {
		return logspace(Transforms.log(start, 10), Transforms.log(stop, 10), samples, endpoint, 10, shape);
	}
	
}