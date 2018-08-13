package tdk_enum.enumerators.independent_set.single_thread.loggable;

import tdk_enum.common.IO.logger.Logger;
import tdk_enum.enumerators.independent_set.single_thread.improvements.ImprovedJvCachingMaximalIndependentSetsEnumerator;

import java.util.Set;

public class LoggableImprovedJvCachingMaximalIndependentSetsEnumerator<T> extends ImprovedJvCachingMaximalIndependentSetsEnumerator<T> {
//    @Override
//    protected boolean extendSetInDirectionOfNode(final Set<T> s, final T node)
//    {
//
//
//
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
//            Logger.logResultData(result, s, node, baseNodes);
//
//            return newSetFound(result);
//        }
//
//    }


    @Override
    protected void tryGenerateNewResult(final T node, final Set<T> s )
    {
        Set<T> baseNodes = manipulateNodeAndResult(node, s);

        if(!jvCache.add(baseNodes))
        {
            return;
        }
        else
        {
            Set<T> result = generator.generateNew(graph,baseNodes);
            Logger.logResultData(result, s, node, baseNodes);

            newResultFound(result);
        }
    }


    @Override
    protected boolean stepByStepTryGenerateNewResult(final T node, final Set<T> s )
    {


        Set<T> baseNodes = manipulateNodeAndResult(node, s);

        if(!jvCache.add(baseNodes))
        {
            return false;
        }
        else
        {
            Set<T> result = generator.generateNew(graph,baseNodes);
            Logger.logResultData(result, s, node, baseNodes);

            return ( newStepByStepResultFound(result));
        }

    }
}
