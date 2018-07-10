package tdk_enum.graph.data_structures.weighted_queue.single_thread;


import tdk_enum.graph.data_structures.Node;
import tdk_enum.graph.data_structures.NodeSet;

/**
 * Created by dvir.dukhan on 7/6/2017.
 */
public class IncreasingWeightedNodeQueue extends WeightedQueue<Node> {

    //    List<Integer> weight = new ArrayList<>();
//    Set<Pair<Integer, Node>> queue = new HashSet<>();
//    LinkedHashMap<Node, Integer> queue = new LinkedHashMap<>();

    public IncreasingWeightedNodeQueue(int numberOfNoeds) {
        super(true);
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

    public IncreasingWeightedNodeQueue(NodeSet nodes)
    {
        super(true);
        for (Node v : nodes)
        {
            setWeight(v,0);
        }
    }






}

