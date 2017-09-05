package tdenum.graph.independent_set;

import tdenum.common.IO.Logger;
import tdenum.graph.data_structures.WeightedQueue;
import tdenum.graph.graphs.interfaces.ISuccinctGraphRepresentation;
import tdenum.graph.independent_set.interfaces.IIndependentSetExtender;
import tdenum.graph.independent_set.interfaces.IIndependentSetScorer;

import java.util.*;

import static tdenum.graph.independent_set.AlgorithmStep.BEGINNING;
import static tdenum.graph.independent_set.AlgorithmStep.ITERATING_NODES;
import static tdenum.graph.independent_set.AlgorithmStep.ITERATING_SETS;

/**
 * Created by dvir.dukhan on 7/11/2017.
 */
public class MaximalIndependentSetsEnumerator <T> implements IMaximalIndependentSetsEnumerator{


    ISuccinctGraphRepresentation<T> graph;
    IIndependentSetExtender extender;
    IIndependentSetScorer scorer;

    Set<T> nodesGenerated = new HashSet<>();

    Set<Set<T>> setsExtended = new HashSet<>();
    Set<Set<T>> setsNotExtended = new HashSet<>();
    WeightedQueue<Set<T>> extendingQueue = new WeightedQueue<>();
    boolean nextSetReady;
    Set<T> nextIndependentSet;

    Set<T> currentSet;
    T currentNode;

    AlgorithmStep step;

    Iterator<T> nodesIterator;
    Iterator<Set<T>> setsIterator;





    public MaximalIndependentSetsEnumerator(MaximalIndependentSetsEnumerator m)
    {
        graph = m.graph;
        extender = m.extender;
        scorer = m.scorer;
    }

    public MaximalIndependentSetsEnumerator(ISuccinctGraphRepresentation graph,
                                            IIndependentSetExtender extender,
                                            IIndependentSetScorer scorer)
    {
        this.graph = graph;
        this.extender = extender;
        this.scorer = scorer;
        newSetFound(extender.extendToMaxIndependentSet(new HashSet<T>()));
        step = BEGINNING;
    }

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
                while(nodesIterator.hasNext())
                {
                    Set<T> generetedNodes = extendSetInDirectionOfNode(currentSet, nodesIterator.next());
                    if(newSetFound(generetedNodes))
                    {
                        step = ITERATING_NODES;
                        Logger.addForLoopResult();
                        return true;
                    }

                }
                Logger.finishIterationPhase(ITERATING_NODES);
                while(setsNotExtended.isEmpty() && graph.hasNextNode())
                {
                    currentNode = graph.nextNode();
                    nodesGenerated.add(currentNode);
                    setsIterator = setsExtended.iterator();
                    Logger.startForLoop();
                    while(setsIterator.hasNext())
                    {
                        Set<T> generatedSet = extendSetInDirectionOfNode(setsIterator.next(), currentNode);
                        if(newSetFound(generatedSet))
                        {
                            step = ITERATING_SETS;
                            Logger.addForLoopResult();
                            return true;
                        }
                    }
                    Logger.finishIterationPhase(ITERATING_SETS);
                }
                return  runFullEnumeration();
            }
            else if( step == ITERATING_SETS)
            {
                while(setsIterator.hasNext())
                {
                    Set<T> generatedSet = extendSetInDirectionOfNode(setsIterator.next(), currentNode);
                    if(newSetFound(generatedSet))
                    {
                        step = ITERATING_SETS;
                        Logger.addForLoopResult();
                        return true;
                    }
                }
                Logger.finishIterationPhase(ITERATING_SETS);
                while (setsNotExtended.isEmpty() && graph.hasNextNode())
                {
                    currentNode = graph.nextNode();
                    nodesGenerated.add(currentNode);
                    setsIterator = setsExtended.iterator();
                    Logger.startForLoop();
                    while(setsIterator.hasNext())
                    {
                        Set<T> generatedSet = extendSetInDirectionOfNode(setsIterator.next(), currentNode);
                        if(newSetFound(generatedSet))
                        {
                            step = ITERATING_SETS;
                            Logger.addForLoopResult();
                            return true;
                        }
                    }
                    Logger.finishIterationPhase(ITERATING_SETS);
                }
                return runFullEnumeration();
            }
        }
        return false;
    }

    public Set<T> next()
    {
        if (nextSetReady || hasNext()) {
            nextSetReady = false;

            return nextIndependentSet;
        }
        return new HashSet<>();
    }


    private void getNextSetToExtend()
    {
        Set<T> currentScoredSet = extendingQueue.top();
        if (scorer.mayScoreChange())
        {

            int currentScore = scorer.scoreIndependentSet(currentScoredSet);
            while (currentScore > extendingQueue.getWeight(currentScoredSet))
            {
                extendingQueue.setWeight(currentScoredSet, currentScore);
                currentScoredSet = extendingQueue.top();
                currentScore = scorer.scoreIndependentSet(currentScoredSet);
            }
        }
        currentSet = currentScoredSet;
        scorer.independentSetUsed(currentSet);
        setsExtended.add(currentSet);
        setsNotExtended.remove(currentSet);
        extendingQueue.pop();
//        System.out.println("current set " + currentSet);
    }


    Set<T> extendSetInDirectionOfNode (final Set<T>s, final T node)
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
        Logger.addSetToExtend(baseNodes);

        return extender.extendToMaxIndependentSet(baseNodes);
    }

    boolean newSetFound(final Set<T> generatedSet)
    {

        Logger.startMISDuplicationCheck();
        if (!setsExtended.contains(generatedSet))
        {
            if(setsNotExtended.add(generatedSet))
            {
                extendingQueue.setWeight(generatedSet, scorer.scoreIndependentSet(generatedSet));
                nextIndependentSet = generatedSet;
                nextSetReady = true;
//                System.out.println("new set found");
//                System.out.println(generatedSet);

                Logger.finishMISDuplicationCheck();
                return true;
            }
            else
            {
                Logger.addMISDuplication(generatedSet, Logger.MIS_DUPLICATION_HIT.SETS_NOT_EXTENDED);

            }

        }
        else
        {

            Logger.addMISDuplication(generatedSet, Logger.MIS_DUPLICATION_HIT.SETS_EXTENDED);

        }
//        System.out.println("no new set");
        Logger.finishMISDuplicationCheck();
        return false;
    }

    boolean runFullEnumeration()
    {
//        System.out.println("now in run fullEnum");
        while(!extendingQueue.isEmpty())
        {

            getNextSetToExtend();

            nodesIterator = nodesGenerated.iterator();
            Logger.startForLoop();
            while (nodesIterator.hasNext())
            {
                T node = nodesIterator.next();
//                System.out.println("current node " + node + "from " + nodesGenerated);
                Set<T> generatedSet = extendSetInDirectionOfNode(currentSet, node);
//                System.out.println("generated set " + generatedSet);
                if (newSetFound(generatedSet))
                {
                    step = ITERATING_NODES;
                    Logger.addForLoopResult();
                    return true;
                }
            }
            Logger.finishIterationPhase(ITERATING_NODES);

        }


//        System.out.println("setsNotExtended.isEmpty() " + setsNotExtended.isEmpty() );
//        System.out.println("setsNotExtended.size() " + setsNotExtended.size());
//        System.out.println("graph.hasNextNode()" + graph.hasNextNode());
        while(setsNotExtended.isEmpty() && graph.hasNextNode())
        {
            currentNode = graph.nextNode();
            nodesGenerated.add(currentNode);
//            System.out.println("new node " +  currentNode);
            setsIterator = setsExtended.iterator();
            Logger.startForLoop();
            while(setsIterator.hasNext())
            {
                Set<T> s = setsIterator.next();
                Set<T> generatedSet = extendSetInDirectionOfNode(s, currentNode);
                if (newSetFound(generatedSet))
                {
                    step = ITERATING_SETS;
                    Logger.addForLoopResult();
                    return true;
                }
            }
            Logger.finishIterationPhase(ITERATING_SETS);

        }

        return false;
    }




}
