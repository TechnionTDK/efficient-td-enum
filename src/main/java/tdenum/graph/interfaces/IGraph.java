package tdenum.graph.interfaces;

import tdenum.graph.Node;
import tdenum.graph.NodeSet;

import java.util.List;
import java.util.Set;

/**
 * Created by dvir.dukhan on 7/5/2017.
 */
//public interface IGraph <Node extends Integer>{
public interface IGraph{


    void addEdge(Node u , Node v);
    void addClique(final Set<Node> clique);
    void addClique(final List<Node> clique);
    void saturateNodeSets(final Set<? extends Set<Node>> sets);

    Set<Node> getNodes();
    int getNumberOfEdges();
    int getNumberOfNodes();
    Set<Node> getNeighbors(Node v);
    Set<Node> getNeighbors(int v);
    NodeSet getNeighbors(final List<Node> nodes);
    NodeSet getNeighbors(final Set<Node> nodes);
    List<Boolean> getNeighborsMap(Node v);
    List<NodeSet> getComponents(final Set<Node> removeNodes);
    List<NodeSet> getComponents(final NodeSet removeNodes);
    Set<Node> getComponent(Node v, final Set<Node> removedNodes);
    boolean areNeighbors(Node u, Node v);





}
