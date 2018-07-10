package tdk_enum.graph.independent_set.parallel;

import tdk_enum.graph.graphs.succinct_graphs.separator_graph.parallel.DemonSeparatorGraph;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;

public class HorizontalWithDemonParallelMaximalIndependentSetsEnumerator<T> extends HorizontalParallelMaximalIndependentSetsEnumerator<T> {

    protected Set<T>  extendingSet = new HashSet<>();

    ExecutorService executor = Executors.newSingleThreadExecutor();
    Future<?> f;

    @Override
    protected boolean timeLimitReached() {

        if (mainThread.isInterrupted())
        {
            f.cancel(true);

            return  true;
        }
        return false;
    }

    @Override
    protected void runFullEnumeration() {

        DemonSeparatorGraph demon = (DemonSeparatorGraph)graph;
        demon.addId(Thread.currentThread().getId());
        f = executor.submit(demon);
        executor.shutdown();
//        Thread demon = new Thread((DemonSeparatorGraph)graph);
//        demon.start();
        mainThread = Thread.currentThread();
        while(!setsNotExtended.isEmpty() && !timeLimitReached())
        {
            workingSet = setsNotExtended;
            setsNotExtended = ConcurrentHashMap.newKeySet();
            extendingSet = V;
            handleIterationNodePhase();

            while(setsNotExtended.isEmpty() && graph.hasNextNode() && !timeLimitReached())
            {
                Set<T> newNodes = graph.nextBatch();
                V.addAll(newNodes);
                extendingSet = newNodes;
                handleIterationSetPhase();
            }

        }

        f.cancel(true);


    }

    @Override
    protected void handleIterationNodePhase() {
        workingSet.parallelStream().anyMatch(mis -> {

            for(T v : extendingSet)
            {
                if(timeLimitReached())
                {
                    return true;
                }
                extendSetInDirectionOfNode(mis,v);
            }
            P.add(mis);
            return timeLimitReached();
        });

    }

    @Override
    protected void handleIterationSetPhase()
    {
        P.parallelStream().anyMatch(extendedMis ->{
            for(T v : extendingSet)
            {
                if(timeLimitReached())
                {
                    return true;
                }
                extendSetInDirectionOfNode(extendedMis,v);
            }
            return timeLimitReached();}
        );
    }

}
