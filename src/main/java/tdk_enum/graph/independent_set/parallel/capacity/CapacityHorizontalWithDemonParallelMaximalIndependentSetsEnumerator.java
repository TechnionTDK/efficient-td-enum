package tdk_enum.graph.independent_set.parallel.capacity;

import tdk_enum.graph.independent_set.parallel.HorizontalWithDemonParallelMaximalIndependentSetsEnumerator;

public class CapacityHorizontalWithDemonParallelMaximalIndependentSetsEnumerator<T> extends HorizontalWithDemonParallelMaximalIndependentSetsEnumerator<T> implements ICapacityParallelMaximalIndpendentSetsEnumerator{
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