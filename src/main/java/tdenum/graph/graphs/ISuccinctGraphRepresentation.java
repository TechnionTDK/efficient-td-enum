package tdenum.graph.graphs;

/**
 * Created by dvird on 17/07/10.
 */
public interface ISuccinctGraphRepresentation<T>
{
    boolean hasNextNode();
    T nextNode();
    boolean hasEdge(final T u, final  T v);
}
