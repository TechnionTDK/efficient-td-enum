package tdenum.single_thread_improvments.graphs.independent_set;

import tdenum.graph.graphs.interfaces.ISuccinctGraphRepresentation;
import tdenum.graph.independent_set.MaximalIndependentSetsEnumerator;
import tdenum.graph.independent_set.interfaces.IIndependentSetExtender;
import tdenum.graph.independent_set.interfaces.IIndependentSetScorer;

import java.util.Set;

import static tdenum.graph.independent_set.AlgorithmStep.BEGINNING;
import static tdenum.graph.independent_set.AlgorithmStep.ITERATING_NODES;
import static tdenum.graph.independent_set.AlgorithmStep.ITERATING_SETS;

public class ImprovedMaximalIndependentSetsEnumerator<T> extends MaximalIndependentSetsEnumerator<T> {
    public ImprovedMaximalIndependentSetsEnumerator(MaximalIndependentSetsEnumerator m) {
        super(m);
    }

    public ImprovedMaximalIndependentSetsEnumerator(ISuccinctGraphRepresentation graph, IIndependentSetExtender extender, IIndependentSetScorer scorer) {
        super(graph, extender, scorer);
    }


    @Override
    protected boolean runFullEnumeration()
    {
        while(!extendingQueue.isEmpty())
        {

            getNextSetToExtend();

            nodesIterator = nodesGenerated.iterator();

            while (nodesIterator.hasNext())
            {
                T node = nodesIterator.next();

                if (handleIterationNodePhase(node))
                {
                    return true;
                }
            }

        }
        while(setsNotExtended.isEmpty() && graph.hasNextNode())
        {
            currentNode = graph.nextNode();
            nodesGenerated.add(currentNode);
            setsIterator = setsExtended.iterator();

            while(setsIterator.hasNext())
            {
                Set<T> s = setsIterator.next();

                if(handleIterationSetPhase(s))
                {
                    return true;
                }
            }
        }
        return false;
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
                    T node =nodesIterator.next();
                    if (handleIterationNodePhase(node))
                    {
                        return true;
                    }
                }

                while(setsNotExtended.isEmpty() && graph.hasNextNode())
                {
                    currentNode = graph.nextNode();
                    nodesGenerated.add(currentNode);
                    setsIterator = setsExtended.iterator();

                    while(setsIterator.hasNext())
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
                while(setsIterator.hasNext())
                {
                    Set<T> s= setsIterator.next();

                    if(handleIterationSetPhase(s))
                    {
                        return true;
                    }
                }

                while (setsNotExtended.isEmpty() && graph.hasNextNode())
                {
                    currentNode = graph.nextNode();
                    nodesGenerated.add(currentNode);
                    setsIterator = setsExtended.iterator();

                    while(setsIterator.hasNext())
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
    protected boolean handleIterationNodePhase(T node)
    {
        if(currentSet.contains(node))
        {
           return false;
        }
        Set<T> generatedSet = extendSetInDirectionOfNode(currentSet, node);
        if(newSetFound(generatedSet))
        {
            step = ITERATING_NODES;

            return true;
        }
        return false;
    }

    @Override
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


}
