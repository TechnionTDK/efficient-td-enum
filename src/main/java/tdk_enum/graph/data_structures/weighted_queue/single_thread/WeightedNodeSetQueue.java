package tdk_enum.graph.data_structures.weighted_queue.single_thread;

import tdk_enum.graph.data_structures.NodeSet;

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
//    public <T extends NodeSet> T poll()
//    {
//        T nodeSet = (T) queue.iterator().next().getValue();
//        queue.iterator().remove();
//        return nodeSet;
//
//    }
}
