package tdenum.parallel.independent_set.sparators;

import tdenum.graph.data_structures.MinimalSeparator;
import tdenum.graph.data_structures.Node;
import tdenum.graph.data_structures.NodeSet;
import tdenum.graph.graphs.interfaces.IGraph;
import tdenum.graph.independent_set.separators.IMinimalSeparatorsEnumerator;
import tdenum.graph.independent_set.separators.SeparatorScorer;
import tdenum.parallel.data_structures.ConcurrentQueueSet;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ParallelMinimalSeparatorsEnumerator implements IMinimalSeparatorsEnumerator {


    IGraph graph;

    Set<NodeSet> separatorsExtended = ConcurrentHashMap.newKeySet();
    ConcurrentQueueSet<NodeSet> separatorsToExtend = new ConcurrentQueueSet<>();


    public ParallelMinimalSeparatorsEnumerator(IGraph graph) {
        this.graph = graph;

        for (Node v : this.graph.getNodes())
        {
            Set<Node> vAndNeighbors = graph.getNeighborsCopy(v);
            vAndNeighbors.add(v);
            for (NodeSet nodeSet : graph.getComponents(vAndNeighbors))
            {
                NodeSet potentialSeparator = graph.getNeighbors(nodeSet);
                if (potentialSeparator.size() >0)
                {
                    separatorsToExtend.add(potentialSeparator);
                }
            }
        }
    }

    @Override
    public MinimalSeparator next() {
        if (!hasNext())
        {
            return new MinimalSeparator();
        }
        MinimalSeparator s =  new MinimalSeparator((NodeSet) separatorsToExtend.poll());
        separatorsExtended.add(s);
        for (Node x : s)
        {
            Set<Node> xNeighborsAndS = graph.getNeighborsCopy(x);
            xNeighborsAndS.addAll(s);
            for (NodeSet nodeSet : graph.getComponents(xNeighborsAndS))
            {
                minimalSeparatorFound(graph.getNeighbors(nodeSet));
            }
        }

        return s;
    }

    @Override
    public synchronized boolean hasNext() {
        return !separatorsToExtend.isEmpty();
    }

    @Override
    public synchronized  <T extends NodeSet> void minimalSeparatorFound(T s)
    {
        if (s.size() > 0 && !separatorsExtended.contains(s))
        {
            separatorsToExtend.add(s);
        }
    }
}
