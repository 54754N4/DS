package ads.trees;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class BinaryHeap<T extends Comparable<T>> {
	private List<T> heap;
	private Comparator<T> comparator;
	
	public BinaryHeap(Comparator<T> comparator) {
		this.comparator = comparator;
		heap = new ArrayList<>();
	}
	
	private final boolean compare(T l, T r) {
		return comparator.compare(l, r) > 0;
	}
	
	private final int parent(int i) {
		return (i-1)/2;	// no need to floor cause ints
	}
	
	private final int left(int i) {
		return 2 * i + 1;
	}
	
	private final int right(int i) {
		return 2 * i + 2;
	}
	
	private final void swap(int left, int right) {
		T temp = heap.get(left);
		heap.set(left, heap.get(right));
		heap.set(right, temp);
	}
	
	private final void siftDown(int i) {
		int l = left(i),
			r = right(i),
			target = i;		// largest or smallest
		if (l < heap.size() && compare(heap.get(l), heap.get(target)))
			target = l;
		if (r < heap.size() && compare(heap.get(r), heap.get(target)))
			target = r;
		if (target != i) {
			swap(i, target);
			siftDown(target);
		}
	}
	
	private final void siftUp(int i) {
		int parent;
		while (i != 0 && compare(heap.get(i), heap.get(parent = parent(i)))) {
			swap(i, parent);
			i = parent;
		}
	}
	
	/* Heap methods */
	
	public T extract() {	// returns max or min based on comparator
		if (size() == 1)
			return heap.remove(0);
		T extremum = heap.remove(0);
		swap(heap.size()-1, 0);
		siftDown(0);
		return extremum;
	}
	
	public BinaryHeap<T> insert(T data) {
		heap.add(data);
		siftUp(heap.size()-1);
		return this;
	}
	
	/* Tree methods */
	
	public int size() {
		return heap.size();
	}
	
	public int height() {
		return height(0);
	}
	
	private boolean isLeaf(int i) {
		return left(i) >= size() && right(i) >= size();
	}
	
	private int height(int i) {
		if (i > size() || isLeaf(i))
			return 0;
		return 1 + Math.max(height(left(i)), height(right(i)));
	}
	
	@Override
	public String toString() {
		return Arrays.toString(heap.toArray());
	}
}
