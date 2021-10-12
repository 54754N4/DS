package ads.diff.common;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MultidimensionialExpression extends ArrayList<MultivariateExpression> {
	private static final long serialVersionUID = -1069666715063305909L;

	public int getInputDimensions() {
		return getVariables().size();
	}
	
	public int getOutputDimensions() {
		return size();
	}
	
	public MultidimensionialExpression add(MultivariateExpression...functions) {
		for (MultivariateExpression f : functions)
			add(f);
		return this;
	}
	
	public Double[] eval(Map<String, Double> x) {
		List<Double> images = new ArrayList<>();
		for (int i=0; i<size(); i++)
			images.add(get(i).eval(x));
		return images.toArray(new Double[0]);
	}
	
	public Set<String> getVariables() {
		return stream()
				.map(MultivariateExpression::getVariables)
				.reduce(new HashSet<>(), (s1, s2) -> {
					s1.addAll(s2);
					return s1;
				});
	}
	
	public MultivariateExpression[][] jacobian() {
		List<String> vars = new ArrayList<>(getVariables());
		MultivariateExpression[][] jacobian = new MultivariateExpression[size()][vars.size()];
		for (int i = 0; i < size(); i++)
			for (int j = 0; j < vars.size(); j++)
				jacobian[i][j] = get(i).derivative(vars.get(j));	// J(i,j) = df(i)/dx(j)
		return jacobian;
	}
}
