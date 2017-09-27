package tdenum.factories.weighted_queue_factory;

import tdenum.graph.data_structures.weighted_queue.IWeightedQueue;
import tdenum.graph.data_structures.weighted_queue.parallel.ConcurrentQueueSet;

public class ParallelWeightedQueueFactory implements IWeightedQueueFactory {


    IWeightedQueue queue;

    @Override
    public IWeightedQueue produce() {


        if (queue==null)
        {
            queue = new ConcurrentQueueSet();
        }
        return  queue;
    }
}
