package ads.bool.parser;

import ads.bool.ast.AST;
import ads.bool.ast.And;
import ads.bool.ast.Not;
import ads.bool.ast.Or;
import ads.bool.ast.Variable;

/**
 * Boolean operators precendence from highest to lowest: ! -> & -> |
 * Boolean expressions grammar (for recursive descent) :
expr 		: or_expr ;
or_expr		: and_expr (OR or_expr)? ;
and_expr	: unary (AND and_expr)? ;
unary		: NOT? atom ;
atom		: VARIABLE | LPAREN expr RPAREN | expr ;

LPAREN		: '('
RPAREN		: ')'
NOT			: '!'
AND			: '&'
OR			: '|'
VARIABLE 	: [a-zA-Z]+

References: Operators precedence - https://docs.oracle.com/javase/tutorial/java/nutsandbolts/operators.html
 */
public class Parser {
	private Lexer lexer;
	private Token token;
	
	public Parser(Lexer lexer) {
		this.lexer = lexer;
		token = lexer.getNextToken();
	}
	
	private <T> T error(String message, Object...args) {
		message = String.format(message, args);
		throw new RuntimeException(String.format("Parser error at pos %d: %s", lexer.getPosition(), message));
	}
	
	private boolean is(Type type) {
		return token.getType() == type;
	}
	
	private void consume(Type type) {
		if (is(type)) 
			token = lexer.getNextToken();
		else
			error("Expected token of type %s", type);
	}
	
	public AST parse() {
		return expr();
	}
	
	// expr 		: or_expr ;
	private AST expr() {
		return or_expr();
	}
	
	// or_expr		: and_expr (OR or_expr)? ;
	private AST or_expr() {
		AST and_expr = and_expr();
		if (is(Type.OR)){
			consume(Type.OR);
			return new Or(and_expr, or_expr());
		} else
			return and_expr;
	}
	
	// and_expr		: unary (AND and_expr)? ;
	private AST and_expr() {
		AST unary = unary();
		if (is(Type.AND)){
			consume(Type.AND);
			return new And(unary, and_expr());
		} else
			return unary;
	}
	
	// unary		: NOT? atom 
	private AST unary() {
		if (is(Type.NOT)) {
			consume(Type.NOT);
			return new Not(atom());
		} else 
			return atom();
	}
	
	// atom			: VARIABLE | LPAREN expr RPAREN | expr ;
	private AST atom() {
		if (is(Type.VAR)) {
			Variable variable = new Variable(token.getToken());
			consume(Type.VAR);
			return variable;
		} else if (is(Type.LPAREN)) {
			consume(Type.LPAREN);
			AST ast = expr();
			consume(Type.RPAREN);
			return ast;
		} else
			return expr();
	}
}