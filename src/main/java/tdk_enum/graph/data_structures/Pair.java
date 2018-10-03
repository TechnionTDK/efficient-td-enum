package tdk_enum.graph.data_structures;

import java.util.Objects;

public class Pair<K,V> {


    K key;
    V Value;


    public Pair(K key, V value) {
        this.key = key;
        Value = value;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return Value;
    }

    public void setValue(V value) {
        Value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(key, pair.key) &&
                Objects.equals(Value, pair.Value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, Value);
    }

    @Override
    public String toString() {
        return "Pair{" +
                "key=" + key +
                ", Value=" + Value +
                '}';
    }
}
