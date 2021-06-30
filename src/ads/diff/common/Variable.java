package ads.diff.common;

import java.util.Map;

public class Variable extends MultivariateExpression {
	private String name;
	
	public Variable(String name) {
		super(HashcodeBase.VAR.base, false);
		this.name = name;
	}
	
	public String name() {
		return name;
	}
	
	@Override
	public Double eval(Map<String, Double> x) {
		Double d = x.get(name);
		if (d == null)
			throw new IllegalArgumentException("You didn't give an argument for variable: "+name);
		return d;
	}

	@Override
	public MultivariateExpression derivative(String name) {	// du/du = 1 or du/dv = 0
		return name.equals(this.name) ? 
				constant(1) :
				constant(0);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Variable other = (Variable) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return name;
	}
}
