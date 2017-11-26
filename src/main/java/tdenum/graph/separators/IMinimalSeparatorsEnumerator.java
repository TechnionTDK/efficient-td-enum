package tdenum.graph.separators;

import tdenum.graph.data_structures.MinimalSeparator;
import tdenum.graph.data_structures.NodeSet;
import tdenum.graph.data_structures.weighted_queue.IWeightedQueue;
import tdenum.graph.graphs.IGraph;
import tdenum.graph.separators.scorer.ISeparatorScorer;

import java.util.Set;

public interface IMinimalSeparatorsEnumerator {

    MinimalSeparator next();
    boolean hasNext();


    <T extends NodeSet> void minimalSeparatorFound(final T s);


    void setGraph(IGraph graph);

    void setScorer(ISeparatorScorer scorer);

    void setSeparatorsToExtend(IWeightedQueue separatorsToExtend);

    void setSeparatorsExtended(Set<NodeSet> separatorsExtended);

    void init();
}
