package tdenum.graph.data_structures.weighted_queue.single_thread;

import tdenum.graph.data_structures.Node;
import tdenum.graph.data_structures.NodeSet;

public class IncreasingWeightRandomizedNodeQueue extends RandomizedWeightedQueue<Node> {


    public IncreasingWeightRandomizedNodeQueue(int numberOfNoeds)
    {
        super(true);
        for (int v = 0; v < numberOfNoeds; v++) {
            setWeight(new Node(v), 0);
        }
    }

    public IncreasingWeightRandomizedNodeQueue(NodeSet nodes)
    {
        super(true);
        for (Node v : nodes)
        {
            setWeight(v,0);
        }
    }
}
