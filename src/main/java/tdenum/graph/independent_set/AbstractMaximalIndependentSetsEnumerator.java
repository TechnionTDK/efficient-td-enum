package tdenum.graph.independent_set;

import tdenum.graph.data_structures.weighted_queue.IWeightedQueue;
import tdenum.graph.graphs.ISuccinctGraphRepresentation;
import tdenum.graph.independent_set.scoring.IIndependentSetScorer;
import tdenum.graph.independent_set.set_extender.IIndependentSetExtender;

import java.util.Set;

public abstract class AbstractMaximalIndependentSetsEnumerator<T> implements IMaximalIndependentSetsEnumerator<T>{

    protected ISuccinctGraphRepresentation<T> graph;
    protected IIndependentSetExtender extender;
    protected IIndependentSetScorer scorer;



    protected IWeightedQueue<Set<T>> Q;


    protected Set<T> V;
    protected Set<Set<T>> P;
    protected Set<Set<T>> setsNotExtended;


    @Override
    public void setGraph(ISuccinctGraphRepresentation<T> graph) {
        this.graph = graph;
    }

    @Override
    public void setV(Set<T> v) {
        V = v;
    }

    @Override
    public void setP(Set<Set<T>> p) {
        P = p;
    }

    @Override
    public void setSetsNotExtended(Set<Set<T>> setsNotExtended) {
        this.setsNotExtended = setsNotExtended;
    }


    @Override
    public void setExtender(IIndependentSetExtender extender) {
        this.extender = extender;
    }

    @Override
    public void setScorer(IIndependentSetScorer scorer) {
        this.scorer = scorer;
    }


    @Override
    public void setQ(IWeightedQueue<Set<T>> q) {
        this.Q = q;
    }


}
