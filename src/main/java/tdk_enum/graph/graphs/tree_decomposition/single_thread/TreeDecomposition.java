package tdk_enum.graph.graphs.tree_decomposition.single_thread;

import tdk_enum.graph.data_structures.Node;
import tdk_enum.graph.data_structures.NodeSet;
import tdk_enum.graph.graphs.IGraph;
import tdk_enum.graph.graphs.chordal_graph.single_thread.ChordalGraph;
import tdk_enum.graph.graphs.tree_decomposition.ITreeDecomposition;

import java.util.*;

public class TreeDecomposition extends ChordalGraph implements ITreeDecomposition {

    List<DecompositionNode> bags = new ArrayList<>();
    DecompositionNode root = null;
    List<Node> items = new NodeSet();



    public TreeDecomposition()
    {
        super();
    }

    TreeDecomposition(IGraph graph)
    {
        super(graph);
//        bags.

    }

    public TreeDecomposition (int nodes)
    {
        super(nodes);
//        bags = super.getMaximalCliques();
    }

    public TreeDecomposition(DecompositionNode root, List<DecompositionNode> bags)
    {
        super(bags.size());
        this.root = root;
        this.bags = bags;


        update();
    }

    public void update() {

        this.items = new NodeSet();
        vertices.clear();
        adjacentVertices.clear();
        for (int i =0; i< bags.size(); i++)
        {
            vertices.add(new Node(i));
            adjacentVertices.put(new Node(i), new HashSet<>());
        }
        numberOfNodes = vertices.size();

        update(root, 0);

    }

    private void update(DecompositionNode parent, int currentDepth) {



        if (parent != null) {
            List<DecompositionNode> children =
                    parent.getChildrenList();

            parent.setIndex(bags.indexOf(parent));
            parent.setDepth(currentDepth);

            for (Node item : parent.accessItemList()) {
                if (item != null && !items.contains(item)) {
                    items.add(item);
                }
            }



            if (children != null) {
                for (DecompositionNode child : children) {
                    update(child, currentDepth + 1);
                }
            }
        }
    }

    @Override
    public int getMaximumDepth() {
        return getMaximumDepth(root, 0);
    }

    @Override
    public int getMaximumDepth(DecompositionNode parent) {
        return getMaximumDepth(parent, 0);
    }

    private int getMaximumDepth(DecompositionNode parent, int currentDepth) {
        int ret = -1;

        if (parent != null) {
            if (parent.isLeafNode()) {
                ret = currentDepth;
            }
            else {
                List<DecompositionNode> children = parent.getChildrenList();

                if (children != null) {
                    for (int i = 0; i < children.size(); i++) {
                        int tmp = getMaximumDepth(children.get(i), currentDepth + 1);

                        if (tmp > ret) {
                            ret = tmp;
                        }
                    }
                }
                else {
                    ret = -1;
                }
            }
        }
        else {
            ret = -1;
        }

        return ret;
    }

    @Override
    public int getNodeCount() {

        return bags.size();
    }

    @Override
    public List<DecompositionNode> accessNodeList() {
        return bags;
    }

    @Override
    public List<DecompositionNode> getNodeList() {
        return new ArrayList<>(bags);
    }

    @Override
    public void setBags(List<DecompositionNode> bags) {
        this.bags = bags;
        update();


    }

    @Override
    public List<DecompositionNode> getLeafNodeList() {
        List<DecompositionNode> ret = getNodeList();

        for (int i = ret.size() - 1; i >= 0; i--) {
            DecompositionNode bag = ret.get(i);

            if (bag == null || !bag.isLeafNode()) {
                ret.remove(i);
            }
        }

        return ret;
    }


    @Override
    public List<DecompositionNode> getJoinNodeList() {
        List<DecompositionNode> ret = getNodeList();

        for (int i = ret.size() - 1; i >= 0; i--) {
            DecompositionNode bag = ret.get(i);

            if (bag == null || !bag.isJoinNode()) {
                ret.remove(i);
            }
        }

        return ret;
    }

    @Override
    public List<DecompositionNode> getForgetNodeList() {
        List<DecompositionNode> ret = getNodeList();

        for (int i = ret.size() - 1; i >= 0; i--) {
            DecompositionNode bag = ret.get(i);

            if (bag == null || !bag.isForgetNode()) {
                ret.remove(i);
            }
        }

        return ret;
    }

    @Override
    public List<DecompositionNode> getIntroduceNodeList() {
        List<DecompositionNode> ret = getNodeList();

        for (int i = ret.size() - 1; i >= 0; i--) {
            DecompositionNode bag = ret.get(i);

            if (bag == null || !bag.isIntroduceNode()) {
                ret.remove(i);
            }
        }

        return ret;
    }

    @Override
    public List<DecompositionNode> getExchangeNodeList() {
        List<DecompositionNode> ret = getNodeList();

        for (int i = ret.size() - 1; i >= 0; i--) {
            DecompositionNode bag = ret.get(i);

            if (bag == null || !bag.isExchangeNode()) {
                ret.remove(i);
            }
        }

        return ret;
    }

    public List<Node> getItemList() {

        return new ArrayList<>(items);
    }

    public List<Node> accessItemList() {


        return items;
    }




    @Override
    public void addEdge(Node u, Node v)
    {
        super.addEdge(u, v);
        DecompositionNode uBag = bags.get(u.intValue());
        DecompositionNode vBag = bags.get(v.intValue());

        uBag.accessNeighborList().add(vBag);
        vBag.accessNeighborList().add(uBag);
    }

    @Override
    public DecompositionNode getRoot() {
        return root;
    }

    @Override
    public void setRoot(DecompositionNode root) {
        this.root = root;
    }

    @Override
    public DecompositionNode getBag(Node bagId) {
        return bags.get(bagId.intValue());
    }

    @Override
    public DecompositionNode getBag(int id) {

        return bags.stream().filter(decompositionNode -> decompositionNode.getID() == id).findFirst().get();
//        DecompositionNode ret = null;
//        List<DecompositionNode> bags = getNodeList();
//
//        if (bags != null) {
//            for (int i = 0; ret == null && i < bags.size(); i++) {
//                Node bag = bags.get(i);
//
//                if (bag != null) {
//                    if (bag.getID() == id) {
//                        ret = bag;
//                    }
//                }
//            }
//        }
//
//        return ret;
    }



    @Override
    public void removeEmptyLeaves() {
        if (root != null) {
            removeEmptyLeaves(root);
        }
    }

    private void removeEmptyLeaves(DecompositionNode target) {
        boolean updateNeeded = false;
        if (target != null) {
            List<DecompositionNode> children =
                    target.accessChildrenList();

            for (int i = children.size() - 1; i >= 0; i--) {
                DecompositionNode child = children.get(i);

                if (child.isLeafNode()) {
                    if (child.getItemCount() == 0) {
                        children.remove(i);

                        bags.remove(child);

                        updateNeeded = true;
                    }
                }
                else {
                    removeEmptyLeaves(child);
                }
            }

            if (target.isLeafNode() && target.getItemCount() == 0 && target != root && target.getParent() != null) {
                target.getParent().removeChild(target);

                updateNeeded = true;
            }
        }
        if (updateNeeded)
        {
            update();
        }
    }


    @Override
    public int getDistance(Node startBagID, Node targetBagID)
    {
        return getDistance(startBagID.intValue(), targetBagID.intValue());
    }


    @Override
    public int getDistance(int startBagID, int targetBagID) {

        return getDistance(getBag(startBagID), getBag(targetBagID));
    }

    @Override
    public int getDistance(DecompositionNode start, DecompositionNode target) {
        int ret = -1;

        if (start != null && target != null) {
            if (start == target) {
                ret = 0;
            }
            else {
                ret = getDistance(start,
                        target,
                        new boolean[getNodeCount()]);
            }
        }

        return ret;
    }

    private int getDistance(DecompositionNode start, DecompositionNode target, boolean[] visitedBags) {
        int ret = -1;

        if (start != null && target != null && visitedBags != null) {
            visitedBags[start.getIndex()] = true;

            if (start == target) {
                ret = 0;
            }
            else {
                DecompositionNode parent = start.getParent();

                if (parent != null && !visitedBags[parent.getIndex()]) {
                    int tmp = getDistance(parent, target, visitedBags);

                    if (tmp >= 0 && (ret == -1 || tmp + 1 < ret)) {
                        ret = tmp + 1;
                    }
                }

                for (DecompositionNode child : start.accessChildrenList()) {
                    if (child != null && !visitedBags[child.getIndex()]) {
                        int tmp = getDistance(child, target, visitedBags);

                        if (tmp >= 0 && (ret == -1 || tmp + 1 < ret)) {
                            ret = tmp + 1;
                        }
                    }
                }
            }
        }

        return ret;
    }

    @Override
    public boolean isConnected(List<DecompositionNode> bags) {
        boolean ret = false;

        if (bags != null) {
            if (bags.size() > 0) {
                ret = true;

                if (bags.size() > 1) {
                    boolean[] visitedBags =
                            new boolean[getNodeCount()];

                    fillReachabilityList(bags,
                            visitedBags);
                    for (DecompositionNode bag : bags) {
                        if (!visitedBags[bag.getIndex()]) {
                            ret = false;
                        }
                    }
                }
            }
        }

        return ret;
    }



    private void fillReachabilityList(List<DecompositionNode> requiredBags,
                                      boolean[] visitedBags) {



        if (requiredBags != null && visitedBags != null) {
            if (requiredBags.size() > 0) {
                DecompositionNode initialBag =
                        requiredBags.get(0);

                fillReachabilityList(initialBag,
                        requiredBags,
                        visitedBags);
            }
        }
    }

    private void fillReachabilityList(DecompositionNode initialBag,
                                      List<DecompositionNode> requiredBags,
                                      boolean[] visitedBags) {
        if (requiredBags != null && visitedBags != null) {
            if (requiredBags.size() > 0) {
                if (requiredBags.size() > 1) {
                    visitedBags[initialBag.getIndex()] = true;

                    DecompositionNode parent = initialBag.getParent();

                    if (parent != null && !visitedBags[parent.getIndex()]) {
                        if (requiredBags.contains(parent)) {
                            fillReachabilityList(parent,
                                    requiredBags,
                                    visitedBags);
                        }
                    }

                    for (DecompositionNode child : initialBag.accessChildrenList()) {
                        if (child != null && !visitedBags[child.getIndex()]) {
                            if (requiredBags.contains(child)) {
                                fillReachabilityList(child,
                                        requiredBags,
                                        visitedBags);
                            }
                        }
                    }
                }
            }
        }
    }



    @Override
    public int getLifetime(int item) {
        int ret = 0;

        List<DecompositionNode> containers =
                getContainerList(item);

        if (containers != null) {
            Set<Integer> containerLevels = new HashSet<>();

            for (DecompositionNode container : containers) {
                if (container != null) {
                    containerLevels.add(container.getDepth());
                }
            }

            for (int i : containerLevels) {
                for (int j : containerLevels) {
                    if (Math.abs(i - j) > ret) {
                        ret = Math.abs(i - j);
                    }
                }
            }

            if (!containerLevels.isEmpty()) {
                ret += 1;
            }
        }

        return ret;
    }

    public int getContainerCount(Integer item) {
        return getContainerList(item).size();
    }

    public List<DecompositionNode> getContainerList(Integer item) {
        List<DecompositionNode> ret =
                new ArrayList<>();

        fillContainerList(root, ret, item);

        return ret;
    }

    private void fillContainerList(DecompositionNode bag, List<DecompositionNode> bags, Integer item) {
        if (bag != null && bags != null && item != null) {
            if (bag.containsItem(item)) {
                bags.add(bag);
            }

            for (DecompositionNode child : bag.getChildrenList()) {
                fillContainerList(child, bags, item);
            }
        }
    }


//    @Override
//    public boolean isTree() {
//
//        if (numberOfNodes >0 && numberOfNodes-1 != numberOfEdges)
//        {
//            return false;
//        }
//        else if (numberOfNodes ==0)
//        {
//            return (numberOfEdges==0);
//        }
//        TdListMap<Boolean> seen = new TdListMap(numberOfNodes, false);
//        boolean cycle = !treeDFS(seen, new Node(0) ,new Node(-1));
//        int seenSize = (int) seen.values().stream().filter(aBoolean -> aBoolean.equals(true)).count();
//        if (cycle || seenSize != numberOfNodes)
//        {
//            return false;
//        }
//        return true;
//    }
//
//    boolean treeDFS(TdListMap<Boolean> seen, Node root, Node parent)
//    {
//        if (seen.get(root) != false)
//        {
//            return false;
//        }
//        seen.put(root, true);
//        for(Node child : accessNeighbors(root))
//        {
//            if(!child.equals(parent))
//            {
//                if (!treeDFS(seen, child, root))
//                {
//                    return false;
//                }
//            }
//        }
//        return true;
//    }

    @Override
    public Set<NodeSet> getMaximalCliques()
    {
        Set ret = new HashSet();
        for (DecompositionNode bag : bags)
        {
            ret.add(new NodeSet(bag.accessItemList()));
        }
        return ret;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TreeDecomposition)) return false;
        if (!super.equals(o)) return false;

        TreeDecomposition that = (TreeDecomposition) o;

        if (accessNodeList() != null ? !accessNodeList().equals(that.accessNodeList()) : that.accessNodeList() != null) return false;

        return (getRoot() != null ? getRoot().equals(that.getRoot()) : that.getRoot() == null);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (accessNodeList() != null ? accessNodeList().hashCode() : 0);
        result = 31 * result + (getRoot() != null ? getRoot().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("td ");
        sb.append(numberOfNodes).append(" ").append(bags.stream().mapToInt(value -> value.accessItemList().size()).max().getAsInt()).append(System.lineSeparator());
        for(DecompositionNode bag: bags)
        {
            sb.append(bag).append(System.lineSeparator());
        }
        for(DecompositionNode bag :bags)
        {
            if(bag.accessChildrenList().size() != 0)
            {
                sb.append(bag.getID());
                for (DecompositionNode child : bag.accessChildrenList())
                {
                    sb.append(" ").append(child);
                }
                sb.append(System.lineSeparator());
            }
        }
        return sb.toString();
    }
}
