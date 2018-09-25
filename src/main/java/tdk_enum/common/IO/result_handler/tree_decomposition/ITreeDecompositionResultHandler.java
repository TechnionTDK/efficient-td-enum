package tdk_enum.common.IO.result_handler.tree_decomposition;

import tdk_enum.common.IO.result_handler.IResultHandler;
import tdk_enum.common.IO.result_handler.chordal_graph.IChordalGraphResultHandler;
import tdk_enum.graph.graphs.tree_decomposition.ITreeDecomposition;

import java.util.Set;

public interface ITreeDecompositionResultHandler extends IResultHandler<ITreeDecomposition> {
    void setAlgorithm(String algorithm);

    void setSeparators(int separators);

    Set<ITreeDecomposition> getResults();
}
