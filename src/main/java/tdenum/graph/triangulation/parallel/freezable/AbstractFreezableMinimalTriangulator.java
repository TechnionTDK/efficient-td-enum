package tdenum.graph.triangulation.parallel.freezable;

import tdenum.graph.graphs.IGraph;
import tdenum.graph.triangulation.minimal_triangulators.TriangulationAlgorithm;

public abstract class AbstractFreezableMinimalTriangulator implements IFreezableMinimalTriangulator
{
    IGraph g;
    TriangulationAlgorithm heuristic;
}
