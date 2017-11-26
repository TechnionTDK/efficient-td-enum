package tdenum.graph.independent_set;

import tdenum.common.cache.ICache;
import tdenum.graph.data_structures.weighted_queue.IWeightedQueue;
import tdenum.graph.graphs.succinct_graphs.ISuccinctGraphRepresentation;
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


    protected boolean nextSetReady;
    protected Set<T> nextIndependentSet;

    protected ICache jvCache;


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

    @Override
    public void setCache(ICache cache) {
        this.jvCache = cache;
    }

    protected abstract boolean timeLimitReached();

    protected boolean newSetFound(final Set<T> generatedSet)
    {


        if (!P.contains(generatedSet))
        {
            if(setsNotExtended.add(generatedSet))
            {
                Q.setWeight(generatedSet, scorer.scoreIndependentSet(generatedSet));
                nextIndependentSet = generatedSet;
                nextSetReady = true;
                return true;
            }

            return false;
        }

        return false;
    }




}
