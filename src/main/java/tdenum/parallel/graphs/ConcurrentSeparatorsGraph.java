package tdenum.parallel.graphs;

import tdenum.graph.data_structures.MinimalSeparator;
import tdenum.graph.data_structures.Node;
import tdenum.graph.data_structures.TdMap;
import tdenum.graph.graphs.interfaces.IGraph;
import tdenum.graph.graphs.interfaces.ISeparatorGraph;
import tdenum.graph.independent_set.separators.IMinimalSeparatorsEnumerator;
import tdenum.graph.independent_set.separators.SeparatorsScoringCriterion;
import tdenum.parallel.independent_set.sparators.ParallelMinimalSeparatorsEnumerator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ConcurrentSeparatorsGraph implements ISeparatorGraph {

    IGraph graph;
    AtomicInteger nodesGenerated = new AtomicInteger(0);
    IMinimalSeparatorsEnumerator nodesEnumerator = new ParallelMinimalSeparatorsEnumerator(graph);
    Map<Set<MinimalSeparator>, Boolean> hasEdgeCache = new ConcurrentHashMap<>();

    public ConcurrentSeparatorsGraph(final IGraph g)
    {
        graph = g;
    }

    @Override
    public int getNumberOfNodesGenerated() {
        return  nodesGenerated.get();
    }

    @Override
    public boolean hasNextNode() {
        return nodesEnumerator.hasNext();
    }

    @Override
    public MinimalSeparator nextNode() {

        nodesGenerated.incrementAndGet();
        return nodesEnumerator.next();
    }

    @Override
    public boolean hasEdge(MinimalSeparator u, MinimalSeparator v) {

        Set<MinimalSeparator> cacheEntry= new HashSet<>();
        cacheEntry.add(u);
        cacheEntry.add(v);
        if(!hasEdgeCache.containsKey(cacheEntry))
        {
            hasEdgeCache.put(cacheEntry, calculateHasEdge(u,v));
        }
        return hasEdgeCache.get(cacheEntry);
    }

    boolean calculateHasEdge(MinimalSeparator u, MinimalSeparator v)
    {
        TdMap<Integer> componentMap = graph.getComponentsMap(u);
        int componentContainingV = 0;
        for (Node n : v)
        {
            int componentContainingCurrentNode = componentMap.get(n);
            if (componentContainingV == componentContainingCurrentNode)
            {
                continue;
            }
            else if (componentContainingCurrentNode == -1)
            {
                continue;
            }
            else if (componentContainingV == 0)
            {
                componentContainingV = componentContainingCurrentNode;
                continue;
            }
            else
            {
                return true;
            }
        }
        return false;
    }
}
