package tdk_enum.enumerators;

import tdk_enum.common.IO.result_printer.IResultPrinter;
import tdk_enum.generators.IGenerator;

import java.util.Collection;
import java.util.Queue;
import java.util.Set;

public interface IEnumerator<NodeType, EnumType, GraphType> {

    //enumerator interface
    boolean hasNext();
    EnumType next();

    void executeAlgorithm();

    //enumerator object fields setters
    void setQueue(Queue<EnumType> queue);
    void setV(Collection<NodeType> v);
    void setExtendedCollection(Collection<EnumType> collection);
    void setGraph(GraphType graph);



    void setGenerator(IGenerator<GraphType, EnumType> generator);

    void setResultPrinter(IResultPrinter<EnumType> resultPrinter);
}
