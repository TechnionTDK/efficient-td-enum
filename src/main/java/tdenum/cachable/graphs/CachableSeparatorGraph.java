package tdenum.cachable.graphs;

import tdenum.graph.data_structures.MinimalSeparator;
import tdenum.graph.graphs.SeparatorGraph;
import tdenum.graph.graphs.interfaces.IGraph;
import tdenum.graph.independent_set.separators.SeparatorsScoringCriterion;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CachableSeparatorGraph extends SeparatorGraph {
    public CachableSeparatorGraph(IGraph g, SeparatorsScoringCriterion c) {
        super(g, c);
    }


    Map<Set<MinimalSeparator>, Boolean> hasEdgeCache = new HashMap<>();

    @Override
    public boolean hasEdge(MinimalSeparator u, MinimalSeparator v)
    {
        Set<MinimalSeparator> cacheEntry= new HashSet<>();
        cacheEntry.add(u);
        cacheEntry.add(v);
        if(!hasEdgeCache.containsKey(cacheEntry))
        {
            hasEdgeCache.put(cacheEntry, super.hasEdge(u,v));
        }
        return hasEdgeCache.get(cacheEntry);
    }
}
