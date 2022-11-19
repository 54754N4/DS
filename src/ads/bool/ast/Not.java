package ads.bool.ast;

import java.util.Map;

public class Not implements AST {
	private AST operand;
	
	public Not(AST operand) {
		this.operand = operand;
	}
	
	public AST getOperand() {
		return operand;
	}

	@Override
	public boolean eval(Map<String, Boolean> variables) {
		return !operand.eval(variables);
	}

	@Override
	public AST complement() { // !!A = A
		return operand;
	}
}