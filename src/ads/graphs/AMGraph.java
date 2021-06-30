package ads.graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ads.contracts.Graph;

/**
 * Adjacency matrix graph (matrix using nested java.util.List 
 * because we want mutability).
 *
 * @param <T> the vertex class type
 */
public class AMGraph<T> implements Graph<T> {
	private final List<T> vertices;
	private final Map<T, Integer> cache;		// index cache
	private final List<List<Double>> matrix; 	// size is mutable
	
	@SafeVarargs
	public AMGraph(T...vertices) {
		this.vertices = new ArrayList<>(Arrays.asList(vertices));
		int n = vertices.length;
		// Generate adjacency matrix
		matrix = new ArrayList<>();
		List<Double> cells;
		for (int i=0; i<n; i++) {
			cells = new ArrayList<>();
			for (int j=0; j<n; j++)
				cells.add(0d);
			matrix.add(cells);
		}
		// Generate index cache of vertices
		cache = new HashMap<>();
		for (T vertex : vertices) 
			cache(vertex);
	}
	
	@Override
	public boolean hasLoop(T vertex) {
		return matrix.get(cache.get(vertex)).get(cache.get(vertex)) > 2;
	}
	
	@Override
	public AMGraph<T> addVertex(T v) {
		if (vertices.contains(v))
			throw new IllegalArgumentException("Vertex already in graph.");
		// Add vertex
		vertices.add(v);
		cache(v);
		// Add 1 cell to each row
		matrix.forEach(list -> list.add(0d));
		// Add new row
		matrix.add(zeros(vertices.size()));
		return null;
	}

	@Override
	public AMGraph<T> removeVertex(T v) {
		int index = indexOf(v);
		// Remove column first
		matrix.forEach(row -> row.remove(index));
		// Then remove row
		matrix.remove(index);
		return null;
	}
	
	@Override
	public double getEdgeWeight(T from, T to) {		// Assumption: to and from need to be inside [0, n]
		return matrix.get(cache.get(from)).get(cache.get(to));
	}
	
	@Override
	public AMGraph<T> addEdge(T v1, T v2, Direction direction) {
		direction.check(
			() -> matrix.get(cache.get(v2)).set(cache.get(v1), matrix.get(cache.get(v2)).get(cache.get(v1))+1),
			() -> matrix.get(cache.get(v1)).set(cache.get(v2), matrix.get(cache.get(v1)).get(cache.get(v2))+1));
		return this;
	}
	
	@Override
	public AMGraph<T> removeEdge(T from, T to, Direction direction) {
		direction.check(
			() -> matrix.get(cache.get(to)).set(cache.get(from), 0d), 
			() -> matrix.get(cache.get(from)).set(cache.get(to), 0d));
		return null;
	}
	
	public AMGraph<T> setEdge(T v1, T v2, double weight) {
		return setEdge(v1, v2, weight, Direction.NONE);	// undirected by default
	}
	
	public AMGraph<T> setEdge(T v1, T v2, final double weight, Direction direction) {
		direction.check(
			() -> matrix.get(cache.get(v2)).set(cache.get(v1), weight),
			() -> matrix.get(cache.get(v1)).set(cache.get(v2), weight));
		return this;
	}
	
	@Override
	public Collection<T> getAdjacent(T v) {
		// Get positions of non-zero weights in row
		List<Integer> positions = new ArrayList<>();
		List<Double> row = matrix.get(cache.get(v));
		for (int i=0; i<row.size(); i++)
			if (row.get(i) != 0)
				positions.add(i);
		// Get adjacents
		List<T> adjacent = new ArrayList<>();
		for (int position : positions)
			adjacent.add(vertices.get(position));
		return adjacent;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<matrix.size(); i++) { 
			for (int j=0; j<matrix.get(i).size(); j++) 
				sb.append(matrix.get(i).get(j)+" ");
			sb.append(System.lineSeparator());
		}
		return sb.toString();
	}
	
	/* Helper methods */
	
	public final int indexOf(T vertex) {
		for (int i=0; i<vertices.size(); i++)
			if (vertex.equals(vertices.get(i)))
				return i;
		return -1;
	}
	
	private final void cache(T vertex) {
		cache.put(vertex, indexOf(vertex));
	}
	
	public static final List<Double> zeros(int count) {
		List<Double> zeros = new ArrayList<>();
		while (count-->0)
			zeros.add(0d);
		return zeros;
	}
	
	public static void main(String[] args) {
		// Undirected graph example https://en.wikipedia.org/wiki/Adjacency_matrix#Undirected_graphs
		AMGraph<Integer> graph = new AMGraph<>(1,2,3,4,5,6);
		graph.addEdge(1, 1)
			.addEdge(1, 2)
			.addEdge(1, 5)
			.addEdge(2, 5)
			.addEdge(2, 3)
			.addEdge(5, 4)
			.addEdge(3, 4)
			.addEdge(4, 6);
		System.out.println(graph);
		graph.removeEdge(4, 6);
		System.out.println(graph);
	}
}
