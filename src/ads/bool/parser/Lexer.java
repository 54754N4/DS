package ads.bool.parser;

public class Lexer {
	private static final char EOF = '\0';
	
	private int pos;
	private char c;
	private String text;
	
	public Lexer(String text) {
		if (text.length() == 0)
			throw new IllegalArgumentException("Lexer input cannot be empty");
		this.text = text;
		c = text.charAt(0);
		pos = 0;
	}
	
	public int getPosition() {
		return pos;
	}
	
	private <T> T error(String message, Object...args) {
		message = String.format(message, args);
		throw new RuntimeException(String.format("Lexer error at pos %d: %s", pos, message));
	}
	
	private void advance() {
		pos++;
		if (pos < text.length())
			c = text.charAt(pos);
		else 
			c = EOF;
	}
	
	private void skipWhitespace() {
		while (Character.isWhitespace(c))
			advance();
	}
	
	private boolean isVariableChar() {
		return (('a' <= c && c <= 'z') 
			|| ('A' <= c && c <= 'Z') 
			|| ('0' <= c && c <= '9'));
	}
	
	/**
	 * Legal tokens that can be created for our grammar (defined in parser):
	LPAREN	: '('
	RPAREN	: ')'
	NOT		: '!'
	AND		: '&'
	OR		: '|'
	VARIABLE : [a-zA-Z0-9]+
	 */	
	public Token getNextToken() {
		while (c != EOF) {
			skipWhitespace();
			switch (c) {
				case '(':
					advance();
					return new Token(Type.LPAREN, "(");
				case ')':
					advance();
					return new Token(Type.RPAREN, ")");
				case '!':
					advance();
					return new Token(Type.NOT, "!");
				case '&':
					advance();
					return new Token(Type.AND, "&");
				case '|':
					advance();
					return new Token(Type.OR, "|");
				default:
					StringBuilder sb = new StringBuilder();
					while (isVariableChar()) {
						sb.append(c);
						advance();
					}
					if (sb.length() == 0)
						return error("Illegal character %s", c);
					return new Token(Type.VAR, sb.toString());
			}
		}
		return new Token(Type.EOF, "\0");
	}
}