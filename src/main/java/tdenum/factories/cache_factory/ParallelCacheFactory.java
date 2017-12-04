package tdenum.factories.cache_factory;

import tdenum.common.cache.ICache;
import tdenum.common.cache.parallel.ParallelCache;
import tdenum.common.cache.parallel.ParallelECache;

public class ParallelCacheFactory implements ICacheFactory {
    @Override
    public ICache produce() {
        System.out.println("Producing parallel cache");
        return new ParallelECache();
    }
}
