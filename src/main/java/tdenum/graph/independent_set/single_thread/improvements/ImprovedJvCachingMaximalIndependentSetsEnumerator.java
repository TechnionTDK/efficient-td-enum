package tdenum.graph.independent_set.single_thread.improvements;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import tdenum.common.cache.ICache;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static tdenum.graph.independent_set.AlgorithmStep.ITERATING_SETS;

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
