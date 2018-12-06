package tdk_enum.factories.enumeration.weighted_queue_factory;

import tdk_enum.common.configuration.TDKEnumConfiguration;
import tdk_enum.factories.TDKEnumFactory;
import tdk_enum.graph.data_structures.weighted_queue.IWeightedQueue;
import tdk_enum.graph.data_structures.weighted_queue.parallel.ConcurrentQueueSet;
import tdk_enum.graph.data_structures.weighted_queue.single_thread.QueueSet;

public class WeightedQueueFactory implements IWeightedQueueFactory {
    @Override
    public IWeightedQueue produce() {
        TDKEnumConfiguration configuration = TDKEnumFactory.getConfiguration();
        switch (configuration.getRunningMode())
        {
            case SINGLE_THREAD:
                return produceSingleThreadWeightedQueue();
            case PARALLEL:
                return produceParallelWeightedQueue();
            default:
                return produceSingleThreadWeightedQueue();
        }
    }

    private IWeightedQueue produceSingleThreadWeightedQueue() {
        return new QueueSet();
    }

    private IWeightedQueue produceParallelWeightedQueue() {
        return new ConcurrentQueueSet();
    }
}
