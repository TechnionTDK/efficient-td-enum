package tdk_enum.graph.graphs.succinct_graphs.separator_graph;

import tdk_enum.graph.graphs.IGraph;
import tdk_enum.graph.separators.IMinimalSeparatorsEnumerator;

public abstract class AbstractSeparatorGraph implements ISeparatorGraph {


    protected int nodesGenerated= 0;


    protected IGraph graph;
    protected IMinimalSeparatorsEnumerator nodesEnumerator;

    @Override
    public void setGraph(IGraph graph) {
        this.graph = graph;
    }


    @Override
    public void setNodesEnumerator(IMinimalSeparatorsEnumerator nodesEnumerator) {
        this.nodesEnumerator = nodesEnumerator;
    }
}
