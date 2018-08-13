package tdk_enum.enumerators.triangulation;


import tdk_enum.common.IO.result_printer.IResultPrinter;
import tdk_enum.common.configuration.config_types.EnumerationPurpose;
import tdk_enum.enumerators.common.AbstractConverterEnumerator;
import tdk_enum.enumerators.common.AbstractEnumerator;
import tdk_enum.graph.data_structures.Node;
import tdk_enum.graph.graphs.IGraph;
import tdk_enum.graph.graphs.chordal_graph.IChordalGraph;
import tdk_enum.graph.graphs.succinct_graphs.ISuccinctGraphRepresentation;
import tdk_enum.enumerators.independent_set.IMaximalIndependentSetsEnumerator;


public abstract class AbstractMinimalTriangulationsEnumerator extends AbstractConverterEnumerator<Node,IChordalGraph,IGraph> implements IMinimalTriangulationsEnumerator  {


   // protected IGraph graph;
    protected ISuccinctGraphRepresentation seperatorGraph;

    protected IMaximalIndependentSetsEnumerator setsEnumerator;



//    public IResultPrinter<IChordalGraph> getResultPrinter() {
//        return resultPrinter;
//    }
//
//    @Override
//    public void setResultPrinter(IResultPrinter<IChordalGraph> resultPrinter) {
//        this.resultPrinter = resultPrinter;
//    }
//
//
//
//    protected IResultPrinter<IChordalGraph> resultPrinter;



//    @Override
//    public void setGraph(IGraph graph) {
//        this.graph = graph;
//    }
//
//    @Override
//    public IGraph getGraph()
//    {
//        return this.graph;
//    }

//    @Override
//    public void setSeparatorGraph(ISuccinctGraphRepresentation separatorGraph) {
//        this.seperatorGraph = separatorGraph;
//    }



    @Override
    public void setSetsEnumerator(IMaximalIndependentSetsEnumerator setsEnumerator) {
        this.setsEnumerator = setsEnumerator;
    }


//    @Override
//    public void run() {
//        executeAlgorithm();
//    }
//
//    @Override
//    public Object call()
//    {
//        executeAlgorithm();
//        return true;
//    }


    @Override
    public int getNumberOfMinimalSeperatorsGenerated()
    {
        return ((ISuccinctGraphRepresentation)setsEnumerator.getGraph()).getNumberOfNodesGenerated();
        //return seperatorGraph.getNumberOfNodesGenerated();

    }






}
