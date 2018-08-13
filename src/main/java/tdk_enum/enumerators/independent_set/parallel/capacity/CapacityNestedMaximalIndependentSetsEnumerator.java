package tdk_enum.enumerators.independent_set.parallel.capacity;

import tdk_enum.enumerators.common.AbstractEnumerator;
import tdk_enum.enumerators.common.IEnumerator;
import tdk_enum.graph.graphs.succinct_graphs.separator_graph.parallel.DemonSeparatorGraph;
import tdk_enum.enumerators.independent_set.parallel.NestedMaximalIndependentSetsEnumerator;
import tdk_enum.enumerators.independent_set.parallel.capacity.nested.CapacityBusyWaitAuxiliaryMaximalIndependentSetsEnumerator;
import tdk_enum.enumerators.independent_set.parallel.nested.AuxiliaryMaximalIndependentSetsEnumerator;
import tdk_enum.common.configuration.config_types.TaskManagerType;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class CapacityNestedMaximalIndependentSetsEnumerator<T> extends NestedMaximalIndependentSetsEnumerator<T>
implements ICapacityParallelMaximalIndpendentSetsEnumerator{

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
    protected boolean finishCondition() {

        if (capacity <= Q.size() + P.size())
         {
            enumerators.shutdownNow();
            for (Future future : futures)
            {
                if(!future.isCancelled())
                {
                    future.cancel(true);
                }

            }
            demon.shutdownNow();
            if(!f.isCancelled())
            {
                f.cancel(true);
            }



            return  true;
        }
        return false;
    }



    @Override
    protected AuxiliaryMaximalIndependentSetsEnumerator generateAuxEnumerator(TaskManagerType enumeratorType, int id) {
        AuxiliaryMaximalIndependentSetsEnumerator enumerator;
        switch (enumeratorType){
            case BUSY_WAIT:
            {
                enumerator = new CapacityBusyWaitAuxiliaryMaximalIndependentSetsEnumerator();

                break;
            }
            default:
            {
                enumerator = new CapacityBusyWaitAuxiliaryMaximalIndependentSetsEnumerator();
                break;
            }
        }
        ICapacityParallelMaximalIndpendentSetsEnumerator capacityEnumerator = (ICapacityParallelMaximalIndpendentSetsEnumerator)enumerator;
        capacityEnumerator.setCapacity(capacity);
        enumerator.setId(id);
        enumerator.setTaskManager(taskManager);
        enumerator.setCache(jvCache);
        enumerator.setQueue(Q);
        enumerator.setExtendedCollection(P);
        enumerator.setV(V);
        enumerator.setGenerator(generator);
      //  enumerator.setScorer(scorer);
        enumerator.setResultPrinter(resultPrinter);
        //enumerator.setSetsNotExtended(setsNotExtended);
        enumerator.setGraph(graph);
        if(graph!=null)
        {
            DemonSeparatorGraph demon = (DemonSeparatorGraph)graph;
            demon.addId(enumerator.getId());
        }

        return enumerator;
    }

    @Override
    public void executeAlgorithm() {

        doFirstStep();
        f = demon.submit((DemonSeparatorGraph) graph);
        demon.shutdown();
        for (IEnumerator enumerator : enumeratorList) {
            futures.add(enumerators.submit((Callable<Object>) enumerator));
        }

        while (!finishCondition()) {
            try {
                demon.awaitTermination(1, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                demon.shutdownNow();
                enumerators.shutdownNow();
                for (Future future : futures) {
                    future.cancel(true);
                }
                f.cancel(true);
            }

        }

        if (!finishCondition()) {
            futures.add(enumerators.submit((Callable<Object>) generateAuxEnumerator(this.enumeratorType, taskManager.getThreadNumber() - 1)));
            enumerators.shutdown();
            while (!finishCondition()) {
                try {
                    enumerators.awaitTermination(1, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    for (Future future : futures) {
                        enumerators.shutdownNow();
                        if (!future.isCancelled()) {
                            future.cancel(true);
                        }

                    }
                }
            }

        }

        demon.shutdownNow();
        enumerators.shutdownNow();
        for (Future future : futures) {
            if (!future.isCancelled()) {
                future.cancel(true);
            }
        }
        if (!f.isCancelled()) {
            f.cancel(true);
        }
    }

}
