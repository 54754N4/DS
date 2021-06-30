package run;

import java.util.Arrays;
import java.util.Iterator;

import ads.common.Numbers;
import ads.common.Tensors;
import ads.contracts.Tensor;
import ads.tensors.ColMajorTensor;
import ads.tensors.CoordinatesIterator;
import ads.tensors.MemoryLayout;
import ads.tensors.RowMajorTensor;

@SuppressWarnings("unused")
public class Test {
	public static void main(String[] args) throws InterruptedException {
//		testTensorsRank3();
//		testSingleMemoryLayout();
//		testBothLayoutsOnRealData();
//		testOuterProductRowMajor();
//		testOuterProductColMajor();
//		testTensorSimple();
//		testReflection();
//		testStriding();
		testCoords();
	}
	
	public static void testTensorsRank3() {
		int[] shape = { 3,4,5 };
		double[] data = {
				1,2,3,4,5,
				6,7,8,9,10,
				11,12,13,14,15,
				16,17,18,19,20,
				
				21,22,23,24,25,
				26,27,28,29,30,
				31,32,33,34,35,
				36,37,38,39,40,
				
				41,42,43,44,45,
				46,47,48,49,50,
				51,52,53,54,55,
				56,57,58,59,60
		};
		Tensor rm, cm;
		System.out.printf("Row-major tensor :%n%s%n", rm = Tensors.rowMajor(shape, data));
		System.out.println(Arrays.toString(rm.stride()));
		System.out.printf("Col-major tensor :%n%s%n", cm = Tensors.colMajor(shape, data));
		System.out.println(Arrays.toString(cm.stride()));
	}
	
	public static void testSingleMemoryLayout() {
		int[] shape = { 3,3 };
		double[] data = {
			1,2,3,
			4,5,6,
			7,8,9
		};
		System.out.printf("Row-major tensor :%n%s%n", Tensors.rowMajor(shape, data));
		System.out.printf("Col-major tensor :%n%s%n", Tensors.colMajor(shape, data));
	}
	
	public static void testBothLayoutsOnRealData() {
		int[] shape = { 3,3 };
		double[] rm = { 1,2,3,4,5,6,7,8,9 },
				cm = { 1,4,7,2,5,8,3,6,9 };
		System.out.printf("Row-major tensor :%n%s%n", Tensors.rowMajor(shape, rm));
		System.out.printf("Col-major tensor :%n%s%n", Tensors.colMajor(shape, cm));
	}
	
	public static void testOuterProductRowMajor() {
		int[] s1 = {3}, s2 = {2};
		double[] d1 = {1,2,3}, d2 = {4,5};
		Tensor u = Tensors.rowMajor(s1, d1),
			v = Tensors.rowMajor(s2, d2),
			outer = u.product(v);
		System.out.println(u);
		System.out.println(v);
		System.out.println(Arrays.toString(outer.shape()));
		System.out.println(outer);
	}
	
	public static void testOuterProductColMajor() {
		int[] s1 = {3}, s2 = {2};
		double[] d1 = {1,2,3}, d2 = {4,5};
		Tensor u = Tensors.colMajor(s1, d1),
			v = Tensors.colMajor(s2, d2),
			outer = u.product(v);
		System.out.println(u);
		System.out.println(v);
		System.out.println(Arrays.toString(outer.shape()));
		System.out.println(outer);
	}
	
	public static void testTensorSimple() {
		Tensor t = Tensors.builder()
			.rowMajor()
			.shape(new int[] {3,4,5})
			.memory(
				1,2,3,4,5,
				6,7,8,9,10,
				11,12,13,14,15,
				16,17,18,19,20,
				
				21,22,23,24,25,
				26,27,28,29,30,
				31,32,33,34,35,
				36,37,38,39,40,
				
				41,42,43,44,45,
				46,47,48,49,50,
				51,52,53,54,55,
				56,57,58,59,60)
			.build();
		System.out.println(t);
		System.out.println(t.flatteni());
		System.out.println(t.reshapei(10, 6));
	}
	
	public static void testReflection() {
		Tensor t = Tensors.builder()
				.rowMajor()
				.shape(new int[] {3,4,5})
				.memory(
					1,2,3,4,5,
					6,7,8,9,10,
					11,12,13,14,15,
					16,17,18,19,20,
					
					21,22,23,24,25,
					26,27,28,29,30,
					31,32,33,34,35,
					36,37,38,39,40,
					
					41,42,43,44,45,
					46,47,48,49,50,
					51,52,53,54,55,
					56,57,58,59,60)
				.build();
		System.out.println(t.arrange(7,2, 1));
	}
	
	public static void testStriding() {
		int[] shape = {3,3,3,3},
			cms = ColMajorTensor.computeStride(shape),
			rms = RowMajorTensor.computeStride(shape);
		int rank = shape.length;
		int total = Numbers.multiply(shape);
		
		int[] inccm = Numbers.zerosI(rank), 
				incrm = Numbers.zerosI(rank);
	
		System.out.printf("CM: %s | RM : %s%n", Arrays.toString(cms), Arrays.toString(rms));
		System.out.println(total+" elements");
		
		int[] stride = cms;
		int[] increments = inccm;
	
		for (int count = 0, max = rank-1, axis = max;; count++) {
			System.out.printf("%d. %s %n", count, Arrays.toString(increments));
			if (increments[max-axis]+1 != shape[axis])
				increments[max-axis]++;
			else {
				// Find axis with unfulfilled dimension
				while (axis+1 < rank && (count+1)%stride[axis+1] == 0) 
					axis++;
				if (count+1 == total)
					break;
				// Increment current biggest dimension
				increments[max-axis]++;
				// Reset previous dimension increments
				while (axis != 0 && axis != rank) 
					increments[max-(--axis)] = 0;
			}
		}
		
//		for (int count = 0, axis = 0, max = rank-1;; count++) {
//			System.out.printf("%d. %s %n", count, Arrays.toString(increments));
//			if (increments[max-axis]+1 != shape[axis])
//				increments[max-axis]++;
//			else {
//				// Find axis with unfulfilled dimension
//				while (axis+1 < rank && (count+1)%stride[axis+1] == 0) 
//					axis++;
//				if (count+1 == total)
//					break;
//				// Increment current biggest dimension
//				increments[max-axis]++;
//				// Reset previous dimension increments
//				while (axis != 0 && axis != rank) 
//					increments[max-(--axis)] = 0;
//			}
//		}
	}
	
	public static void testCoords() {
		int[] shape = new int[]{10,10,10,10}, coordsCM, coordsRM;
		Tensor tcm = Tensors.of(MemoryLayout.COL_MAJOR, shape),
				trm = Tensors.of(MemoryLayout.ROW_MAJOR, shape);
		Iterator<int[]> iteratorCM = tcm.coordinates(),
				iteratorRM = trm.coordinates();
		System.out.printf("Strides=%s%n", Arrays.toString(tcm.stride()));
		System.out.printf("Strides=%s%n", Arrays.toString(trm.stride()));
		int i = 0;
		while (iteratorCM.hasNext()) {
			coordsCM = iteratorCM.next();
			coordsRM = iteratorRM.next();
			System.out.printf(
					"%s %s | (%d,%d) -> %d%n",
					Arrays.toString(coordsCM),
					Arrays.toString(coordsRM),
					tcm.layout().offsetProcessor().apply(tcm.stride(), coordsCM),
					trm.layout().offsetProcessor().apply(trm.stride(), coordsRM),
					i++);
		}
	}
}