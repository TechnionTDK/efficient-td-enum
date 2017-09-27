package tdenum.graph.graphs.chordal_graph;

import tdenum.graph.data_structures.Edge;
import tdenum.graph.data_structures.NodeSet;
import tdenum.graph.graphs.IGraph;

import java.util.List;
import java.util.Set;

/**
 * Created by dvir.dukhan on 7/6/2017.
 */
public interface IChordalGraph extends IGraph
{


    Set<NodeSet> getMaximalCliques();

    List<Edge> getFillEdges(final IGraph origin);

    int getFillIn(final IGraph origin);

    int getTreeWidth();

    long getExpBagsSize();

    void printTriangulation(final IGraph origin);

    void printMaximumClique();
}
