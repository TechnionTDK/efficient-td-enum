package tdk_enum.enumerators.independent_set.parallel;

import tdk_enum.graph.graphs.succinct_graphs.separator_graph.parallel.DemonSeparatorGraph;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;

public class HorizontalWithDemonParallelMaximalIndependentSetsEnumerator<T> extends HorizontalParallelMaximalIndependentSetsEnumerator<T> {

    protected Set<T>  extendingSet = new HashSet<>();

    ExecutorService executor = Executors.newSingleThreadExecutor();
    Future<?> f;

    @Override
    protected boolean finishCondition() {

        if (mainThread.isInterrupted())
        {
            f.cancel(true);
            executor.shutdownNow();

            return  true;
        }
        return false;
    }

    @Override
    public void executeAlgorithm() {

        DemonSeparatorGraph demon = (DemonSeparatorGraph)graph;
        demon.addId(Thread.currentThread().getId());
        f = executor.submit(demon);
        executor.shutdown();
//        Thread demon = new Thread((DemonSeparatorGraph)graph);
//        demon.start();
        mainThread = Thread.currentThread();
        doFirstStep();
        while(!storageSet.isEmpty() && !finishCondition())
        {
            workingSet = storageSet;
            storageSet = ConcurrentHashMap.newKeySet();
            extendingSet = V;
            iteratingNodePhase();

            while(storageSet.isEmpty() && graph.hasNextNode() && !finishCondition())
            {
                Set<T> newNodes = graph.nextBatch();
                V.addAll(newNodes);
                extendingSet = newNodes;
                iteratingResultsPhase();
            }

        }

        if(!f.isCancelled())
        {
            f.cancel(true);
        }

        executor.shutdownNow();
        jvCache.close();


    }

    @Override
    protected void iteratingNodePhase() {
        workingSet.parallelStream().anyMatch(mis -> {

            for(T v : extendingSet)
            {
                if(finishCondition())
                {
                    return true;
                }
                tryGenerateNewResult(v, mis);
            }
            P.add(mis);
            return finishCondition();
        });

    }

    @Override
    protected void iteratingResultsPhase()
    {
        P.parallelStream().anyMatch(extendedMis ->{
            for(T v : extendingSet)
            {
                if(finishCondition())
                {
                    return true;
                }
                tryGenerateNewResult(v, extendedMis);
            }
            return finishCondition();}
        );
    }

}
