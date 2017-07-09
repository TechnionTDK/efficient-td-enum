package tdenum.graph;

import tdenum.graph.data_structures.IncreasingWeightNodeQueue;
import tdenum.graph.data_structures.NodeSetProducer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by dvird on 17/07/09.
 */
public class Converter
{

    public static ChordalGraph minimalSeparatorsToTriangulation(final Graph g,
                                                                final Set<? extends NodeSet> minimalSeparators)
    {
        ChordalGraph triangulation = new ChordalGraph(g);
        triangulation.saturateNodeSets(minimalSeparators);
        return triangulation;
    }

    public static Set<MinimalSeparator> triangulationToMinimalSeparators(final ChordalGraph g)
    {
        Set<MinimalSeparator> minimalSeparators = new HashSet<>();
        Map<Node, Boolean> isVisited = new HashMap<>();
        IncreasingWeightNodeQueue queue = new IncreasingWeightNodeQueue(g.getNumberOfNodes());
        int previousNumberOfNeighbors = -1;
        while (!queue.isEmpty())
        {
            Node currentNode = queue.pop();
            int currentNumberOfNeighbors = queue.getWeight(currentNode);
            if (currentNumberOfNeighbors <= previousNumberOfNeighbors)
            {
                NodeSetProducer sparatorProducer = new NodeSetProducer(g.getNumberOfNodes());
                for (Node v : g.getNeighbors(currentNode))
                {
                    if (isVisited.get(v))
                    {
                        sparatorProducer.insert(v);
                    }
                }
                MinimalSeparator currentSeparator = sparatorProducer.produce();
                if (!currentSeparator.isEmpty())
                {
                    minimalSeparators.add(currentSeparator);
                }
            }
            for (Node v : g.getNeighbors(currentNode))
            {
                if (!isVisited.get(v))
                {
                    queue.increaseWeight(v);
                }
            }
            isVisited.put(currentNode, true);
        }
        return minimalSeparators;

    }
}
