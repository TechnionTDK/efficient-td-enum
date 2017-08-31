package tdenum.parallel.independent_set.triangulation;

import tdenum.graph.graphs.interfaces.IGraph;
import tdenum.graph.independent_set.triangulation.TriangulationAlgorithm;

public abstract class AbstractFreezableMinimalTriangulator implements IFreezableMinimalTriangulator
{
    IGraph g;
    TriangulationAlgorithm heuristic;
}
