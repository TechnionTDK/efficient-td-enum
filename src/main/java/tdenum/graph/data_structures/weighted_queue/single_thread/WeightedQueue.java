package tdenum.graph.data_structures.weighted_queue.single_thread;

import javafx.util.Pair;

import java.util.*;


import org.jetbrains.annotations.NotNull;
import tdenum.graph.data_structures.comparators.ValueComparator;
import tdenum.graph.data_structures.comparators.ValueComparatorDesc;
import tdenum.graph.data_structures.weighted_queue.IWeightedQueue;

/**
 * Created by dvir.dukhan on 7/11/2017.
 */
public  class WeightedQueue<T > implements IWeightedQueue<T>
{






    Map<T, Integer> data =new HashMap<>();
    TreeSet<Pair<T, Integer>> queue = new TreeSet<>(new ValueComparator(data));


    public WeightedQueue()
    {

    }

    public WeightedQueue(boolean descOrder)
    {
        if (descOrder)
        {
            queue = new TreeSet<>(new ValueComparatorDesc(data));
        }
    }

    @Override
    public void increaseWeight(T v)
    {
        int weight = data.get(v);

        weight++;
        setWeight(v, weight);
    }


    @Override
    public void setWeight(T v, int weight) {

        if (data.containsKey(v))
        {
            int prevWeight = data.get(v);
//            System.out.println("change weight for " + v);
//            System.out.println("from " + prevWeight + " to " + weight);
//            System.out.println("in set weight map size " + data.size() + " tree size " + queue.size() + " " +this.hashCode());
            queue.remove(new Pair<>(v, prevWeight));
        }

        data.put(v, weight);
        queue.add(new Pair<>(v, weight));
//        System.out.println("in set weight map size " + data.size() + " tree size " + queue.size() + " " +this.hashCode());

    }

    @Override
    public int getWeight(T v) {
        return data.get(v);
    }

    @Override
    public T poll()
    {
        T v = peek();
        queue.remove(queue.first());
        data.remove(v);

//        System.out.println("in poll map size " + data.size() + " tree size " + queue.size() + " " +this.hashCode());
        return v;
    }

    @Override
    public T peek()
    {
        T v = queue.first().getKey();
//        System.out.println("in peek map size " + data.size() + " tree size " + queue.size() + " " +this.hashCode());
        return v;
    }

    public boolean isEmpty()
    {
//        System.out.println("in isEmpty map size " + data.size() + " tree size " + queue.size() + " " +this.hashCode());
//        System.out.println(data);
        return  data.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @NotNull
    @Override
    public Iterator iterator() {
        return null;
    }

    @NotNull
    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @NotNull
    @Override
    public Object[] toArray(@NotNull Object[] a) {
        return new Object[0];
    }

    public int size()
    {
//        System.out.println("in size map size " + data.size() + " tree size " + queue.size() + " " +this.hashCode());
        return data.size();
    }


    @Override
    public boolean add(T t) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
       if (remove() != null)
       {
           return true;
       }
       return false;
    }

    @Override
    public boolean containsAll(@NotNull Collection<?> c) {
        return queue.containsAll(c);
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends T> c) {
        return false;
    }

    @Override
    public boolean removeAll(@NotNull Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(@NotNull Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

        data.clear();
        queue.clear();

    }

    @Override
    public boolean offer(T t) {
        return false;
    }

    @Override
    public T remove() {
        return poll();
    }


    @Override
    public T element() {
        return peek();
    }


}
