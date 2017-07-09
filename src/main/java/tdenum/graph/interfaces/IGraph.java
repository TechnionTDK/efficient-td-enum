package tdenum.graph.interfaces;

import tdenum.graph.Node;
import tdenum.graph.NodeSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by dvir.dukhan on 7/5/2017.
 */
//public interface IGraph <Node extends Integer>{
public interface IGraph
{


    void addEdge(Node u, Node v);

    void addClique(final NodeSet clique);

    void addClique(final List<Node> clique);

    void saturateNodeSets(final Set<? extends NodeSet> sets);

    NodeSet getNodes();

    int getNumberOfEdges();

    int getNumberOfNodes();

    NodeSet getNeighbors(Node v);

    NodeSet getNeighbors(int v);

    NodeSet getNeighbors(final List<Node> nodes);

    NodeSet getNeighbors(final NodeSet nodes);

    List<Boolean> getNeighborsMap(Node v);

    List<NodeSet> getComponents(final List<Node> removeNodes);

    List<NodeSet> getComponents(final NodeSet removeNodes);

    NodeSet getComponent(Node v, final NodeSet removedNodes);

    boolean areNeighbors(Node u, Node v);


}
