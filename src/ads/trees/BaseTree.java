package ads.trees;

import ads.contracts.Tree;

public abstract class BaseTree<V> implements AbstractTree<V> {
	private Tree<V> parent;
	private V value;
	
	public BaseTree(V value) {
		this.value = value;
	}
	
	@Override
	public V getValue() {
		return value;
	}

	@Override
	public Tree<V> setValue(V value) {
		this.value = value;
		return this;
	}

	@Override
	public Tree<V> getParent() {
		return parent;
	}

	@Override
	public Tree<V> setParent(Tree<V> parent) {
		this.parent = parent;
		return this;
	}
}
