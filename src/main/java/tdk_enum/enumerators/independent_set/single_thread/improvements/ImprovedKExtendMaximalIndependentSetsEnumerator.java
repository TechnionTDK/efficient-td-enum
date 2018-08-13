package tdk_enum.enumerators.independent_set.single_thread.improvements;

import java.util.HashSet;
import java.util.Set;

public class ImprovedKExtendMaximalIndependentSetsEnumerator<T> extends ImprovedMaximalIndependentSetsEnumerator<T> {


    int k =1;

    public void setK(int k)
    {
        this.k = k;
    }





    @Override
    protected void tryGenerateNewResult (T node, Set<T> currentResult)
    {
        Set<T> baseNodes = manipulateNodeAndResult(node, currentResult);
        for(int i =0; i < k; i++)
        {
            newResultFound(generator.generateNew(baseNodes));
        }
    }


    @Override
    protected boolean stepByStepTryGenerateNewResult(T node, Set<T> currentResult) {
        Set<T> baseNodes = manipulateNodeAndResult(node, currentResult);

        Set<Boolean> newSetsIndicator = new HashSet<>();

        for(int i =0; i < k; i++)
        {
            newSetsIndicator.add(newStepByStepResultFound(generator.generateNew(baseNodes)));
        }

        return newSetsIndicator.contains(true);
    }

//
//    @Override
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
//        Set<Boolean> newSetsIndicator = new HashSet<>();
//
//        for(int i =0; i < k; i++)
//        {
//            newSetsIndicator.add(newSetFound(extender.extendToMaxIndependentSet(baseNodes)));
//        }
//
//        return newSetsIndicator.contains(true);
//    }



}
