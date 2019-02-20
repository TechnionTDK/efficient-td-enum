package tdk_enum.enumerators.separators.parallel.nested;

import tdk_enum.enumerators.separators.single_thread.CachedMinimalSeparatorsEnumerator;
import tdk_enum.enumerators.separators.single_thread.MinimalSeparatorsEnumerator;

public class AuxiliaryMinimalSeparatorEnumerator extends CachedMinimalSeparatorsEnumerator {
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
