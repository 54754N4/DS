package ads.common;

import ads.contracts.Tensor;
import ads.tensors.MemoryLayout;
import ads.tensors.TensorFactory;

/**
 * 
 * Refs: 
 * https://en.wikipedia.org/wiki/Row-_and_column-major_order
 * https://eli.thegreenplace.net/2015/memory-layout-of-multi-dimensional-arrays
 */
public interface Tensors {
	
	static <K> Tensor of(MemoryLayout layout, int[] shape, double...data) {
		return layout.tensorConstructor().apply(shape, data);
	}
	
	static Tensor rowMajor(int... shape) {
		return of(MemoryLayout.ROW_MAJOR, shape);
	}
	
	static Tensor rowMajor(int[] shape, double... data) {
		return of(MemoryLayout.ROW_MAJOR, shape, data);
	}
	
	static Tensor colMajor(int... shape) {
		return of(MemoryLayout.COL_MAJOR, shape);
	}
	
	static Tensor colMajor(int[] shape, double... data) {
		return of(MemoryLayout.COL_MAJOR, shape, data);
	}
	
	static Tensor sparse(int... shape) {
		return of(MemoryLayout.SPARSE, shape);
	}
	
	static Tensor sparse(int[] shape, double... data) {
		return of(MemoryLayout.SPARSE, shape, data);
	}
	
	static TensorFactory factory() {
		return TensorFactory.getInstance();
	}
}
