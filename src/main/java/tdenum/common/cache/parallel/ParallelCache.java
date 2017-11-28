package tdenum.common.cache.parallel;

import org.jetbrains.annotations.NotNull;
import tdenum.common.cache.ICache;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ParallelCache<T>  implements ICache<T> {

    Set<T> cache = ConcurrentHashMap.newKeySet();

    @Override
    public int size() {
        return cache.size();
    }

    @Override
    public boolean isEmpty() {
        return cache.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return cache.contains(o);
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return cache.iterator();
    }
    @NotNull
    @Override
    public Object[] toArray() {
        return cache.toArray();
    }

    @NotNull
    @Override
    public <T1> T1[] toArray(@NotNull T1[] a) {
        return cache.toArray(a);
    }

    @Override
    public boolean add(T t) {
        return cache.add(t);
    }

    @Override
    public boolean remove(Object o) {
        return cache.remove(o);
    }

    @Override
    public boolean containsAll(@NotNull Collection<?> c) {
        return cache.containsAll(c);
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends T> c) {
        return cache.addAll(c);
    }

    @Override
    public boolean retainAll(@NotNull Collection<?> c) {
        return cache.retainAll(c);
    }

    @Override
    public boolean removeAll(@NotNull Collection<?> c) {
        return cache.retainAll(c);
    }

    @Override
    public void clear() {
        cache.clear();

    }
}
