package tdenum.graph.data_structures.weighted_queue.single_thread;

import org.jetbrains.annotations.NotNull;
import tdenum.graph.data_structures.weighted_queue.IWeightedQueue;

import java.util.*;

public class RandomizedWeightedQueue<T> extends WeightedQueue<T> {






//    Map<T, Integer> data = new HashMap<>();
//    Map<Integer, List<T>> queue = new HashMap<>();
//    TreeSet<Integer> weights = new TreeSet<>();
//    T head = null;


    public RandomizedWeightedQueue()
    {

    }

    public RandomizedWeightedQueue(boolean descOrder)
    {
        super(descOrder);

    }

//    @Override
//    public void increaseWeight(T v) {
//        int weight = data.get(v);
//        weight++;
//        setWeight(v, weight);
//
//    }
//
//    @Override
//    public void setWeight(T v, int weight) {
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
//
//    }
//
//    @Override
//    public int getWeight(T v) {
//        return data.get(v);
//    }
//
//    @Override
//    public int size() {
//        return data.size();
//    }
//
//    @Override
//    public boolean isEmpty() {
//        return data.isEmpty();
//    }
//
//    @Override
//    public boolean contains(Object o) {
//        return data.containsKey(o);
//    }
//
//    @NotNull
//    @Override
//    public Iterator<T> iterator() {
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
//    public <T1> T1[] toArray(@NotNull T1[] a) {
//        return null;
//    }
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
//    @Override
//    public T poll() {
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
//    public T element() {
//        return peek();
//    }

    @Override
    public T peek() {
        if (head!=null)
        {
            return  head;
        }
        int firstWeight = weights.first();
        removeIndex = new Random().nextInt(queue.get(firstWeight).size());
        head = queue.get(firstWeight).get(removeIndex);
        return head;


    }
}
