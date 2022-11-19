package ads.bool.parser;

public class Token {
	private final Type type;
	private final String token;
	
	public Token(Type type, String token) {
		this.type = type;
		this.token = token;
	}

	public Type getType() {
		return type;
	}

	public String getToken() {
		return token;
	}
	
	@Override
	public String toString() {
		return String.format("Token(%s, %s)", type, token);
	}
}