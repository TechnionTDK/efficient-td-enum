package tdk_enum.factories.cache_factory;

import tdk_enum.common.Utils;
import tdk_enum.common.cache.Cache;
import tdk_enum.common.cache.single_thread.LoggingCache;
import tdk_enum.common.configuration.config_types.CachePolicy;
import tdk_enum.common.cache.ICache;
import tdk_enum.common.cache.parallel.ParallelECache;
import tdk_enum.common.configuration.TDKEnumConfiguration;
import tdk_enum.factories.TDKEnumFactory;

import static tdk_enum.common.configuration.config_types.CachePolicy.NONE;

public class CacheFactory implements ICacheFactory {

    ICache cache = null;

    @Override
    public ICache produce() {
        if(cache != null)
        {
            return  cache;
        }
        TDKEnumConfiguration configuration = TDKEnumFactory.getConfiguration();
        switch (configuration.getRunningMode())
        {
            case SINGLE_THREAD:
                return new SingleThreadCacheFactory().produce();
            case PARALLEL:
                return new ParallelCacheFactory().produce();
            default:
                return new SingleThreadCacheFactory().produce();
        }
    }


}
