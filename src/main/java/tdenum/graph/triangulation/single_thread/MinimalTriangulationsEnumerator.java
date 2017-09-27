package tdenum.graph.triangulation.single_thread;

import tdenum.graph.graphs.chordal_graph.IChordalGraph;
import tdenum.graph.independent_set.Converter;
import tdenum.graph.triangulation.AbstractMinimalTriangulationsEnumerator;

public class MinimalTriangulationsEnumerator extends AbstractMinimalTriangulationsEnumerator{



    @Override
    public boolean hasNext()
    {
        return setsEnumerator.hasNext();

    }

    @Override
    public IChordalGraph next()
    {
        return Converter.minimalSeparatorsToTriangulation(graph, setsEnumerator.next());
    }

    @Override
    public int getNumberOfMinimalSeperatorsGenerated()
    {
        return seperatorGraph.getNumberOfNodesGenerated();

    }

}
