package tdenum.graph.interfaces;

import tdenum.graph.Edge;
import tdenum.graph.Graph;
import tdenum.graph.MinimalSeparator;
import tdenum.graph.NodeSet;

import java.util.List;
import java.util.Set;

/**
 * Created by dvir.dukhan on 7/6/2017.
 */
public interface IChordalGraph {




    Set<NodeSet> getMaximalCliques();
    List<Edge> getFillEdges(final Graph origin);
    int getFillIn(final Graph origin);
    int getTreeWidht();
    long  getExpBagsSize();
    void printTriangulation(final Graph origin);
    void printMaximumClique();
}