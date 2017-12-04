package tdenum.graph.independent_set.parallel;

import tdenum.graph.independent_set.AbstractMaximalIndependentSetsEnumerator;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


public class HorizontalParallelMaximalIndependentSetsEnumerator<T> extends ParallelMaximalIndependentSetsEnumerator<T>{

    protected Set<Set<T>> workingSet = ConcurrentHashMap.newKeySet();


    @Override
    protected void runFullEnumeration() {
        originalThread = Thread.currentThread();
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
        workingSet.parallelStream().anyMatch(mis -> {

            for(T v : V)
            {
                extendSetInDirectionOfNode(mis,v);
            }
            P.add(mis);
            return originalThread.isInterrupted();
        });


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
