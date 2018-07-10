package tdenum.graph.independent_set.parallel;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

public class ThreadPoolParallelMaximalIndependentSetsEnumerator<T> extends ParallelMaximalIndependentSetsEnumerator<T> {


    protected ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());


    protected void runFullEnumeration()
    {
        while(!Q.isEmpty() && !timeLimitReached())
        {
            getNextSetToExtend();
            handleIterationNodePhase();

            while(setsNotExtended.isEmpty() && graph.hasNextNode() && !timeLimitReached())
            {
                currentNode = graph.nextNode();
                V.add(currentNode);
                handleIterationSetPhase();
            }

        }

        executor.shutdownNow();


    }

    protected class extendTask implements Callable<Boolean>
    {

        T node;
        Set<T> mis;
        public extendTask(T node, Set<T> mis)
        {
            this.node = node;
            this.mis = mis;
        }



        @Override
        public Boolean call() throws Exception {
            extendSetInDirectionOfNode(mis, node);
            return true;

        }
    }

    protected void handleIterationNodePhase()
    {
//        List<Future> futures = new ArrayList<>();
        for (T v : V)
        {
            if (timeLimitReached())
            {
                break;
            }
            //                futures.add(demon.submit(new extendTask(v, mis)));
            executor.submit(new extendTask(v, currentSet));
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


    protected void handleIterationSetPhase(){
//        List<Future> futures = new ArrayList<>();
        for (Set<T> mis : P)
        {
            if (timeLimitReached())
            {
                break;
            }
//                futures.add(demon.submit(new extendTask(v, mis)));
            executor.submit(new extendTask(currentNode, mis));
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
        if(timeLimitReached())
        {
            executor.shutdownNow();
        }
        executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
//        P.parallelStream().anyMatch(extendedMis ->{ extendSetInDirectionOfNode(extendedMis, currentNode);
//            return mainThread.isInterrupted();}
//        );
    }
}
