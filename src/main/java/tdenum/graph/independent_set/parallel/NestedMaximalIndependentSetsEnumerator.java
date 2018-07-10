package tdenum.graph.independent_set.parallel;

import tdenum.common.IO.result_printer.IResultPrinter;
import tdenum.common.cache.ICache;
import tdenum.graph.data_structures.weighted_queue.IWeightedQueue;
import tdenum.graph.graphs.succinct_graphs.ISuccinctGraphRepresentation;
import tdenum.graph.graphs.succinct_graphs.separator_graph.parallel.DemonSeparatorGraph;
import tdenum.graph.independent_set.parallel.nested.AuxiliaryMaximalIndependentSetsEnumerator;
import tdenum.graph.independent_set.parallel.nested.BusyWaitAuxiliaryMaximalIndependentSetsEnumerator;
import tdenum.graph.independent_set.parallel.nested.NestedEnumeratorType;
import tdenum.graph.independent_set.parallel.nested.TaskManager;
import tdenum.graph.independent_set.scoring.IIndependentSetScorer;
import tdenum.graph.independent_set.set_extender.IIndependentSetExtender;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

import static tdenum.graph.independent_set.parallel.nested.NestedEnumeratorType.BUSY_WAIT;

public class NestedMaximalIndependentSetsEnumerator<T> extends ParallelMaximalIndependentSetsEnumerator<T> {




    protected int timeout = 0;
    protected NestedEnumeratorType enumeratorType = BUSY_WAIT;

    protected TaskManager taskManager = new TaskManager();

    protected ExecutorService demon = Executors.newSingleThreadExecutor();
    protected Future<?> f;
    protected List<AuxiliaryMaximalIndependentSetsEnumerator> enumeratorList = new ArrayList<>();
    protected ExecutorService enumerators = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    protected List<Future<?>> futures = new ArrayList<>();

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public void setThreadNumber(int threadNumber)
    {
        taskManager.setThreadNumber(threadNumber);
        enumeratorList.clear();
        for (int i =0; i < threadNumber-1; i++)
        {
            enumeratorList.add(generateAuxEnumerator(enumeratorType, i));
        }
        enumerators = Executors.newFixedThreadPool(threadNumber);

    }

    public void setEnumeratorType(NestedEnumeratorType enumeratorType)
    {
        this.enumeratorType = enumeratorType;
        setThreadNumber(taskManager.getThreadNumber());
    }

    protected AuxiliaryMaximalIndependentSetsEnumerator generateAuxEnumerator(NestedEnumeratorType enumeratorType, int id) {
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
        enumerator.setQ(Q);
        enumerator.setP(P);
        enumerator.setV(V);
        enumerator.setExtender(extender);
        enumerator.setScorer(scorer);
        enumerator.setResultPrinter(resultPrinter);
        enumerator.setSetsNotExtended(setsNotExtended);
        enumerator.setGraph(graph);
        if(graph!=null)
        {
            DemonSeparatorGraph demon = (DemonSeparatorGraph)graph;
            demon.addId(enumerator.getId());
        }

        return enumerator;
    }

    @Override
    public void setQ(IWeightedQueue<Set<T>> q) {
        super.setQ(q);
        for (AuxiliaryMaximalIndependentSetsEnumerator enumerator : enumeratorList) {
            enumerator.setQ(q);
        }
    }

    @Override
    public void setP(Set<Set<T>> p) {
        super.setP(p);
        for (AuxiliaryMaximalIndependentSetsEnumerator enumerator : enumeratorList) {
            enumerator.setP(p);
        }
    }

    @Override
    public void setGraph(ISuccinctGraphRepresentation<T> graph) {
        super.setGraph(graph);
        for (AuxiliaryMaximalIndependentSetsEnumerator enumerator : enumeratorList) {
            enumerator.setGraph(graph);
            DemonSeparatorGraph demon = (DemonSeparatorGraph)graph;
            demon.addId(enumerator.getId());
        }
    }

    @Override
    public void setCache(ICache cache) {
        super.setCache(cache);
        for (AuxiliaryMaximalIndependentSetsEnumerator enumerator : enumeratorList) {
            enumerator.setCache(cache);
        }
    }

    @Override
    public void setSetsNotExtended(Set<Set<T>> setsNotExtended) {
        super.setSetsNotExtended(setsNotExtended);
        for (AuxiliaryMaximalIndependentSetsEnumerator enumerator : enumeratorList) {
            enumerator.setSetsNotExtended(setsNotExtended);
        }
    }

    @Override
    public void setResultPrinter(IResultPrinter<Set<T>> resultPrinter) {
        super.setResultPrinter(resultPrinter);
        for (AuxiliaryMaximalIndependentSetsEnumerator enumerator : enumeratorList) {
            enumerator.setResultPrinter(resultPrinter);
        }
    }

    @Override
    public void setExtender(IIndependentSetExtender extender) {
        super.setExtender(extender);
        for (AuxiliaryMaximalIndependentSetsEnumerator enumerator : enumeratorList) {
            enumerator.setExtender(extender);
        }
    }

    @Override
    public void setScorer(IIndependentSetScorer scorer)
    {
        super.setScorer(scorer);
        for (AuxiliaryMaximalIndependentSetsEnumerator enumerator : enumeratorList)
        {
            enumerator.setScorer(scorer);
        }
    }

    @Override
    protected boolean timeLimitReached() {

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
    protected void runFullEnumeration() {

        f = demon.submit((DemonSeparatorGraph)graph);
        demon.shutdown();
        for (AuxiliaryMaximalIndependentSetsEnumerator enumerator: enumeratorList)
        {
            futures.add(enumerators.submit(enumerator));
        }
        try {
            demon.awaitTermination(timeout, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            demon.shutdownNow();
            enumerators.shutdownNow();
            for (Future future: futures)
            {
                future.cancel(true);
            }
            f.cancel(true);
        }
        System.out.println("demon finished");
        futures.add(enumerators.submit(generateAuxEnumerator(this.enumeratorType, taskManager.getThreadNumber()-1)));
        enumerators.shutdown();
        try {
            enumerators.awaitTermination(timeout, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            for (Future future: futures)
            {
                enumerators.shutdownNow();
                if(!future.isCancelled())
                {
                    future.cancel(true);
                }

            }
        }
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

    @Override
    public void doFirstStep() {
        newSetFound(extender.extendToMaxIndependentSet(new HashSet<T>()));
    }


}
