package tdenum.parallel.independent_set;

import tdenum.graph.graphs.interfaces.ISuccinctGraphRepresentation;
import tdenum.graph.independent_set.IMaximalIndependentSetsEnumerator;
import tdenum.graph.independent_set.interfaces.IIndependentSetExtender;
import tdenum.graph.independent_set.interfaces.IIndependentSetScorer;
import tdenum.parallel.data_structures.ParallelWeightedQueue;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ParallelMaximalIndependentSetsEnumerator<T> implements IMaximalIndependentSetsEnumerator {


    ISuccinctGraphRepresentation<T> graph;
    IIndependentSetExtender extender;
    IIndependentSetScorer scorer;

    Set<T> nodesGenerated = ConcurrentHashMap.newKeySet();

    Set<Set<T>> setsExtended = ConcurrentHashMap.newKeySet();
    Set<Set<T>> setsNotExtended = ConcurrentHashMap.newKeySet();

    ParallelWeightedQueue<Set<T>> extendingQueue = new ParallelWeightedQueue<>();


    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public Set<T> next() {
        return null;
    }



    boolean newSetFound(final Set<T> generatedSet)
    {
        if (!setsExtended.contains(generatedSet))
        {
            if(setsNotExtended.add(generatedSet))
            {
                extendingQueue.setWeight(generatedSet, scorer.scoreIndependentSet(generatedSet));
//                nextIndependentSet = generatedSet;
//                nextSetReady = true;
//                System.out.println("new set found");
//                System.out.println(generatedSet);
                return true;
            }

        }
//        System.out.println("no new set");
        return false;
    }
}
