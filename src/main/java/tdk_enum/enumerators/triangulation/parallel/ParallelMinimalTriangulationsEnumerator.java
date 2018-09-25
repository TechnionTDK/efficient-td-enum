package tdk_enum.enumerators.triangulation.parallel;

import tdk_enum.graph.data_structures.MinimalSeparator;
import tdk_enum.graph.graphs.chordal_graph.IChordalGraph;
import tdk_enum.graph.converters.Converter;
import tdk_enum.enumerators.triangulation.AbstractMinimalTriangulationsEnumerator;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ParallelMinimalTriangulationsEnumerator  extends AbstractMinimalTriangulationsEnumerator {


    IChordalGraph nextChordalGraph;


    Set<IChordalGraph> triangulations = ConcurrentHashMap.newKeySet();




    public Set<IChordalGraph> getTriangulations() {
        return triangulations;
    }

    @Override
    public boolean hasNext()

    {
        return setsEnumerator.hasNext();

    }

    @Override
    public IChordalGraph next()
    {
        setsEnumerator.next();
        return nextChordalGraph;
    }

    @Override
    public void executeAlgorithm() {
        setsEnumerator.executeAlgorithm();
    }



    @Override
    public void print(Set<MinimalSeparator> result) {
        IChordalGraph chordalGraph= Converter.minimalSeparatorsToTriangulation(graph, result);

       // triangulations.add(chordalGraph);

        resultPrinter.print(chordalGraph);
    }
}
