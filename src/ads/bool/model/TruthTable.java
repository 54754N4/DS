package ads.bool.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ads.bool.ast.AST;
import ads.bool.visitor.VariableExtractor;

public class TruthTable {
	private AST ast;
	private Set<String> variables;
	private int cardinality;
	private boolean[][] inputs;
	private boolean[] outputs;
	
	public TruthTable(AST ast) {
		this.ast = ast;
		variables = VariableExtractor.getVariables(ast);
		cardinality = getCardinality(variables.size());
		inputs = generateInputs(variables.size());
		outputs = generateOutputs(ast, variables);
	}

	/* Accessors */
	
	public AST getAST() {
		return ast;
	}
	
	public Set<String> getVariables() {
		return variables;
	}
	
	public int getCardinality() {
		return cardinality;
	}
	
	public boolean[][] getInputs() {
		return inputs;
	}
	
	public boolean[] getOutputs() {
		return outputs;
	}
	
	/* Compressed equivalents (only useful if output cardinality <= 64 because of long data type) */
	
	public long[] getCompressedInputs() {
		long[] results = new long[inputs.length];
		long result;
		for (int i=0; i<inputs.length; i++) {
			result = 0;
			for (int j=0; j<inputs[i].length; j++)
				if (inputs[i][j])
					result |= 1 << (inputs[i].length - 1 - j);
			results[i] = result;
		}
		return results;
	}
	
	public long getCompressedOutputs() {
		long result = 0;
		for (int i=0; i<outputs.length; i++)
			if (outputs[i])
				result |= 1 << i;
		return result;
	}
	
	@Override
	public String toString() {
		return print(variables, inputs, outputs);
	}
	
	/* Static convenience methods */
	
	public static int getCardinality(int bits) {
		return 1 << bits;
	}
	
	public static boolean[][] generateInputs(int inputs) {
		int cardinality = getCardinality(inputs);
		boolean[][] table = new boolean[cardinality][inputs];
		int offset;
		for (int i=0; i<cardinality; i++) {
			for (int col=0; col<inputs; col++) {
				offset = inputs - 1 - col;
				table[i][col] = (i & (1 << offset)) >> offset == 1;
			}
		}
		return table;
	}
	
	public static boolean[] generateOutputs(AST ast, Set<String> variables) {
		List<String> vars = new ArrayList<>(variables);
		Map<String, Boolean> map = new HashMap<>();
		boolean[][] inputs = generateInputs(variables.size());
		int cardinality = getCardinality(variables.size());
		boolean[] outputs = new boolean[cardinality];
		for (int i=0; i<cardinality; i++) {
			for (int col=0; col<variables.size(); col++)
				map.put(vars.get(col), inputs[i][col]);
			outputs[i] = ast.eval(map);
		}
		return outputs;
	}
	
	public static String print(Set<String> variables, boolean[][] inputs, boolean[] outputs) {
		StringBuilder sb = new StringBuilder();
		for (String var : variables)
			sb.append(var).append("\t");
		sb.append("OUTPUT")
			.append(System.lineSeparator());
		for (int i=0; i<inputs.length; i++) {
			for (int j=0; j<inputs[i].length; j++)
				sb.append(String.format("%s\t", inputs[i][j]));
			sb.append(outputs[i])
				.append(System.lineSeparator());
		}
		return sb.toString();
	}
	
	/* Debug */
	
	public static String print(boolean[][] inputs) {
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<inputs.length; i++) {
			for (int j=0; j<inputs[i].length; j++)
				sb.append(String.format("%s\t", inputs[i][j]));
			sb.append(System.lineSeparator());
		}
		return sb.toString();
	}
}