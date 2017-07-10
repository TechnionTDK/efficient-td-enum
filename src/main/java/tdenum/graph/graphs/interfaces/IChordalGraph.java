package tdenum.graph.graphs.interfaces;

import tdenum.graph.data_structures.Edge;
import tdenum.graph.graphs.Graph;
import tdenum.graph.data_structures.NodeSet;

import java.util.List;
import java.util.Set;

/**
 * Created by dvir.dukhan on 7/6/2017.
 */
public interface IChordalGraph
{


    Set<NodeSet> getMaximalCliques();

    List<Edge> getFillEdges(final Graph origin);

    int getFillIn(final Graph origin);

    int getTreeWidth();

    long getExpBagsSize();

    void printTriangulation(final Graph origin);

    void printMaximumClique();
}
