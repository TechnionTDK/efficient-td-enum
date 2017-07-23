package tdenum.graph.data_structures;

import javafx.util.Pair;

import java.util.*;

/**
 * Created by dvir.dukhan on 7/11/2017.
 */
public  class WeightedQueue<T >
{

    private class ValueComparator implements Comparator
    {
        Map<T, Integer> map;
        ValueComparator(Map<T, Integer> map)
        {
            this.map = map;
        }


        @Override
        public int compare(Object o1, Object o2) {
            if (!o1.equals(o2))
            {
                Pair e1 = (Pair) o1;
                Pair e2 = (Pair) o2;
                Integer val2 = map.get(e2.getKey());
                Integer val1 = map.get(e1.getKey());
                int valComp = val2.compareTo(val1);
                if (valComp == 0)
                {

                    return ((Integer)e1.getKey().hashCode()).compareTo(e2.getKey().hashCode());
                }
                return  valComp;
            }
            return 0;

        }
    }
    Map<T, Integer> data =new HashMap<>();
    TreeSet<Pair<T, Integer>> queue = new TreeSet<>(new ValueComparator(data));




    public void increaseWeight(T v)
    {
        int weight = data.get(v);

        weight++;
        setWeight(v, weight);
    }


    public void setWeight(T v, int weight) {

        if (data.containsKey(v))
        {
            int prevWeight = data.get(v);
            queue.remove(new Pair<>(v, prevWeight));
        }

        data.put(v, weight);
        queue.add(new Pair<>(v, weight));

    }

    public int getWeight(T v) {
        return data.get(v);
    }

    public T pop()
    {
        T v = top();
        queue.remove(queue.first());
        data.remove(v);

        return v;
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
}
