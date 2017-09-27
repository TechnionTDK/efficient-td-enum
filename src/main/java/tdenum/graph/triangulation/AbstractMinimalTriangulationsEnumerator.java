package tdenum.graph.triangulation;


import tdenum.graph.graphs.IGraph;
import tdenum.graph.graphs.separator_graph.ISeparatorGraph;
import tdenum.graph.independent_set.IMaximalIndependentSetsEnumerator;


public abstract class AbstractMinimalTriangulationsEnumerator implements IMinimalTriangulationsEnumerator {


    protected IGraph graph;
    protected ISeparatorGraph seperatorGraph;

    protected IMaximalIndependentSetsEnumerator setsEnumerator;



    @Override
    public void setGraph(IGraph graph) {
        this.graph = graph;
    }

    @Override
    public void setSeparatorGraph(ISeparatorGraph separatorGraph) {
        this.seperatorGraph = separatorGraph;
    }



    @Override
    public void setSetsEnumerator(IMaximalIndependentSetsEnumerator setsEnumerator) {
        this.setsEnumerator = setsEnumerator;
    }
}
