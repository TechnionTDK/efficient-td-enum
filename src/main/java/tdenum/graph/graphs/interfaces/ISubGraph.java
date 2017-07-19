package tdenum.graph.graphs.interfaces;

import tdenum.graph.data_structures.*;

import java.util.Map;
import java.util.Set;

/**
 * Created by dvir.dukhan on 7/9/2017.
 */
public interface ISubGraph extends IGraph
{
    IGraph getMainGraph();

    Set<? extends NodeSet> getSeps();

    void setSeps(Set<? extends NodeSet> seps);

    Set<Edge> createEdgeSet();

  //  Map<Node, Node> getNodeMaptoMainGraph();
    TdMap<Node> getNodeMaptoMainGraph();

//    Set<? extends NodeSet> createNewSepGroup(Map<Node, Node> subNodesInFather, final MinimalSeparator excludeSep, final Set<MinimalSeparator> sepsInFatherGraph);

    Set<? extends NodeSet> createNewSepGroup(TdMap<Node> subNodesInFather, final MinimalSeparator excludeSep, final Set<MinimalSeparator> sepsInFatherGraph);

    void print();


}
