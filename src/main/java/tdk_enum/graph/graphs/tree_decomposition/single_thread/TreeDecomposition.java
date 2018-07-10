package tdk_enum.graph.graphs.tree_decomposition.single_thread;

import tdk_enum.graph.data_structures.NodeSet;
import tdk_enum.graph.data_structures.TdListMap;
import tdk_enum.graph.graphs.IGraph;
import tdk_enum.graph.graphs.chordal_graph.single_thread.ChordalGraph;
import tdk_enum.graph.graphs.tree_decomposition.ITreeDecomposition;

public class TreeDecomposition extends ChordalGraph implements ITreeDecomposition {


    TdListMap<NodeSet> bags = new TdListMap<>();

    public TreeDecomposition()
    {
        super();
    }

    public TreeDecomposition(IGraph graph)
    {
        super(graph);
//        bags.

    }

    public TreeDecomposition (int nodes)
    {
        super(nodes);
//        bags = super.getMaximalCliques();
    }


}
