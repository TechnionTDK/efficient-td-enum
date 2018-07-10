package tdenum.graph.independent_set.parallel.capacity;

import tdenum.graph.independent_set.parallel.ParallelMaximalIndependentSetsEnumerator;

import java.util.concurrent.atomic.AtomicInteger;

public class CapcityParallelMaximalIndependentSetsEnumerator<T> extends ParallelMaximalIndependentSetsEnumerator<T> implements ICapacityParallelMaximalIndpendentSetsEnumerator {



    int capacity = 0;

    @Override
    protected boolean timeLimitReached() {
        return capacity <= setsNotExtended.size() + P.size();
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
