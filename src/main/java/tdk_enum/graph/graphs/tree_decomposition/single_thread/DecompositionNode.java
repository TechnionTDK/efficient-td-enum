package tdk_enum.graph.graphs.tree_decomposition.single_thread;

import tdk_enum.graph.data_structures.Node;
import tdk_enum.graph.data_structures.NodeSet;

import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

public class DecompositionNode  {

    int id = 0;
    int index = 0;
    int depth = -1;
    DecompositionNode parent = null;

    List<DecompositionNode> children = null;
    NodeSet content = null;

    DecompositionNode(int id, NodeSet content)
    {
        this(id, null, content);
    }

    DecompositionNode(int id, DecompositionNode parent, NodeSet content)
    {
        this.id = id;
        this.parent = parent;
        this.content = new NodeSet(content);
    }

//    Node bagId = new Node(-1);
//
//    Set<Node> neighbors = new HashSet<>();
//    Node parent = new Node(-1);
//
//
//    List<Node> children = new ArrayList<>();
//
//    public DecompositionNode(NodeSet clique) {
//        super(clique);
//    }
//
//    public Node getBagId() {
//        return bagId;
//    }
//
//    public void setBagId(Node bagId) {
//        this.bagId = bagId;
//    }
//
//    public Set<Node> accessNeighbors() {
//        return neighbors;
//    }
//
//    public void setNeighbors(Set<Node> neighbors) {
//        this.neighbors = neighbors;
//    }
//
//    public Node getParent() {
//        return parent;
//    }
//
//    public void setParent(Node parent) {
//        this.parent = parent;
//
//    }
//
//    public List<Node> getChildren() {
//        return children;
//    }
//
//    public void setChildren(List<Node> children) {
//        this.children = children;
//
//    }
//
//    public void addChild(Node bagId) {
//        children.add(bagId);
//    }
//
//    public void updateNeighbors()
//    {
//        neighbors.clear();
//        neighbors.add(parent);
//        neighbors.addAll(children);
//    }
//
//    public boolean contentEquals(DecompositionNode decompositionNode)
//    {
//        return new HashSet<>(this).equals(new HashSet<>(decompositionNode));
//        //return super.equals(decompositionNode);
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof DecompositionNode)) return false;
//        DecompositionNode that = (DecompositionNode) o;
////        if (!contentEquals(that)) return false;
//        if (!super.equals(o)) return false;
//
//
//
//        if (getBagId() != null ? !getBagId().equals(that.getBagId()) : that.getBagId() != null) return false;
//        if (accessNeighbors() != null ? !accessNeighbors().equals(that.accessNeighbors()) : that.accessNeighbors() != null)
//            return false;
//        if (getParent() != null ? !getParent().equals(that.getParent()) : that.getParent() != null) return false;
//        return getChildren() != null ? getChildren().equals(that.getChildren()) : that.getChildren() == null;
//    }
//
//    @Override
//    public int hashCode() {
//        int result = super.hashCode();
//        result = 31 * result + (getBagId() != null ? getBagId().hashCode() : 0);
//        result = 31 * result + (accessNeighbors() != null ? accessNeighbors().hashCode() : 0);
//        result = 31 * result + (getParent() != null ? getParent().hashCode() : 0);
//        result = 31 * result + (getChildren() != null ? getChildren().hashCode() : 0);
//        return result;
//    }
//
//    @Override
//    public String toString() {
//        final StringBuilder sb = new StringBuilder("b ");
//        sb.append(bagId);
//        for (Node node : this)
//        {
//            sb.append(" ").append(node);
//        }
//        return sb.toString();
//    }
}
