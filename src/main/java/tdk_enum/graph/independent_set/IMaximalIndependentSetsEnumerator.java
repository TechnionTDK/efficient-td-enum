package tdk_enum.graph.independent_set;

import tdk_enum.common.IO.result_printer.IResultPrinter;
import tdk_enum.common.cache.ICache;
import tdk_enum.graph.data_structures.weighted_queue.IWeightedQueue;
import tdk_enum.graph.graphs.succinct_graphs.ISuccinctGraphRepresentation;
import tdk_enum.graph.independent_set.scoring.IIndependentSetScorer;
import tdk_enum.graph.independent_set.set_extender.IIndependentSetExtender;

import java.util.Set;

public interface IMaximalIndependentSetsEnumerator<T> {

    boolean hasNext();
    Set<T> next();

    void setResultPrinter(IResultPrinter<Set<T>> resultPrinter);

    void setGraph(ISuccinctGraphRepresentation<T> graph);

    void setV(Set<T> v);

    void setP(Set<Set<T>> p);

    void setSetsNotExtended(Set<Set<T>> setsNotExtended);

    void setExtender(IIndependentSetExtender extender);

    void setScorer(IIndependentSetScorer scorer);

    void setQ(IWeightedQueue<Set<T>> q);

    void doFirstStep();

    void setCache(ICache cache);
}
