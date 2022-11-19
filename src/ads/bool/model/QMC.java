package ads.bool.model;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;

/*
 * Minimizes boolean functions using the QMC algorithm and then uses the 
 * Petrick's method to find the best candidates.
 * 
 * References:
 * Quine-McCluskey 	- https://en.wikipedia.org/wiki/Quine%E2%80%93McCluskey_algorithm
 * Petrick's method	- https://en.wikipedia.org/wiki/Petrick%27s_method
 */
public class QMC {
	private int variables;
	private int[] minterms;
	private List<String> mintermsBinary;
	private List<List<String>> table;
	private List<String> primeImplicants;
	private List<Set<Integer>> functions;
	
	private String[] variableNames;
	private String or, and;
	private Function<String, String> not;
	
	public QMC(int[] minterms, String[] variableNames, 
			String or, String and, Function<String, String> not) {
		this.variables = variableNames.length;
		this.minterms = minterms;
		this.variableNames = variableNames;
		this.or = or;
		this.and = and;
		this.not = not;
		primeImplicants = new ArrayList<>();
		functions = new ArrayList<>();
		mintermsBinary = new ArrayList<>(minterms.length);
		for (Integer i : minterms)
			mintermsBinary.add(Convert.toBinary(variables, i));
		table = new ArrayList<>(variables+1);
		for (int i=0; i<variables+1; i++)
			table.add(new ArrayList<>());
	}

	public List<String> solve() {
		findPrimeImplicants();
		reduce();
		return computeFunctions();
	}
	
	/***************************************
	 *  QM and Petrick algorithm methods   *
	 ***************************************/
	
	private void findPrimeImplicants() {
		Set<String> primeImpTemp = new TreeSet<>();
		// Initialize table
		String m;
		for (int i=0; i<minterms.length; i++) {
			m = mintermsBinary.get(i);
			table.get(countOnes(m)).add(m);
		}
		// Combine pairs until table is empty
		while (!checkEmpty(table))
			table = combinePairs(table, primeImpTemp);
		for (String x : primeImpTemp)
			primeImplicants.add(x);
	}
	
	/* After computing the prime implicant chart, follows petrick's method to reduce the results */
	private void reduce() {
		// Generate prime implicant chart
		boolean[][] chart = new boolean[primeImplicants.size()][minterms.length];
		for (int i=0; i<primeImplicants.size(); i++)
			for (int j=0; j<minterms.length; j++)
				chart[i][j] = primeIncludes(primeImplicants.get(i), mintermsBinary.get(j));
		
		// Petrick's Method
		List<Set<Integer>> patLogic = new ArrayList<>();
		for (int j=0; j<minterms.length; j++) {	// per column
			Set<Integer> x = new TreeSet<>();
			for (int i=0; i<primeImplicants.size(); i++)
				if (chart[i][j])
					x.add(i);
			patLogic.add(x);
		}
		
		// Multiply set elements and apply absorption law
		Set<Set<Integer>> posComb = multiplyAbsorb(patLogic);
		int min = Integer.MAX_VALUE;
		for (Set<Integer> comb : posComb)
			if (comb.size() < min)
				min = comb.size();
	
		// Minimal comb size
		List<Set<Integer>> minComb = new ArrayList<>();
		for (Set<Integer> comb : posComb)
			if (comb.size() == min)
				minComb.add(comb);
		
		// Comb with minimal variables
		min = Integer.MAX_VALUE;
		int count;
		for (int i=0; i<minComb.size(); i++) {
			count = countVariables(minComb.get(i), primeImplicants);
			if (count < min)
				min = count;
		}
		for (int i=0; i<minComb.size(); i++)
			if (countVariables(minComb.get(i), primeImplicants) == min)
				functions.add(minComb.get(i));
	}
	
	/* Returns the list of possible functions */
	private List<String> computeFunctions() {
		List<String> results = new ArrayList<>();
		Set<Integer> function;
		StringBuilder sb;
		for (int i=0; i<functions.size(); i++) {
			function = functions.get(i);
			sb = new StringBuilder();
			for (int x : function)
				sb.append(toVariable(primeImplicants.get(x)))
					.append(and);
			sb.delete(sb.length() - and.length(), sb.length());	// remove last operator
			results.add(sb.toString());
		}
		return results;
	}
	
	/* Recursive function to multiply elements using petrick logic and storing them in posComb */
	private Set<Set<Integer>> multiplyAbsorb(List<Set<Integer>> patLogic) {
		Set<Set<Integer>> posComb = new LinkedHashSet<>();
		multiplyAbsorb(patLogic, 0, new TreeSet<>(), posComb);
		return posComb;
	}
	
	private void multiplyAbsorb(List<Set<Integer>> patLogic, int level, Set<Integer> product, Set<Set<Integer>> posComb) {
		product = new TreeSet<>(product);
		if (level == patLogic.size()) {
			posComb.add(product);
			return;
		}
		for (int x : patLogic.get(level)) {
			boolean inserted = product.add(x);
			multiplyAbsorb(patLogic, level+1, product, posComb);
			if (inserted)
				product.remove(x);
		}
	}
	
	/* Combines pairs that have a single bit difference and shrinks table continuously */ 
	private List<List<String>> combinePairs(List<List<String>> table, Set<String> primeImpTemp) {
		boolean[][] checked = new boolean[table.size()][minterms.length];
		List<List<String>> newTable = new ArrayList<>(table.size()-1);
		for (int i=0; i<table.size()-1; i++)
			newTable.add(new ArrayList<>());
		
		// Store in new table the combined pairs
		for (int i=0; i<table.size()-1; i++) {
			for (int j=0; j<table.get(i).size(); j++) {
				for (int k=0; k<table.get(i+1).size(); k++) {
					if (bitDifference(table.get(i).get(j), table.get(i+1).get(k))) {
						newTable.get(i).add(replaceDontCares(table.get(i).get(j), table.get(i+1).get(k)));
						checked[i][j] = checked[i+1][k] = true;
					}
				}
			}
		}
		for (int i=0; i<table.size(); i++)
			for (int j=0; j<table.get(i).size(); j++)
				if (!checked[i][j])
					primeImpTemp.add(table.get(i).get(j));
		return newTable;
	}
	
	/**************************
	 *  Convenience Methods   *
	 **************************/
	
	/* Counts the number of 1s in a binary string
	 * Note: Assumes that both inputs are binary
	 */
	private int countOnes(String binary) {
		int count = 0;
		for (int i=0; i<binary.length(); i++)
			if (binary.charAt(i) == '1')
				count++;
		return count;
	}
	
	/* Check if only 1 bit difference between two binary strings 
	 * Note: Assumes that both inputs are binary
	 */
	private boolean bitDifference(String a, String b) {
		int count = 0;
		for (int i=0; i<a.length(); i++)
			if (a.charAt(i) != b.charAt(i))
				count++;
		return count == 1;
	}
	
	/* If matches a single bit difference, replaces with don't care.
	 * Example: 1110 & 0110 becomes -110
	 */
	private String replaceDontCares(String a, String b) {
		StringBuilder result = new StringBuilder();
		for (int i=0; i<a.length(); i++)
			if (a.charAt(i) != b.charAt(i))
				result.append("-");
			else
				result.append(a.charAt(i));
		return result.toString();
	}
	
	/* Checks if table contains only empty lists */
	private boolean checkEmpty(List<List<String>> table) {
		for (int i=0; i<table.size(); i++)
			if (table.get(i).size() != 0)
				return false;
		return true;
	}
	
	/* Converts binary to variables */
	private String toVariable(String binary) {
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<binary.length(); i++) {
			if (binary.charAt(i) == '1')
				sb.append(variableNames[i])
					.append(or);
			else if (binary.charAt(i) == '0')
				sb.append(not.apply(variableNames[i]))
					.append(or);
		}
		// Remove last or
		if (!or.equals("") && sb.toString().endsWith(or))
			sb.delete(sb.length() - or.length(), sb.length());
		return sb.toString();
	}
	
	/* Check if a prime implicant satisfies a minterm */
	private boolean primeIncludes(String implicant, String minterm) {
		for (int i=0; i<implicant.length(); i++)
			if (implicant.charAt(i) != '-'
				&& implicant.charAt(i) != minterm.charAt(i))
				return false;
		return true;
	}
	
	/* Counts the number of variables in the petrick method */
	private int countVariables(Set<Integer> comb, List<String> primeImplicant) {
		int count = 0;
		for (Integer implicant : comb)
			for (int i=0; i<primeImplicant.get(implicant).length(); i++)
				if (primeImplicant.get(implicant).charAt(i) != '-')
					count++;
		return count;
	}
	
	public static class Builder {
		private Integer variables;
		private int[] minterms;
		private String[] names;
		private String and, or;
		private Function<String, String> not;
		private char start;
		
		public Builder() {
			// default ops are algebraic ops
			not = str -> "!" + str;
			and = "+";
			or = "";
			// variable name auto-generation
			start = 'a';
		}
		
		public Builder setFromTruthTable(TruthTable table) {
			Set<String> vars = table.getVariables();
			setVariables(vars.size());
			setVariableNames(vars.toArray(String[]::new));
			boolean[] outputs = table.getOutputs();
			List<Integer> minterms = new ArrayList<>();
			for (int i=0; i<outputs.length; i++)
				if (outputs[i])
					minterms.add(i);
			int[] converted = new int[minterms.size()];
			for (int i=0; i<converted.length; i++)
				converted[i] = minterms.get(i);
			setMinterms(converted);
			return this;
		}
		
		public Builder setVariables(int variables) {
			this.variables = variables;
			return this;
		}
		
		public Builder setVariableNames(String...names) {
			this.names = names;
			return this;
		}
		
		public Builder setUppercaseVariables() {
			start = 'A';
			return this;
		}
		
		public Builder setMinterms(int...minterms) {
			this.minterms = minterms;
			return this;
		}
		
		public Builder setAnd(String and) {
			this.and = and;
			return this;
		}
		
		public Builder setOr(String or) {
			this.or = or;
			return this;
		}
		
		public Builder setNot(Function<String, String> not) {
			this.not = not;
			return this;
		}
		
		public QMC build() {
			if (minterms == null)
				throw new IllegalArgumentException("Minterms can't be null");
			if (variables == null) {	// by default automatically calculate best candidate
				if (names == null) { 
					variables = 1;
					boolean tryAgain = true;
					while (tryAgain) {
						for (int i=0; i<minterms.length; i++) {
							if (minterms[i] > 1 << variables) {
								tryAgain = true;
								variables++;
								break;
							}
							tryAgain = false;
						}
					}
				} else	// if variable names were given then we know how many vars there are
					variables = names.length;
			}
			if (names == null) {		// by default use alphabetic variable names
				names = new String[variables];
				for (int i=0; i<variables; i++)
					names[i] = "" + (char)(start + i);
			}
			Arrays.sort(minterms);
			return new QMC(minterms, names, or, and, not);
		}
	}
	
	public static void main(String[] args) {
		QMC qm = new QMC.Builder()
			.setUppercaseVariables()
			.setNot(s -> s + "'")
			.setMinterms(1,2,3,4,5,6,13)
			.build();
		qm.solve()
			.forEach(System.out::println);
	}
}
