package tdk_enum.enumerators.independent_set.single_thread.improvements;

import java.util.Set;

public class ImprovedJvCachingMaximalIndependentSetsEnumerator<T> extends ImprovedMaximalIndependentSetsEnumerator<T> {




    @Override
    protected boolean stepByStepTryGenerateNewResult(final T node, final Set<T> s )
    {


        Set<T> baseNodes = manipulateNodeAndResult(node, s);
        if(!jvCache.add(baseNodes))
            return false;
        Set<T> result = generator.generateNew(graph,baseNodes);


        return ( newStepByStepResultFound(result));


    }

    @Override
    protected void tryGenerateNewResult(final T node, final Set<T> s )
    {
        Set<T> baseNodes = manipulateNodeAndResult(node, s);
        if(!jvCache.add(baseNodes))
            return;
        Set<T> result = generator.generateNew(graph,baseNodes);

        newResultFound(result);
    }


//
//    @Override
//    protected boolean extendSetInDirectionOfNode(final Set<T> s, final T node)
//    {
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
//        if(!jvCache.add(baseNodes))
//        {
//            return false;
//        }
//        else
//        {
//            Set<T> result =   extender.extendToMaxIndependentSet(baseNodes);
//
//            return newSetFound(result);
//        }
//
////        if (jvCache.contains(baseNodes))
////        {
////            return false;
////        }
////        else
////        {
////            Set<T> result =   extender.extendToMaxIndependentSet(baseNodes);
////            jvCache.add(baseNodes);
////            return newSetFound(result);
////        }
//    }



}
