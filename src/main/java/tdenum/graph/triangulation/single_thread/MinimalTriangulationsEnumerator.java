package tdenum.graph.triangulation.single_thread;

import tdenum.graph.data_structures.MinimalSeparator;
import tdenum.graph.graphs.chordal_graph.IChordalGraph;
import tdenum.graph.independent_set.Converter;
import tdenum.graph.triangulation.AbstractMinimalTriangulationsEnumerator;

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
        nextChordalGraph = Converter.minimalSeparatorsToTriangulation(graph, result);
        resultPrinter.print(nextChordalGraph);
    }
}
