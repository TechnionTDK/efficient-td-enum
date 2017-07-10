package tdenum.graph.graphs;

import tdenum.graph.data_structures.MinimalSeparator;
import tdenum.graph.separators.MinimalSeparatorEnumerator;
import tdenum.graph.data_structures.Node;
import tdenum.graph.separators.SeparatorsScoringCriterion;
import tdenum.graph.interfaces.ISuccinctGraphRepresentation;

import java.util.Map;

/**
 * Created by dvird on 17/07/10.
 */
public class SeparatorGraph implements ISuccinctGraphRepresentation<MinimalSeparator>
{
    Graph graph;
    MinimalSeparatorEnumerator nodesEnumerator;
    int nodesGenerated;

    public SeparatorGraph(final Graph g, SeparatorsScoringCriterion c)
    {
        graph = g;
        nodesEnumerator = new MinimalSeparatorEnumerator(g, c);
        nodesGenerated = 0;
    }

    @Override
    public boolean hasNext()
    {
      return nodesEnumerator.hasNext();
    }

    @Override
    public MinimalSeparator nextNode()
    {
        nodesGenerated++;
        return nodesEnumerator.next();
    }

    @Override
    public boolean hasEdge(MinimalSeparator u, MinimalSeparator v)
    {
        Map<Node,Integer> componentMap = graph.getComponentsMap(u);
        int componenetContatiningV = 0;
        for (Node n : v)
        {
            int componentContainingCurrentNode = componentMap.get(n);
            if (componenetContatiningV == componentContainingCurrentNode)
            {

            }
            else if (componentContainingCurrentNode == -1)
            {

            }
            else if (componenetContatiningV == 0)
            {
                componenetContatiningV = componentContainingCurrentNode;
                continue;
            }
            else
            {
                return true;
            }
        }
        return false;
    }

    int getNumberOfNodesGenerated()
    {
        return nodesGenerated;
    }
}
