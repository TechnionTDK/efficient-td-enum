package tdenum.graph.independent_set.set_extender;

import tdenum.graph.graphs.IGraph;
import tdenum.graph.triangulation.minimal_triangulators.IMinimalTriangulator;
import tdenum.graph.triangulation.minimal_triangulators.MinimalTriangulator;

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
