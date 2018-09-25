package tdk_enum.enumerators.tree_decomposition.single_thread;

import tdk_enum.enumerators.tree_decomposition.INiceTreeDecompositionEnumerator;
import tdk_enum.graph.converters.Converter;
import tdk_enum.graph.graphs.chordal_graph.IChordalGraph;
import tdk_enum.graph.graphs.tree_decomposition.INiceTreeDecomposition;
import tdk_enum.graph.graphs.tree_decomposition.ITreeDecomposition;

public class NiceTreeDecompositionEnumerator extends TreeDecompositionEnumerator implements INiceTreeDecompositionEnumerator {
    INiceTreeDecomposition nextTreeDecomposition;

    @Override
    public void print(IChordalGraph result) {
        nextTreeDecomposition = Converter.chordalGraphToNiceTreeDecomposition(result);
        resultPrinter.print(nextTreeDecomposition);

    }

    @Override
    public boolean hasNext() {
        return minimalTriangulationsEnumerator.hasNext();
    }

    @Override
    public INiceTreeDecomposition next() {
        return Converter.chordalGraphToNiceTreeDecomposition(minimalTriangulationsEnumerator.next());
    }

    @Override
    public void executeAlgorithm() {
        minimalTriangulationsEnumerator.executeAlgorithm();

    }
}
