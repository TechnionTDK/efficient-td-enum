package tdk_enum.enumerators.independent_set.single_thread;

import tdk_enum.common.configuration.config_types.EnumerationPurpose;
import tdk_enum.enumerators.independent_set.AbstractMaximalIndependentSetsEnumerator;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

public class MaximalIndependentSetsEnumerator<T> extends AbstractMaximalIndependentSetsEnumerator<T> {
    public MaximalIndependentSetsEnumerator() {
        super();
    }


//    protected AlgorithmStep step;
//
//    protected Iterator<T> nodesIterator;
//    protected Iterator<Set<T>> setsIterator;
//
//
//
//
//    @Override
//    public boolean hasNext()
//    {
//        if (nextSetReady)
//        {
//            return true;
//        }
//        else
//        {
//            if (step == BEGINNING)
//            {
//                return runFullEnumeration();
//            }
//            else if (step == ITERATING_NODES)
//            {
//                while(nodesIterator.hasNext() && !finishCondition())
//                {
//                    T node =nodesIterator.next();
//                    if (handleIterationNodePhase(node))
//                    {
//                        return true;
//                    }
//                }
//
//                while(setsNotExtended.isEmpty() && graph.hasNextNode() && !finishCondition())
//                {
//                    currentNode = graph.nextNode();
//                    V.add(currentNode);
//                    setsIterator = P.iterator();
//
//                    while(setsIterator.hasNext() && !finishCondition())
//                    {
//                        Set<T> s= setsIterator.next();
//
//                        if(handleIterationSetPhase(s))
//                        {
//                            return true;
//                        }
//                    }
//
//                }
//                return  runFullEnumeration();
//            }
//            else if( step == ITERATING_RESULTS)
//            {
//                while(setsIterator.hasNext() && !finishCondition())
//                {
//                    Set<T> s= setsIterator.next();
//
//                    if(handleIterationSetPhase(s))
//                    {
//                        return true;
//                    }
//                }
//
//                while (setsNotExtended.isEmpty() && graph.hasNextNode() && !finishCondition())
//                {
//                    currentNode = graph.nextNode();
//                    V.add(currentNode);
//                    setsIterator = P.iterator();
//
//                    while(setsIterator.hasNext() && !finishCondition())
//                    {
//                        Set<T> s= setsIterator.next();
//
//                        if(handleIterationSetPhase(s))
//                        {
//                            return true;
//                        }
//                    }
//
//                }
//                return runFullEnumeration();
//            }
//        }
//        return false;
//    }
//
//    @Override
//    public Set<T> next()
//    {
//        if (nextSetReady || hasNext()) {
//            nextSetReady = false;
////            resultPrinter.print(nextIndependentSet);
//            return nextIndependentSet;
//        }
//        return new HashSet<>();
//    }
//
//
//    protected void getNextSetToExtend()
//    {
//        Set<T> currentScoredSet = Q.peek();
//        if (scorer.mayScoreChange())
//        {
//
//            int currentScore = scorer.scoreIndependentSet(currentScoredSet);
//            while (currentScore > Q.getWeight(currentScoredSet))
//            {
//                Q.setWeight(currentScoredSet, currentScore);
//                currentScoredSet = Q.peek();
//                currentScore = scorer.scoreIndependentSet(currentScoredSet);
//            }
//        }
//        currentSet = currentScoredSet;
//        scorer.independentSetUsed(currentSet);
//        P.add(currentSet);
//        setsNotExtended.remove(currentSet);
//        Q.poll();
////        System.out.println("current set " + currentSet);
//    }
//
//
//    protected boolean extendSetInDirectionOfNode(final Set<T> s, final T node)
//    {
////        System.out.println("Extending set " + s + " in direction of node" + node);
//        Set<T> baseNodes = new HashSet<>();
//        baseNodes.add(node);
//        for (T t : s)
//        {
//            if(!graph.hasEdge(node, t))
//            {
//                baseNodes.add(t);
//            }
//        }
//
//
//        return newSetFound(extender.extendToMaxIndependentSet(baseNodes));
//    }
//
//
//
//    protected boolean runFullEnumeration()
//    {
//        while(!Q.isEmpty() && !finishCondition())
//        {
//
//            getNextSetToExtend();
//
//            nodesIterator = V.iterator();
//
//            while (nodesIterator.hasNext() && !finishCondition())
//            {
//                T node = nodesIterator.next();
//                if (handleIterationNodePhase(node))
//                {
//                    return true;
//                }
//            }
//
//
//        }
//
//        while(setsNotExtended.isEmpty() && graph.hasNextNode() && !finishCondition())
//        {
//            currentNode = graph.nextNode();
//            V.add(currentNode);
//            setsIterator = P.iterator();
//            while(setsIterator.hasNext() && !finishCondition())
//            {
//                Set<T> s = setsIterator.next();
////                Set<T> generatedSet = extendSetInDirectionOfNode(s, currentNode);
//                if (extendSetInDirectionOfNode(s, currentNode))
//                {
//                    step = ITERATING_RESULTS;
//
//                    return true;
//                }
//            }
//        }
//
//        return false;
//    }
//
//
//
//    protected boolean handleIterationNodePhase(T node)
//    {
////        Set<T> generatedSet = extendSetInDirectionOfNode(currentSet, node);
//        if(extendSetInDirectionOfNode(currentSet, node))
//        {
//            step = ITERATING_NODES;
//
//            return true;
//        }
//        return false;
//    }
//
//
//    protected boolean handleIterationSetPhase(Set<T> s)
//    {
////        Set<T> generatedSet = extendSetInDirectionOfNode(s, currentNode);
//        if (extendSetInDirectionOfNode(s, currentNode))
//        {
//            step = ITERATING_RESULTS;
//
//            return true;
//        }
//        return false;
//    }
//
//
//    @Override
//    public void doFirstStep()
//    {
//        newSetFound(extender.extendToMaxIndependentSet(new HashSet<T>()));
//        step = BEGINNING;
//    }

//    @Override
//    protected boolean finishCondition() {
//        return Thread.currentThread().isInterrupted();
//    }

    @Override
    protected Set<T> manipulateNodeAndResult(T node, Set<T> s) {
        Set<T> baseNodes = new HashSet<>();
        baseNodes.add(node);
        for (T t : s)
        {
            if(!graph.hasEdge(node, t))
            {
                baseNodes.add(t);
            }
        }
        return  baseNodes;
    }






}
