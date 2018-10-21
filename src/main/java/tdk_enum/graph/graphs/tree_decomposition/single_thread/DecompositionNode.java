package tdk_enum.graph.graphs.tree_decomposition.single_thread;

import tdk_enum.graph.data_structures.Node;
import tdk_enum.graph.data_structures.NodeSet;

import java.util.*;

public class DecompositionNode {

    int id = -1;
    int index = -1;
    int depth = -1;
    DecompositionNode parent = null;

    List<DecompositionNode> children = new ArrayList<>();
    List<Node> content = new NodeSet();


    List<DecompositionNode> neighbors = new ArrayList<>();


    public DecompositionNode(List<Node> content) {
        this.content = content;
    }

    DecompositionNode(int id, List<Node> content) {
        this(id, null, content);
    }

    DecompositionNode(int id, DecompositionNode parent, List<Node> content) {
        this.id = id;
        this.parent = parent;


        this.content = new NodeSet(content);

        if (parent != null && !neighbors.contains(parent)) {
            neighbors.add(parent);
        }
    }


    public int getID() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DecompositionNode getParent() {
        return parent;
    }

    public void setParent(DecompositionNode parent) {
        this.parent = parent;
        if (parent != null && !neighbors.contains(parent)) {
            neighbors.add(parent);
        }
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }


    public boolean isRootNode() {
        return parent == null;
    }

    public boolean isLeafNode() {
        return children.isEmpty();
    }

    public boolean isJoinNode() {
        return children.size() > 1;
    }

    public boolean isIntroduceNode() {
        return !getIntroducedItemList().isEmpty();
    }

    public boolean isForgetNode() {
        return !getForgottenItemList().isEmpty();
    }

    public boolean isExchangeNode() {
        return isIntroduceNode() && isForgetNode();
    }

    public boolean hasType(NodeType type) {
        boolean ret = false;

        switch (type) {
            case DEFAULT: {
                ret = true;

                break;
            }
            case ROOT: {
                ret = isRootNode();

                break;
            }
            case LEAF: {
                ret = isLeafNode();

                break;
            }
            case JOIN: {
                ret = isJoinNode();

                break;
            }
            case FORGET: {
                ret = isForgetNode();

                break;
            }
            case EXCHANGE: {
                ret = isExchangeNode();

                break;
            }
            case INTRODUCE: {
                ret = isIntroduceNode();

                break;
            }
            default: {
                ret = true;

                break;
            }
        }

        return ret;
    }

    public void addChild(DecompositionNode bag) {
        if (bag != null) {
            if (!children.contains(bag)) {
                children.add(bag);
            }
        }
    }

    public void removeChild(DecompositionNode bag) {
        if (bag != null) {
            if (children.contains(bag)) {
                children.remove(bag);
            }
        }
    }

    public int getItemCount() {
        return content.size();
    }

    public List<Node> getItemList() {
        List<Node> ret =
                new ArrayList<>(content);


//        for (Node item : content) {
//            if (item != null) {
//                ret.add(item);
//            }
//        }

        return ret;
    }

    public List<Node> accessItemList() {
        return content;
    }

    public List<Node> getIntroducedItemList() {
        List<Node> ret =
                new ArrayList<>();

        for (Node item : content) {
            if (item != null) {
                ret.add(item);
            }
        }

        for (DecompositionNode child : children) {
            ret.removeAll(child.accessItemList());
        }

        return ret;
    }

    public List<Node> getForgottenItemList() {
        List<Node> ret =
                new ArrayList<>();

        for (DecompositionNode child : children) {
            for (Node item : child.accessItemList()) {
                if (item != null && !ret.contains(item)) {
                    ret.add(item);
                }
            }
        }

        ret.removeAll(content);

        return ret;
    }

    public int getChildrenCount() {
        return children.size();
    }

    public List<DecompositionNode> getChildrenList() {
        List<DecompositionNode> ret =
                new ArrayList<>();

        for (DecompositionNode child : children) {
            if (child != null) {
                ret.add(child);
            }
        }

        return ret;
    }

    public void setChildren(List<DecompositionNode> children) {
        this.children = children;

    }

    public List<DecompositionNode> accessChildrenList() {
        return children;
    }

    public List<DecompositionNode> accessNeighborList() {
        return neighbors;
    }

    public List<DecompositionNode> getNeighborList() {
        List<DecompositionNode> ret =
                new ArrayList<>(neighbors);
//
//        if (parent != null) {
//            ret.add(parent);
//        }
//
//        for (DecompositionNode child : children) {
//            if (child != null) {
//                ret.add(child);
//            }
//        }

        return ret;
    }

    public DecompositionNode getCopy() {
        return getCopy(parent);
    }

    public DecompositionNode getCopy(DecompositionNode newParent) {
        DecompositionNode ret = createInstance(id, newParent, content);

        if (ret != null) {
            for (DecompositionNode child : getChildrenList()) {
                if (child != null) {
                    ret.addChild(child.getCopy(ret));
                }
            }
        }

        return ret;
    }

    public boolean containsItem(Integer item) {
        return containsItem(new Node(item));
    }

    public boolean containsItem(Node item) {
        return content.contains(item);
    }

    public static DecompositionNode createInstance(int id, List<Node> content) {
        return createInstance(id, null, content);
    }

    public static DecompositionNode createInstance(int id, DecompositionNode parent, List<Node> content) {
        NodeSet newContent =
                new NodeSet();

        if (content != null) {
            for (Node item : content) {
                if (item != null && !newContent.contains(item)) {
                    newContent.add(item);
                }
            }
        }

        Collections.sort(newContent);

        return new DecompositionNode(id, parent, newContent);
    }

    public int getSubTreeSize() {
        int ret = 1;

        for (DecompositionNode child : getChildrenList()) {
            ret += child.getSubTreeSize();
        }

        return ret;
    }

    public boolean contentEquals(DecompositionNode decompositionNode) {
        return new HashSet<>(this.content).equals(new HashSet<>(decompositionNode.content));
        //return super.equals(decompositionNode);
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DecompositionNode that = (DecompositionNode) o;

        if (id != that.id) return false;
        if (index != that.index) return false;
        if (depth != that.depth) return false;
        return content != null ? content.equals(that.content) : that.content == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + index;
        result = 31 * result + depth;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("b ");
        sb.append(id);
        for (Node node : this.content)
        {
            sb.append(" ").append(node);
        }
        return sb.toString();
    }


}
