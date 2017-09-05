package tdenum.loggable.independent_set;

import tdenum.common.IO.Logger;
import tdenum.graph.data_structures.MinimalSeparator;
import tdenum.graph.graphs.Graph;
import tdenum.graph.graphs.interfaces.IChordalGraph;
import tdenum.graph.graphs.interfaces.IGraph;
import tdenum.graph.independent_set.Converter;
import tdenum.graph.independent_set.IndSetExtByTriangulation;
import tdenum.graph.independent_set.triangulation.MinimalTriangulator;

import java.util.Set;

public class LoggableIndSetExtByTriangulation extends IndSetExtByTriangulation {
    public LoggableIndSetExtByTriangulation(IGraph g, MinimalTriangulator t) {
        super(g, t);
    }

    @Override
    public Set<MinimalSeparator> extendToMaxIndependentSet(Set<MinimalSeparator> s) {
        Logger.startExtendCall();
        IGraph saturatedGraph = new Graph(graph);
        saturatedGraph.saturateNodeSets(s);

        Logger.addSaturatedGraph(saturatedGraph, s);
        IChordalGraph minimalTriangulation = triangulator.triangulate(saturatedGraph);
        Set<MinimalSeparator> minimalSeparators = Converter.triangulationToMinimalSeparators(minimalTriangulation);
        Logger.finishExtendCall();
        return minimalSeparators;

    }
}
