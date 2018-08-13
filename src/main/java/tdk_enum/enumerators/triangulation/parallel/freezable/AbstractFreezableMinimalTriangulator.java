package tdk_enum.enumerators.triangulation.parallel.freezable;

import tdk_enum.graph.graphs.IGraph;
import tdk_enum.common.configuration.config_types.TriangulationAlgorithm;

public abstract class AbstractFreezableMinimalTriangulator implements IFreezableMinimalTriangulator
{
    IGraph g;
    TriangulationAlgorithm heuristic;
}
