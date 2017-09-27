package tdenum.graph.graphs.separator_graph;

import tdenum.graph.data_structures.MinimalSeparator;
import tdenum.graph.graphs.IGraph;
import tdenum.graph.graphs.ISuccinctGraphRepresentation;
import tdenum.graph.separators.IMinimalSeparatorsEnumerator;

/**
 * Created by dvir.dukhan on 7/11/2017.
 */
public interface ISeparatorGraph extends ISuccinctGraphRepresentation<MinimalSeparator > {

    int getNumberOfNodesGenerated();

    void setGraph(IGraph graph);

    void setNodesEnumerator(IMinimalSeparatorsEnumerator nodesEnumerator);
}
