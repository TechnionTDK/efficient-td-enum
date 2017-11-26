package tdenum.graph.independent_set.single_thread;

import tdenum.graph.independent_set.AbstractMaximalIndependentSetsEnumerator;

import java.util.HashSet;
import java.util.Set;

public class RandomMaximalIndependentSetsEnumerator<T> extends AbstractMaximalIndependentSetsEnumerator<T>{


    @Override
    protected boolean timeLimitReached() {
        return Thread.currentThread().isInterrupted();    }



    @Override
    public boolean hasNext() {
        return !timeLimitReached();
    }

    @Override
    public Set next() {
        Set result = extender.extendToMaxIndependentSet(new HashSet());
        while(!newSetFound(result) && !timeLimitReached() )
        {
            result = extender.extendToMaxIndependentSet(new HashSet());
        }
        return result;
    }

    @Override
    public void doFirstStep() {
        newSetFound(extender.extendToMaxIndependentSet(new HashSet<T>()));
    }
}
