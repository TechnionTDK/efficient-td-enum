package tdk_enum.graph.triangulation;

import tdk_enum.common.IO.result_printer.IResultPrinter;
import tdk_enum.graph.data_structures.MinimalSeparator;
import tdk_enum.graph.graphs.IGraph;
import tdk_enum.graph.graphs.succinct_graphs.ISuccinctGraphRepresentation;
import tdk_enum.graph.graphs.chordal_graph.IChordalGraph;
import tdk_enum.graph.independent_set.IMaximalIndependentSetsEnumerator;

import java.util.Set;

public interface IMinimalTriangulationsEnumerator extends IResultPrinter<Set<MinimalSeparator>> {
    void setResultPrinter(IResultPrinter<IChordalGraph> resultPrinter);

    void setGraph(IGraph graph);

    void setSeparatorGraph(ISuccinctGraphRepresentation separatorGraph);

    void setSetsEnumerator(IMaximalIndependentSetsEnumerator setsEnumerator);


    boolean hasNext();

    IChordalGraph next();

    int getNumberOfMinimalSeperatorsGenerated();
}
