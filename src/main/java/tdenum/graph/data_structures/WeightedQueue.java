package tdenum.graph.data_structures;

import javafx.util.Pair;

import java.util.*;

/**
 * Created by dvir.dukhan on 7/11/2017.
 */
public  class WeightedQueue<T >
{

    class ValueComparatorDesc implements Comparator
    {
        Map<T, Integer> map;
        ValueComparatorDesc(Map<T, Integer> map)
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
                    if (!e2.getKey().equals(e1.getKey()))
                    {
                        int keyComp = ((Integer)e1.getKey().hashCode()).compareTo(e2.getKey().hashCode());
                        if (keyComp == 0)
                        {
                            return -1;
                        }
                        return keyComp;
                    }

                }
                return  valComp;
            }
            return 0;

        }
    }


    class ValueComparator implements Comparator
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
                int valComp = val1.compareTo(val2);
                if (valComp == 0)
                {
                    if (!e1.getKey().equals(e2.getKey()))
                    {
                        int keyComp = ((Integer)e1.getKey().hashCode()).compareTo(e2.getKey().hashCode());
                        if (keyComp == 0)
                        {
                            return -1;
                        }
                        return keyComp;
                    }

                }
                return  valComp;
            }
            return 0;

        }
    }

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
//            System.out.println("change weight for " + v);
//            System.out.println("from " + prevWeight + " to " + weight);
//            System.out.println("in set weight map size " + data.size() + " tree size " + queue.size() + " " +this.hashCode());
            queue.remove(new Pair<>(v, prevWeight));
        }

        data.put(v, weight);
        queue.add(new Pair<>(v, weight));
//        System.out.println("in set weight map size " + data.size() + " tree size " + queue.size() + " " +this.hashCode());

    }

    public int getWeight(T v) {
        return data.get(v);
    }

    public T pop()
    {
        T v = top();
        queue.remove(queue.first());
        data.remove(v);

//        System.out.println("in pop map size " + data.size() + " tree size " + queue.size() + " " +this.hashCode());
        return v;
    }

    public T top()
    {
        T v = queue.first().getKey();
//        System.out.println("in top map size " + data.size() + " tree size " + queue.size() + " " +this.hashCode());
        return v;
    }

    public boolean isEmpty()
    {
//        System.out.println("in isEmpty map size " + data.size() + " tree size " + queue.size() + " " +this.hashCode());
//        System.out.println(data);
        return  data.isEmpty();
    }

    public int size()
    {
//        System.out.println("in size map size " + data.size() + " tree size " + queue.size() + " " +this.hashCode());
        return data.size();
    }

    public Collection<T> getKeys()
    {
        return data.keySet();
    }
}
