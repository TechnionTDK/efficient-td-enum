package tdenum.graph.independent_set.set_extender;

import tdenum.graph.data_structures.MinimalSeparator;
import tdenum.graph.graphs.IGraph;
import tdenum.graph.triangulation.minimal_triangulators.IMinimalTriangulator;


public abstract class AbstractIndependentSetExtender implements IIndependentSetExtender<MinimalSeparator> {

    protected IGraph graph;
    protected IMinimalTriangulator triangulator;

    @Override
    public void setGraph(IGraph graph) {
        this.graph = graph;
    }

    @Override
    public void setTriangulator(IMinimalTriangulator triangulator) {
        this.triangulator = triangulator;
    }
}
