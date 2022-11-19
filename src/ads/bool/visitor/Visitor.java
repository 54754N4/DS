package ads.bool.visitor;

import ads.bool.ast.AST;
import ads.bool.ast.And;
import ads.bool.ast.Not;
import ads.bool.ast.Or;
import ads.bool.ast.Variable;

public interface Visitor<T> {
	T visit(Variable variable);
	T visit(Not not);
	T visit(And and);
	T visit(Or or);
	
	default T visit(AST ast) {
		if (Variable.class.isInstance(ast))
			return visit(Variable.class.cast(ast));
		else if (Not.class.isInstance(ast))
			return visit(Not.class.cast(ast));
		else if (And.class.isInstance(ast))
			return visit(And.class.cast(ast));
		else if (Or.class.isInstance(ast))
			return visit(Or.class.cast(ast));
		else
			throw new IllegalStateException("No visitor branch implemented for type : "+ast.getClass());
	}
}
