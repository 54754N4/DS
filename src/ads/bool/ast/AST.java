package ads.bool.ast;

import java.util.Map;

public interface AST {
	boolean eval(Map<String, Boolean> variables);
	AST complement();
}
