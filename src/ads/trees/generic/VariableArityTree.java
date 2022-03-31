package ads.trees.generic;

import java.util.ArrayList;
import java.util.List;

import ads.contracts.Tree;

public class VariableArityTree<V> extends BaseTree<V> {
	private List<Tree<V>> children;
	
	public VariableArityTree(V value) {
		super(value);
		children = new ArrayList<>();
	}
	
	public VariableArityTree() {
		this(null);
	}
	
	@Override
	public List<Tree<V>> getChildren() {
		return children;
	}

	@Override
	public Tree<V> getChild(int i) {
		return children.get(i);
	}

	@Override
	public Tree<V> addChild(Tree<V> child) {
		children.add(child);
		return this;
	}

	@Override
	public int getChildCount() {
		return children.size();
	}

	@Override
	public Tree<V> removeChild(Tree<V> child) {
		children.remove(child);
		return this;
	}
}