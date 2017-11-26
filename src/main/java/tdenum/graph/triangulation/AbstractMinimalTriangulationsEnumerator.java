package tdenum.graph.triangulation;


import tdenum.graph.graphs.IGraph;
import tdenum.graph.graphs.succinct_graphs.ISuccinctGraphRepresentation;
import tdenum.graph.independent_set.IMaximalIndependentSetsEnumerator;


public abstract class AbstractMinimalTriangulationsEnumerator implements IMinimalTriangulationsEnumerator {


    protected IGraph graph;
    protected ISuccinctGraphRepresentation seperatorGraph;

    protected IMaximalIndependentSetsEnumerator setsEnumerator;



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
