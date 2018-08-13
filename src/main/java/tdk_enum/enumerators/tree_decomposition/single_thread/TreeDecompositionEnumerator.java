package tdk_enum.enumerators.tree_decomposition.single_thread;

import tdk_enum.common.IO.result_printer.IResultPrinter;
import tdk_enum.enumerators.generators.IGenerator;
import tdk_enum.enumerators.tree_decomposition.AbstractTreeDecompositionEnumerator;
import tdk_enum.graph.data_structures.Node;
import tdk_enum.graph.graphs.Converter;
import tdk_enum.graph.graphs.IGraph;
import tdk_enum.graph.graphs.chordal_graph.IChordalGraph;
import tdk_enum.graph.graphs.tree_decomposition.ITreeDecomposition;

import java.util.Queue;
import java.util.Set;

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
        return Converter.chordalGraphToProperTreeDecomposition(minimalTriangulationsEnumerator.next());
    }

    @Override
    public void executeAlgorithm() {
        minimalTriangulationsEnumerator.executeAlgorithm();

    }


}
