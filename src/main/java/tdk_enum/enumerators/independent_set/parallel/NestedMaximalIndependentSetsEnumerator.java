package tdk_enum.enumerators.independent_set.parallel;

import tdk_enum.common.cache.Cache;
import tdk_enum.common.cache.ICache;
import tdk_enum.common.configuration.config_types.EnumerationPurpose;
import tdk_enum.enumerators.common.AbstractEnumerator;
import tdk_enum.enumerators.common.DefaultBuilder;
import tdk_enum.enumerators.common.IEnumerator;
import tdk_enum.enumerators.common.nested.AbstractNestetEnumerator;
import tdk_enum.graph.graphs.chordal_graph.IChordalGraph;
import tdk_enum.graph.graphs.succinct_graphs.ISuccinctGraphRepresentation;
import tdk_enum.graph.graphs.succinct_graphs.separator_graph.parallel.DemonSeparatorGraph;
import tdk_enum.enumerators.independent_set.AbstractMaximalIndependentSetsEnumerator;
import tdk_enum.enumerators.independent_set.IMaximalIndependentSetsEnumerator;
import tdk_enum.enumerators.independent_set.parallel.nested.AuxiliaryMaximalIndependentSetsEnumerator;
import tdk_enum.enumerators.independent_set.parallel.nested.BusyWaitAuxiliaryMaximalIndependentSetsEnumerator;
import tdk_enum.common.configuration.config_types.TaskManagerType;

import java.util.*;
import java.util.concurrent.*;

public class NestedMaximalIndependentSetsEnumerator<T> extends AbstractNestetEnumerator <T, Set<T>, ISuccinctGraphRepresentation<T>> implements IMaximalIndependentSetsEnumerator<T>{

    protected ICache jvCache = new Cache();


    protected ExecutorService demon = Executors.newSingleThreadExecutor();
    protected Future<?> f;



    public NestedMaximalIndependentSetsEnumerator()
    {
        super();
        defaultBuilder = new DefaultBuilder(HashSet::new);
    }


    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }


//    @Override
//    public void setThreadNumber(int threadNumber)
//    {
//        taskManager.setThreadNumber(threadNumber);
//        enumeratorList.clear();
//        for (int i =0; i < threadNumber; i++)
//        {
//            enumeratorList.add(generateAuxEnumerator(enumeratorType, i));
//        }
//        enumerators = Executors.newFixedThreadPool(threadNumber);
//
//
//    }


    @Override
    protected AbstractEnumerator generateAuxEnumerator(TaskManagerType enumeratorType, int id) {
        AuxiliaryMaximalIndependentSetsEnumerator enumerator;
        switch (enumeratorType){
            case BUSY_WAIT:
            {
                enumerator = new BusyWaitAuxiliaryMaximalIndependentSetsEnumerator();
                break;
            }
            default:
            {
                enumerator = new BusyWaitAuxiliaryMaximalIndependentSetsEnumerator();
                break;
            }
        }
        enumerator.setId(id);
        enumerator.setTaskManager(taskManager);
        enumerator.setCache(jvCache);
        enumerator.setQueue(Q);
        enumerator.setExtendedCollection(P);
        enumerator.setV(V);
        enumerator.setGenerator(generator);

        enumerator.setResultPrinter(resultPrinter);
        enumerator.setPurpose(purpose);

        enumerator.setGraph(graph);
        if(graph!=null)
        {
            DemonSeparatorGraph demon = (DemonSeparatorGraph)graph;
            demon.addId(enumerator.getId());
        }

        return enumerator;
    }


    @Override
    public void setCache(ICache cache) {
        this.jvCache = cache;
        for (IEnumerator enumerator : enumeratorList) {
            ((AbstractMaximalIndependentSetsEnumerator)enumerator).setCache(cache);
        }
    }



    @Override
    public void setGraph(ISuccinctGraphRepresentation<T> graph) {
        super.setGraph(graph);
        for (IEnumerator enumerator : enumeratorList) {
            enumerator.setGraph(graph);
            DemonSeparatorGraph demon = (DemonSeparatorGraph)graph;
            demon.addId(enumerator.getId());
        }
    }


    @Override
    protected boolean finishCondition() {

        if (Thread.currentThread().isInterrupted())
        {
            enumerators.shutdownNow();
            for (Future future : futures)
            {
                if(!future.isCancelled())
                {
                    future.cancel(true);
                }

            }
            demon.shutdownNow();
            if(!f.isCancelled())
            {
                f.cancel(true);
            }



            return  true;
        }
        return false;
    }



    @Override
    public void executeAlgorithm() {

        doFirstStep();
        f = demon.submit((DemonSeparatorGraph)graph);
        demon.shutdown();


        try {
            futures = enumerators.invokeAll(callables);
        } catch (InterruptedException e) {
            e.printStackTrace();
            demon.shutdownNow();
            enumerators.shutdownNow();
            for (Future future: futures)
            {
                if(!future.isCancelled())
                {
                    future.cancel(true);
                }
            }
            if(!f.isCancelled())
            {
                f.cancel(true);
            }
        }

//        for (IEnumerator enumerator: enumeratorList)
//        {
//            futures.add(enumerators.submit((Callable<Object>) enumerator));
//        }
//        try {
//            demon.awaitTermination(timeout, TimeUnit.SECONDS);
//        } catch (InterruptedException e) {
//            demon.shutdownNow();
//            enumerators.shutdownNow();
//            for (Future future: futures)
//            {
//                future.cancel(true);
//            }
//            f.cancel(true);
//        }
//        System.out.println("demon finished");
        //futures.add(enumerators.submit((Callable<Object>) generateAuxEnumerator(this.enumeratorType, taskManager.getThreadNumber()-1)));
//        try {
//            futures.addAll(enumerators.invokeAll(Arrays.asList((Callable<Object>)generateAuxEnumerator(this.enumeratorType, taskManager.getThreadNumber()-1))));
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        enumerators.shutdown();
//        try {
//            enumerators.awaitTermination(timeout, TimeUnit.SECONDS);
//        } catch (InterruptedException e) {
//            for (Future future: futures)
//            {
//                enumerators.shutdownNow();
//                if(!future.isCancelled())
//                {
//                    future.cancel(true);
//                }
//
//            }
//        }

        jvCache.close();




    }

    @Override
    protected void newResultFound(Set<T> c)
    {
        if (!P.contains(c) && Q.add(c))
        {
            //assume Q checks before insertion
            //  currentEnumResult = c;
            resultPrinter.print(c);
        }
    }


    @Override
    protected void doFirstStep()
    {
        Set<T> a = defaultBuilder.createInstance();
        Set<T> c = generator.generateNew(graph, a);
        newResultFound(c);
    }

//    @Override
//    public void doFirstStep() {
//        newSetFound(extender.extendToMaxIndependentSet(new HashSet<T>()));
//  }


}
