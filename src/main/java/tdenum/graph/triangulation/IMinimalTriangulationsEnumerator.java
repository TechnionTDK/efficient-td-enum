package tdenum.graph.triangulation;

import tdenum.common.IO.result_printer.IResultPrinter;
import tdenum.graph.data_structures.MinimalSeparator;
import tdenum.graph.graphs.IGraph;
import tdenum.graph.graphs.succinct_graphs.ISuccinctGraphRepresentation;
import tdenum.graph.graphs.chordal_graph.IChordalGraph;
import tdenum.graph.independent_set.IMaximalIndependentSetsEnumerator;

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
