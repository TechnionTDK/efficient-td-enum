package tdk_enum.enumerators.independent_set.parallel;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


public class HorizontalParallelMaximalIndependentSetsEnumerator<T> extends ParallelMaximalIndependentSetsEnumerator<T>{

    protected Set<Set<T>> workingSet = ConcurrentHashMap.newKeySet();

    protected Set<Set<T>> storageSet = ConcurrentHashMap.newKeySet();






    @Override
    public void executeAlgorithm() {
        mainThread = Thread.currentThread();
        doFirstStep();
        while(!storageSet.isEmpty() && !finishCondition())
        {
            iteratingNodePhase();

            while(storageSet.isEmpty() && graph.hasNextNode() && !finishCondition())
            {
                currentNode = graph.nextNode();
                V.add(currentNode);
                iteratingResultsPhase();
            }

        }
        jvCache.close();

    }



    @Override
    protected void iteratingNodePhase() {
        workingSet = storageSet;
        storageSet = ConcurrentHashMap.newKeySet();
        workingSet.parallelStream().anyMatch(mis -> {

            for(T v : V)
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
    protected void newResultFound(final Set<T> generatedSet)
    {

        if (!P.contains(generatedSet) && !workingSet.contains(generatedSet))
        {
            if(storageSet.add(generatedSet))
            {
                resultPrinter.print(generatedSet);
            }

        }
    }


}
