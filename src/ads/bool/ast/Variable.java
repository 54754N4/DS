package ads.bool.ast;

import java.util.Map;

public class Variable implements AST {
	private String name;
	
	public Variable(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	@Override
	public boolean eval(Map<String, Boolean> variables) {
		return variables.get(name);
	}

	@Override
	public AST complement() {
		return new Not(new Variable(name));
	}
}