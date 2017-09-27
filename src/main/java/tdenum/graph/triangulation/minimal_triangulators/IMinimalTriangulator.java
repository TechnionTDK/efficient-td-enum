package tdenum.graph.triangulation.minimal_triangulators;

import tdenum.graph.graphs.chordal_graph.IChordalGraph;
import tdenum.graph.graphs.IGraph;

public interface IMinimalTriangulator {
    IChordalGraph triangulate(final IGraph g);

    void setHeuristic(TriangulationAlgorithm heuristic);
}
