package tdk_enum.enumerators.separators.parallel.nested;

import tdk_enum.enumerators.separators.single_thread.MinimalSeparatorsEnumerator;

public class AuxiliaryMinimalSeparatorEnumerator extends MinimalSeparatorsEnumerator{
    @Override
    public void executeAlgorithm()
    {
        while(!finishCondition())
        {
            while(!Q.isEmpty() && !finishCondition())
            {
                notifyWorking();
                currentEnumResult = Q.poll();
                P.add(currentEnumResult);
                changeVIfNeeded();
                iteratingNodePhase();
            }
            algFinish();
        }

    }

}
