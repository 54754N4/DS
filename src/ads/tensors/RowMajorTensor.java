package ads.tensors;

public class RowMajorTensor extends LinearTensor {

	public RowMajorTensor(int[] shape, double...data) {
		super(MemoryLayout.ROW_MAJOR, shape, data);
	}

	/* Public to be able to use untied to any tensors */
	
	public static int[] computeStride(int...shape) {
		int coef;
		int[] stride = new int[shape.length];
		for (int i=0; i<shape.length; i++) {
			coef = 1;
			for (int j=i+1; j<shape.length; j++)
				coef *= shape[j];
			stride[i] = coef;
		}
		return stride;
	}
	
	public static int computeOffset(int[] stride, int...indices) {
		if (indices.length != stride.length)
			return -1;
		int sum = 0;
		for (int i=0; i<stride.length; i++) 
			sum += stride[i] * indices[i];
		return sum;
	}
}