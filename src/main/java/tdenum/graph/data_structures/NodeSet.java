package tdenum.graph.data_structures;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;


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



}
