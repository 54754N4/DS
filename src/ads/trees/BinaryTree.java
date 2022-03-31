package ads.trees;

import java.util.LinkedList;
import java.util.Queue;

public class BinaryTree<T> {
	private T data;
	private BinaryTree<T> parent, left, right;
	
	public BinaryTree(T data) {
		this.data = data;
	}
	
	/* Accessors/Setters */
	
	public T getData() {
		return data;
	}
	
	public BinaryTree<T> getParent() {
		return parent;
	}
	
	public BinaryTree<T> getLeft() {
		return left;
	}
	
	public BinaryTree<T> getRight() {
		return right;
	}
	
	private BinaryTree<T> setParent(BinaryTree<T> parent) {
		this.parent = parent;
		return this;
	}
	
	public BinaryTree<T> setLeft(BinaryTree<T> left) {
		this.left = left;
		left.setParent(this);
		return this;
	}
	
	public BinaryTree<T> setRight(BinaryTree<T> right) {
		this.right = right;
		right.setParent(this);
		return this;
	}
	
	/* Tree methods */
	
	public int height() {
		if (isLeaf())
			return 0;
		return 1 + Math.max(left.height(), right.height());
	}
	
	public int size() {
		if (isLeaf())
			return 1;
		return 1 + left.size() + right.size();
	}
	
	public boolean isLeaf() {
		return left == null && right == null;
	}
	
	public boolean isFull() {
		return isLeaf() ? 
				true : 
				left.isFull() && right.isFull();  
	}
	
	public boolean isPerfect() {
		Queue<BinaryTree<T>> queue = new LinkedList<>();
		queue.add(this);
		boolean leafFound = false;
		BinaryTree<T> node;
		while (!queue.isEmpty()) {
			node = queue.poll();
			if (node.left != null && node.right != null) {
				if (leafFound)
					return false;
				else {
					queue.add(node.left);
					queue.add(node.right);
				}
			} else if (node.isLeaf())
				leafFound = true;
			else if (node.left == null || node.right == null)
				return false;
		}
		return true;
	}
	
	public boolean isBalanced() {
		if (isLeaf())
			return true;
		int lh = left.height(), 
			rh = right.height();
		if (lh != rh + 1 && lh != rh - 1) 
			return false;
		return left.isBalanced() && right.isBalanced();
	}
	
	public boolean isDegenerate() {
		if (isLeaf())
			return true;
		if (left != null && right != null)
			return false;
		if (left == null)
			return right.isDegenerate(); 
		else
			return left.isDegenerate();
	}
}
