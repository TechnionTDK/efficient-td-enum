package tdk_enum.factories.minimal_separators_enumerator_factory;

import tdk_enum.factories.TDEnumFactory;
import tdk_enum.graph.data_structures.weighted_queue.single_thread.QueueSet;
import tdk_enum.graph.data_structures.weighted_queue.single_thread.WeightedQueue;
import tdk_enum.graph.separators.IMinimalSeparatorsEnumerator;
import tdk_enum.graph.separators.single_thread.MinimalSeparatorsEnumerator;

import java.util.HashSet;

public class SingleThreadMinimalSeparatorsEnumeratorFactory implements IMinimalSeparatorsEnumeratorFactory {
    @Override
    public IMinimalSeparatorsEnumerator produce() {
        return inject(new MinimalSeparatorsEnumerator());
    }

    IMinimalSeparatorsEnumerator inject(IMinimalSeparatorsEnumerator enumerator)
    {
        enumerator.setGraph(TDEnumFactory.getGraph());
        enumerator.setScorer(TDEnumFactory.getSeparatorScorerFactory().produce());
        enumerator.setQueue(new QueueSet<>());
        enumerator.setExtendedCollection(new HashSet<>());

//        enumerator.init();
        return enumerator;
    }
}
