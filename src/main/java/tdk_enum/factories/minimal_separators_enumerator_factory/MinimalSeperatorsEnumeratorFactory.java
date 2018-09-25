package tdk_enum.factories.minimal_separators_enumerator_factory;

import tdk_enum.common.configuration.TDKSeperatorsEnumConfiguration;
import tdk_enum.enumerators.separators.IMinimalSeparatorsEnumerator;
import tdk_enum.enumerators.separators.parallel.HorizontalMinimalSepratorsEnumerator;
import tdk_enum.enumerators.separators.parallel.NestedMinimalSeperatorsEnumerator;
import tdk_enum.enumerators.separators.parallel.ParallelMinimalSeparatorsEnumerator;
import tdk_enum.enumerators.separators.single_thread.MinimalSeparatorsEnumerator;
import tdk_enum.enumerators.triangulation.IMinimalTriangulationsEnumerator;
import tdk_enum.factories.TDKEnumFactory;
import tdk_enum.factories.separator_scorer_factory.SeparatorScorerFactory;
import tdk_enum.graph.data_structures.weighted_queue.parallel.ConcurrentQueueSet;
import tdk_enum.graph.data_structures.weighted_queue.single_thread.QueueSet;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;

public class MinimalSeperatorsEnumeratorFactory implements IMinimalSeparatorsEnumeratorFactory {
    @Override
    public IMinimalSeparatorsEnumerator produce() {
        if(TDKEnumFactory.getConfiguration() instanceof  TDKSeperatorsEnumConfiguration)
        {
            TDKSeperatorsEnumConfiguration configuration = (TDKSeperatorsEnumConfiguration) TDKEnumFactory.getConfiguration();
            switch (configuration.getRunningMode())
            {
                case PARALLEL:
                    return produceParallelEnumerator();
                case SINGLE_THREAD:
                    return produceSingleThreadEnumerator();
            }
        }

        return produceSingleThreadEnumerator();
    }

    private IMinimalSeparatorsEnumerator produceSingleThreadEnumerator() {


        IMinimalSeparatorsEnumerator enumerator = new MinimalSeparatorsEnumerator();
        enumerator.setGraph(TDKEnumFactory.getGraph());
        enumerator.setScorer( new SeparatorScorerFactory().produce());
        enumerator.setQueue(new QueueSet<>());
        enumerator.setExtendedCollection(new HashSet<>());
        return enumerator;
    }

    private IMinimalSeparatorsEnumerator produceParallelEnumerator() {


        TDKSeperatorsEnumConfiguration configuration = (TDKSeperatorsEnumConfiguration) TDKEnumFactory.getConfiguration();

        IMinimalSeparatorsEnumerator enumerator = null;
        switch (configuration.getParallelSeperatorsEnumeratorType())
        {
            case VANILLA:
            {

                System.out.println("number of threads for streams is " + System.getProperty("java.util.concurrent.ForkJoinPool.common.parallelism"));
                System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", Integer.toString(configuration.getThreadNumder()-1) );
                System.out.println("number of threads for streams is " + System.getProperty("java.util.concurrent.ForkJoinPool.common.parallelism"));
                enumerator = new ParallelMinimalSeparatorsEnumerator();

                break;

            }


            case HORIZONTAL:
            {
                System.out.println("number of threads for streams is " + System.getProperty("java.util.concurrent.ForkJoinPool.common.parallelism"));
                System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", Integer.toString(configuration.getThreadNumder()-1) );
                System.out.println("number of threads for streams is " + System.getProperty("java.util.concurrent.ForkJoinPool.common.parallelism"));
                enumerator = new HorizontalMinimalSepratorsEnumerator();
                break;
            }


            case NESTED:

            {
                enumerator = new NestedMinimalSeperatorsEnumerator();
                System.out.println("setting number of threads to " + configuration.getThreadNumder());
                ((NestedMinimalSeperatorsEnumerator)enumerator).setThreadNumber(configuration.getThreadNumder());
                ((NestedMinimalSeperatorsEnumerator)enumerator).setTimeout(TDKEnumFactory.getConfiguration().getTime_limit());
                break;
            }



        }

        enumerator.setGraph(TDKEnumFactory.getGraph());
        enumerator.setScorer(new SeparatorScorerFactory().produce());
        enumerator.setQueue(new ConcurrentQueueSet<>());
        enumerator.setExtendedCollection(ConcurrentHashMap.newKeySet());

        return enumerator;
    }
}
