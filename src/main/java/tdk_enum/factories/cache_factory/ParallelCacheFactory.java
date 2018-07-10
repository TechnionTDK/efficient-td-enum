package tdk_enum.factories.cache_factory;

import tdk_enum.common.cache.ICache;
import tdk_enum.common.cache.parallel.ParallelECache;

public class ParallelCacheFactory implements ICacheFactory {
    @Override
    public ICache produce() {
        System.out.println("Producing parallel cache");
        return new ParallelECache();
    }
}
