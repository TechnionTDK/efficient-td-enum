package tdk_enum.graph.independent_set.parallel.capacity;

import tdk_enum.graph.graphs.succinct_graphs.separator_graph.parallel.DemonSeparatorGraph;
import tdk_enum.graph.independent_set.parallel.NestedMaximalIndependentSetsEnumerator;
import tdk_enum.graph.independent_set.parallel.capacity.nested.CapacityBusyWaitAuxiliaryMaximalIndependentSetsEnumerator;
import tdk_enum.graph.independent_set.parallel.nested.AuxiliaryMaximalIndependentSetsEnumerator;
import tdk_enum.graph.independent_set.parallel.nested.NestedEnumeratorType;

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
    protected boolean timeLimitReached() {

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
    protected AuxiliaryMaximalIndependentSetsEnumerator generateAuxEnumerator(NestedEnumeratorType enumeratorType, int id) {
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
        enumerator.setQ(Q);
        enumerator.setP(P);
        enumerator.setV(V);
        enumerator.setExtender(extender);
        enumerator.setScorer(scorer);
        enumerator.setResultPrinter(resultPrinter);
        enumerator.setSetsNotExtended(setsNotExtended);
        enumerator.setGraph(graph);
        if(graph!=null)
        {
            DemonSeparatorGraph demon = (DemonSeparatorGraph)graph;
            demon.addId(enumerator.getId());
        }

        return enumerator;
    }

    @Override
    protected void runFullEnumeration() {

        f = demon.submit((DemonSeparatorGraph) graph);
        demon.shutdown();
        for (AuxiliaryMaximalIndependentSetsEnumerator enumerator : enumeratorList) {
            futures.add(enumerators.submit(enumerator));
        }

        while (!timeLimitReached()) {
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

        if (!timeLimitReached()) {
            futures.add(enumerators.submit(generateAuxEnumerator(this.enumeratorType, taskManager.getThreadNumber() - 1)));
            enumerators.shutdown();
            while (!timeLimitReached()) {
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
