package tdk_enum.graph.independent_set.parallel;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


public class HorizontalParallelMaximalIndependentSetsEnumerator<T> extends ParallelMaximalIndependentSetsEnumerator<T>{

    protected Set<Set<T>> workingSet = ConcurrentHashMap.newKeySet();


    @Override
    protected void runFullEnumeration() {
        mainThread = Thread.currentThread();
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
