package tdk_enum.graph.independent_set.set_extender;

import tdk_enum.graph.graphs.IGraph;
import tdk_enum.graph.triangulation.minimal_triangulators.IMinimalTriangulator;

import java.util.Set;

/**
 * Created by dvird on 17/07/10.
 */
public interface IIndependentSetExtender<T>
{
    Set<T> extendToMaxIndependentSet(final Set<T> s);

    void setGraph(IGraph graph);

    void setTriangulator(IMinimalTriangulator triangulator);
}
