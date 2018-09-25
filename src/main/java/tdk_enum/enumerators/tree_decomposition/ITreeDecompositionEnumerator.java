package tdk_enum.enumerators.tree_decomposition;

import tdk_enum.common.IO.result_printer.IResultPrinter;
import tdk_enum.enumerators.common.IEnumerator;
import tdk_enum.enumerators.triangulation.IMinimalTriangulationsEnumerator;
import tdk_enum.graph.data_structures.MinimalSeparator;
import tdk_enum.graph.data_structures.Node;
import tdk_enum.graph.graphs.IGraph;
import tdk_enum.graph.graphs.chordal_graph.IChordalGraph;
import tdk_enum.graph.graphs.tree_decomposition.ITreeDecomposition;

import java.util.Set;

public interface ITreeDecompositionEnumerator extends IResultPrinter<IChordalGraph>, IEnumerator<Node,ITreeDecomposition,IGraph> {

    void setMinimalTriangulationsEnumerator(IMinimalTriangulationsEnumerator triangulationsEnumerator);

    int getNumberOfMinimalSeperatorsGenerated();
}
