package tdk_enum.enumerators.separators.parallel.nested;

import tdk_enum.common.configuration.config_types.EnumerationPurpose;

import java.util.function.Function;

public class BusyWaitMinimalSeperatorEnumerator extends AuxiliaryMinimalSeparatorEnumerator {



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
//
//
//    }


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
                        if(capacity <=  P.size() + Q.size())
                        {
                            return true;

                        }
                        return false ;
                    }
                };
                break;
        }
    }


    @Override
    protected void algFinish() {
        taskManager.addToWatingList(id);


    }

    @Override
    protected void notifyWorking() {
        taskManager.removeFromWaitingList(id);

    }
}
