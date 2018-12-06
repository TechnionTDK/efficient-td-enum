package tdk_enum.enumerators.triangulation.parallel;

import tdk_enum.graph.converters.Converter;
import tdk_enum.graph.data_structures.MinimalSeparator;
import tdk_enum.graph.graphs.chordal_graph.IChordalGraph;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class StoringParallelMinimalTriangulationsEnumerator extends ParallelMinimalTriangulationsEnumerator {

    @Override
    public void print(Set<MinimalSeparator> result) {
        IChordalGraph chordalGraph = Converter.minimalSeparatorsToTriangulation(graph, result);

        triangulations.add(chordalGraph);

        resultPrinter.print(chordalGraph);
    }

    Set<IChordalGraph> triangulations = ConcurrentHashMap.newKeySet();

    public Set<IChordalGraph> getTriangulations() {
        return triangulations;
    }


}
