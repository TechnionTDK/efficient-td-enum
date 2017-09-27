package tdenum.graph.independent_set.single_thread;

import tdenum.graph.independent_set.AbstractMaximalIndependentSetsEnumerator;
import tdenum.graph.independent_set.AlgorithmStep;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static tdenum.graph.independent_set.AlgorithmStep.BEGINNING;
import static tdenum.graph.independent_set.AlgorithmStep.ITERATING_NODES;
import static tdenum.graph.independent_set.AlgorithmStep.ITERATING_SETS;

public class MaximalIndependentSetsEnumerator<T> extends AbstractMaximalIndependentSetsEnumerator<T> {




    protected boolean nextSetReady;
    protected Set<T> nextIndependentSet;

    protected Set<T> currentSet;
    protected T currentNode;

    protected AlgorithmStep step;

    protected Iterator<T> nodesIterator;
    protected Iterator<Set<T>> setsIterator;




    @Override
    public boolean hasNext()
    {
        if (nextSetReady)
        {
            return true;
        }
        else
        {
            if (step == BEGINNING)
            {
                return runFullEnumeration();
            }
            else if (step == ITERATING_NODES)
            {
                while(nodesIterator.hasNext() && !Thread.currentThread().isInterrupted())
                {
                    T node =nodesIterator.next();
                    if (handleIterationNodePhase(node))
                    {
                        return true;
                    }
                }

                while(setsNotExtended.isEmpty() && graph.hasNextNode() && !Thread.currentThread().isInterrupted())
                {
                    currentNode = graph.nextNode();
                    V.add(currentNode);
                    setsIterator = P.iterator();

                    while(setsIterator.hasNext() && !Thread.currentThread().isInterrupted())
                    {
                        Set<T> s= setsIterator.next();

                        if(handleIterationSetPhase(s))
                        {
                            return true;
                        }
                    }

                }
                return  runFullEnumeration();
            }
            else if( step == ITERATING_SETS)
            {
                while(setsIterator.hasNext() && !Thread.currentThread().isInterrupted())
                {
                    Set<T> s= setsIterator.next();

                    if(handleIterationSetPhase(s))
                    {
                        return true;
                    }
                }

                while (setsNotExtended.isEmpty() && graph.hasNextNode() && !Thread.currentThread().isInterrupted())
                {
                    currentNode = graph.nextNode();
                    V.add(currentNode);
                    setsIterator = P.iterator();

                    while(setsIterator.hasNext() && !Thread.currentThread().isInterrupted())
                    {
                        Set<T> s= setsIterator.next();

                        if(handleIterationSetPhase(s))
                        {
                            return true;
                        }
                    }

                }
                return runFullEnumeration();
            }
        }
        return false;
    }

    @Override
    public Set<T> next()
    {
        if (nextSetReady || hasNext()) {
            nextSetReady = false;

            return nextIndependentSet;
        }
        return new HashSet<>();
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
//        System.out.println("current set " + currentSet);
    }


    protected Set<T> extendSetInDirectionOfNode(final Set<T> s, final T node)
    {
//        System.out.println("Extending set " + s + " in direction of node" + node);
        Set<T> baseNodes = new HashSet<>();
        baseNodes.add(node);
        for (T t : s)
        {
            if(!graph.hasEdge(node, t))
            {
                baseNodes.add(t);
            }
        }


        return extender.extendToMaxIndependentSet(baseNodes);
    }

    protected boolean newSetFound(final Set<T> generatedSet)
    {


        if (!P.contains(generatedSet))
        {
            if(setsNotExtended.add(generatedSet))
            {
                Q.setWeight(generatedSet, scorer.scoreIndependentSet(generatedSet));
                nextIndependentSet = generatedSet;
                nextSetReady = true;
                return true;
            }
        }
        return false;
    }

    protected boolean runFullEnumeration()
    {
        while(!Q.isEmpty() && !Thread.currentThread().isInterrupted())
        {

            getNextSetToExtend();

            nodesIterator = V.iterator();

            while (nodesIterator.hasNext() && !Thread.currentThread().isInterrupted())
            {
                T node = nodesIterator.next();
                if (handleIterationNodePhase(node))
                {
                    return true;
                }
            }


        }

        while(setsNotExtended.isEmpty() && graph.hasNextNode() && !Thread.currentThread().isInterrupted())
        {
            currentNode = graph.nextNode();
            V.add(currentNode);
            setsIterator = P.iterator();
            while(setsIterator.hasNext() && !Thread.currentThread().isInterrupted())
            {
                Set<T> s = setsIterator.next();
                Set<T> generatedSet = extendSetInDirectionOfNode(s, currentNode);
                if (newSetFound(generatedSet))
                {
                    step = ITERATING_SETS;

                    return true;
                }
            }
        }

        return false;
    }



    protected boolean handleIterationNodePhase(T node)
    {
        Set<T> generatedSet = extendSetInDirectionOfNode(currentSet, node);
        if(newSetFound(generatedSet))
        {
            step = ITERATING_NODES;

            return true;
        }
        return false;
    }


    protected boolean handleIterationSetPhase(Set<T> s)
    {
        Set<T> generatedSet = extendSetInDirectionOfNode(s, currentNode);
        if (newSetFound(generatedSet))
        {
            step = ITERATING_SETS;

            return true;
        }
        return false;
    }


    @Override
    public void doFirstStep()
    {
        newSetFound(extender.extendToMaxIndependentSet(new HashSet<T>()));
        step = BEGINNING;
    }
}
