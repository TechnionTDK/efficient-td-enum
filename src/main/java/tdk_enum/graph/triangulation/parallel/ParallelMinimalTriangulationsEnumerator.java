package tdk_enum.graph.triangulation.parallel;

import tdk_enum.graph.data_structures.MinimalSeparator;
import tdk_enum.graph.graphs.chordal_graph.IChordalGraph;
import tdk_enum.graph.graphs.Converter;
import tdk_enum.graph.triangulation.AbstractMinimalTriangulationsEnumerator;

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
    public int getNumberOfMinimalSeperatorsGenerated()
    {
        return seperatorGraph.getNumberOfNodesGenerated();

    }

    @Override
    public void print(Set<MinimalSeparator> result) {
        IChordalGraph chordalGraph= Converter.minimalSeparatorsToTriangulation(graph, result);
        nextChordalGraph = chordalGraph;
//        triangulations.add(chordalGraph);

        resultPrinter.print(chordalGraph);
    }
}