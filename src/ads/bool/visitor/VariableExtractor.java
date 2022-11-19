package ads.bool.visitor;

import java.util.HashSet;
import java.util.Set;

import ads.bool.ast.AST;
import ads.bool.ast.And;
import ads.bool.ast.Not;
import ads.bool.ast.Or;
import ads.bool.ast.Variable;

public class VariableExtractor implements Visitor<Void> {
	private Set<String> variables;
	
	public VariableExtractor() {
		variables = new HashSet<>();
	}
	
	public static Set<String> getVariables(AST ast) {
		VariableExtractor counter = new VariableExtractor();
		counter.visit(ast);
		return counter.variables;
	}

	@Override
	public Void visit(Variable variable) {
		variables.add(variable.getName());
		return null;
	}

	@Override
	public Void visit(Not not) {
		return visit(not.getOperand());
	}

	@Override
	public Void visit(And and) {
		visit(and.getLeft());
		visit(and.getRight());
		return null;
	}

	@Override
	public Void visit(Or or) {
		visit(or.getLeft());
		visit(or.getRight());
		return null;
	}
}