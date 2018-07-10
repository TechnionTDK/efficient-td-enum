package tdk_enum.factories.maximal_independent_sets_enumerator_factory;

import tdk_enum.RunningMode;
import tdk_enum.factories.TDEnumFactory;
import tdk_enum.graph.independent_set.IMaximalIndependentSetsEnumerator;
import tdk_enum.graph.independent_set.parallel.*;
import tdk_enum.graph.independent_set.parallel.capacity.CapacityHorizontalMaximalIndependentSetsEnumerator;
import tdk_enum.graph.independent_set.parallel.capacity.CapacityHorizontalWithDemonParallelMaximalIndependentSetsEnumerator;
import tdk_enum.graph.independent_set.parallel.capacity.CapacityNestedMaximalIndependentSetsEnumerator;
import tdk_enum.graph.independent_set.parallel.capacity.CapcityParallelMaximalIndependentSetsEnumerator;

import java.util.concurrent.ConcurrentHashMap;

public class ParallelMaximalIndependentSetsEnumeratorFactory implements IMaximalIndependentSetsEnumeratorFactory {


    IMaximalIndependentSetsEnumerator enumerator;

    @Override
    public IMaximalIndependentSetsEnumerator produce() {


        int threadNum = Integer.valueOf(TDEnumFactory.getProperties().getProperty("threadNum",  Integer.toString(Runtime.getRuntime().availableProcessors())));
        System.out.println("number of actual cores " + threadNum);
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", String.valueOf(threadNum));
        if(enumerator == null)
        {


            if (RunningMode.valueOf(TDEnumFactory.getProperties().getProperty("mode")).equals(RunningMode.MIXED))
            {
                switch (ParallelMISEnumeratorType.valueOf(TDEnumFactory.getProperties().getProperty("parallelMisEnumerator"))) {
                    case BASElINE: {
                        CapcityParallelMaximalIndependentSetsEnumerator capcityParallelMaximalIndependentSetsEnumerator = new CapcityParallelMaximalIndependentSetsEnumerator();
                        capcityParallelMaximalIndependentSetsEnumerator.setCapacity(TDEnumFactory.getSingleThreadResults());
                        enumerator = inject(capcityParallelMaximalIndependentSetsEnumerator);

                        break;
                    }
                    case HORIZONTAL: {
                        CapacityHorizontalMaximalIndependentSetsEnumerator capacityHorizontalMaximalIndependentSetsEnumerator = new CapacityHorizontalMaximalIndependentSetsEnumerator();
                        capacityHorizontalMaximalIndependentSetsEnumerator.setCapacity(TDEnumFactory.getSingleThreadResults());
                        enumerator = inject(capacityHorizontalMaximalIndependentSetsEnumerator);
                        break;
                    }
                    case HORIZONTAL_DEMON: {
                        CapacityHorizontalWithDemonParallelMaximalIndependentSetsEnumerator capacityHorizontalWithDemonParallelMaximalIndependentSetsEnumerator = new CapacityHorizontalWithDemonParallelMaximalIndependentSetsEnumerator();
                        capacityHorizontalWithDemonParallelMaximalIndependentSetsEnumerator.setCapacity(TDEnumFactory.getSingleThreadResults());
                        enumerator = inject(capacityHorizontalWithDemonParallelMaximalIndependentSetsEnumerator);
                        break;
                    }
                    case NESTED:
                    {
                        CapacityNestedMaximalIndependentSetsEnumerator capacityNestedMaximalIndependentSetsEnumerator = new CapacityNestedMaximalIndependentSetsEnumerator();
                        capacityNestedMaximalIndependentSetsEnumerator.setCapacity(TDEnumFactory.getSingleThreadResults());

                        capacityNestedMaximalIndependentSetsEnumerator.setThreadNumber(threadNum-1);
                        int timeout = Integer.valueOf(TDEnumFactory.getProperties().getProperty("time_limit"));
                        capacityNestedMaximalIndependentSetsEnumerator.setTimeout(timeout);

                        enumerator = inject(capacityNestedMaximalIndependentSetsEnumerator);
                    }

                }
                return enumerator;

            }

            switch (ParallelMISEnumeratorType.valueOf(TDEnumFactory.getProperties().getProperty("parallelMisEnumerator")))
            {
                case BASElINE:
                {
                    enumerator = inject(new ParallelMaximalIndependentSetsEnumerator());
                    break;
                }
                case HORIZONTAL:
                {
                    enumerator = inject(new HorizontalParallelMaximalIndependentSetsEnumerator());
                    break;
                }
                case HORIZONTAL_DEMON:
                {
                    enumerator = inject(new HorizontalWithDemonParallelMaximalIndependentSetsEnumerator());
                    break;
                }
                case BASELINE_THREADPOOL:
                {
                    enumerator = inject(new ThreadPoolParallelMaximalIndependentSetsEnumerator());
                    break;
                }
                case HORIZONTAL_THREADPOOL:
                {
                    enumerator = inject(new HorizontalThreadPoolMaximalIndependentSetsEnumerator());
                }
                case NESTED:
                {
                    NestedMaximalIndependentSetsEnumerator nestedMaximalIndependentSetsEnumerator = new NestedMaximalIndependentSetsEnumerator();

                    nestedMaximalIndependentSetsEnumerator.setThreadNumber(threadNum-1);
                    int timeout = Integer.valueOf(TDEnumFactory.getProperties().getProperty("time_limit"));
                    nestedMaximalIndependentSetsEnumerator.setTimeout(timeout);

                    enumerator = inject(nestedMaximalIndependentSetsEnumerator);

                }
            }

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
