package tdk_enum.graph.triangulation.minimal_triangulators;

import tdk_enum.graph.graphs.chordal_graph.IChordalGraph;
import tdk_enum.graph.graphs.IGraph;

public interface IMinimalTriangulator {
    IChordalGraph triangulate(final IGraph g);

    void setHeuristic(TriangulationAlgorithm heuristic);
}
