package tdk_enum.common.IO.result_handler.tree_decomposition;

import tdk_enum.common.IO.result_handler.chordal_graph.ChordalGraphResultInformation;
import tdk_enum.graph.graphs.IGraph;
import tdk_enum.graph.graphs.chordal_graph.IChordalGraph;

public class TreeDecompositionResultInformation extends ChordalGraphResultInformation {
    public TreeDecompositionResultInformation(int index, double time, IGraph input, IChordalGraph result) {
        super(index, time, input, result);
    }
}
