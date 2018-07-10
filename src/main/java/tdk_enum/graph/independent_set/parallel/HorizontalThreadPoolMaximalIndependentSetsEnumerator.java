package tdk_enum.graph.independent_set.parallel;

import java.util.Set;
import java.util.concurrent.*;

public class HorizontalThreadPoolMaximalIndependentSetsEnumerator<T> extends ThreadPoolParallelMaximalIndependentSetsEnumerator<T> {
    protected Set<Set<T>> workingSet = ConcurrentHashMap.newKeySet();


    @Override
    protected void runFullEnumeration() {
        while(!setsNotExtended.isEmpty() && !timeLimitReached())
        {
            handleIterationNodePhase();

            while(setsNotExtended.isEmpty() && graph.hasNextNode() && !timeLimitReached())
            {
                currentNode = graph.nextNode();
                V.add(currentNode);
                handleIterationSetPhase();
            }

        }

    }

    @Override
    protected void handleIterationNodePhase() {
        workingSet = setsNotExtended;
        setsNotExtended = ConcurrentHashMap.newKeySet();
//        List<Future> futures = new ArrayList<>();
        for (Set<T> mis : workingSet)
        {
            for (T v : V)
            {
                if (timeLimitReached())
                {
                    break;
                }
//                futures.add(demon.submit(new extendTask(v, mis)));
                executor.submit(new extendTask(v, mis));
            }
            P.add(mis);
        }

//        for (Future future : futures)
//        {
//            if (timeLimitReached())
//            {
//                break;
//            }
//            try {
//                future.get();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
//        }
        executor.shutdown();
        while(!timeLimitReached() && !executor.isTerminated())
        {
            try {
                executor.awaitTermination(50, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if(timeLimitReached())
        {
            executor.shutdownNow();
        }
        executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());




    }

    @Override
    protected void parallelNewSetFound(final Set<T> generatedSet)
    {

        if (!P.contains(generatedSet) && !workingSet.contains(generatedSet))
        {
            if(setsNotExtended.add(generatedSet))
            {
                resultPrinter.print(generatedSet);
            }

        }
    }
}

