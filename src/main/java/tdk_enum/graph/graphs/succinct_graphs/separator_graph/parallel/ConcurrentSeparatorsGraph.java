package tdk_enum.graph.graphs.succinct_graphs.separator_graph.parallel;

import tdk_enum.graph.data_structures.MinimalSeparator;
import tdk_enum.graph.data_structures.Node;
import tdk_enum.graph.data_structures.TdMap;
import tdk_enum.graph.graphs.succinct_graphs.separator_graph.AbstractSeparatorGraph;

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
        return  nodesGenerated.intValue();
    }

    @Override
    public Set<MinimalSeparator> nextBatch(long id) {
        nodesGenerated.incrementAndGet();
        Set<MinimalSeparator> result = new HashSet<>();
        result.add(nodesEnumerator.next());
        return result;
    }

    @Override
    public boolean hasNextNode(long id) {
        return hasNextNode();
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
    public Set<MinimalSeparator> nextBatch() {
        nodesGenerated.incrementAndGet();
        Set<MinimalSeparator> result = new HashSet<>();
        result.add(nodesEnumerator.next());
        return result;
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
