package tdk_enum.enumerators.independent_set.single_thread;

import tdk_enum.enumerators.independent_set.AbstractMaximalIndependentSetsEnumerator;

import java.util.HashSet;
import java.util.Set;

public class RandomMaximalIndependentSetsEnumerator<T> extends AbstractMaximalIndependentSetsEnumerator<T>{


    @Override
    protected boolean finishCondition() {
        return Thread.currentThread().isInterrupted();    }

    @Override
    protected Set<T> manipulateNodeAndResult(T node, Set<T> result) {
        return null;
    }


    @Override
    public void executeAlgorithm()
    {
        Set result = generator.generateNew(new HashSet());
        while(!finishCondition() )
        {
            newResultFound(generator.generateNew(new HashSet()));
        }
    }

    @Override
    public boolean hasNext() {
        return !finishCondition();
    }

    @Override
    public Set next() {
        Set result = generator.generateNew(new HashSet());
        while(!newStepByStepResultFound(result) && !finishCondition() )
        {
            result = generator.generateNew(new HashSet());
        }
        return result;
    }

    @Override
    public void doFirstStep() {
        newResultFound(generator.generateNew(new HashSet<T>()));
    }

    @Override
    public void stepByStepDoFirstStep()
    {
        newStepByStepResultFound(generator.generateNew(new HashSet<T>()));
    }
}
