package tdenum.graph.data_structures;

import java.util.*;

/**
 * Created by dvir.dukhan on 7/10/2017.
 */
public class TdListMap<T> extends TdMap<T> {

    List<T> list = new ArrayList<>();

    public TdListMap(int size)
    {
        for (int i= 0; i < size; i ++)
        {
            list.add(i , null);
        }
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
        Node v = (Node) key;
        return list.get(v.intValue())!= null;
    }

    @Override
    public boolean containsValue(Object value) {
        return list.contains(value);
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

        list.set(v.intValue(), value);


        return value;
    }

    @Override
    public T remove(Object key) {
        Node v = (Node) key;
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

    }

    @Override
    public Set<Node> keySet() {

        Set<Node> set = new HashSet<>();
        for (int i = 0; i < size(); i++)
        {
            Node v = new Node(i);
            if(containsKey(v))
            {
                set.add(v);
            }
        }
        return set;
    }

    @Override
    public Collection<T> values() {
        Collection<T> collection = new ArrayList<>();
        for (int i = 0; i < size(); i++)
        {
            Node v = new Node(i);
            if(containsKey(v))
            {
                collection.add(get(v));
            }
        }
        return collection;
    }

    @Override
    public Set<Entry<Node, T>> entrySet() {
        Set<Entry<Node, T>> set = new HashSet<>();
        for (int i = 0; i < size(); i++)
        {
            Node v = new Node(i);
            if(containsKey(v))
            {
                set.add(new AbstractMap.SimpleEntry<Node, T>(v, get(v)));
            }
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
