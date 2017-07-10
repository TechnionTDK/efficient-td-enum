package tdenum.graph.interfaces;

import tdenum.graph.data_structures.Edge;
import tdenum.graph.data_structures.MinimalSeparator;
import tdenum.graph.data_structures.Node;

import java.util.Map;
import java.util.Set;

/**
 * Created by dvir.dukhan on 7/9/2017.
 */
public interface ISubGraph
{

    Set<MinimalSeparator> getSeps();

    void setSeps(Set<MinimalSeparator> seps);

    Set<Edge> createEdgeSet();

    Map<Node, Node> getNodeMaptoMainGraph();

    Set<MinimalSeparator> createNewSepGroup(Map<Node, Node> subNodesInFather, final MinimalSeparator excludeSep, final Set<MinimalSeparator> sepsInFatherGraph);

    void print();


}
