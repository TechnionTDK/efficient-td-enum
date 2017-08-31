package tdenum.parallel.independent_set.triangulation;

import tdenum.graph.graphs.interfaces.IChordalGraph;
import tdenum.graph.graphs.interfaces.IGraph;

public interface IFreezableMinimalTriangulator {


    IChordalGraph triangulate();
    IChordalGraph continueTriangulate();
    void setGraph(IGraph graph);
}
