package tdk_enum.enumerators.common;

import tdk_enum.common.IO.result_printer.IResultPrinter;
import tdk_enum.common.configuration.config_types.EnumerationPurpose;
import tdk_enum.enumerators.common.nested.TaskManager;
import tdk_enum.enumerators.generators.IGenerator;
import tdk_enum.graph.data_structures.weighted_queue.single_thread.QueueSet;

import java.util.*;
import java.util.function.Function;

public abstract class AbstractEnumerator<NodeType, EnumType, GraphType> implements IEnumerator<NodeType, EnumType, GraphType>{










    protected boolean started = false;

    protected GraphType graph;

    protected EnumType currentEnumResult;
    protected NodeType currentNode;

    protected Boolean nextResultReady = false;
    protected EnumType nextResult;

    protected IGenerator<GraphType, EnumType> generator;
    protected Set<NodeType> V = new HashSet<>();
    protected Set<EnumType> P = new HashSet<>();
    protected Queue<EnumType> Q = new QueueSet<>();

    protected Iterator<NodeType> nodesIterator;

    protected AlgorithmStep step;

    public DefaultBuilder<EnumType> defaultBuilder;


    protected long id;

    protected TaskManager taskManager;

    Thread mainThread;

    public void setMainThread(Thread mainThread)
    {
        this.mainThread = mainThread;
    }


    public void setTaskManager(TaskManager taskManager) {
        this.taskManager = taskManager;
    }
    @Override
    public void run() {
        executeAlgorithm();

    }

    @Override
    public Object call()
    {
        executeAlgorithm();
        return true;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }
    protected IResultPrinter<EnumType> resultPrinter = new IResultPrinter<EnumType>() {
        @Override
        public void print(EnumType result) {

        }
    };

    @Override
    public IResultPrinter<EnumType> getResultPrinter()
    {
        return  this.resultPrinter;
    }



    @Override
    public void setQueue(Queue<EnumType> queue) {
        this.Q = queue;

    }

    @Override
    public void setV(Set<NodeType> v) {
        this.V = v;
    }



    @Override
    public void setExtendedCollection(Set<EnumType> collection) {
        this.P = collection;
    }

    @Override
    public void setGraph(GraphType graph) {
        this.graph = graph;

    }

    @Override
    public GraphType getGraph()
    {
        return this.graph;
    }

    @Override
    public void setGenerator(IGenerator<GraphType, EnumType> generator) {
        this.generator = generator;
    }

    @Override
    public void setResultPrinter(IResultPrinter<EnumType> resultPrinter)
    {
        this.resultPrinter = resultPrinter;
    }


    protected EnumType generateNewResult(GraphType graph, EnumType B) {
        return generator.generateNew(graph, B);
    }


    protected abstract void iteratingNodePhase();

    public abstract void stepByStepDoFirstStep();

    protected abstract void getNextResultToManipulate();

    protected abstract boolean runStepByStepFullEnumeration();



    protected abstract EnumType manipulateNodeAndResult(NodeType node, EnumType result);

    protected abstract boolean stepByStepTryGenerateNewResultFromNode(NodeType node);
    protected abstract boolean stepByStepTryGenerateNewResultFromResult(EnumType result);

    protected abstract boolean stepByStepTryGenerateNewResult(NodeType node, EnumType currentResult);

    protected abstract void doFirstStep();

    protected abstract void tryGenerateNewResult(NodeType node, EnumType result);

    protected abstract void newResultFound(EnumType c);
    protected abstract void changeVIfNeeded();



    protected abstract boolean newStepByStepResultFound(EnumType c);

    protected abstract boolean stepByStepIteratingNodesPhase();


    protected  void algFinish(){}
    protected  void notifyWorking() {}

    protected Function<Void, Boolean> finishConditionFunction =  new Function<Void, Boolean>() {
        @Override
        public Boolean apply(Void aVoid) {
            return Thread.currentThread().isInterrupted();
        }
    };
    protected  boolean finishCondition()
    {
        return finishConditionFunction.apply(null);
    }
    protected int capacity;

    public int getCapacity() {
        return capacity;
    }

    @Override
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    protected EnumerationPurpose purpose = EnumerationPurpose.STANDALONE;
    @Override
    public  void setPurpose(EnumerationPurpose purpose)
    {
        this.purpose = purpose;
    }





}
