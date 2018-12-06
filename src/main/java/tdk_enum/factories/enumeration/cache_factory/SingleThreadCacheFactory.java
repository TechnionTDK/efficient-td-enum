package tdk_enum.factories.enumeration.cache_factory;

import tdk_enum.common.Utils;
import tdk_enum.common.cache.Cache;
import tdk_enum.common.cache.ICache;
import tdk_enum.common.cache.single_thread.LoggingCache;
import tdk_enum.common.configuration.config_types.CachePolicy;
import tdk_enum.factories.TDKEnumFactory;

import static tdk_enum.common.configuration.config_types.CachePolicy.NONE;

public class SingleThreadCacheFactory implements  ICacheFactory
{
    @Override
    public ICache produce() {
        CachePolicy policy = (CachePolicy) Utils.getFieldValue(TDKEnumFactory.getConfiguration(), "cachePolicy", NONE);
        switch (policy) {
            case NONE: {
                System.out.println("Producing Single Thread cache");
                return new Cache();
            }
            case LOG: {
                System.out.println("Producing Single Thread Loggable cache");
                return new LoggingCache();
            }
            default: {
                return new Cache();
            }


        }
    }
}
