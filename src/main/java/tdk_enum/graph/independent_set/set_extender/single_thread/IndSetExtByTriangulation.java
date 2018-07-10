package tdk_enum.graph.independent_set.set_extender.single_thread;


import tdk_enum.graph.data_structures.MinimalSeparator;
import tdk_enum.graph.graphs.Graph;
import tdk_enum.graph.graphs.chordal_graph.IChordalGraph;
import tdk_enum.graph.graphs.IGraph;
import tdk_enum.graph.graphs.Converter;
import tdk_enum.graph.independent_set.set_extender.AbstractIndependentSetExtender;
import tdk_enum.graph.triangulation.minimal_triangulators.MinimalTriangulator;

import java.util.Set;

/**
 * Created by dvir.dukhan on 7/11/2017.
 */
public class IndSetExtByTriangulation extends AbstractIndependentSetExtender {



    public IndSetExtByTriangulation()
    {

    }


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
