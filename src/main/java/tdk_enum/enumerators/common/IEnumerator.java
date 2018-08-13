package tdk_enum.enumerators.common;

import tdk_enum.common.IO.result_printer.IResultPrinter;
import tdk_enum.common.configuration.config_types.EnumerationPurpose;
import tdk_enum.enumerators.generators.IGenerator;

import java.util.Queue;
import java.util.Set;
import java.util.concurrent.Callable;

public interface IEnumerator<NodeType, EnumType, GraphType> extends Runnable, Callable{

    //enumerator interface
    boolean hasNext();
    EnumType next();

    void executeAlgorithm();

    //enumerator object fields setters
    void setQueue(Queue<EnumType> queue);
    void setV(Set<NodeType> v);
    void setExtendedCollection(Set<EnumType> collection);
    void setGraph(GraphType graph);
    GraphType getGraph();


    void setGenerator(IGenerator<GraphType, EnumType> generator);

    void setResultPrinter(IResultPrinter<EnumType> resultPrinter);

    long getId();

    void setId(long id);

    IResultPrinter<EnumType> getResultPrinter();

    void setCapacity(int capacity);

    void setPurpose(EnumerationPurpose purpose);
}
