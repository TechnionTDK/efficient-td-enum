package tdk_enum.graph.data_structures;

import tdk_enum.graph.data_structures.weighted_queue.single_thread.IncreasingWeightedNodeQueue;
import tdk_enum.graph.graphs.IGraph;
import tdk_enum.graph.triangulation.minimal_triangulators.TriangulationAlgorithm;

import java.util.Set;

import static tdk_enum.graph.triangulation.minimal_triangulators.TriangulationAlgorithm.*;

/**
 * Created by dvir.dukhan on 7/11/2017.
 */
public class NodeQueue {
    IGraph graph;
    TriangulationAlgorithm heuristic;
    IncreasingWeightedNodeQueue queue;

    public NodeQueue(IGraph g, TriangulationAlgorithm h) {
        graph = g;
        heuristic = h;
        queue = new IncreasingWeightedNodeQueue(g.getNodes());
    }


    int score(Node v)
    {
        if (heuristic == MIN_DEGREE_LB_TRIANG || heuristic == INITIAL_DEGREE_LB_TRIANG) {
            return graph.getNeighbors(v).size();
        } else if (heuristic == MIN_FILL_LB_TRIANG || heuristic == INITIAL_FILL_LB_TRIANG || heuristic == COMBINED) {

            return getFill(graph, v);
        }
        return 0;
    }


    int getFill(final IGraph g, Node v)
    {
//        final NodeSet neighborsSet = g.getNeighborsCopy(v);
//        final NodeSet neighborsSet = g.getNeighbors(v);
        final Set<Node> neighborsSet = g.getNeighbors(v);
        int twiceFillEdges = 0;
        TdMap<Boolean> notNeighborsOfCurrentNode = g.getNeighborsMap(v);
        for (Node u : neighborsSet)
        {

            notNeighborsOfCurrentNode.put(u, false);
        }
        for(Node u : g.getNodes())
        {
            if (notNeighborsOfCurrentNode.get(u))
            {
                twiceFillEdges++;
            }
        }
        return twiceFillEdges/2;
    }

    public Node pop()
    {
        Node top = queue.peek();
        if (heuristic ==MIN_DEGREE_LB_TRIANG || heuristic == MIN_FILL_LB_TRIANG || heuristic == COMBINED)
        {
            int savedScore = queue.getWeight(top);
            int currentScore = score(top);
            while(currentScore > savedScore)
            {
                queue.setWeight(top, currentScore);
                top = queue.peek();
                savedScore = queue.getWeight(top);
                currentScore = score(top);
            }
        }
        return queue.poll();
    }

    public boolean isEmpty()
    {
        return queue.isEmpty();
    }
}
