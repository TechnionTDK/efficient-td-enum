package tdk_enum.factories.maximal_independent_sets_enumerator_factory;

import tdk_enum.common.Utils;
import tdk_enum.common.configuration.config_types.SingleThreadMISEnumeratorType;
import tdk_enum.factories.TDKEnumFactory;
import tdk_enum.enumerators.independent_set.IMaximalIndependentSetsEnumerator;
import tdk_enum.enumerators.independent_set.single_thread.MaximalIndependentSetsEnumerator;
import tdk_enum.enumerators.independent_set.single_thread.RandomMaximalIndependentSetsEnumerator;
import tdk_enum.enumerators.independent_set.single_thread.improvements.*;
import tdk_enum.enumerators.independent_set.single_thread.loggable.ImprovedLoggableMaximalIndependentSetsEnumerator;
import tdk_enum.enumerators.independent_set.single_thread.loggable.LoggableImprovedJvCachingMaximalIndependentSetsEnumerator;
import tdk_enum.enumerators.independent_set.single_thread.loggable.LoggableMaximalIndependentSetsEnumerator;
import tdk_enum.factories.cache_factory.CacheFactory;
import tdk_enum.factories.separator_graph_factory.SeparatorGraphFactory;
import tdk_enum.factories.sets_extender_factory.SetsExtenderFactory;
import tdk_enum.factories.weighted_queue_factory.WeightedQueueFactory;

import java.util.HashSet;

import static tdk_enum.common.configuration.config_types.SingleThreadMISEnumeratorType.IMPROVED_JV_CACHE;

public class SingleThreadMaximalIndependentSetsEnumeratorFactory implements IMaximalIndependentSetsEnumeratorFactory {
    @Override
    public IMaximalIndependentSetsEnumerator produce() {


        SingleThreadMISEnumeratorType enumeratorType = (SingleThreadMISEnumeratorType)
                Utils.getFieldValue(TDKEnumFactory.getConfiguration(), "singleThreadMISEnumeratorType", IMPROVED_JV_CACHE);
//        SingleThreadMISEnumeratorType enumeratorType = SingleThreadMISEnumeratorType.valueOf(
//                TDKEnumFactory.getProperties().getProperty("misEnumerator"));
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
                int k = (int) Utils.getFieldValue(TDKEnumFactory.getConfiguration(), "k", 1);
                System.out.println("producing improved MIS enumerator with "+k+" extend");
                return produceImprovedKExtendEnumerator(k);
            }
            case NOT_COMPLETE:
            {
                return produceNotCompleteEnumerator();
            }


        }

        return produceVanillaEnumerator();

    }

    private IMaximalIndependentSetsEnumerator produceNotCompleteEnumerator() {
        return inject(new NotCompleteMaximalindependentSetsEnumerator());
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
       // enumerator.setSetsNotExtended(new HashSet<>());
        enumerator.setExtendedCollection(new HashSet<>());
        enumerator.setV(new HashSet());

        enumerator.setGraph(new SeparatorGraphFactory().produce());
        enumerator.setGenerator(new SetsExtenderFactory().produce());
       // enumerator.setScorer(TDKEnumFactory.getSetScorerFactory().produce());

        enumerator.setQueue(new WeightedQueueFactory().produce());



        enumerator.setCache(new CacheFactory().produce());
       // enumerator.doFirstStep();

        enumerator.setPurpose(TDKEnumFactory.getConfiguration().getEnumerationPurpose());

        return enumerator;
    }


}
