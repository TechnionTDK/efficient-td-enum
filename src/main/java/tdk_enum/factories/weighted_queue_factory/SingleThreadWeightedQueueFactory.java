package tdk_enum.factories.weighted_queue_factory;

import tdk_enum.factories.TDEnumFactory;
import tdk_enum.graph.data_structures.weighted_queue.single_thread.WeightedQueue;
import tdk_enum.graph.data_structures.weighted_queue.IWeightedQueue;
import tdk_enum.graph.triangulation.TriangulationScoringCriterion;

import static tdk_enum.graph.triangulation.TriangulationScoringCriterion.NONE;

public class SingleThreadWeightedQueueFactory implements IWeightedQueueFactory {
    @Override
    public IWeightedQueue produce() {
        if(!TriangulationScoringCriterion.valueOf(TDEnumFactory.getProperties().getProperty("t_order")).equals(NONE))
        {
            return new WeightedQueue();
        }
        return new WeightedQueue();
//        return new WeightedQueue();
    }
}
