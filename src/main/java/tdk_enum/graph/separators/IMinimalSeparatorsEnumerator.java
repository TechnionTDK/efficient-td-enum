package tdk_enum.graph.separators;

import tdk_enum.enumerators.IEnumerator;
import tdk_enum.graph.data_structures.MinimalSeparator;
import tdk_enum.graph.data_structures.Node;
import tdk_enum.graph.data_structures.NodeSet;
import tdk_enum.graph.data_structures.weighted_queue.IWeightedQueue;
import tdk_enum.graph.graphs.IGraph;
import tdk_enum.graph.separators.scorer.ISeparatorScorer;

import java.util.Set;

public interface IMinimalSeparatorsEnumerator   extends IEnumerator <tdk_enum.graph.data_structures.Node, tdk_enum.graph.data_structures.MinimalSeparator, tdk_enum.graph.graphs.IGraph> {

   // MinimalSeparator next();
    //boolean hasNext();


    //<T extends NodeSet> void minimalSeparatorFound(final T s);


    //void setGraph(IGraph graph);

    void setScorer(ISeparatorScorer scorer);

    //void setSeparatorsToExtend(IWeightedQueue separatorsToExtend);

   // void setSeparatorsExtended(Set<NodeSet> separatorsExtended);

   // void init();
}
