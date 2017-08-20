package tdenum.graph.independent_set;

import tdenum.graph.data_structures.MinimalSeparator;
import tdenum.graph.graphs.Graph;
import tdenum.graph.graphs.interfaces.IChordalGraph;
import tdenum.graph.graphs.interfaces.IGraph;
import tdenum.graph.independent_set.interfaces.IIndependentSetExtender;
import tdenum.graph.independent_set.triangulation.MinimalTriangulator;

import java.util.Set;

/**
 * Created by dvir.dukhan on 7/11/2017.
 */
public class IndSetExtByTriangulation implements IIndependentSetExtender<MinimalSeparator> {


    IGraph graph;
    MinimalTriangulator triangulator;

    public IndSetExtByTriangulation(final IGraph g, final MinimalTriangulator t)
    {
        this.graph = g;
        this.triangulator = t;
    }

    @Override
    public Set<MinimalSeparator> extendToMaxIndependentSet(Set<MinimalSeparator> s) {
        IGraph saturatedGraph = new Graph(graph);
        saturatedGraph.saturateNodeSets(s);
        IChordalGraph minimalTriangulation = triangulator.triangulate(saturatedGraph);
        Set<MinimalSeparator> minimalSeparators = Converter.triangulationToMinimalSeparators(minimalTriangulation);
        return minimalSeparators;

    }
}
