package tdk_enum.graph.graphs;

import tdk_enum.graph.data_structures.HyperEdge;
import tdk_enum.graph.data_structures.Node;
import tdk_enum.graph.graphs.tree_decomposition.single_thread.DecompositionNode;

import java.util.List;

public interface IMLGraph extends IGraph {
    int getVertexIndex(String vertexName);

    void addHyperEdge(HyperEdge hyperEdge);

    boolean isVertex(int id);

    boolean isVertex(String id);

    boolean isConnected();

    boolean isConnected(int vertex1, int vertex2);
    boolean isConnected(Node vertex1, Node vertex2);

    List<Node> getReachableVertices(int vertex);

    List<Node> accessReachableVertices(int vertex);

    List<Node> getNeighbors(int vertex, DecompositionNode node);
    List<Node> getNeighbors(Node vertex, DecompositionNode node);

    int getEccentricity(int vertex);

    int getEccentricity(Node vertex);

    int getEccentricity(String vertex);

    void update();

    String getOriginalPath();

    void setOriginalPath(String originalPath);
}

