package tdenum.graph.interfaces;

/**
 * Created by dvird on 17/07/10.
 */
public interface ISuccinctGraphRepresentation<T>
{
    boolean hasNext();
    T nextNode();
    boolean hasEdge(final T u, final  T v);
}
