package tdenum.factories.minimal_separators_enumerator_factory;

import tdenum.factories.TDEnumFactory;
import tdenum.graph.data_structures.weighted_queue.single_thread.WeightedQueue;
import tdenum.graph.separators.IMinimalSeparatorsEnumerator;
import tdenum.graph.separators.single_thread.MinimalSeparatorsEnumerator;

import java.util.HashSet;

public class ParallelMinimalSeparatorsEnumeratorFactory implements IMinimalSeparatorsEnumeratorFactory {
    @Override
    public IMinimalSeparatorsEnumerator produce() {
        return inject(new MinimalSeparatorsEnumerator());
    }

    IMinimalSeparatorsEnumerator inject(IMinimalSeparatorsEnumerator enumerator)
    {
        enumerator.setGraph(TDEnumFactory.getGraph());
        enumerator.setScorer(TDEnumFactory.getSeparatorScorerFactory().produce());
        enumerator.setSeparatorsToExtend(new WeightedQueue());
        enumerator.setSeparatorsExtended(new HashSet<>());

        enumerator.init();
        return enumerator;
    }
}
