package tdk_enum.graph.triangulation;


import tdk_enum.common.IO.result_printer.IResultPrinter;
import tdk_enum.graph.graphs.IGraph;
import tdk_enum.graph.graphs.chordal_graph.IChordalGraph;
import tdk_enum.graph.graphs.succinct_graphs.ISuccinctGraphRepresentation;
import tdk_enum.graph.independent_set.IMaximalIndependentSetsEnumerator;


public abstract class AbstractMinimalTriangulationsEnumerator implements IMinimalTriangulationsEnumerator {


    protected IGraph graph;
    protected ISuccinctGraphRepresentation seperatorGraph;

    protected IMaximalIndependentSetsEnumerator setsEnumerator;

    public IResultPrinter<IChordalGraph> getResultPrinter() {
        return resultPrinter;
    }

    @Override
    public void setResultPrinter(IResultPrinter<IChordalGraph> resultPrinter) {
        this.resultPrinter = resultPrinter;
    }



    protected IResultPrinter<IChordalGraph> resultPrinter;



    @Override
    public void setGraph(IGraph graph) {
        this.graph = graph;
    }

    @Override
    public void setSeparatorGraph(ISuccinctGraphRepresentation separatorGraph) {
        this.seperatorGraph = separatorGraph;
    }



    @Override
    public void setSetsEnumerator(IMaximalIndependentSetsEnumerator setsEnumerator) {
        this.setsEnumerator = setsEnumerator;
    }
}
