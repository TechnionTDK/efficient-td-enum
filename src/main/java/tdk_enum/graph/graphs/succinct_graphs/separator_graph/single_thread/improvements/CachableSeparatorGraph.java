package tdk_enum.graph.graphs.succinct_graphs.separator_graph.single_thread.improvements;

import tdk_enum.graph.data_structures.MinimalSeparator;
import tdk_enum.graph.graphs.succinct_graphs.separator_graph.single_thread.SeparatorGraph;
import tdk_enum.graph.graphs.IGraph;
import tdk_enum.common.configuration.config_types.SeparatorsScoringCriterion;

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
