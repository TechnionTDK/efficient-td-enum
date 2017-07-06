package tdenum.graph.data_structures;



import tdenum.graph.Node;

import java.util.*;

/**
 * Created by dvir.dukhan on 7/6/2017.
 */
public class IncreasingWeightNodeQueue {

//    List<Integer> weight = new ArrayList<>();
//    Set<Pair<Integer, Node>> queue = new HashSet<>();
    LinkedHashMap<Node, Integer> queue;
    public IncreasingWeightNodeQueue(int numberOfNoeds)
    {
//        weight = Utils.generateFixedList(numberOfNoeds, 0);
//        List<Pair<Integer, Node>> temporaryQueue = new ArrayList<>();
//        for(int v = 0; v < numberOfNoeds; v++)
//        {
//            temporaryQueue.add(new Pair<>(0, new Node(v)));
//
//        }
//        queue = new HashSet<>(temporaryQueue);
        queue = new LinkedHashMap<>(numberOfNoeds);
        for (int v = 0; v < numberOfNoeds; v++)
        {
            queue.put(new Node(v), 0);
        }
    }

    public void increaseWeight(Node v)
    {
//        queue.remove(new Pair(weight.get(v.intValue()),v));
//        weight.set(v.intValue(), weight.get(v.intValue())+1);
//        queue.add(new Pair(weight.get(v.intValue()),v));
        int weight = queue.get(v);
        queue.remove(v);
        weight++;
        queue.put(v,weight );
    }

    public int getWeight(Node v)
    {
        return queue.get(v);
    }

    public boolean isEmpty()
    {
        return queue.isEmpty();
    }

    public Node pop()
    {
        Node v = queue.keySet().iterator().next();
        queue.remove(v);
        return v;
    }




}
