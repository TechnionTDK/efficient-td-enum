package tdk_enum.graph.data_structures.weighted_queue.single_thread;

import org.jetbrains.annotations.NotNull;
import tdk_enum.graph.data_structures.weighted_queue.IWeightedQueue;

import java.util.*;

public class QueueSet<T> implements IWeightedQueue<T> {


    Set<T> queueSet = new HashSet<>();
    LinkedList<T> queueQueue = new LinkedList<>();



    @Override
    public int size() {
        return queueSet.size();
    }

    @Override
    public boolean isEmpty() {
        return  queueSet.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return queueSet.contains(o);
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return queueQueue.iterator();
    }

    @NotNull
    @Override
    public Object[] toArray() {
        return queueQueue.toArray();
    }

    @NotNull
    @Override
    public <T1> T1[] toArray(@NotNull T1[] a) {
        return queueQueue.toArray(a);
    }

    @Override
    public boolean add(T t) {
        if(t != null &&queueSet.add(t))
        {
            queueQueue.add(t);
            return (true);
        }
        return false;
    }

    @Override
    public boolean remove(Object o) {
        if(o != null && queueSet.remove(o))
        {
            return queueQueue.remove(o);
        }
        return false;

    }

    @Override
    public boolean containsAll(@NotNull Collection<?> c) {
        return queueSet.containsAll(c);
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends T> c) {

        for (T t : c)
        {
            if (!add(t))
            {
                removeAll(c);
                return false;
            }
        }
        return true;
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
        queueQueue.clear();
        queueSet.clear();


    }

    @Override
    public boolean offer(T t) {
        return add(t);
    }

    @Override
    public T remove() {
        return poll();
    }


    @Override
    public T poll() {
        T t = queueQueue.poll();
        if(t !=null)
        {
            queueSet.remove(t);
        }
        return  t;

    }

    @Override
    public T element() {
        return queueQueue.element();
    }

    @Override
    public T peek() {
        return queueQueue.peek();
    }

    @Override
    public void increaseWeight(T v) {
        setWeight(v,0);
    }

    @Override
    public void setWeight(T v, int weight) {
        if(!queueSet.contains(v))
        {
            add(v);
        }

    }

    @Override
    public int getWeight(T v) {
        return 0;
    }
}
