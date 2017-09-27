package tdenum.graph.graphs.separator_graph.parallel;

import tdenum.graph.data_structures.MinimalSeparator;
import tdenum.graph.data_structures.Node;
import tdenum.graph.data_structures.TdMap;
import tdenum.graph.graphs.IGraph;
import tdenum.graph.graphs.separator_graph.AbstractSeparatorGraph;
import tdenum.graph.graphs.separator_graph.ISeparatorGraph;
import tdenum.graph.separators.IMinimalSeparatorsEnumerator;
import tdenum.graph.separators.parallel.ParallelMinimalSeparatorsEnumerator;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ConcurrentSeparatorsGraph extends AbstractSeparatorGraph {


    AtomicInteger nodesGenerated = new AtomicInteger(0);

    Map<Set<MinimalSeparator>, Boolean> hasEdgeCache = new ConcurrentHashMap<>();


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
