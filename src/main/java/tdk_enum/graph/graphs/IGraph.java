package tdk_enum.graph.graphs;

import tdk_enum.graph.data_structures.HyperEdge;
import tdk_enum.graph.data_structures.Node;
import tdk_enum.graph.data_structures.NodeSet;
import tdk_enum.graph.data_structures.TdMap;
import tdk_enum.graph.graphs.tree_decomposition.single_thread.DecompositionNode;

import java.util.List;
import java.util.Set;

/**
 * Created by dvir.dukhan on 7/5/2017.
 */
//public interface IGraph <Node extends Integer>{
public interface IGraph {


    void addEdge(Node u, Node v);


    void addClique(final NodeSet clique);

    void addClique(final List<Node> clique);

    void saturateNodeSets(final Set<? extends NodeSet> sets);

    NodeSet getVertices();

    NodeSet accessVertices();

    int getNumberOfEdges();

    int getNumberOfNodes();

    Set<Node> getNeighbors(Node v);

    //    NodeSet accessNeighbors(Node v);
    Set<Node> accessNeighbors(Node v);

//    NodeSet accessNeighbors(int v);

    NodeSet getNeighbors(final List<Node> nodes);

    NodeSet getNeighbors(final NodeSet nodes);

    TdMap<Boolean> getNeighborsMap(Node v);

    List<NodeSet> getComponents(final Set<Node> removeNodes);

    List<NodeSet> getComponents(final NodeSet removeNodes);

    NodeSet getComponent(Node v, final NodeSet removedNodes);

    NodeSet getComponent(Node v, final Set<Node> removedNodes);


    boolean areNeighbors(Node u, Node v);

    TdMap<Integer> getComponentsMap(final NodeSet removedNodes);

//    TdMap<NodeSet> getAdjacentVertices();

    TdMap<Set<Node>> getAdjacentVertices();

    Set<Set<Node>> getEdgesDelta(NodeSet nodes);


}
