package tdk_enum.enumerators.separators;

import tdk_enum.enumerators.common.IEnumerator;
import tdk_enum.enumerators.separators.scorer.ISeparatorScorer;

public interface IMinimalSeparatorsEnumerator   extends IEnumerator <tdk_enum.graph.data_structures.Node, tdk_enum.graph.data_structures.MinimalSeparator, tdk_enum.graph.graphs.IGraph> {

   // MinimalSeparator next();
    //boolean hasNext();


    //<T extends NodeSet> void minimalSeparatorFound(final T s);


    //void setGraph(IGraph graph);

    void setScorer(ISeparatorScorer scorer);

    //void setSeparatorsToExtend(IWeightedQueue separatorsToExtend);

   // void setSeparatorsExtended(Set<NodeSet> separatorsExtended);

   // void init();


    int numberOfSeparators();
}
