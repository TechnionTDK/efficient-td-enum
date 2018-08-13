package tdk_enum.enumerators.common.nested;

import tdk_enum.common.IO.result_printer.IResultPrinter;
import tdk_enum.common.configuration.config_types.EnumerationPurpose;
import tdk_enum.common.configuration.config_types.TaskManagerType;
import tdk_enum.enumerators.common.AbstractEnumerator;
import tdk_enum.enumerators.common.IEnumerator;
import tdk_enum.enumerators.generators.IGenerator;
import tdk_enum.graph.graphs.succinct_graphs.separator_graph.parallel.DemonSeparatorGraph;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static tdk_enum.common.configuration.config_types.TaskManagerType.BUSY_WAIT;

public abstract class AbstractNestetEnumerator <NodeType, EnumType, GraphType> extends AbstractEnumerator <NodeType, EnumType, GraphType> {

    protected int timeout = 0;
    protected TaskManagerType enumeratorType = BUSY_WAIT;

    protected TaskManager taskManager = new TaskManager();


    protected List<IEnumerator> enumeratorList = new ArrayList<>();
    protected ExecutorService enumerators = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    protected List<Future<Object>> futures = new ArrayList<>();
    protected List<Callable<Object>> callables = new ArrayList<>();


    public AbstractNestetEnumerator()
    {
        setThreadNumber(Runtime.getRuntime().availableProcessors());
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public void setThreadNumber(int threadNumber)
    {
        taskManager.setThreadNumber(threadNumber);
        enumeratorList.clear();
        callables.clear();
        for (int i =0; i < threadNumber; i++)
        {
            enumeratorList.add(generateAuxEnumerator(enumeratorType, i));
        }
        enumerators = Executors.newFixedThreadPool(threadNumber);
        for(IEnumerator enumerator : enumeratorList)
        {
            enumerator.setPurpose(purpose);
            callables.add(enumerator);
        }



    }

    public void setEnumeratorType(TaskManagerType enumeratorType)
    {
        this.enumeratorType = enumeratorType;
        setThreadNumber(taskManager.getThreadNumber());
    }

    protected abstract AbstractEnumerator generateAuxEnumerator(TaskManagerType enumeratorType, int id);
//    {
//        AuxiliaryMaximalIndependentSetsEnumerator enumerator;
//        switch (enumeratorType){
//            case BUSY_WAIT:
//            {
//                enumerator = new BusyWaitAuxiliaryMaximalIndependentSetsEnumerator();
//                break;
//            }
//            default:
//            {
//                enumerator = new BusyWaitAuxiliaryMaximalIndependentSetsEnumerator();
//                break;
//            }
//        }
//        enumerator.setId(id);
//        enumerator.setTaskManager(taskManager);
//        enumerator.setCache(jvCache);
//        enumerator.setQueue(Q);
//        enumerator.setExtendedCollection(P);
//        enumerator.setV(V);
//        enumerator.setGenerator(generator);
//
//        enumerator.setResultPrinter(resultPrinter);
//
//        enumerator.setGraph(graph);
//        if(graph!=null)
//        {
//            DemonSeparatorGraph demon = (DemonSeparatorGraph)graph;
//            demon.addId(enumerator.getId());
//        }
//
//        return enumerator;
//    }

    @Override
    public void setQueue(Queue<EnumType> q) {
        super.setQueue(q);
        for (IEnumerator enumerator : enumeratorList) {
            enumerator.setQueue(q);
        }
    }

    @Override
    public void setExtendedCollection(Set<EnumType> p) {
        super.setExtendedCollection(p);
        for (IEnumerator enumerator : enumeratorList) {
            enumerator.setExtendedCollection(p);
        }
    }

    @Override
    public void setGraph(GraphType graph) {
        super.setGraph(graph);
        for (IEnumerator enumerator : enumeratorList) {
            enumerator.setGraph(graph);
           // DemonSeparatorGraph demon = (DemonSeparatorGraph)graph;
           // demon.addId(enumerator.getId());
        }
    }

//    @Override
//    public void setCache(ICache cache) {
//        super.setCache(cache);
//        for (AuxiliaryMaximalIndependentSetsEnumerator enumerator : enumeratorList) {
//            enumerator.setCache(cache);
//        }
//    }

//    @Override
//    public void setSetsNotExtended(Set<Set<T>> setsNotExtended) {
//        super.setSetsNotExtended(setsNotExtended);
//        for (AuxiliaryMaximalIndependentSetsEnumerator enumerator : enumeratorList) {
//            enumerator.setSetsNotExtended(setsNotExtended);
//        }
//    }

    @Override
    public void setResultPrinter(IResultPrinter<EnumType> resultPrinter) {
        super.setResultPrinter(resultPrinter);
        for (IEnumerator enumerator : enumeratorList) {
            enumerator.setResultPrinter(resultPrinter);
        }
    }

    @Override
    public void setGenerator(IGenerator extender) {
        super.setGenerator(extender);
        for (IEnumerator enumerator : enumeratorList) {
            enumerator.setGenerator(extender);
        }
    }



    @Override
    protected void iteratingNodePhase() {

    }

    @Override
    public void stepByStepDoFirstStep() {

    }

    @Override
    protected void getNextResultToManipulate() {

    }

    @Override
    protected boolean runStepByStepFullEnumeration() {
        return false;
    }



    @Override
    protected EnumType manipulateNodeAndResult(NodeType node, EnumType result) {
        return null;
    }

    @Override
    protected boolean stepByStepTryGenerateNewResultFromNode(NodeType node) {
        return false;
    }

    @Override
    protected boolean stepByStepTryGenerateNewResultFromResult(EnumType result) {
        return false;
    }

    @Override
    protected boolean stepByStepTryGenerateNewResult(NodeType node, EnumType currentResult) {
        return false;
    }

    @Override
    protected void doFirstStep() {

    }

    @Override
    protected void tryGenerateNewResult(NodeType node, EnumType result) {

    }

    @Override
    protected void newResultFound(EnumType c) {

    }

    @Override
    protected void changeVIfNeeded() {

    }

    @Override
    protected boolean newStepByStepResultFound(EnumType c) {
        return false;
    }

    @Override
    protected boolean stepByStepIteratingNodesPhase() {
        return false;
    }


    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public EnumType next() {
        return null;
    }


    @Override
    public void setPurpose(EnumerationPurpose purpose) {
        this.purpose = purpose;
        for(IEnumerator enumerator : enumeratorList)
        {
            enumerator.setPurpose(purpose);
        }

    }

    @Override
    public void setCapacity(int capacity) {
        this.capacity = capacity;
        for(IEnumerator enumerator : enumeratorList)
        {
            enumerator.setCapacity(capacity);
        }
    }


}
