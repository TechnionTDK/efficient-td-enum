package tdenum.graph.graphs.separator_graph;

import tdenum.graph.data_structures.MinimalSeparator;
import tdenum.graph.graphs.IGraph;
import tdenum.graph.separators.IMinimalSeparatorsEnumerator;
import tdenum.graph.separators.single_thread.MinimalSeparatorsEnumerator;

public abstract class AbstractSeparatorGraph implements ISeparatorGraph {


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
