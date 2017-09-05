package tdenum.graph.data_structures.comparators;

import javafx.util.Pair;

import java.util.Comparator;
import java.util.Map;


    public class ValueComparatorDesc<T> implements Comparator
    {
        Map<T, Integer> map;
        public ValueComparatorDesc(Map<T, Integer> map)
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

