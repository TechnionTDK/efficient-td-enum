package tdk_enum.graph.separators;

import tdk_enum.graph.data_structures.NodeSet;
import tdk_enum.graph.data_structures.weighted_queue.IWeightedQueue;
import tdk_enum.graph.graphs.IGraph;
import tdk_enum.graph.separators.scorer.ISeparatorScorer;

import java.util.Set;

public abstract class AbstractMinimalSeparatorsEnumerator implements IMinimalSeparatorsEnumerator {

    protected IGraph graph;
    protected ISeparatorScorer scorer;
    protected IWeightedQueue separatorsToExtend;
    protected Set<NodeSet> separatorsExtended ;


    @Override
    public void setGraph(IGraph graph) {
        this.graph = graph;
    }

    @Override
    public void setScorer(ISeparatorScorer scorer) {
        this.scorer = scorer;
    }

    @Override
    public void setSeparatorsToExtend(IWeightedQueue separatorsToExtend) {
        this.separatorsToExtend = separatorsToExtend;
    }

    @Override
    public void setSeparatorsExtended(Set<NodeSet> separatorsExtended) {
        this.separatorsExtended = separatorsExtended;
    }
}
