package tdenum.common.cache.parallel;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.jetbrains.annotations.NotNull;
import tdenum.common.cache.ICache;
import tdenum.factories.TDEnumFactory;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Iterator;

public class ParallelECache<T> implements ICache<T> {


    CacheManager cacheManager = TDEnumFactory.getCacheManager();
    Cache<Object, Boolean> cache = cacheManager.createCache("cache",
            CacheConfigurationBuilder.newCacheConfigurationBuilder(
                Object.class, Boolean.class,
                    ResourcePoolsBuilder.newResourcePoolsBuilder().heap(
                            (long)(Runtime.getRuntime().availableProcessors()*0.5), MemoryUnit.GB)
            ));



    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return cache.containsKey(o);
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @NotNull
    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @NotNull
    @Override
    public <T1> T1[] toArray(@NotNull T1[] a) {
        return null;
    }

    @Override
    public boolean add(T t) {
        if (cache.putIfAbsent(t, true)==null)
        {
            return true;
        }
        return false;
    }

    @Override
    public boolean remove(Object o) {
        cache.remove(o);

        return true;
    }
    @Override
    public boolean containsAll(@NotNull Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends T> c) {
        return false;
    }

    @Override
    public boolean retainAll(@NotNull Collection<?> c) {
        return false;
    }

    @Override
    public boolean removeAll(@NotNull Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {
        cache.clear();
    }
}

