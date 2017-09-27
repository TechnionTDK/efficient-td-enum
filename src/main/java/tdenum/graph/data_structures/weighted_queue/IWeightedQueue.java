package tdenum.graph.data_structures.weighted_queue;

import java.util.Queue;
import java.util.Set;

public interface IWeightedQueue<T> extends Queue<T>, Set<T> {

    void increaseWeight(T v);
    void setWeight(T v, int weight);
    int getWeight(T v);
}
