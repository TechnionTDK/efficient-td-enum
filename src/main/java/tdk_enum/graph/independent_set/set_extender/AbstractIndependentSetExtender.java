package tdk_enum.graph.independent_set.set_extender;

import tdk_enum.graph.data_structures.MinimalSeparator;
import tdk_enum.graph.graphs.IGraph;
import tdk_enum.graph.triangulation.minimal_triangulators.IMinimalTriangulator;


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
