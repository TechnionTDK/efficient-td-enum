package tdk_enum.graph.graphs.tree_decomposition.single_thread;

import tdk_enum.graph.data_structures.Node;
import tdk_enum.graph.data_structures.NodeSet;
import tdk_enum.graph.data_structures.TdListMap;
import tdk_enum.graph.graphs.IGraph;
import tdk_enum.graph.graphs.chordal_graph.single_thread.ChordalGraph;
import tdk_enum.graph.graphs.tree_decomposition.ITreeDecomposition;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TreeDecomposition extends ChordalGraph implements ITreeDecomposition {

    List<DecompositionNode> bags = new ArrayList<>();
    Node root = new Node(-1);



    public TreeDecomposition()
    {
        super();
    }

    public TreeDecomposition(IGraph graph)
    {
        super(graph);
//        bags.

    }

    public TreeDecomposition (int nodes)
    {
        super(nodes);
//        bags = super.getMaximalCliques();
    }


    @Override
    public List<DecompositionNode> getBags() {
        return bags;
    }

    @Override
    public void setBags(List<DecompositionNode> bags) {
        this.bags = bags;
        nodes.clear();
        neighborSets.clear();
        for (int i =0; i< bags.size(); i++)
        {
            nodes.add(new Node(i));
            neighborSets.put(new Node(i), new HashSet<>());
        }
        numberOfNodes = nodes.size();

    }

    @Override
    public void addEdge(Node u, Node v)
    {
        super.addEdge(u, v);
        DecompositionNode uBag = bags.get(u.intValue());
        DecompositionNode vBag = bags.get(v.intValue());

        uBag.getNeighbors().add(v);
        vBag.getNeighbors().add(u);
    }

    @Override
    public Node getRoot() {
        return root;
    }

    @Override
    public void setRoot(Node root) {
        this.root = root;
    }

    @Override
    public DecompositionNode getBag(Node bagId) {
        return bags.get(bagId.intValue());
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
//        for(Node child : getNeighbors(root))
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
        return new HashSet<>(bags);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TreeDecomposition)) return false;
        if (!super.equals(o)) return false;

        TreeDecomposition that = (TreeDecomposition) o;

        if (getBags() != null ? !getBags().equals(that.getBags()) : that.getBags() != null) return false;

        return (getRoot() != null ? getRoot().equals(that.getRoot()) : that.getRoot() == null);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getBags() != null ? getBags().hashCode() : 0);
        result = 31 * result + (getRoot() != null ? getRoot().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("td ");
        sb.append(numberOfNodes).append(" ").append(bags.stream().mapToInt(value -> value.size()).max().getAsInt()).append(System.lineSeparator());
        for(DecompositionNode bag: bags)
        {
            sb.append(bag).append(System.lineSeparator());
        }
        for(DecompositionNode bag :bags)
        {
            if(bag.getChildren().size() != 0)
            {
                sb.append(bag.getBagId());
                for (Node child : bag.getChildren())
                {
                    sb.append(" ").append(child);
                }
                sb.append(System.lineSeparator());
            }
        }
        return sb.toString();
    }
}
