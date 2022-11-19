package ads.bool.visitor;

import java.util.function.Function;

import ads.bool.ast.AST;
import ads.bool.ast.And;
import ads.bool.ast.Not;
import ads.bool.ast.Or;
import ads.bool.ast.Variable;

public class Printer implements Visitor<String>{
	public static final String 
		DEFAULT_AND = "&", DEFAULT_OR = "|", 
		ALGEBRA_AND = "+", ALGEBRA_OR = "";
	public static final Function<String, String> 
		DEFAULT_NOT = str -> "!" + str, 
		ALGEBRA_NOT = str -> str + "'";
	
	private final String AND, OR;
	private final Function<String, String> NOT;
	
	public Printer(boolean algebricOps) {
		this(
			algebricOps ? ALGEBRA_AND : DEFAULT_AND,
			algebricOps ? ALGEBRA_OR : DEFAULT_OR,
			algebricOps ? ALGEBRA_NOT : DEFAULT_NOT
		);
	}
	
	public Printer(String AND, String OR, Function<String, String> NOT) {
		this.NOT = NOT;
		this.AND = AND;
		this.OR = OR;
	}
	
	@Override
	public String visit(Variable variable) {
		return variable.getName();
	}

	@Override
	public String visit(Not not) {
		return NOT.apply(visit(not.getOperand()));
	}

	@Override
	public String visit(And and) {
		return String.format("%s%s%s", visit(and.getLeft()), AND, visit(and.getRight()));
	}

	@Override
	public String visit(Or or) {
		return String.format("%s%s%s", visit(or.getLeft()), OR, visit(or.getRight()));
	}
	
	/* Static convenience methods */
	
	public static String printAlgebraic(AST ast) {
		return print(true, ast);
	}
	
	public static String print(AST ast) {
		return print(false, ast);
	}
	
	public static String print(boolean algebraic, AST ast) {
		return new Printer(algebraic).visit(ast);
	}
}
