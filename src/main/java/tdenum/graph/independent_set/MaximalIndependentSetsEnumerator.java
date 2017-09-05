package tdenum.graph.independent_set;


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


    protected ISuccinctGraphRepresentation<T> graph;
    protected IIndependentSetExtender extender;
    protected IIndependentSetScorer scorer;

    protected Set<T> nodesGenerated = new HashSet<>();

    protected Set<Set<T>> setsExtended = new HashSet<>();
    protected Set<Set<T>> setsNotExtended = new HashSet<>();
    protected WeightedQueue<Set<T>> extendingQueue = new WeightedQueue<>();
    protected boolean nextSetReady;
    protected Set<T> nextIndependentSet;

    protected Set<T> currentSet;
    protected T currentNode;

    protected AlgorithmStep step;

    protected Iterator<T> nodesIterator;
    protected Iterator<Set<T>> setsIterator;





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
                        Set<T> generatedSet = extendSetInDirectionOfNode(setsIterator.next(), currentNode);
                        if(newSetFound(generatedSet))
                        {
                            step = ITERATING_SETS;

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
                    Set<T> generatedSet = extendSetInDirectionOfNode(setsIterator.next(), currentNode);
                    if(newSetFound(generatedSet))
                    {
                        step = ITERATING_SETS;

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
                        Set<T> generatedSet = extendSetInDirectionOfNode(setsIterator.next(), currentNode);
                        if(newSetFound(generatedSet))
                        {
                            step = ITERATING_SETS;

                            return true;
                        }
                    }

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


    protected void getNextSetToExtend()
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


        if (!setsExtended.contains(generatedSet))
        {
            if(setsNotExtended.add(generatedSet))
            {
                extendingQueue.setWeight(generatedSet, scorer.scoreIndependentSet(generatedSet));
                nextIndependentSet = generatedSet;
                nextSetReady = true;
//                System.out.println("new set found");
//                System.out.println(generatedSet);


                return true;
            }

        }

        return false;
    }

    protected boolean runFullEnumeration()
    {
//        System.out.println("now in run fullEnum");
        while(!extendingQueue.isEmpty())
        {

            getNextSetToExtend();

            nodesIterator = nodesGenerated.iterator();

            while (nodesIterator.hasNext())
            {
                T node = nodesIterator.next();
//                System.out.println("current node " + node + "from " + nodesGenerated);
                Set<T> generatedSet = extendSetInDirectionOfNode(currentSet, node);
//                System.out.println("generated set " + generatedSet);
                if (newSetFound(generatedSet))
                {
                    step = ITERATING_NODES;

                    return true;
                }
            }


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

            while(setsIterator.hasNext())
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




}
