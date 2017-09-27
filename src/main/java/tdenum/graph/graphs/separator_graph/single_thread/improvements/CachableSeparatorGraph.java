package tdenum.graph.graphs.separator_graph.single_thread.improvements;

import tdenum.graph.data_structures.MinimalSeparator;
import tdenum.graph.graphs.separator_graph.single_thread.SeparatorGraph;
import tdenum.graph.graphs.IGraph;
import tdenum.graph.separators.SeparatorsScoringCriterion;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CachableSeparatorGraph extends SeparatorGraph {

    public CachableSeparatorGraph()
    {

    }

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
