package tdk_enum.enumerators.independent_set.parallel;

import java.util.Set;
import java.util.concurrent.*;

public class ThreadPoolParallelMaximalIndependentSetsEnumerator<T> extends ParallelMaximalIndependentSetsEnumerator<T> {


    protected ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());


    protected class extendTask implements Callable<Boolean> {

        T node;
        Set<T> mis;

        public extendTask(T node, Set<T> mis) {
            this.node = node;
            this.mis = mis;
        }


        @Override
        public Boolean call() throws Exception {
            tryGenerateNewResult(node, mis);
            return true;

        }
    }


    @Override
    public void executeAlgorithm() {
        super.executeAlgorithm();
        executor.shutdownNow();
    }







    @Override
    protected void iteratingNodePhase()
    {
        for (T v : V) {
            if (finishCondition()) {
                break;
            }
            //                futures.add(demon.submit(new extendTask(v, mis)));
            executor.submit(new extendTask(v, currentEnumResult));
        }
        shutdownAndRestartExecutor();
    }

    protected void shutdownAndRestartExecutor() {
        executor.shutdown();
        while (!finishCondition() && !executor.isTerminated()) {
            try {
                executor.awaitTermination(50, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (finishCondition()) {
            executor.shutdownNow();
        }
        executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }


    @Override
    protected void iteratingResultsPhase()
    {
        for (Set<T> mis : P)
        {
            if (finishCondition())
            {
                break;
            }
//                futures.add(demon.submit(new extendTask(v, mis)));
            executor.submit(new extendTask(currentNode, mis));
        }
        shutdownAndRestartExecutor();
    }

//    protected void handleIterationNodePhase() {
////        List<Future> futures = new ArrayList<>();
//        for (T v : V) {
//            if (timeLimitReached()) {
//                break;
//            }
//            //                futures.add(demon.submit(new extendTask(v, mis)));
//            executor.submit(new extendTask(v, currentSet));
//        }
////        for (Future future : futures)
////        {
////            if (finishCondition())
////            {
////                break;
////            }
////            try {
////                future.get();
////            } catch (InterruptedException e) {
////                e.printStackTrace();
////            } catch (ExecutionException e) {
////                e.printStackTrace();
////            }
////        }
//        executor.shutdown();
//        while (!timeLimitReached() && !executor.isTerminated()) {
//            try {
//                executor.awaitTermination(50, TimeUnit.MILLISECONDS);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        if (timeLimitReached()) {
//            executor.shutdownNow();
//        }
//        executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
//
//
//    }
//




//    protected void iteratingResultsPhase(){
////        List<Future> futures = new ArrayList<>();
//        for (Set<T> mis : P)
//        {
//            if (finishCondition())
//            {
//                break;
//            }
////                futures.add(demon.submit(new extendTask(v, mis)));
//            executor.submit(new extendTask(currentNode, mis));
//        }
////        for (Future future : futures)
////        {
////            if (finishCondition())
////            {
////                break;
////            }
////            try {
////                future.get();
////            } catch (InterruptedException e) {
////                e.printStackTrace();
////            } catch (ExecutionException e) {
////                e.printStackTrace();
////            }
////        }
//        executor.shutdown();
//        while(!finishCondition() && !executor.isTerminated())
//        {
//            try {
//                executor.awaitTermination(50, TimeUnit.MILLISECONDS);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        if(finishCondition())
//        {
//            executor.shutdownNow();
//        }
//        if(finishCondition())
//        {
//            executor.shutdownNow();
//        }
//        executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
////        P.parallelStream().anyMatch(extendedMis ->{ extendSetInDirectionOfNode(extendedMis, currentNode);
////            return mainThread.isInterrupted();}
////        );
//    }



//
//    protected void runFullEnumeration()
//    {
////        while(!Q.isEmpty() && !timeLimitReached())
////        {
////            getNextSetToExtend();
////            handleIterationNodePhase();
////
////            while(setsNotExtended.isEmpty() && graph.hasNextNode() && !timeLimitReached())
////            {
////                currentNode = graph.nextNode();
////                V.add(currentNode);
////                handleIterationSetPhase();
////            }
////
////        }
//
//
//
//
//
//    }

}

