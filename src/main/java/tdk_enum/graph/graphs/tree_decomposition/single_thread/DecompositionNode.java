package tdk_enum.graph.graphs.tree_decomposition.single_thread;

import tdk_enum.graph.data_structures.Node;
import tdk_enum.graph.data_structures.NodeSet;

import java.util.HashSet;
import java.util.Set;

public class DecompositionNode extends NodeSet {

    Node bagId;

    Set<Node> neighbors = new HashSet<>();
    Node parent;
    Set<Node> children = new HashSet<>();

    public DecompositionNode(NodeSet clique) {
        super(clique);
    }

    public Node getBagId() {
        return bagId;
    }

    public void setBagId(Node bagId) {
        this.bagId = bagId;
    }

    public Set<Node> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(Set<Node> neighbors) {
        this.neighbors = neighbors;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public Set<Node> getChildren() {
        return children;
    }

    public void setChildren(Set<Node> children) {
        this.children = children;
    }

    public void addChild(Node bagId) {
        children.add(bagId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DecompositionNode)) return false;
        if (!super.equals(o)) return false;

        DecompositionNode that = (DecompositionNode) o;

        if (getBagId() != null ? !getBagId().equals(that.getBagId()) : that.getBagId() != null) return false;
        if (getNeighbors() != null ? !getNeighbors().equals(that.getNeighbors()) : that.getNeighbors() != null)
            return false;
        if (getParent() != null ? !getParent().equals(that.getParent()) : that.getParent() != null) return false;
        return getChildren() != null ? getChildren().equals(that.getChildren()) : that.getChildren() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getBagId() != null ? getBagId().hashCode() : 0);
        result = 31 * result + (getNeighbors() != null ? getNeighbors().hashCode() : 0);
        result = 31 * result + (getParent() != null ? getParent().hashCode() : 0);
        result = 31 * result + (getChildren() != null ? getChildren().hashCode() : 0);
        return result;
    }
}
