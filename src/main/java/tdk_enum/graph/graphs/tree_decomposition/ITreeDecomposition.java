package tdk_enum.graph.graphs.tree_decomposition;

import tdk_enum.graph.data_structures.Node;
import tdk_enum.graph.graphs.chordal_graph.IChordalGraph;
import tdk_enum.graph.graphs.tree_decomposition.single_thread.DecompositionNode;

import java.util.ArrayList;
import java.util.List;

public interface ITreeDecomposition  extends IChordalGraph{


    List<DecompositionNode> getBags();

    void setBags(List<DecompositionNode> bags);

    Node getRoot();

    void setRoot(Node root);

    DecompositionNode getBag(Node bagId);

    boolean isTree();
}
