package tdk_enum.enumerators.independent_set.single_thread.improvements;

import tdk_enum.enumerators.independent_set.single_thread.MaximalIndependentSetsEnumerator;


import java.util.Set;

public class ImprovedMaximalIndependentSetsEnumerator<T> extends MaximalIndependentSetsEnumerator<T> {


    public ImprovedMaximalIndependentSetsEnumerator() {
        super();
    }

    @Override
    protected void tryGenerateNewResult(T node, Set<T> s)
    {
        if (!s.contains(node))
            super.tryGenerateNewResult(node, s);
    }


    @Override
    protected boolean stepByStepTryGenerateNewResult(T node, Set<T>s)
    {
        if (s.contains(node))
            return false;
        return super.stepByStepTryGenerateNewResult(node, s);
    }




//    @Override
//    protected boolean handleIterationNodePhase(T node)
//    {
//        if(currentSet.contains(node))
//        {
//           return false;
//        }
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
//    @Override
//    protected boolean handleIterationSetPhase(Set<T> s)
//    {
//
//        if(s.contains(currentNode))
//        {
//            return false;
//        }
////        Set<T> generatedSet = extendSetInDirectionOfNode(s, currentNode);
//        if (extendSetInDirectionOfNode(s, currentNode))
//        {
//            step = ITERATING_RESULTS;
//
//            return true;
//        }
//        return false;
//    }
}
