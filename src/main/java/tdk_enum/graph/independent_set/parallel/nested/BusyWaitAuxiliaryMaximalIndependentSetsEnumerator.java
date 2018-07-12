package tdk_enum.graph.independent_set.parallel.nested;

import java.util.HashSet;
import java.util.Set;

public class BusyWaitAuxiliaryMaximalIndependentSetsEnumerator<T> extends AuxiliaryMaximalIndependentSetsEnumerator<T> {
    //assume Q is global
    //P is global
    //extending locally
    //v local
    boolean firstEntrance = true;
    @Override
    public void setSetsNotExtended(Set<Set<T>> setsNotExtended) {
        this.setsNotExtended = new HashSet<>();
    }
    @Override
    public void setV(Set<T> v)
    {
        this.V = new HashSet<>();
    }


    @Override
    protected boolean finishCondition() {
        if (timeLimitReached())
        {
            return true;
        }
        if(Q.isEmpty())
        {
            return (taskManager.waitingList.size() == taskManager.threadNumber);

        }
        else
        {
            return false;
        }
//        if (firstEntrance)
//        {
//            firstEntrance = false;
//            return false;
//        }
//        if (taskManager.doneThreads.intValue() == taskManager.threadNumber) {
//            return true;
//        } else {
//            return false;
//        }

    }

    @Override
    protected void algFinish() {
        taskManager.waitingList.add(id);


    }

    @Override
    protected void notifyWorking() {
        taskManager.waitingList.remove(id);

    }


}