package tdk_enum.enumerators.triangulation.single_thread;

import tdk_enum.common.configuration.config_types.EnumerationPurpose;
import tdk_enum.enumerators.generators.IGenerator;
import tdk_enum.graph.data_structures.MinimalSeparator;
import tdk_enum.graph.data_structures.Node;
import tdk_enum.graph.graphs.IGraph;
import tdk_enum.graph.graphs.chordal_graph.IChordalGraph;
import tdk_enum.graph.graphs.Converter;
import tdk_enum.enumerators.triangulation.AbstractMinimalTriangulationsEnumerator;

import java.util.Queue;
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

        return Converter.minimalSeparatorsToTriangulation(graph, (Set<MinimalSeparator>)setsEnumerator.next());
    }

    @Override
    public void executeAlgorithm() {
        setsEnumerator.executeAlgorithm();
    }





    @Override
    public void print(Set<MinimalSeparator> result) {
        nextChordalGraph = Converter.minimalSeparatorsToTriangulation(graph, result);
        resultPrinter.print(nextChordalGraph);
    }


}
