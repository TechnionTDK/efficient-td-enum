package tdenum.graph.triangulation.parallel.freezable;

import tdenum.graph.graphs.chordal_graph.IChordalGraph;
import tdenum.graph.graphs.IGraph;

public interface IFreezableMinimalTriangulator {


    IChordalGraph triangulate();
    IChordalGraph continueTriangulate();
    void setGraph(IGraph graph);
}
