package tdenum.factories.maximal_independent_sets_enumerator_factory;

import tdenum.factories.TDEnumFactory;
import tdenum.graph.independent_set.IMaximalIndependentSetsEnumerator;
import tdenum.graph.independent_set.parallel.ParallelMaximalIndependentSetsEnumerator;

import java.util.concurrent.ConcurrentHashMap;

public class ParallelMaximalIndependentSetsEnumeratorFactory implements IMaximalIndependentSetsEnumeratorFactory {


    IMaximalIndependentSetsEnumerator enumerator;

    @Override
    public IMaximalIndependentSetsEnumerator produce() {
        if(enumerator == null)
        {
            enumerator = inject(new ParallelMaximalIndependentSetsEnumerator());
        }
        return  enumerator;
    }

    IMaximalIndependentSetsEnumerator inject(IMaximalIndependentSetsEnumerator enumerator)
    {


        enumerator.setCache(TDEnumFactory.getCacheFactory().produce());
        enumerator.setExtender(TDEnumFactory.getSetsExtenderFactory().produce());
        enumerator.setScorer(TDEnumFactory.getSetScorerFactory().produce());
        enumerator.setGraph(TDEnumFactory.getSeparatorGraphFactory().produce());
        enumerator.setP(ConcurrentHashMap.newKeySet());
        enumerator.setQ(TDEnumFactory.getWeightedQueueFactory().produce());
        enumerator.setV(ConcurrentHashMap.newKeySet());
        enumerator.setSetsNotExtended(ConcurrentHashMap.newKeySet());

        enumerator.doFirstStep();
        return enumerator;
    }
}
