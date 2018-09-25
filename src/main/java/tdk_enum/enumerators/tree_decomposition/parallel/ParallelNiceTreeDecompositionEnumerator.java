package tdk_enum.enumerators.tree_decomposition.parallel;

import tdk_enum.enumerators.tree_decomposition.INiceTreeDecompositionEnumerator;
import tdk_enum.graph.converters.Converter;
import tdk_enum.graph.graphs.chordal_graph.IChordalGraph;
import tdk_enum.graph.graphs.tree_decomposition.INiceTreeDecomposition;

public class ParallelNiceTreeDecompositionEnumerator extends ParallelTreeDecompositionEnumerator implements INiceTreeDecompositionEnumerator {
    @Override
    public void print(IChordalGraph result) {
        resultPrinter.print(Converter.chordalGraphToNiceTreeDecomposition(result));

    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public INiceTreeDecomposition next() {
        return null;
    }

    @Override
    public void executeAlgorithm() {
        minimalTriangulationsEnumerator.executeAlgorithm();

    }
}
