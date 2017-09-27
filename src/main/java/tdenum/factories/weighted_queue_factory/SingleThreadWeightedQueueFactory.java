package tdenum.factories.weighted_queue_factory;

import tdenum.factories.TDEnumFactory;
import tdenum.graph.data_structures.weighted_queue.single_thread.QueueSet;
import tdenum.graph.data_structures.weighted_queue.single_thread.WeightedQueue;
import tdenum.graph.data_structures.weighted_queue.IWeightedQueue;
import tdenum.graph.triangulation.TriangulationScoringCriterion;

import static tdenum.graph.triangulation.TriangulationScoringCriterion.DIFFERENECE;

public class SingleThreadWeightedQueueFactory implements IWeightedQueueFactory {
    @Override
    public IWeightedQueue produce() {
        if(TriangulationScoringCriterion.valueOf(TDEnumFactory.getProperties().getProperty("t_order")).equals(DIFFERENECE))
        {
            return new WeightedQueue();
        }
        return new QueueSet();
//        return new WeightedQueue();
    }
}
