package tdk_enum.graph.graphs.succinct_graphs;

import tdk_enum.graph.graphs.IGraph;
import tdk_enum.graph.separators.IMinimalSeparatorsEnumerator;

import java.util.Set;

/**
 * Created by dvird on 17/07/10.
 */
public interface ISuccinctGraphRepresentation<T>
{
    boolean hasNextNode();
    T nextNode();
    Set<T> nextBatch();
    boolean hasEdge(final T u, final  T v);
    int getNumberOfNodesGenerated();
    void setGraph(IGraph graph);

    void setNodesEnumerator(IMinimalSeparatorsEnumerator nodesEnumerator);

    Set<T> nextBatch(long id);

    boolean hasNextNode(long id);
}
