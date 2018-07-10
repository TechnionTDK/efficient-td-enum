package tdenum.graph.graphs.tree_decomposition.single_thread;

import tdenum.graph.data_structures.NodeSet;
import tdenum.graph.data_structures.TdListMap;
import tdenum.graph.graphs.IGraph;
import tdenum.graph.graphs.chordal_graph.IChordalGraph;
import tdenum.graph.graphs.chordal_graph.single_thread.ChordalGraph;
import tdenum.graph.graphs.tree_decomposition.ITreeDecomposition;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
