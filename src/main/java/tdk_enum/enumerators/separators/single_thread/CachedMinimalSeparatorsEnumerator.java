package tdk_enum.enumerators.separators.single_thread;

import tdk_enum.graph.data_structures.MinimalSeparator;
import tdk_enum.graph.data_structures.Node;
import tdk_enum.graph.data_structures.NodeSet;

import java.util.HashSet;
import java.util.Set;

public class CachedMinimalSeparatorsEnumerator extends  MinimalSeparatorsEnumerator {

    Set<Set<Node>> intermediateCache = new HashSet<>();
    Set<NodeSet> compononetsCache = new HashSet<>();

    @Override
    protected void tryGenerateNewResult(Node node, MinimalSeparator result)
    {
        if (result.contains(node))
        {
            return;
        }
        Set<Node> xNeighborsAndS = graph.getNeighbors(node);
        xNeighborsAndS.addAll(result);
        if (intermediateCache.contains(xNeighborsAndS)){
            return;
        }
        intermediateCache.add(xNeighborsAndS);
        for (NodeSet nodeSet : graph.getComponents(xNeighborsAndS))
        {

            if(compononetsCache.contains(nodeSet)){
                continue;
            }
            compononetsCache.add(nodeSet);
            newResultFound(new MinimalSeparator(graph.getNeighbors(nodeSet)));

        }
    }
}
