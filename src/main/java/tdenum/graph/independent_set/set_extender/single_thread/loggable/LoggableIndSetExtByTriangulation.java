package tdenum.graph.independent_set.set_extender.single_thread.loggable;

import tdenum.common.IO.logger.Logger;
import tdenum.graph.data_structures.MinimalSeparator;
import tdenum.graph.graphs.Graph;
import tdenum.graph.graphs.chordal_graph.IChordalGraph;
import tdenum.graph.graphs.IGraph;
import tdenum.graph.independent_set.Converter;
import tdenum.graph.independent_set.set_extender.single_thread.IndSetExtByTriangulation;
import tdenum.graph.triangulation.minimal_triangulators.MinimalTriangulator;

import java.util.Set;

public class LoggableIndSetExtByTriangulation extends IndSetExtByTriangulation {

    public LoggableIndSetExtByTriangulation()
    {

    }

    public LoggableIndSetExtByTriangulation(IGraph g, MinimalTriangulator t) {
        super(g, t);
    }

    @Override
    public Set<MinimalSeparator> extendToMaxIndependentSet(Set<MinimalSeparator> s) {

        IGraph saturatedGraph = new Graph(graph);
        saturatedGraph.saturateNodeSets(s);

        Logger.addSaturatedGraph(saturatedGraph, s);
        IChordalGraph minimalTriangulation = triangulator.triangulate(saturatedGraph);
        Set<MinimalSeparator> minimalSeparators = Converter.triangulationToMinimalSeparators(minimalTriangulation);

        return minimalSeparators;

    }
}
