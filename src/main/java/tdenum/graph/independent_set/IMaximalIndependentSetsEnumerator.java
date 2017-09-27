package tdenum.graph.independent_set;

import tdenum.graph.data_structures.weighted_queue.IWeightedQueue;
import tdenum.graph.graphs.ISuccinctGraphRepresentation;
import tdenum.graph.independent_set.scoring.IIndependentSetScorer;
import tdenum.graph.independent_set.set_extender.IIndependentSetExtender;

import java.util.Set;

public interface IMaximalIndependentSetsEnumerator<T> {

    boolean hasNext();
    Set<T> next();

    void setGraph(ISuccinctGraphRepresentation<T> graph);

    void setV(Set<T> v);

    void setP(Set<Set<T>> p);

    void setSetsNotExtended(Set<Set<T>> setsNotExtended);

    void setExtender(IIndependentSetExtender extender);

    void setScorer(IIndependentSetScorer scorer);

    void setQ(IWeightedQueue<Set<T>> q);

    void doFirstStep();
}
