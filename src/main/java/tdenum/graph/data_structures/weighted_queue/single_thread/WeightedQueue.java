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


    Map<T, Integer> data = new HashMap<>();
    Map<Integer, List<T>> queue = new HashMap<>();
    TreeSet<Integer> weights = new TreeSet<>();
    T head = null;
    int removeIndex =0;

    public WeightedQueue()
    {

    }

    public WeightedQueue(boolean descOrder)
    {
        if (descOrder)
        {
            weights = new TreeSet<>(Comparator.reverseOrder());
        }
    }

    @Override
    public void increaseWeight(T v) {
        int weight = data.get(v);
        weight++;
        setWeight(v, weight);

    }

    @Override
    public void setWeight(T v, int weight) {
        if(data.containsKey(v))
        {
            int prevWeight = data.get(v);
            queue.get(prevWeight).remove(v);
            if (queue.get(prevWeight).size()==0)
            {
                queue.remove(prevWeight);
                weights.remove(prevWeight);
            }
        }
        data.put(v, weight);
        if(!queue.containsKey(weight))
        {
            queue.put(weight, new ArrayList<>());
        }
        queue.get(weight).add(v);
        weights.add(weight);


    }

    @Override
    public int getWeight(T v) {
        return data.get(v);
    }

    @Override
    public int size() {
        return data.size();
    }

    @Override
    public boolean isEmpty() {
        return data.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return data.containsKey(o);
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @NotNull
    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @NotNull
    @Override
    public <T1> T1[] toArray(@NotNull T1[] a) {
        return null;
    }

    @Override
    public boolean add(T t) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        if (!data.containsKey(o))
        {
            return  false;
        }

        int weight = data.get(o);
        data.remove(o);
        queue.get(weight).remove(removeIndex);
        if (queue.get(weight).size()==0)
        {
            queue.remove(weight);
            weights.remove(weight);
        }
        return true;
    }

    @Override
    public boolean containsAll(@NotNull Collection<?> c) {
        return false;
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
        weights.clear();

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
    public T poll() {
        if (head==null)
        {
            peek();
        }
        T result = head;
        head = null;
        remove(result);
        return result;
    }

    @Override
    public T element() {
        return peek();
    }

    @Override
    public T peek() {
        if (head!=null)
        {
            return  head;
        }
        int firstWeight = weights.first();

        head = queue.get(firstWeight).get(0);
        return head;


    }



//    Map<T, Integer> data =new HashMap<>();
////    TreeSet<Pair<T, Integer>> queue = new TreeSet<>(new ValueComparator(data));
//    Map<Integer, List<T>> queue = new HashMap<>();
//    TreeSet<Integer> weights = new TreeSet<>();
//    T head;
//
//
//    public WeightedQueue()
//    {
//
//    }
//
//    public WeightedQueue(boolean descOrder)
//    {
////        if (descOrder)
////        {
////            queue = new TreeSet<>(new ValueComparatorDesc(data));
////        }
//    }
//
//    @Override
//    public void increaseWeight(T v)
//    {
//        int weight = data.get(v);
//
//        weight++;
//        setWeight(v, weight);
//    }
//
//
//    @Override
//    public void setWeight(T v, int weight) {
//
////        if (data.containsKey(v))
////        {
////            int prevWeight = data.get(v);
//////            System.out.println("change weight for " + v);
//////            System.out.println("from " + prevWeight + " to " + weight);
//////            System.out.println("in set weight map size " + data.size() + " tree size " + queue.size() + " " +this.hashCode());
////            queue.remove(new Pair<>(v, prevWeight));
////        }
////
////        data.put(v, weight);
////        queue.add(new Pair<>(v, weight));
//////        System.out.println("in set weight map size " + data.size() + " tree size " + queue.size() + " " +this.hashCode());
//        if(data.containsKey(v))
//        {
//            int prevWeight = data.get(v);
//            queue.get(prevWeight).remove(v);
//            if (queue.get(prevWeight).size()==0)
//            {
//                queue.remove(prevWeight);
//                weights.remove(prevWeight);
//            }
//        }
//        data.put(v, weight);
//        if(!queue.containsKey(weight))
//        {
//            queue.put(weight, new ArrayList<>());
//        }
//        queue.get(weight).add(v);
//        weights.add(weight);
//
//    }
//
//    @Override
//    public int getWeight(T v) {
//        return data.get(v);
//    }
//
//    @Override
//    public T poll()
//    {
////        T v = peek();
////        queue.remove(queue.first());
////        data.remove(v);
////
//////        System.out.println("in poll map size " + data.size() + " tree size " + queue.size() + " " +this.hashCode());
////        return v;
//        if (head==null)
//        {
//            peek();
//        }
//        T result = head;
//        head = null;
//        remove(result);
//        return result;
//    }
//
//    @Override
//    public T peek()
//    {
////        T v = queue.first().getKey();
//////        System.out.println("in peek map size " + data.size() + " tree size " + queue.size() + " " +this.hashCode());
////        return v;
//
//        if (head!=null)
//        {
//            return  head;
//        }
//        int firstWeight = weights.first();
//        head = queue.get(firstWeight).get(0);
//        return head;
//    }
//
//    public boolean isEmpty()
//    {
////        System.out.println("in isEmpty map size " + data.size() + " tree size " + queue.size() + " " +this.hashCode());
////        System.out.println(data);
//        return  data.isEmpty();
//    }
//
//    @Override
//    public boolean contains(Object o) {
//        return false;
//    }
//
//    @NotNull
//    @Override
//    public Iterator iterator() {
//        return null;
//    }
//
//    @NotNull
//    @Override
//    public Object[] toArray() {
//        return new Object[0];
//    }
//
//    @NotNull
//    @Override
//    public Object[] toArray(@NotNull Object[] a) {
//        return new Object[0];
//    }
//
//    public int size()
//    {
////        System.out.println("in size map size " + data.size() + " tree size " + queue.size() + " " +this.hashCode());
//        return data.size();
//    }
//
//
//    @Override
//    public boolean add(T t) {
//        return false;
//    }
//
//    @Override
//    public boolean remove(Object o) {
//        if (!data.containsKey(o))
//        {
//            return  false;
//        }
//
//        int weight = data.get(o);
//        data.remove(o);
//        queue.get(weight).remove(o);
//        if (queue.get(weight).size()==0)
//        {
//            queue.remove(weight);
//            weights.remove(weight);
//        }
//        return true;
//    }
//
//    @Override
//    public boolean containsAll(@NotNull Collection<?> c) {
//        return false;
//    }
//
//    @Override
//    public boolean addAll(@NotNull Collection<? extends T> c) {
//        return false;
//    }
//
//    @Override
//    public boolean removeAll(@NotNull Collection<?> c) {
//        return false;
//    }
//
//    @Override
//    public boolean retainAll(@NotNull Collection<?> c) {
//        return false;
//    }
//
//    @Override
//    public void clear() {
//
//        data.clear();
//        queue.clear();
//        weights.clear();
//
//    }
//
//    @Override
//    public boolean offer(T t) {
//        return false;
//    }
//
//    @Override
//    public T remove() {
//        return poll();
//    }
//
//
//    @Override
//    public T element() {
//        return peek();
//    }


}
