package tdk_enum.graph.triangulation.parallel.freezable;

import tdk_enum.graph.graphs.chordal_graph.IChordalGraph;
import tdk_enum.graph.graphs.IGraph;
import tdk_enum.graph.triangulation.minimal_triangulators.TriangulationAlgorithm;

import java.util.concurrent.Callable;

import static tdk_enum.graph.triangulation.minimal_triangulators.TriangulationAlgorithm.COMBINED;
import static tdk_enum.graph.triangulation.minimal_triangulators.TriangulationAlgorithm.MCS_M;


public class FreezableMinimalTriangulator implements Callable<IChordalGraph> {




    private TriangulationAlgorithm heuristic;
    int time = 0;
    IFreezableMinimalTriangulator triangulator;




    public FreezableMinimalTriangulator (TriangulationAlgorithm h)
    {
        heuristic = h;
    }

    public void setGraph(IGraph graph)
    {
        time++;
        if(heuristic == MCS_M || (heuristic == COMBINED && time % 2 ==0))
        {
            triangulator = new McsmFreezableMinimalTriangulator();

        }
        else
        {
            triangulator = new LBTriangFreezableMinimalTriangulator(heuristic);
        }
        triangulator.setGraph(graph);

    }


    @Override
    public IChordalGraph call() {

        return triangulator.continueTriangulate();

    }

}
