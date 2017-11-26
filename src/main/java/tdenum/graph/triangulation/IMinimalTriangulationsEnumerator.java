package tdenum.graph.triangulation;

import tdenum.graph.graphs.IGraph;
import tdenum.graph.graphs.succinct_graphs.ISuccinctGraphRepresentation;
import tdenum.graph.graphs.chordal_graph.IChordalGraph;
import tdenum.graph.independent_set.IMaximalIndependentSetsEnumerator;

public interface IMinimalTriangulationsEnumerator  {
    void setGraph(IGraph graph);

    void setSeparatorGraph(ISuccinctGraphRepresentation separatorGraph);

    void setSetsEnumerator(IMaximalIndependentSetsEnumerator setsEnumerator);


    boolean hasNext();

    IChordalGraph next();

    int getNumberOfMinimalSeperatorsGenerated();
}
