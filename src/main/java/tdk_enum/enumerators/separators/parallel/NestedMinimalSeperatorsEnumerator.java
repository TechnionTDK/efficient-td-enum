package tdk_enum.enumerators.separators.parallel;

import tdk_enum.enumerators.common.AbstractEnumerator;
import tdk_enum.enumerators.common.nested.AbstractNestetEnumerator;
import tdk_enum.common.configuration.config_types.TaskManagerType;
import tdk_enum.graph.data_structures.MinimalSeparator;
import tdk_enum.graph.data_structures.Node;
import tdk_enum.graph.data_structures.NodeSet;
import tdk_enum.graph.graphs.IGraph;
import tdk_enum.enumerators.separators.IMinimalSeparatorsEnumerator;
import tdk_enum.enumerators.separators.parallel.nested.AuxiliaryMinimalSeparatorEnumerator;
import tdk_enum.enumerators.separators.parallel.nested.BusyWaitMinimalSeperatorEnumerator;
import tdk_enum.enumerators.separators.scorer.ISeparatorScorer;

import java.util.Set;
import java.util.concurrent.Future;

public class NestedMinimalSeperatorsEnumerator extends AbstractNestetEnumerator <Node, MinimalSeparator,IGraph>  implements IMinimalSeparatorsEnumerator {


    protected ISeparatorScorer scorer;
    @Override
    public void setScorer(ISeparatorScorer scorer) {
        this.scorer = scorer;
    }



    @Override
    public int numberOfSeparators()
    {
        return P.size();
    }





    @Override
    protected AbstractEnumerator generateAuxEnumerator(TaskManagerType enumeratorType, int id) {
        AuxiliaryMinimalSeparatorEnumerator enumerator;
        switch (enumeratorType){
            case BUSY_WAIT:
            {
                enumerator = new BusyWaitMinimalSeperatorEnumerator();
                break;
            }
            default:
            {
                enumerator = new BusyWaitMinimalSeperatorEnumerator();
                break;
            }
        }
        enumerator.setId(id);
        enumerator.setTaskManager(taskManager);

        enumerator.setQueue(Q);
        enumerator.setExtendedCollection(P);
        enumerator.setV(V);
        enumerator.setGenerator(generator);

        enumerator.setResultPrinter(resultPrinter);

        enumerator.setGraph(graph);

        return enumerator;
    }

    @Override
    protected boolean finishCondition() {

        if (Thread.currentThread().isInterrupted())
        {
            enumerators.shutdownNow();
            for (Future future : futures)
            {
                if(!future.isCancelled())
                {
                    future.cancel(true);
                }

            }

            return  true;
        }
        return false;
    }



    @Override
    public void executeAlgorithm() {
        doFirstStep();

//        for (IEnumerator enumerator: enumeratorList)
//        {
//            futures.add(enumerators.submit((Runnable) enumerator));
//        }




        try {
            futures = enumerators.invokeAll(callables);
        } catch (InterruptedException e) {
            e.printStackTrace();
            enumerators.shutdownNow();
            for (Future future: futures)
            {
                if(!future.isCancelled())
                {
                    future.cancel(true);
                }
            }
        }
        enumerators.shutdown();
//        try {
//            enumerators.awaitTermination(timeout, TimeUnit.SECONDS);
//        } catch (InterruptedException e) {
//            for (Future future: futures)
//            {
//                enumerators.shutdownNow();
//                if(!future.isCancelled())
//                {
//                    future.cancel(true);
//                }
//
//            }
//        }


//        System.out.println("results " + P.size());

    }


    @Override
    protected void doFirstStep()
    {
        graph.accessVertices().parallelStream().forEach(v -> {
            Set<Node> vAndNeighbors = graph.getNeighbors(v);
            vAndNeighbors.add(v);
            for (NodeSet nodeSet : graph.getComponents(vAndNeighbors))
            {
                MinimalSeparator potentialSeparator = new MinimalSeparator(graph.getNeighbors(nodeSet));
                newResultFound(potentialSeparator);
            }
        });

    }


    @Override
    protected void newResultFound(MinimalSeparator sep) {
        if (sep.size() > 0) {
            if (!P.contains(sep) && Q.add(sep)) {
                resultPrinter.print(sep);
            }


        }
    }

    }
