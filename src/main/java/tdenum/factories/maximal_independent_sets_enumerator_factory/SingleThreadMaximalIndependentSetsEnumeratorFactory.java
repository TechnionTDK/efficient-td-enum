package tdenum.factories.maximal_independent_sets_enumerator_factory;

import tdenum.factories.TDEnumFactory;
import tdenum.graph.independent_set.IMaximalIndependentSetsEnumerator;
import tdenum.graph.independent_set.single_thread.MaximalIndependentSetsEnumerator;
import tdenum.graph.independent_set.single_thread.improvements.ImprovedMaximalIndependentSetsEnumerator;
import tdenum.graph.independent_set.single_thread.loggable.LoggableMaximalIndependentSetsEnumerator;

import java.util.HashSet;

public class SingleThreadMaximalIndependentSetsEnumeratorFactory implements IMaximalIndependentSetsEnumeratorFactory {
    @Override
    public IMaximalIndependentSetsEnumerator produce() {


        MISEnumeratorType enumeratorType = MISEnumeratorType.valueOf(
                TDEnumFactory.getProperties().getProperty("misEnumerator"));
        switch (enumeratorType)
        {
            case VANILLA:
            {
                System.out.println("producing baseline MIS enumerator");
                return produceVanillaEnumerator();
            }

            case IMPROVED:
            {
                System.out.println("producing improved MIS enumerator");
                return produceImproveEnumerator();
            }
            case VANILLA_LOGGABLE:
            {
                System.out.println("producing baseline loggable MIS enumerator");
                return produceLoggableEnumerator();
            }

        }

        return produceVanillaEnumerator();

    }

    IMaximalIndependentSetsEnumerator produceImproveEnumerator()
    {
        return inject(new ImprovedMaximalIndependentSetsEnumerator());
    }

    IMaximalIndependentSetsEnumerator produceLoggableEnumerator()
    {
        return inject(new LoggableMaximalIndependentSetsEnumerator());
    }


    IMaximalIndependentSetsEnumerator produceVanillaEnumerator()
    {
        return inject(new MaximalIndependentSetsEnumerator());
    }

    IMaximalIndependentSetsEnumerator inject(IMaximalIndependentSetsEnumerator enumerator)
    {
        enumerator.setSetsNotExtended(new HashSet<>());
        enumerator.setP(new HashSet<>());
        enumerator.setV(new HashSet());

        enumerator.setGraph(TDEnumFactory.getSeparatorGraphFactory().produce());
        enumerator.setExtender(TDEnumFactory.getSetsExtenderFactory().produce());
        enumerator.setScorer(TDEnumFactory.getSetScorerFactory().produce());

        enumerator.setQ(TDEnumFactory.getWeightedQueueFactory().produce());

        enumerator.doFirstStep();

        return enumerator;
    }


}
