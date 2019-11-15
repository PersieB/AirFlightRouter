// This class contains the implementation of the graph used throughout this project and the methods that we will rely on

import org.jgrapht.*;
import org.jgrapht.graph.*;
import org.jgrapht.traverse.*;
import java.io.*;
import java.net.*;
import java.util.*;
import org.jgrapht.alg.shortestpath.*;

@SuppressWarnings("unused")
public class GraphImplementation {
	protected Graph<Airports, Route> BaseGraph;
	
	public GraphImplementation() {
		this.BaseGraph = new DirectedWeightedMultigraph<Airports, Route>(null, null); // The suppliers can be set to null because all the vertices and edges that we will add will be custom vertices.
	}
	
	// This method allows us to implement the DFS-Traversal of the graph
	public void DFOTraversal() {
		Iterator<Airports> iter = new DepthFirstIterator<>(this.BaseGraph);
        while (iter.hasNext()) {
            Airports vertex = iter.next();
            System.out.println(vertex.getName());
        }
	}
	
	// This method allows us to implement a BFS-Traversal of the graph
	public void BFOTraversal() {
		Iterator<Airports> iter = new BreadthFirstIterator<>(this.BaseGraph);
        while (iter.hasNext()) {
            Airports vertex = iter.next();
            System.out.println(vertex.getName());
        }
	}
	
	// Adds new Vertices to the graph
	public void addNewVertex(Airports A) {
		this.BaseGraph.addVertex(A);
	}
	
	// Adds a new Edge between two vertices
	public void addNewEdge(Airports A, Airports B, Route E) {
		this.BaseGraph.addEdge(A, B, E);
	}
	
	// This method allows us to get all the possible ways of getting from one airport to another
	public List<GraphPath<Airports, Route>> AllPosiblePaths(Airports depart, Airports arrival, boolean simplePathOnly, int max){
		AllDirectedPaths<Airports,Route> temp = new AllDirectedPaths<Airports, Route>(this.BaseGraph);
		return temp.getAllPaths(depart, arrival, simplePathOnly, max);
	}
	
	//Implementing Dijkstra's Algorithm
	public GraphPath<Airports, Route> ShortestDijkstra(Airports A, Airports B){
		DijkstraShortestPath<Airports, Route> temp = new DijkstraShortestPath<Airports, Route>(this.BaseGraph);
		return temp.getPath(A, B);
	}
	
	
}
