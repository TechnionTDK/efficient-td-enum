package tdenum.graph.independent_set;

import tdenum.graph.data_structures.*;
import tdenum.graph.graphs.ChordalGraph;
import tdenum.graph.graphs.interfaces.IChordalGraph;
import tdenum.graph.graphs.interfaces.IGraph;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by dvird on 17/07/09.
 */
public class Converter
{

    public static IChordalGraph minimalSeparatorsToTriangulation(final IGraph g,
                                                                 final Set<? extends NodeSet> minimalSeparators)
    {
        IChordalGraph triangulation = new ChordalGraph(g);
        triangulation.saturateNodeSets(minimalSeparators);
        return triangulation;
    }

    public static Set<MinimalSeparator> triangulationToMinimalSeparators(final IGraph g)
    {
        Set<MinimalSeparator> minimalSeparators = new HashSet<>();
        TdMap<Boolean> isVisited = new TdListMap<>(g.getNumberOfNodes(), false);
        IncreasingWeightedNodeQueue queue = new IncreasingWeightedNodeQueue(g.getNodes());
        int previousNumberOfNeighbors = -1;
        while (!queue.isEmpty())
        {
            Node currentNode = queue.top();
            int currentNumberOfNeighbors = queue.getWeight(currentNode);
            queue.pop();
            if (currentNumberOfNeighbors <= previousNumberOfNeighbors)
            {
                NodeSetProducer separatorProducer = new NodeSetProducer(g.getNumberOfNodes());
//                for (Node v : g.getNeighbors(currentNode))
                for (Node v : g.getNeighbors(currentNode))
                {
                    Boolean iv = isVisited.get(v);
                    if (iv != null && iv)
                    {
                        separatorProducer.insert(v);
                    }
                }
                MinimalSeparator currentSeparator = separatorProducer.produce(MinimalSeparator.class);
                if (!currentSeparator.isEmpty())
                {
                    minimalSeparators.add(currentSeparator);
                }
            }
//            for (Node v : g.getNeighbors(currentNode))
            for (Node v : g.getNeighbors(currentNode))
            {
                Boolean iv = isVisited.get(v);
                if (iv != null && !iv)
                {
                    queue.increaseWeight(v);
                }
            }
            isVisited.put(currentNode, true);
            previousNumberOfNeighbors = currentNumberOfNeighbors;
        }
        return minimalSeparators;

    }
}
