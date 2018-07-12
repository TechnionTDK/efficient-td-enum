package tdk_enum.enumerators;

import tdk_enum.common.IO.result_printer.IResultPrinter;
import tdk_enum.generators.IGenerator;
import tdk_enum.graph.data_structures.weighted_queue.single_thread.QueueSet;
import tdk_enum.graph.data_structures.weighted_queue.single_thread.WeightedQueue;

import java.util.*;
import java.util.function.Supplier;

public abstract class AbstractEnumerator<NodeType, EnumType, GraphType> implements IEnumerator<NodeType, EnumType, GraphType>{





    protected abstract Boolean newStepByStepResultFound(EnumType c);

    protected abstract boolean iteratingNodesPhase();

    protected class DefaultBuilder<E> {
        private Supplier<E> supplier;

        DefaultBuilder(Supplier<E> supplier) {
            this.supplier = supplier;
        }

        E createInstance() {
            return supplier.get();
        }
    }


    protected boolean started = false;

    protected GraphType graph;

    protected EnumType currentEnumResult;
    protected NodeType currentNode;

    protected Boolean nextResultReady = false;
    protected EnumType nextResult;

    protected IGenerator<GraphType, EnumType> generator;
    protected Collection<NodeType> V = new HashSet<>();
    protected Collection<EnumType> P = new HashSet<>();
    protected Queue<EnumType> Q = new QueueSet<>();

    protected Iterator<NodeType> nodesIterator;

    protected AlgorithmStep step;

    DefaultBuilder<EnumType> enumTypeDefaultBuilder;


    protected IResultPrinter<EnumType> resultPrinter = new IResultPrinter<EnumType>() {
        @Override
        public void print(EnumType result) {

        }
    };



    @Override
    public void setQueue(Queue<EnumType> queue) {
        this.Q = queue;

    }

    @Override
    public void setV(Collection<NodeType> v) {
        this.V = v;
    }



    @Override
    public void setExtendedCollection(Collection<EnumType> collection) {
        this.P = collection;
    }

    @Override
    public void setGraph(GraphType graph) {
        this.graph = graph;

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


    public abstract void stepByStepDoFirstStep();

    abstract void getNextResultToManipulate();

    protected abstract boolean runStepByStepFullEnumeration();

    protected abstract boolean finishCondition();

    protected abstract EnumType manipulateNodeAndResult(NodeType node, EnumType result);

    protected abstract boolean stepByStepTryGenerateNewResultFromNode(NodeType node);
    protected abstract boolean stepByStepTryGenerateNewResultFromResult(EnumType result);

    protected abstract boolean stepByStepTryGenerateNewResult(NodeType node, EnumType currentResult);

    abstract void doFirstStep();

    protected abstract void tryGenerateNewResult(NodeType node, EnumType result);

    protected abstract void newResultFound(EnumType c);
    protected abstract void changeVIfNeeded();



}
