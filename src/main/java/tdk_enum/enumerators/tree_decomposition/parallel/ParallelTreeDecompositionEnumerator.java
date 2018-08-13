package tdk_enum.enumerators.tree_decomposition.parallel;

import tdk_enum.enumerators.tree_decomposition.AbstractTreeDecompositionEnumerator;
import tdk_enum.graph.graphs.Converter;
import tdk_enum.graph.graphs.chordal_graph.IChordalGraph;
import tdk_enum.graph.graphs.tree_decomposition.ITreeDecomposition;

public class ParallelTreeDecompositionEnumerator extends AbstractTreeDecompositionEnumerator {
    @Override
    public void print(IChordalGraph result) {
        resultPrinter.print(Converter.chordalGraphToProperTreeDecomposition(result));
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public ITreeDecomposition next() {
        return null;
    }

    @Override
    public void executeAlgorithm() {
        minimalTriangulationsEnumerator.executeAlgorithm();

    }
}
