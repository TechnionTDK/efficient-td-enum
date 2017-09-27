package tdenum.graph.independent_set.single_thread.improvements;

import tdenum.graph.independent_set.single_thread.MaximalIndependentSetsEnumerator;


import java.util.Set;

import static tdenum.graph.independent_set.AlgorithmStep.ITERATING_NODES;

public class ImprovedMaximalIndependentSetsEnumerator<T> extends MaximalIndependentSetsEnumerator<T> {



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



}
