package ads.bool.ast;

import java.util.Map;

public class And implements AST {
	private AST left, right;
	
	public And(AST left, AST right) {
		this.left = left;
		this.right = right;
	}

	public AST getLeft() {
		return left;
	}

	public AST getRight() {
		return right;
	}

	@Override
	public boolean eval(Map<String, Boolean> variables) {
		return left.eval(variables) && right.eval(variables);
	}

	@Override
	public AST complement() { // !(A&B) == !A|!B
		return new Or(left.complement(), right.complement());
	}
}