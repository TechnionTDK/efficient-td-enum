package tdk_enum.graph.triangulation.parallel.freezable;

import tdk_enum.graph.graphs.IGraph;
import tdk_enum.graph.triangulation.minimal_triangulators.TriangulationAlgorithm;

public abstract class AbstractFreezableMinimalTriangulator implements IFreezableMinimalTriangulator
{
    IGraph g;
    TriangulationAlgorithm heuristic;
}
