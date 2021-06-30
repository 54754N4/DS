package ads.diff.common;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import ads.contracts.Tree;
import ads.trees.VariableArityTree;

/** Base class of all mathematical operations.
 */
public abstract class MultivariateExpression extends VariableArityTree<MultivariateExpression> implements ExpressionProvider {
	protected static final int HASHCODE_BASE = 31;	// chosen base prime
	protected final boolean commutative;			// because some operators require fixed child order
	
	protected final long base;
	
	public MultivariateExpression(long base, boolean commutative) {
		this.base = base;
		this.commutative = commutative;
		setValue(this);
	}

	public abstract Double eval(Map<String, Double> variables);
	public abstract MultivariateExpression derivative(String dx);
	
	public MultivariateExpression[] gradient() {
		return getVariables()
				.stream()
				.map(this::derivative)
				.collect(Collectors.toList())
				.toArray(MultivariateExpression[]::new);
	}
	
	public int getInputDimensions() {
		return getVariables().size();
	}
	
	public int getOutputDimensions() {
		return 1;
	}
	
	public Set<String> getVariables() {
		Set<String> set = new HashSet<>();
		if (isLeaf()) {
			if (Variable.class.isInstance(this))
				set.add(Variable.class.cast(this).name());
			return set;
		}
		return getChildren().stream()
				.map(Tree::getValue)
				.map(MultivariateExpression::getVariables)
				.reduce(set, (set1, set2) -> {
					set1.addAll(set2);
					return set1;
				});
	}
		
	/**
	 * Simple hashCode usually follows a recursive formula of the likes:
	 * 		h(0) = 0
	 * 		h(i+1) = BASE_PRIME * h(i) + b(i)
	 * e.g. Same as what Java does with either UTF or ASCII strings or with usual hashCode
	 * methods. 
	 * In our case, we will call this method Expression::graphCode.
	 * The only difference is that, since we have a tree, at each iteration step, 
	 * h(i+1) represents the hashCode of a parent element, and h(i) represents 
	 * the sum of each children hash (IFF commutative, otherwise nested additions to keep 
	 * dependency on children order). The last term b(i) is used to uniquely identify
	 * each tree node type; and each implementing class can choose to:
	 * - override graphCode to make class type unique: by making b(i) depend on it's own
	 * class attributes (e.g. Constant::graphCode's b(i) = base+value.hashCode(), so each 
	 * constant class generates a unique graphCode depending on their value as well)
	 * - override to make class type invisible: by implementing h(i+1) = h(i) [e.g. passthrough]
	 * - override nothing: uses default formula where each b(i) has been sequentially generated 
	 * to make all instances of the same class type equivalent
	 * @return unique graph-code value
	 */
	public long graphCode() {
		if (isLeaf())
			return base;
		if (commutative) {
			long sum = getChildren()
					.stream()
					.map(Tree::getValue)
					.map(MultivariateExpression::graphCode)
					.reduce(0l, Long::sum);
			return HASHCODE_BASE * sum + base;
		} else {
			long h = 0;
			for (Tree<MultivariateExpression> child : getChildren())
				h = HASHCODE_BASE * h + child.getValue().graphCode();
			return HASHCODE_BASE * h + base;
		}
	}
	
	/* Uses graphCode to evaluate if two branches are the same with
	 * respect to each operators commutativity.
	 */
	@Override
	public boolean equals(Object o) {
		if (MultivariateExpression.class.isInstance(o))
			return MultivariateExpression.class.cast(o).graphCode() == graphCode();
		return false;
	}
}