package tdk_enum.enumerators.independent_set.parallel.nested;

import tdk_enum.enumerators.independent_set.single_thread.improvements.ImprovedJvCachingMaximalIndependentSetsEnumerator;

import java.util.HashSet;
import java.util.Set;

public abstract class AuxiliaryMaximalIndependentSetsEnumerator<T>  extends ImprovedJvCachingMaximalIndependentSetsEnumerator<T>
        implements  Runnable{




    Set<T> extendingSet = new HashSet<>();
    Set<Set<T>> localResults = new HashSet<>();


    @Override
    public void executeAlgorithm() {


        //while (!taskManager.allDone)
        try
        {

            while (!finishCondition()) {
                while (!Q.isEmpty() && !finishCondition()) {
                    notifyWorking();
                    currentEnumResult = Q.poll();
                    if(currentEnumResult==null)
                    {
                        break;
                    }
//                    P.add(currentEnumResult);
                    localResults.add(currentEnumResult);
                    changeVIfNeeded();
                    iteratingNodePhase();

                }
                while (graph.hasNextNode(id) && !finishCondition()) {
                    notifyWorking();
                    getAndSetNextNode();
                    iteratingResultsPhase();


                }
                algFinish();
            }
//            System.out.println(id + " has finished");
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }


    }


    @Override
    protected void getAndSetNextNode()
    {
        Set<T> newNodes = graph.nextBatch(id);
        V.addAll(newNodes);
        extendingSet = newNodes;
    }




    @Override
    protected void iteratingNodePhase() {

        for (T v : V) {

            if (finishCondition()) {
                return;
            }
            tryGenerateNewResult(v, currentEnumResult);
        }
    }


    @Override
    protected void iteratingResultsPhase() {
        for (Set<T> extendedMis : localResults) {

            if (finishCondition()) {
                return;
            }
            for (T v : extendingSet) {
                if (finishCondition()) {
                    return;
                }
                tryGenerateNewResult(v, extendedMis);
            }

        }
    }

//    @Override
//    protected void parallelNewSetFound(final Set<T> generatedSet) {
//
//        if (!P.contains(generatedSet)) {
//            if (Q.add(generatedSet)) {
//                resultPrinter.print(generatedSet);
//            }
//        }
//    }

//    @Override
//    protected boolean timeLimitReached() {
//        return Thread.currentThread().isInterrupted();
//    }


   
    @Override
    public void setV(Set<T> v)
    {
        this.V = new HashSet<>();
    }



    @Override
    protected void newResultFound(Set<T> c)
    {
        if (Q.add(c))
        {
            //assume Q checks before insertion
            //  currentEnumResult = c;
            resultPrinter.print(c);
        }
    }


}