package tdk_enum.graph.triangulation.single_thread;

import tdk_enum.graph.data_structures.MinimalSeparator;
import tdk_enum.graph.graphs.chordal_graph.IChordalGraph;
import tdk_enum.graph.graphs.Converter;
import tdk_enum.graph.triangulation.AbstractMinimalTriangulationsEnumerator;

import java.util.Set;

public class MinimalTriangulationsEnumerator extends AbstractMinimalTriangulationsEnumerator{


    IChordalGraph nextChordalGraph;

    @Override
    public boolean hasNext()
    {
        return setsEnumerator.hasNext();

    }

    @Override
    public IChordalGraph next()
    {
//        setsEnumerator.next();
//        return nextChordalGraph;
        return Converter.minimalSeparatorsToTriangulation(graph, setsEnumerator.next());
    }

    @Override
    public int getNumberOfMinimalSeperatorsGenerated()
    {
        return seperatorGraph.getNumberOfNodesGenerated();

    }

    @Override
    public void print(Set<MinimalSeparator> result) {
        nextChordalGraph = Converter.minimalSeparatorsToTriangulation(graph, result);
        resultPrinter.print(nextChordalGraph);
    }
}
