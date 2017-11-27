package tdenum.factories.maximal_independent_sets_enumerator_factory;

import tdenum.factories.TDEnumFactory;
import tdenum.graph.independent_set.IMaximalIndependentSetsEnumerator;
import tdenum.graph.independent_set.single_thread.MaximalIndependentSetsEnumerator;
import tdenum.graph.independent_set.single_thread.RandomMaximalIndependentSetsEnumerator;
import tdenum.graph.independent_set.single_thread.improvements.*;
import tdenum.graph.independent_set.single_thread.loggable.ImprovedLoggableMaximalIndependentSetsEnumerator;
import tdenum.graph.independent_set.single_thread.loggable.LoggableImprovedJvCachingMaximalIndependentSetsEnumerator;
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
            case IMPROVED_LOGGABLE:
            {
                System.out.println("producing improved loggable MIS enumerator");
                return produceImprovedLoggableEnumerator();
            }
            case IMPROVED_JV_CACHE:
            {
                System.out.println("producing improved MIS enumerator with jv caching");
                return produceImprovedJvCachingEnumerator();
            }

            case LOGGABLE_IMPROVED_JV_CACHE:
            {
                System.out.println("producing loggable improved MIS enumerator with subset checking");
                return produceLoggableImprovedJvCachingEnumerator();
            }
            case RANDOM:
            {
                System.out.println("producing random MIS enumerator");
                return inject(new RandomMaximalIndependentSetsEnumerator());
            }
            case IMPROVED_JV_CACHE_RANDOM_FIRST:
            {
                System.out.println("producing improved MIS enumerator with jv caching, with random producing first");
                return produceImprovedJvCachingRandomFirstEnumerator();
            }
            case IMPROVED_RANDOM_FIRST:
            {
                System.out.println("producing improved MIS enumerator  with random producing first");
                return produceImprovedRandomFirstEnumerator();
            }

            case IMPROVED_K_EXTEND:
            {
                int k = Integer.valueOf(TDEnumFactory.getProperties().getProperty("k"));
                System.out.println("producing improved MIS enumerator with "+k+" extend");
                return produceImprovedKExtendEnumerator(k);
            }


        }

        return produceVanillaEnumerator();

    }



    private IMaximalIndependentSetsEnumerator produceImprovedKExtendEnumerator(int k) {
        ImprovedKExtendMaximalIndependentSetsEnumerator enumerator = new ImprovedKExtendMaximalIndependentSetsEnumerator();
        enumerator.setK(k);
        return inject(enumerator);

    }

    private IMaximalIndependentSetsEnumerator produceImprovedRandomFirstEnumerator() {
        return inject(new ImprovedRandomFirstMaximalIndependentSetsEnumerator());
    }

    private IMaximalIndependentSetsEnumerator produceLoggableImprovedJvCachingEnumerator() {
        return inject(new LoggableImprovedJvCachingMaximalIndependentSetsEnumerator());
    }

    private IMaximalIndependentSetsEnumerator produceImprovedJvCachingRandomFirstEnumerator() {


        return inject(new ImprovedJvCachingRandomFirstMaximalIndependentSetsEnumerator());
    }

    private IMaximalIndependentSetsEnumerator produceImprovedJvCachingEnumerator() {
        return  inject(new ImprovedJvCachingMaximalIndependentSetsEnumerator() );
    }

    private IMaximalIndependentSetsEnumerator produceImprovedLoggableEnumerator() {
        return  inject(new ImprovedLoggableMaximalIndependentSetsEnumerator());
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



        enumerator.setCache(TDEnumFactory.getCacheFactory().produce());
        enumerator.doFirstStep();


        return enumerator;
    }


}
