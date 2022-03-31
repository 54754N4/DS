package ads.trees.generic;

import java.util.Arrays;
import java.util.List;

import ads.contracts.Tree;

public class FixedArityTree<V> extends BaseTree<V> {
	private final int arity;
	private final Tree<V>[] children;
	private int count;
	
	@SuppressWarnings("unchecked")	// they're null at first anyways
	public FixedArityTree(int arity, V value) {
		super(value);
		this.arity = arity;
		children = (Tree<V>[]) new Object[arity];
		count = 0;
	}
	
	public FixedArityTree(int arity) {
		this(arity, null);
	}
	
	@Override
	public List<Tree<V>> getChildren() {
		return Arrays.asList(children);
	}

	@Override
	public Tree<V> getChild(int i) {
		return children[i];
	}

	@Override
	public Tree<V> addChild(Tree<V> child) {
		if (count >= arity)
			throw new IllegalArgumentException("Can't add more children than tree arity");
		children[count++] = child;
		return this;
	}

	@Override
	public int getChildCount() {
		return count;
	}

	@Override
	public Tree<V> removeChild(Tree<V> child) {
		for (int i=0; i<children.length; i++)
			if (child == children[i])
				children[i] = null;
		return this;
	}
}
