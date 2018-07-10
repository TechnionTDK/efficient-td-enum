package tdk_enum.graph.data_structures;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by dvir.dukhan on 7/10/2017.
 */
public class TdHashMap<T> extends TdMap<T> {

    Map<Node, T> map = new HashMap<>();

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    @Override
    public T get(Object key) {
        return map.get(key);
    }

    @Override
    public T put(Node key, T value) {
        return map.put(key, value);
    }

    @Override
    public T remove(Object key) {
        return map.remove(key);
    }

    @Override
    public void putAll(Map<? extends Node, ? extends T> m) {
        map.putAll(m);

    }

    @Override
    public void clear() {
        map.clear();

    }

    @Override
    public Set<Node> keySet() {
        return map.keySet();
    }

    @Override
    public Collection<T> values() {
        return map.values();
    }

    @Override
    public Set<Entry<Node, T>> entrySet() {
        return map.entrySet();
    }
}
