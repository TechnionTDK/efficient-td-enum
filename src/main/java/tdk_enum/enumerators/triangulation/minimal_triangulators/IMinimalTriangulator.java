package tdk_enum.enumerators.triangulation.minimal_triangulators;

import tdk_enum.common.configuration.config_types.TriangulationAlgorithm;
import tdk_enum.graph.graphs.chordal_graph.IChordalGraph;
import tdk_enum.graph.graphs.IGraph;

public interface IMinimalTriangulator {
    IChordalGraph triangulate(final IGraph g);

    void setHeuristic(TriangulationAlgorithm heuristic);
}
