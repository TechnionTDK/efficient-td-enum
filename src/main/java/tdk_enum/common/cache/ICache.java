package tdk_enum.common.cache;

import java.util.Set;

public interface ICache<T> extends Set<T> {
    void close();
}
