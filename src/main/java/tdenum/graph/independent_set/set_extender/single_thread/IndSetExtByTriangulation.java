package tdenum.graph.independent_set.set_extender.single_thread;


import tdenum.graph.data_structures.MinimalSeparator;
import tdenum.graph.graphs.Graph;
import tdenum.graph.graphs.chordal_graph.IChordalGraph;
import tdenum.graph.graphs.IGraph;
import tdenum.graph.independent_set.Converter;
import tdenum.graph.independent_set.set_extender.AbstractIndependentSetExtender;
import tdenum.graph.triangulation.minimal_triangulators.MinimalTriangulator;

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
