package tdenum.parallel.independent_set.triangulation;

import tdenum.graph.data_structures.*;
import tdenum.graph.graphs.ChordalGraph;
import tdenum.graph.graphs.interfaces.IChordalGraph;
import tdenum.graph.graphs.interfaces.IGraph;
import tdenum.graph.independent_set.triangulation.TriangulationAlgorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import static tdenum.graph.independent_set.triangulation.TriangulationAlgorithm.COMBINED;
import static tdenum.graph.independent_set.triangulation.TriangulationAlgorithm.MCS_M;


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
            triangulator = new LBTriangFreezableMinimalTriangulator();
        }
        triangulator.setGraph(graph);

    }


    @Override
    public IChordalGraph call() {

        return triangulator.continueTriangulate();

    }

}
