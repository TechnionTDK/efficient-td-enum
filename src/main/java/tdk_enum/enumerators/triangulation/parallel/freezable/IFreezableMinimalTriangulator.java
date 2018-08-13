package tdk_enum.enumerators.triangulation.parallel.freezable;

import tdk_enum.graph.graphs.chordal_graph.IChordalGraph;
import tdk_enum.graph.graphs.IGraph;

public interface IFreezableMinimalTriangulator {


    IChordalGraph triangulate();
    IChordalGraph continueTriangulate();
    void setGraph(IGraph graph);
}
