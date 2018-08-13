package tdk_enum.enumerators.independent_set;

import tdk_enum.common.cache.ICache;
import tdk_enum.enumerators.common.IEnumerator;
import tdk_enum.graph.graphs.succinct_graphs.ISuccinctGraphRepresentation;

import java.util.Set;

public interface IMaximalIndependentSetsEnumerator<T> extends IEnumerator<T, Set<T>, ISuccinctGraphRepresentation<T>> {

    //boolean hasNext();
    //Set<T> next();

    //void setResultPrinter(IResultPrinter<Set<T>> resultPrinter);

    //void setGraph(ISuccinctGraphRepresentation<T> graph);

    //void setV(Set<T> v);

    //void setP(Set<Set<T>> p);

   // void setSetsNotExtended(Set<Set<T>> setsNotExtended);

    //void setExtender(IIndependentSetExtender extender);

    //void setScorer(IIndependentSetScorer scorer);

    //void setQ(IWeightedQueue<Set<T>> q);

   // void doFirstStep();

    void setCache(ICache cache);
}
