package tdk_enum.common.IO.result_handler.chordal_graph;

import tdk_enum.common.IO.result_handler.IResultHandler;
import tdk_enum.graph.graphs.chordal_graph.IChordalGraph;
import tdk_enum.graph.graphs.chordal_graph.single_thread.ChordalGraph;

public interface IChordalGraphResultHandler extends IResultHandler<IChordalGraph> {

    void setAlgorithm(String algorithm);

    void setSeparators(int separators);
}
