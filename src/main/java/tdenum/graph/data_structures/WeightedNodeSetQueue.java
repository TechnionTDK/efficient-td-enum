package tdenum.graph.data_structures;

import javafx.util.Pair;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by dvird on 17/07/10.
 */
public class WeightedNodeSetQueue<T extends NodeSet> extends WeightedQueue<T>
{

//    Set<Pair<Integer, NodeSet>>queue = new LinkedHashSet<>();
//
//    public boolean isEmpty()
//    {
//        return queue.isEmpty();
//    }
//
//    public boolean isMemeber(final NodeSet nodeSet, int weight)
//    {
//        return queue.contains(new Pair<>(weight, nodeSet));
//    }
//
//    public void insert(final NodeSet nodeSet, int weight)
//    {
//        queue.add(new Pair<>(weight, nodeSet));
//    }
//
//    public <T extends NodeSet> T pop()
//    {
//        T nodeSet = (T) queue.iterator().next().getValue();
//        queue.iterator().remove();
//        return nodeSet;
//
//    }
}
