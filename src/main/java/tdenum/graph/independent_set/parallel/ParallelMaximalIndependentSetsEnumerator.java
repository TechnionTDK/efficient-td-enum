package tdenum.graph.independent_set.parallel;

import tdenum.graph.independent_set.AbstractMaximalIndependentSetsEnumerator;
import tdenum.graph.independent_set.single_thread.MaximalIndependentSetsEnumerator;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;

import static tdenum.graph.independent_set.AlgorithmStep.ITERATING_NODES;
import static tdenum.graph.independent_set.AlgorithmStep.ITERATING_SETS;


public class ParallelMaximalIndependentSetsEnumerator<T> extends AbstractMaximalIndependentSetsEnumerator<T> {



    @Override
    protected boolean timeLimitReached() {
        return Thread.currentThread().isInterrupted();
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


    protected boolean parallelNewSetFound(final Set<T> generatedSet)
    {

        if (!P.contains(generatedSet))
        {
            if(setsNotExtended.add(generatedSet))
            {
                Q.setWeight(generatedSet, scorer.scoreIndependentSet(generatedSet));
                resultPrinter.print(generatedSet);

                return true;
            }
            return false;
        }

        return false;
    }


    protected void extendSetInDirectionOfNode(final Set<T> s, final T node)
    {
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


    void runFullEnumeration()
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
        V.parallelStream().forEach(v->extendSetInDirectionOfNode(currentSet,v));
    }


    protected void handleIterationSetPhase()
    {
        P.parallelStream().forEach(extendedMis -> extendSetInDirectionOfNode(extendedMis, currentNode));
    }



}
