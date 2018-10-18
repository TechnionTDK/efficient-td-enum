package tdk_enum.graph.graphs.tree_decomposition;

import tdk_enum.graph.data_structures.Node;
import tdk_enum.graph.graphs.chordal_graph.IChordalGraph;
import tdk_enum.graph.graphs.tree_decomposition.single_thread.DecompositionNode;

import java.util.List;

public interface ITreeDecomposition  extends IChordalGraph{


    int getMaximumDepth();

    int getMaximumDepth(DecompositionNode parent);

    int getNodeCount();

    List<DecompositionNode> accessNodeList();

    List<DecompositionNode> getNodeList();

    void setBags(List<DecompositionNode> bags);

    List<DecompositionNode> getLeafNodeList();

    List<DecompositionNode> getJoinNodeList();

    List<DecompositionNode> getForgetNodeList();

    List<DecompositionNode> getIntroduceNodeList();

    List<DecompositionNode> getExchangeNodeList();

    DecompositionNode getBag(int id);

    DecompositionNode getRoot();

    void setRoot(DecompositionNode root);

    DecompositionNode getBag(Node bagId);

    void removeEmptyLeaves();

    int getDistance(Node startBagID, Node targetBagID);

    int getDistance(int startBagID, int targetBagID);

    int getDistance(DecompositionNode start, DecompositionNode target);

    boolean isConnected(List<DecompositionNode> bags);

    int getLifetime(int item);

//    boolean isTree();
}
