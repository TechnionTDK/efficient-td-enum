package tdenum.graph.independent_set.parallel.capacity;

import tdenum.graph.independent_set.parallel.HorizontalParallelMaximalIndependentSetsEnumerator;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class CapacityHorizontalMaximalIndependentSetsEnumerator<T> extends HorizontalParallelMaximalIndependentSetsEnumerator<T> implements ICapacityParallelMaximalIndpendentSetsEnumerator{

    int capacity = 0;


    @Override
    protected boolean timeLimitReached() {
        return capacity <= Q.size() + P.size();
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    @Override
    public void setCapacity(int capacity) {
        this.capacity = capacity;

    }
}
