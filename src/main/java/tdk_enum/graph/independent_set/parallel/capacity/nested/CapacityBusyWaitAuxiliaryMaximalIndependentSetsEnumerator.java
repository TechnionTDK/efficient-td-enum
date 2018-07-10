package tdk_enum.graph.independent_set.parallel.capacity.nested;

import tdk_enum.graph.independent_set.parallel.capacity.ICapacityParallelMaximalIndpendentSetsEnumerator;
import tdk_enum.graph.independent_set.parallel.nested.BusyWaitAuxiliaryMaximalIndependentSetsEnumerator;

public class CapacityBusyWaitAuxiliaryMaximalIndependentSetsEnumerator<T> extends BusyWaitAuxiliaryMaximalIndependentSetsEnumerator<T> implements ICapacityParallelMaximalIndpendentSetsEnumerator {


    int capacity = 0;

    @Override
    public int getCapacity() {
        return capacity;
    }

    @Override
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }


    @Override
    protected boolean timeLimitReached() {
        return capacity <= Q.size() + P.size();
    }
}
