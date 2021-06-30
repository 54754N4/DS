package ads.contracts;

import java.util.Collection;

import ads.graphs.Direction;

public interface Graph<Vertex> {
	static Direction DEFAULT_DIRECTION = Direction.NONE; // undirected graphs
	
	boolean hasLoop(Vertex vertex);
	
	Graph<Vertex> addVertex(Vertex v);
	Graph<Vertex> removeVertex(Vertex v);
	
	double getEdgeWeight(Vertex from, Vertex to);
	Graph<Vertex> addEdge(Vertex from, Vertex to, Direction direction);
	Graph<Vertex> removeEdge(Vertex from, Vertex to, Direction direction);
	
	Collection<Vertex> getAdjacent(Vertex v);
	
	default Graph<Vertex> addVertices(@SuppressWarnings("unchecked") Vertex...vertices) {
		for (Vertex vertex : vertices)
			try { addVertex(vertex); }
			catch (Exception e) { continue; }
		return this;
	}
	
	default Graph<Vertex> addEdge(Vertex from, Vertex to) {
		return addEdge(from, to, DEFAULT_DIRECTION);	// undirected graph edges by default
	}
	
	default Graph<Vertex> removeEdge(Vertex from, Vertex to) {
		return removeEdge(from, to, DEFAULT_DIRECTION);
	}
}
