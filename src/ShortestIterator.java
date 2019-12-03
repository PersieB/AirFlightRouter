import org.jgrapht.*;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm.*;
import org.jgrapht.alg.shortestpath.TreeSingleSourcePathsImpl;
import org.jgrapht.alg.util.*;
import org.jgrapht.util.*;

import java.util.*;
public class ShortestIterator implements Iterator<Airports> {
	private final Graph<Airports, Route> graph;
    private final Airports source;
    private final double radius;
    private final FibonacciHeap<QueueEntry> heap;
    private final Map<Airports, FibonacciHeapNode<QueueEntry>> seen;
    
    public ShortestIterator(Graph<Airports, Route> graph, Airports source, double radius)
    {
        this.radius = radius;
        this.source = source;
        this.graph = graph;
        this.heap = new FibonacciHeap<>();
        this.seen = new HashMap<>();

        // initialize with source vertex
        updateDistance(source, null, 0d);
    }
    
    public boolean hasNext()
    {
        if (heap.isEmpty()) {
            return false;
        }
        FibonacciHeapNode<QueueEntry> currentNode = heap.min();
        double vDistance = currentNode.getKey();
        if (radius < vDistance) {
            heap.clear();
            return false;
        }
        return true;
    }
    
    @Override
    public Airports next()
    {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        // settle next node
        FibonacciHeapNode<QueueEntry> currentNode = heap.removeMin();
        Airports v = currentNode.getData().v;
        double vDistance = currentNode.getKey();

        // relax edges
        for (Route e : graph.outgoingEdgesOf(v)) {
            Airports u = Graphs.getOppositeVertex(graph, e, v);
            double eWeight = graph.getEdgeWeight(e);
            updateDistance(u, e, vDistance + eWeight);
        }

        return v;
    }
    
    public SingleSourcePaths<Airports, Route> getPaths()
    {
        return new TreeSingleSourcePathsImpl<>(graph, source, getDistanceAndPredecessorMap());
    }
    
    public Map<Airports, Pair<Double, Route>> getDistanceAndPredecessorMap()
    {
        Map<Airports, Pair<Double, Route>> distanceAndPredecessorMap = new HashMap<>();

        for (FibonacciHeapNode<QueueEntry> vNode : seen.values()) {
            double vDistance = vNode.getKey();
            if (radius < vDistance) {
                continue;
            }
            Airports v = vNode.getData().v;
            distanceAndPredecessorMap.put(v, Pair.of(vDistance, vNode.getData().e));
        }

        return distanceAndPredecessorMap;
    }
    
    
    
    private void updateDistance(Airports v, Route e, double distance)
    {
        FibonacciHeapNode<QueueEntry> node = seen.get(v);
        if (node == null) {
            node = new FibonacciHeapNode<>(new QueueEntry(e, v));
            heap.insert(node, distance);
            seen.put(v, node);
        } else if (distance < node.getKey()) {
            heap.decreaseKey(node, distance);
            node.getData().e = e;
        }
    }
    
    
    class QueueEntry
    {
        Route e;
        Airports v;

        public QueueEntry(Route e, Airports v)
        {
            this.e = e;
            this.v = v;
        }
    }
    
    public GraphPath<Airports, Route> getFinalPath(Airports source, Airports sink)
    {
		ShortestIterator temp = new ShortestIterator(graph, source, Double.POSITIVE_INFINITY);
        while (temp.hasNext()) {
            Airports vertex = temp.next();
            if (vertex.equals(sink)) {
                break;
            }
        }

        return temp.getPaths().getPath(sink);
    }
}
