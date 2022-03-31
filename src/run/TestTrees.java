package run;

import java.util.Comparator;

import ads.trees.BinaryHeap;
import ads.trees.BinaryTree;

public class TestTrees {
	
	public static void main(String[] args) {
//		testBinaryTree();
		testHeaps();
	}
	
	public static void testBinaryTree() {
		BinaryTree<Integer> root = new BinaryTree<>(45),
			l1 = new BinaryTree<>(5),
			l2 = new BinaryTree<>(15),
			l3 = new BinaryTree<>(2),
			l4 = new BinaryTree<>(3),
			l5 = new BinaryTree<>(1),
			l6 = new BinaryTree<>(4);
		
		root.setLeft(l1);
		root.setRight(l2);
		l1.setLeft(l3);
		l1.setRight(l4);
		l2.setLeft(l5);
		l2.setRight(l6);
		
		System.out.printf("Height: %d%n", root.height());
		System.out.printf("Size: %d%n", root.size());
		System.out.println(root);
	}
	
	public static void testHeaps() {
		BinaryHeap<Integer> heap = new BinaryHeap<>(Comparator.<Integer>naturalOrder());
		heap.insert(3);
		heap.insert(2);
		heap.insert(1);
		heap.insert(15);
		heap.insert(5);
		heap.insert(4);
		heap.insert(45);
		
		System.out.printf("Height: %d%n", heap.height());
		System.out.printf("Size: %d%n", heap.size());
		System.out.println(heap);	// [45, 5, 15, 2, 3, 1, 4]
		
		while (heap.size() > 0)
			System.out.println("" + heap.extract());
	}
}
