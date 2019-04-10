package tdk_enum.enumerators.tree_decomposition.single_thread;

import tdk_enum.enumerators.tree_decomposition.AbstractTreeDecompositionEnumerator;
import tdk_enum.graph.converters.Converter;
import tdk_enum.graph.graphs.chordal_graph.IChordalGraph;
import tdk_enum.graph.graphs.tree_decomposition.ITreeDecomposition;

public class TreeDecompositionEnumerator extends AbstractTreeDecompositionEnumerator {

    ITreeDecomposition nextTreeDecomposition;

    @Override
    public void print(IChordalGraph result) {
        nextTreeDecomposition = Converter.chordalGraphToProperTreeDecomposition(result);
        resultPrinter.print(nextTreeDecomposition);

    }

    @Override
    public boolean hasNext() {
        return minimalTriangulationsEnumerator.hasNext();
    }

    @Override
    public ITreeDecomposition next() {
        ITreeDecomposition treeDecomposition = Converter.chordalGraphToProperTreeDecomposition(minimalTriangulationsEnumerator.next());
        resultPrinter.print(treeDecomposition);
        return(treeDecomposition);
    }

    @Override
    public void executeAlgorithm() {
        minimalTriangulationsEnumerator.executeAlgorithm();

    }


}
