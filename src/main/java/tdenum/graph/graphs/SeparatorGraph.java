package tdenum.graph.graphs;

import tdenum.graph.data_structures.MinimalSeparator;
import tdenum.graph.data_structures.NodeSet;
import tdenum.graph.data_structures.TdMap;
import tdenum.graph.graphs.interfaces.IGraph;
import tdenum.graph.graphs.interfaces.ISeparatorGraph;
import tdenum.graph.independent_set.separators.MinimalSeparatorsEnumerator;
import tdenum.graph.data_structures.Node;
import tdenum.graph.independent_set.separators.SeparatorsScoringCriterion;

/**
 * Created by dvird on 17/07/10.
 */
public class SeparatorGraph implements ISeparatorGraph
{
    IGraph graph;
    MinimalSeparatorsEnumerator nodesEnumerator;
    int nodesGenerated;

    public SeparatorGraph(final IGraph g, SeparatorsScoringCriterion c)
    {
        graph = g;
        nodesEnumerator = new MinimalSeparatorsEnumerator(g, c);
        nodesGenerated = 0;
    }

    @Override
    public boolean hasNextNode()
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
        TdMap<Integer> componentMap = graph.getComponentsMap(u);
        int componentContainingV = 0;
        for (Node n : v)
        {
            int componentContainingCurrentNode = componentMap.get(n);
            if (componentContainingV == componentContainingCurrentNode)
            {
                continue;
            }
            else if (componentContainingCurrentNode == -1)
            {
                continue;
            }
            else if (componentContainingV == 0)
            {
                componentContainingV = componentContainingCurrentNode;
                continue;
            }
            else
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getNumberOfNodesGenerated()
    {
        return nodesGenerated;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SeparatorGraph)) return false;

        SeparatorGraph that = (SeparatorGraph) o;

        if (nodesGenerated != that.nodesGenerated) return false;
        if (!graph.equals(that.graph)) return false;
        return nodesEnumerator.equals(that.nodesEnumerator);
    }

    @Override
    public int hashCode() {
        int result = graph.hashCode();
        result = 31 * result + nodesEnumerator.hashCode();
        result = 31 * result + nodesGenerated;
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SeparatorGraph{");
        sb.append("graph=").append(graph);
        sb.append(", nodesEnumerator=").append(nodesEnumerator);
        sb.append(", nodesGenerated=").append(nodesGenerated);
        sb.append('}');
        return sb.toString();
    }
}
