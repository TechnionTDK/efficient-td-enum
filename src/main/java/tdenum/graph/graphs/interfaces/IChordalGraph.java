package tdenum.graph.graphs.interfaces;

import tdenum.graph.data_structures.Edge;
import tdenum.graph.graphs.Graph;
import tdenum.graph.data_structures.NodeSet;

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
