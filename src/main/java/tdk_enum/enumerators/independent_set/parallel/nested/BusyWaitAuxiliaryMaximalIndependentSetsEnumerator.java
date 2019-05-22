package tdk_enum.enumerators.independent_set.parallel.nested;

import tdk_enum.common.configuration.config_types.EnumerationPurpose;

import java.util.function.Function;

public class BusyWaitAuxiliaryMaximalIndependentSetsEnumerator<T> extends AuxiliaryMaximalIndependentSetsEnumerator<T> {
    //assume Q is global
    //P is global
    //extending locally
    //v local
    boolean firstEntrance = true;
//    @Override
//    public void setSetsNotExtended(Set<Set<T>> setsNotExtended) {
//        this.setsNotExtended = new HashSet<>();
//    }
//    @Override
//    public void setV(Set<T> v)
//    {
//        this.V = new HashSet<>();
//    }


//    @Override
//    protected boolean finishCondition() {
//        if (Thread.currentThread().isInterrupted())
//        {
//            return true;
//        }
//        if(Q.isEmpty())
//        {
//            return (taskManager.numberOfWaiting() == taskManager.getThreadNumber());
//
//        }
//        else
//        {
//            return false;
//        }
////        if (firstEntrance)
////        {
////            firstEntrance = false;
////            return false;
////        }
////        if (taskManager.doneThreads.intValue() == taskManager.threadNumber) {
////            return true;
////        } else {
////            return false;
////        }
//
//    }

    @Override
    protected void algFinish() {
        taskManager.addToWatingList(id);


    }

    @Override
    protected void notifyWorking() {
        taskManager.removeFromWaitingList(id);

    }


    @Override
    public  void setPurpose(EnumerationPurpose purpose)
    {
        this.purpose =purpose;
        switch (purpose)
        {
            case STANDALONE:
                finishConditionFunction = new Function<Void, Boolean>() {
                    @Override
                    public Boolean apply(Void aVoid) {
                        if (Thread.currentThread().isInterrupted())
                        {
                            return true;
                        }
                        if(Q.isEmpty())
                        {
                            return (taskManager.numberOfWaiting() == taskManager.getThreadNumber());

                        }
                        else
                        {
                            return false;
                        }
                    }
                };
                break;
            case BENCHMARK_COMPARE:
                finishConditionFunction = new Function<Void, Boolean>() {
                    @Override
                    public Boolean apply(Void aVoid) {
                        if(capacity <=   Q.size())
                        {
                            return true;

                        }
                        return false ;
                    }
                };
                break;
        }
    }


}