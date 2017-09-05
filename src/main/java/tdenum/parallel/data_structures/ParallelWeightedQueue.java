package tdenum.parallel.data_structures;

import javafx.util.Pair;
import tdenum.graph.data_structures.comparators.ValueComparator;
import tdenum.graph.data_structures.comparators.ValueComparatorDesc;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

public class ParallelWeightedQueue<T> {

    Map<T, Integer> data = new ConcurrentHashMap<>();
    ConcurrentSkipListSet<Pair<T, Integer>> queue = new ConcurrentSkipListSet<>(new ValueComparator<>(data));

    public ParallelWeightedQueue()
    {

    }


    public ParallelWeightedQueue(boolean descOrder)
    {
        if (descOrder)
        {
            queue = new ConcurrentSkipListSet<>(new ValueComparatorDesc(data));
        }
    }

    public synchronized void increaseWeight(T v)
    {
        int weight = data.get(v);

        weight++;
        setWeight(v, weight);
    }


    public synchronized void setWeight(T v, int weight) {

        if (data.containsKey(v))
        {
            int prevWeight = data.get(v);
            queue.remove(new Pair<>(v, prevWeight));
        }

        data.put(v, weight);
        queue.add(new Pair<>(v, weight));



    }

    public  int getWeight(T v) {
        return data.get(v);
    }

    public  T pop()
    {

        return queue.pollFirst().getKey();

    }

    public T top()
    {
        T v = queue.first().getKey();

        return v;
    }

    public boolean isEmpty()
    {

        return  queue.isEmpty();
    }

    public int size()
    {

        return queue.size();
    }

    public Collection<T> getKeys()
    {
        return data.keySet();
    }


}
