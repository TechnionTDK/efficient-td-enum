package tdk_enum.graph.independent_set.single_thread.improvements;

import tdk_enum.graph.independent_set.single_thread.MaximalIndependentSetsEnumerator;


import java.util.Set;

import static tdk_enum.graph.independent_set.AlgorithmStep.ITERATING_NODES;
import static tdk_enum.graph.independent_set.AlgorithmStep.ITERATING_SETS;

public class ImprovedMaximalIndependentSetsEnumerator<T> extends MaximalIndependentSetsEnumerator<T> {



    @Override
    protected boolean handleIterationNodePhase(T node)
    {
        if(currentSet.contains(node))
        {
           return false;
        }
//        Set<T> generatedSet = extendSetInDirectionOfNode(currentSet, node);
        if(extendSetInDirectionOfNode(currentSet, node))
        {
            step = ITERATING_NODES;

            return true;
        }
        return false;
    }

    @Override
    protected boolean handleIterationSetPhase(Set<T> s)
    {

        if(s.contains(currentNode))
        {
            return false;
        }
//        Set<T> generatedSet = extendSetInDirectionOfNode(s, currentNode);
        if (extendSetInDirectionOfNode(s, currentNode))
        {
            step = ITERATING_SETS;

            return true;
        }
        return false;
    }
}
