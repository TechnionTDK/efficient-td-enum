package tdk_enum.graph.independent_set.single_thread.improvements;

import java.util.HashSet;
import java.util.Set;

public class ImprovedJvCachingMaximalIndependentSetsEnumerator<T> extends ImprovedMaximalIndependentSetsEnumerator<T> {




    @Override
    protected boolean extendSetInDirectionOfNode(final Set<T> s, final T node)
    {
        Set<T> baseNodes = new HashSet<>();
        baseNodes.add(node);
        for (T t : s)
        {
            if(!graph.hasEdge(node, t))
            {
                baseNodes.add(t);
            }
        }


        if(!jvCache.add(baseNodes))
        {
            return false;
        }
        else
        {
            Set<T> result =   extender.extendToMaxIndependentSet(baseNodes);

            return newSetFound(result);
        }

//        if (jvCache.contains(baseNodes))
//        {
//            return false;
//        }
//        else
//        {
//            Set<T> result =   extender.extendToMaxIndependentSet(baseNodes);
//            jvCache.add(baseNodes);
//            return newSetFound(result);
//        }
    }



}
