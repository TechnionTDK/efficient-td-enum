package tdk_enum.graph.graphs.tree_decomposition.single_thread;

import tdk_enum.graph.graphs.tree_decomposition.INiceTreeDecomposition;

import java.util.List;

public class NiceTreeDecomposition extends TreeDecomposition implements INiceTreeDecomposition {
    public NiceTreeDecomposition(DecompositionNode root, List<DecompositionNode> bags) {
        super(root, bags);
    }
}
