package ads.common;

import ads.contracts.Tree;
import ads.trees.FixedArityTree;
import ads.trees.VariableArityTree;

public interface Trees {
	static <V> Tree<V> binary() {
		return fixedArity(2);
	}
	
	static <V> Tree<V> ternary() {
		return fixedArity(3);
	}
	
	static <V> Tree<V> fixedArity(int arity) {
		return new FixedArityTree<>(arity);
	}
	
	static <V> Tree<V> variableArity() {
		return new VariableArityTree<>();
	}
}
