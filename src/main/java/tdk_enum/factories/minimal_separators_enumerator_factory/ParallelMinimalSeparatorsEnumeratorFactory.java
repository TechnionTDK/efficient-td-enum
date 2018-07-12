package tdk_enum.factories.minimal_separators_enumerator_factory;

import tdk_enum.factories.TDEnumFactory;
import tdk_enum.graph.data_structures.weighted_queue.parallel.ConcurrentQueueSet;
import tdk_enum.graph.data_structures.weighted_queue.single_thread.QueueSet;
import tdk_enum.graph.data_structures.weighted_queue.single_thread.WeightedQueue;
import tdk_enum.graph.separators.IMinimalSeparatorsEnumerator;
import tdk_enum.graph.separators.single_thread.MinimalSeparatorsEnumerator;

import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;

public class ParallelMinimalSeparatorsEnumeratorFactory implements IMinimalSeparatorsEnumeratorFactory {
    @Override
    public IMinimalSeparatorsEnumerator produce() {
        return inject(new MinimalSeparatorsEnumerator());
    }

    IMinimalSeparatorsEnumerator inject(IMinimalSeparatorsEnumerator enumerator)
    {
        enumerator.setGraph(TDEnumFactory.getGraph());
        enumerator.setScorer(TDEnumFactory.getSeparatorScorerFactory().produce());
        enumerator.setQueue(new ConcurrentQueueSet<>());
        enumerator.setExtendedCollection(ConcurrentHashMap.newKeySet());

      //  enumerator.init();
        return enumerator;
    }
}
