package tdk_enum.graph.data_structures;

import java.util.*;


/**
 * Created by dvir.dukhan on 7/5/2017.
 */

public class NodeSet extends ArrayList<Node>
//public class NodeSet extends LinkedHashSet<Node>
{

//    Set<Node> inserted = new HashSet<>();

    public NodeSet()
    {
        super();
    }

    public NodeSet(Collection<Node> nodes)
    {
        super(nodes);
    }




    @Override
    public String toString() {
        return super.toString();
    }

    public void add(int node)
    {
        add(new Node(node));
    }

    public void add (Integer node)
    {
        add(new Node(node));
    }


}
