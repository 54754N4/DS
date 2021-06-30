package ads.graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import ads.contracts.Graph;

/**
 * Adjacency list graph.
 *
 * @param <T> the vertex class type
 */
public class ALGraph<T> implements Graph<T> {
	private final boolean trackAllLoops;
	private final Map<T, Collection<T>> adjacencyList;
	
	public ALGraph(boolean trackAllLoops, Map<T, Collection<T>> adjacencyList ) {
		this.trackAllLoops = trackAllLoops;
		this.adjacencyList = adjacencyList;
	}
	
	public ALGraph(Map<T, Collection<T>> adjacencyList) {
		this(false, adjacencyList);
	}
	
	@SafeVarargs
	public ALGraph(boolean trackLoops, T...vertices) {
		this(trackLoops);
		addVertices(vertices);
	}
	
	public ALGraph(boolean trackAllLoops) {
		adjacencyList = new HashMap<>();
		this.trackAllLoops = trackAllLoops;
	}
	
	public ALGraph() {
		this(false);
	}
	
	private Collection<T> initializer() {
		if (trackAllLoops) 
			return new ArrayList<>();
		else // trakcs only a single loop
			return new HashSet<>();
	}
	
	@Override
	public boolean hasLoop(T vertex) {
		Collection<T> adjacents = adjacencyList.get(vertex);
		if (adjacents == null)
			return false;
		return adjacents.contains(vertex);
	}
	
	@Override
	public ALGraph<T> addVertex(T vertex) {
		if (adjacencyList.get(vertex) != null)
			throw new IllegalArgumentException("Vertex already added in graph");
		adjacencyList.put(vertex, initializer());
		return this;
	}
	
	@Override
	public ALGraph<T> removeVertex(T vertex) {
		adjacencyList.forEach((key, val) -> val.remove(vertex));
		adjacencyList.remove(vertex);
		return this;
	}
	
	@Override
	public ALGraph<T> addEdge(T from, T to, Direction direction) {
		direction.check(
			() -> adjacencyList.get(to).add(from), 	// if left
			() -> adjacencyList.get(from).add(to));	// if right
		return this;
	}
	
	@Override
	public ALGraph<T> removeEdge(T from, T to, Direction direction) {
		direction.check(
			() -> adjacencyList.get(to).remove(from),
			() -> adjacencyList.get(from).remove(to));
		return this;
	}
	
	@Override
	public double getEdgeWeight(final T from, final T to) {
		return from.equals(to) && hasLoop(from) ? 
			(trackAllLoops ? 
				adjacencyList.get(from)
					.stream()
					.filter(vertex -> vertex.equals(from))
					.count() :
				2d)
				: 
			adjacencyList.get(from).contains(to) ? 
					1d : 
					0d;
	}
	
	@Override
	public Collection<T> getAdjacent(T vertex) {
		return adjacencyList.get(vertex);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		adjacencyList.forEach((key, val) -> 
			sb.append(String.format("%s: %s%n", key.toString(), Arrays.toString(val.toArray()))));
		return sb.toString();
	}
	
	// Example at https://www.baeldung.com/java-graphs
	public static void main(String[] args) {
		String bob = "Bob", alice = "Alice", mark = "Mark", rob = "Rob", maria = "Maria";
		Graph<String> graph = new ALGraph<String>()
				.addVertices(bob, alice, mark, rob, maria)
				.addEdge(bob, alice)
				.addEdge(bob, rob)
				.addEdge(alice, mark)
				.addEdge(alice, maria)
				.addEdge(mark, rob)
				.addEdge(rob, maria);
		System.out.println(graph);
	}
}
