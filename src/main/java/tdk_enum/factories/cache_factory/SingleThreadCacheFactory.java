package tdk_enum.factories.cache_factory;

import tdk_enum.common.cache.Cache;
import tdk_enum.common.cache.CachePolicy;
import tdk_enum.common.cache.ICache;
import tdk_enum.common.cache.single_thread.LoggingCache;
import tdk_enum.factories.TDEnumFactory;

public class SingleThreadCacheFactory implements ICacheFactory{


    ICache cache;

    @Override
    public ICache produce() {
        CachePolicy policy = CachePolicy.valueOf(TDEnumFactory.getProperties().getProperty("cachePolicy"));
        switch (policy)
        {
            case LOG:
                return produceLoggingCache();
            case NONE:
                return prodcueCache();
        }
        return prodcueCache();
    }

    private ICache prodcueCache() {

        System.out.println("producing cache");
        if (cache == null)
        {
            cache = new Cache();
        }
        return  cache;
    }

    private ICache produceLoggingCache() {
        System.out.println("producing logging cache");
        if (cache == null)
        {
            cache = new LoggingCache();
        }
        return  cache;


    }
}
