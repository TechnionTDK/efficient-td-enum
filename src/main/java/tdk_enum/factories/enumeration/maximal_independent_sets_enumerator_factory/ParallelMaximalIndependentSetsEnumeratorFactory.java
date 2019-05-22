package tdk_enum.factories.enumeration.maximal_independent_sets_enumerator_factory;

import tdk_enum.common.Utils;
import tdk_enum.common.configuration.config_types.EnumerationPurpose;
import tdk_enum.common.configuration.config_types.ParallelMISEnumeratorType;
import tdk_enum.factories.TDKEnumFactory;
import tdk_enum.enumerators.independent_set.IMaximalIndependentSetsEnumerator;
import tdk_enum.enumerators.independent_set.parallel.*;
import tdk_enum.enumerators.independent_set.parallel.capacity.CapacityHorizontalMaximalIndependentSetsEnumerator;
import tdk_enum.enumerators.independent_set.parallel.capacity.CapacityHorizontalWithDemonParallelMaximalIndependentSetsEnumerator;
import tdk_enum.enumerators.independent_set.parallel.capacity.CapacityNestedMaximalIndependentSetsEnumerator;
import tdk_enum.enumerators.independent_set.parallel.capacity.CapcityParallelMaximalIndependentSetsEnumerator;
import tdk_enum.factories.enumeration.cache_factory.CacheFactory;
import tdk_enum.factories.enumeration.separator_graph_factory.SeparatorGraphFactory;
import tdk_enum.factories.enumeration.sets_extender_factory.SetsExtenderFactory;
import tdk_enum.factories.enumeration.weighted_queue_factory.WeightedQueueFactory;

import java.util.concurrent.ConcurrentHashMap;

import static tdk_enum.common.configuration.config_types.EnumerationPurpose.STANDALONE;
import static tdk_enum.common.configuration.config_types.ParallelMISEnumeratorType.HORIZONTAL;

public class ParallelMaximalIndependentSetsEnumeratorFactory implements IMaximalIndependentSetsEnumeratorFactory {


    IMaximalIndependentSetsEnumerator enumerator;

    @Override
    public IMaximalIndependentSetsEnumerator produce() {


        EnumerationPurpose enumerationPropose = (EnumerationPurpose) Utils.getFieldValue(TDKEnumFactory.getConfiguration(), "enumerationPurpose", STANDALONE);

//        if(enumerationPropose.equals(BENCHMARK_COMPARE))
//        {
//            return produceCapcityEnumerator();
//        }


        ParallelMISEnumeratorType parallelMISEnumeratorType = (ParallelMISEnumeratorType)
                Utils.getFieldValue(TDKEnumFactory.getConfiguration(), "parallelMISEnumeratorType", HORIZONTAL);
        switch (parallelMISEnumeratorType)
        {
            case BASElINE:
            {
                return inject(new ParallelMaximalIndependentSetsEnumerator());

            }
            case HORIZONTAL:
            {
                return inject(new HorizontalParallelMaximalIndependentSetsEnumerator());

            }
            case DOUBLE_BUFFER_CKK:
            {
                return inject(new HorizontalWithDemonParallelMaximalIndependentSetsEnumerator());

            }
            case BASELINE_THREADPOOL:
            {
                return inject(new ThreadPoolParallelMaximalIndependentSetsEnumerator());

            }
            case HORIZONTAL_THREADPOOL:
            {
                return inject(new HorizontalThreadPoolMaximalIndependentSetsEnumerator());
            }
            case QUEUE_POLLING_CKK:
            {
                NestedMaximalIndependentSetsEnumerator nestedMaximalIndependentSetsEnumerator = new NestedMaximalIndependentSetsEnumerator();

                nestedMaximalIndependentSetsEnumerator.setThreadNumber(Runtime.getRuntime().availableProcessors()-1);
                int timeout = Integer.valueOf(TDKEnumFactory.getConfiguration().getTime_limit());
                nestedMaximalIndependentSetsEnumerator.setTimeout(timeout);

                return inject(nestedMaximalIndependentSetsEnumerator);

            }
            default:
            {
                return inject(new HorizontalParallelMaximalIndependentSetsEnumerator());
            }
        }

    }

    private IMaximalIndependentSetsEnumerator produceCapcityEnumerator() {




        ParallelMISEnumeratorType parallelMISEnumeratorType = (ParallelMISEnumeratorType)
                Utils.getFieldValue(TDKEnumFactory.getConfiguration(), "parallelMISEnumeratorType", HORIZONTAL);

        switch (parallelMISEnumeratorType) {
            case BASElINE: {
                CapcityParallelMaximalIndependentSetsEnumerator capcityParallelMaximalIndependentSetsEnumerator = new CapcityParallelMaximalIndependentSetsEnumerator();
                capcityParallelMaximalIndependentSetsEnumerator.setCapacity(TDKEnumFactory.getBenchMarkResults());
                return inject(capcityParallelMaximalIndependentSetsEnumerator);


            }
            case HORIZONTAL: {
                CapacityHorizontalMaximalIndependentSetsEnumerator capacityHorizontalMaximalIndependentSetsEnumerator = new CapacityHorizontalMaximalIndependentSetsEnumerator();
                capacityHorizontalMaximalIndependentSetsEnumerator.setCapacity(TDKEnumFactory.getBenchMarkResults());
                return inject(capacityHorizontalMaximalIndependentSetsEnumerator);

            }
            case DOUBLE_BUFFER_CKK: {
                CapacityHorizontalWithDemonParallelMaximalIndependentSetsEnumerator capacityHorizontalWithDemonParallelMaximalIndependentSetsEnumerator = new CapacityHorizontalWithDemonParallelMaximalIndependentSetsEnumerator();
                capacityHorizontalWithDemonParallelMaximalIndependentSetsEnumerator.setCapacity(TDKEnumFactory.getBenchMarkResults());
                return inject(capacityHorizontalWithDemonParallelMaximalIndependentSetsEnumerator);

            }
            case QUEUE_POLLING_CKK:
            {
                CapacityNestedMaximalIndependentSetsEnumerator capacityNestedMaximalIndependentSetsEnumerator = new CapacityNestedMaximalIndependentSetsEnumerator();
                capacityNestedMaximalIndependentSetsEnumerator.setCapacity(TDKEnumFactory.getBenchMarkResults());

                capacityNestedMaximalIndependentSetsEnumerator.setThreadNumber(Runtime.getRuntime().availableProcessors()-1);
                int timeout = Integer.valueOf(TDKEnumFactory.getConfiguration().getTime_limit());
                capacityNestedMaximalIndependentSetsEnumerator.setTimeout(timeout);

                return inject(capacityNestedMaximalIndependentSetsEnumerator);
            }

            default:
            {
                CapacityHorizontalMaximalIndependentSetsEnumerator capacityHorizontalMaximalIndependentSetsEnumerator = new CapacityHorizontalMaximalIndependentSetsEnumerator();
                capacityHorizontalMaximalIndependentSetsEnumerator.setCapacity(TDKEnumFactory.getBenchMarkResults());
                return inject(capacityHorizontalMaximalIndependentSetsEnumerator);
            }

        }

    }

    IMaximalIndependentSetsEnumerator inject(IMaximalIndependentSetsEnumerator enumerator)
    {


        enumerator.setCache(new CacheFactory().produce());
        enumerator.setGenerator(new SetsExtenderFactory().produce());
       // enumerator.setScorer(TDKEnumFactory.getSetScorerFactory().produce());
        enumerator.setGraph(new SeparatorGraphFactory().produce());
        enumerator.setExtendedCollection(ConcurrentHashMap.newKeySet());
        enumerator.setQueue(new WeightedQueueFactory().produce());
        enumerator.setV(ConcurrentHashMap.newKeySet());
//        enumerator.setSetsNotExtended(ConcurrentHashMap.newKeySet());



//        enumerator.doFirstStep();
        return enumerator;
    }
}
