package tdk_enum.common.cache.single_thread;

import tdk_enum.common.IO.logger.Logger;
import tdk_enum.common.cache.Cache;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class LoggingCache<T> extends Cache<T> {
    int count = 0;

    Map<T,Integer> distanceMap = new HashMap<>();


    @Override
    public boolean add(T t)
    {
        count++;
        boolean result = super.add(t);
        Logger.logCacheHit((Set)t, count);
        return result;
    }


}
