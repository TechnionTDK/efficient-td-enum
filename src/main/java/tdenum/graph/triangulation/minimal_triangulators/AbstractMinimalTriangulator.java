package tdenum.graph.triangulation.minimal_triangulators;

public abstract class AbstractMinimalTriangulator implements IMinimalTriangulator {


    protected int time = 0;

    TriangulationAlgorithm heuristic;

    @Override
    public void setHeuristic(TriangulationAlgorithm heuristic) {
        this.heuristic = heuristic;
    }


}
