package tdk_enum.graph.independent_set.parallel;

import tdk_enum.graph.data_structures.weighted_queue.parallel.ConcurrentQueueSet;
import tdk_enum.graph.independent_set.AbstractMaximalIndependentSetsEnumerator;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


public class ParallelMaximalIndependentSetsEnumerator<T> extends AbstractMaximalIndependentSetsEnumerator<T> {

    protected Thread mainThread;

    public ParallelMaximalIndependentSetsEnumerator()
    {
        super();
        P = ConcurrentHashMap.newKeySet();
        V = ConcurrentHashMap.newKeySet();
        setsNotExtended = ConcurrentHashMap.newKeySet();
        Q = new ConcurrentQueueSet<>();
    }

    @Override
    protected boolean timeLimitReached() {
        return mainThread.isInterrupted();
    }



    @Override
    public boolean hasNext() {
        if (nextSetReady)
        {
            return true;
        }
        else
        {
            runFullEnumeration();
            return false;
        }
    }

    @Override
    public Set<T> next() {
        if (nextSetReady || hasNext()) {
            nextSetReady = false;
            resultPrinter.print(nextIndependentSet);
            return nextIndependentSet;
        }
        return new HashSet<>();
    }

    @Override
    public void doFirstStep() {
        newSetFound(extender.extendToMaxIndependentSet(new HashSet<T>()));
    }


    protected void parallelNewSetFound(final Set<T> generatedSet)
    {

        if (!P.contains(generatedSet))
        {
            if(setsNotExtended.add(generatedSet))
            {
                Q.setWeight(generatedSet, scorer.scoreIndependentSet(generatedSet));
                resultPrinter.print(generatedSet);
            }

        }
    }


    protected void extendSetInDirectionOfNode(final Set<T> s, final T node)
    {
        if (s.contains(node))
        {
            return;
        }
        Set<T> baseNodes = new HashSet<>();
        baseNodes.add(node);
        for (T t : s)
        {
            if(!graph.hasEdge(node, t))
            {
                baseNodes.add(t);
            }
        }

        if(jvCache.add(baseNodes))
        {
            Set<T> result =   extender.extendToMaxIndependentSet(baseNodes);

            parallelNewSetFound(result);
        }
    }


    protected void runFullEnumeration()
    {
        mainThread = Thread.currentThread();
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


    }

    protected void getNextSetToExtend()
    {
        Set<T> currentScoredSet = Q.peek();
        if (scorer.mayScoreChange())
        {

            int currentScore = scorer.scoreIndependentSet(currentScoredSet);
            while (currentScore > Q.getWeight(currentScoredSet))
            {
                Q.setWeight(currentScoredSet, currentScore);
                currentScoredSet = Q.peek();
                currentScore = scorer.scoreIndependentSet(currentScoredSet);
            }
        }
        currentSet = currentScoredSet;
        scorer.independentSetUsed(currentSet);
        P.add(currentSet);
        setsNotExtended.remove(currentSet);
        Q.poll();

    }



    protected void handleIterationNodePhase()
    {

        V.parallelStream().anyMatch(v->
        {
            extendSetInDirectionOfNode(currentSet, v);
            return timeLimitReached();
        });
    }


    protected void handleIterationSetPhase()
    {
        P.parallelStream().anyMatch(extendedMis ->{ extendSetInDirectionOfNode(extendedMis, currentNode);
        return timeLimitReached();}
        );
    }



}
