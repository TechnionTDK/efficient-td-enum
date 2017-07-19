package tdenum.graph.data_structures;

import tdenum.common.Utils;

import java.util.*;

/**
 * Created by dvir.dukhan on 7/10/2017.
 */
public class TdListMap<T> extends TdMap<T> {

    List<T> list = new ArrayList<>();
    Set<Node> keys = new HashSet<>();
    Set<T> values = new HashSet<>();


    public TdListMap()
    {

    }

    public TdListMap(int size)
    {
        list = Utils.generateFixedList(size, null);
    }

    public TdListMap(int size, T value)
    {
        list = Utils.generateFixedList(size, value);
    }


    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {

        return keys.contains(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return values.contains(value);
    }

    @Override
    public T get(Object key) {
        Node v = (Node) key;
        return list.get(v.intValue());
    }

    @Override
    public T put(Node key, T value) {

        for (int i= list.size(); i <= key.intValue(); i++)
        {
            list.add(i, null);
        }
        Node v = (Node) key;
        keys.add(key);

        list.set(v.intValue(), value);


        return value;
    }

    @Override
    public T remove(Object key) {
        Node v = (Node) key;
        keys.remove(key);
        return list.remove((v.intValue()));
    }

    @Override
    public void putAll(Map<? extends Node, ? extends T> m) {
        for(Node v : m.keySet())
        {
            put(v, m.get(v));
        }
    }

    @Override
    public void clear() {
        list.clear();
        keys.clear();
        values.clear();

    }

    @Override
    public Set<Node> keySet() {


        return keys;
    }

    @Override
    public Collection<T> values() {
        Collection<T> collection = new ArrayList<>();
        for (Node key : keys)
        {
            collection.add(get(key));
        }
        return collection;
    }

    @Override
    public Set<Entry<Node, T>> entrySet() {
        Set<Entry<Node, T>> set = new HashSet<>();
        for (Node key : keys)
        {
            set.add(new AbstractMap.SimpleEntry<Node, T>(key, get(key)));
        }
        return set;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TdListMap)) return false;

        TdListMap<?> tdListMap = (TdListMap<?>) o;

        return list != null ? list.equals(tdListMap.list) : tdListMap.list == null;
    }

    @Override
    public int hashCode() {
        return list != null ? list.hashCode() : 0;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TdListMap{");
        sb.append("list=").append(list);
        sb.append('}');
        return sb.toString();
    }
}
