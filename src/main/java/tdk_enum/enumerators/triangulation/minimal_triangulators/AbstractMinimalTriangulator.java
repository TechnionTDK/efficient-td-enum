package tdk_enum.enumerators.triangulation.minimal_triangulators;

import tdk_enum.common.configuration.config_types.TriangulationAlgorithm;

public abstract class AbstractMinimalTriangulator implements IMinimalTriangulator {


    protected int time = 0;

    TriangulationAlgorithm heuristic;

    @Override
    public void setHeuristic(TriangulationAlgorithm heuristic) {
        this.heuristic = heuristic;
    }


}
