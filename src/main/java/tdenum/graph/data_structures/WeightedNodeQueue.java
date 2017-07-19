package tdenum.graph.data_structures;


import java.util.*;

/**
 * Created by dvir.dukhan on 7/6/2017.
 */
public class WeightedNodeQueue extends WeightedQueue<Node> {

    //    List<Integer> weight = new ArrayList<>();
//    Set<Pair<Integer, Node>> queue = new HashSet<>();
//    LinkedHashMap<Node, Integer> queue = new LinkedHashMap<>();

    public WeightedNodeQueue(int numberOfNoeds) {
//        weight = utils.generateFixedList(numberOfNoeds, 0);
//        List<Pair<Integer, Node>> temporaryQueue = new ArrayList<>();
//        for(int v = 0; v < numberOfNoeds; v++)
//        {
//            temporaryQueue.add(new Pair<>(0, new Node(v)));
//
//        }
//        queue = new HashSet<>(temporaryQueue);

        for (int v = 0; v < numberOfNoeds; v++) {
            setWeight(new Node(v), 0);
        }
    }

    public WeightedNodeQueue(NodeSet nodes)
    {
        for (Node v : nodes)
        {
            setWeight(v,0);
        }
    }






}

