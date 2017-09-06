package tdenum.loggable.independent_set;

import tdenum.common.IO.Logger;
import tdenum.graph.graphs.interfaces.ISuccinctGraphRepresentation;
import tdenum.graph.independent_set.IMaximalIndependentSetsEnumerator;
import tdenum.graph.independent_set.MaximalIndependentSetsEnumerator;
import tdenum.graph.independent_set.interfaces.IIndependentSetExtender;
import tdenum.graph.independent_set.interfaces.IIndependentSetScorer;

import java.util.HashSet;
import java.util.Set;

import static tdenum.graph.independent_set.AlgorithmStep.BEGINNING;
import static tdenum.graph.independent_set.AlgorithmStep.ITERATING_NODES;
import static tdenum.graph.independent_set.AlgorithmStep.ITERATING_SETS;

public class LoggableMaximalIndependentSetsEnumerator<T> extends MaximalIndependentSetsEnumerator<T> implements IMaximalIndependentSetsEnumerator {
    public LoggableMaximalIndependentSetsEnumerator(MaximalIndependentSetsEnumerator m) {
        super(m);
    }

    public LoggableMaximalIndependentSetsEnumerator(ISuccinctGraphRepresentation graph, IIndependentSetExtender extender, IIndependentSetScorer scorer) {
        super(graph, extender, scorer);
    }



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


    @Override
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

        Set<T> result = extender.extendToMaxIndependentSet(baseNodes);
        Logger.addSetToExtend(baseNodes);
        Logger.logResultData(result, s, node, baseNodes);

        return result;
    }

    @Override
    protected boolean newSetFound(final Set<T> generatedSet)
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

    @Override
    protected boolean runFullEnumeration()
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
