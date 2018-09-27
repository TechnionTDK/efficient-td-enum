package tdk_enum.enumerators.independent_set.set_extender;

import tdk_enum.graph.data_structures.MinimalSeparator;
import tdk_enum.graph.graphs.IGraph;
import tdk_enum.graph.graphs.succinct_graphs.ISuccinctGraphRepresentation;
import tdk_enum.enumerators.triangulation.minimal_triangulators.IMinimalTriangulator;

import java.util.Set;


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


    @Override
    public Set<MinimalSeparator> generateNew(ISuccinctGraphRepresentation graph, Set<MinimalSeparator> B) {
        return extendToMaxIndependentSet(B, graph.getGraph());
    }



    @Override
    public Set<MinimalSeparator> generateNew(Set<MinimalSeparator> B) {
        return extendToMaxIndependentSet(B, graph);
    }
}
