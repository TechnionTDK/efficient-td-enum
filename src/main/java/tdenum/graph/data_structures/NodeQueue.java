package tdenum.graph.data_structures;

import tdenum.graph.graphs.interfaces.IGraph;
import tdenum.graph.independent_set.triangulation.TriangulationAlgorithm;

import static tdenum.graph.independent_set.triangulation.TriangulationAlgorithm.*;

/**
 * Created by dvir.dukhan on 7/11/2017.
 */
public class NodeQueue {
    IGraph graph;
    TriangulationAlgorithm heuristic;
    WeightedNodeQueue queue;

    public NodeQueue(IGraph g, TriangulationAlgorithm h) {
        graph = g;
        heuristic = h;
        queue = new WeightedNodeQueue(g.getNodes());
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
        final NodeSet neighborsSet = g.getNeighbors(v);
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
        Node top = queue.top();
        if (heuristic ==MIN_DEGREE_LB_TRIANG || heuristic == MIN_FILL_LB_TRIANG || heuristic == COMBINED)
        {
            int savedScore = queue.getWeight(top);
            int currentScore = score(top);
            while(currentScore > savedScore)
            {
                queue.setWeight(top, currentScore);
                top = queue.top();
                savedScore = queue.getWeight(top);
                currentScore = score(top);
            }
        }
        return queue.pop();
    }

    public boolean isEmpty()
    {
        return queue.isEmpty();
    }
}
