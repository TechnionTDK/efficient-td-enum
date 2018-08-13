package tdk_enum.graph.graphs.succinct_graphs.separator_graph.single_thread;

import tdk_enum.graph.data_structures.MinimalSeparator;
import tdk_enum.graph.data_structures.TdMap;
import tdk_enum.graph.graphs.IGraph;
import tdk_enum.graph.graphs.succinct_graphs.separator_graph.AbstractSeparatorGraph;
import tdk_enum.enumerators.separators.single_thread.MinimalSeparatorsEnumerator;
import tdk_enum.graph.data_structures.Node;
import tdk_enum.common.configuration.config_types.SeparatorsScoringCriterion;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by dvird on 17/07/10.
 */
public class SeparatorGraph extends AbstractSeparatorGraph
{





    public SeparatorGraph()
    {

    }

    public SeparatorGraph(final IGraph g, SeparatorsScoringCriterion c)
    {
        graph = g;
        nodesEnumerator = new MinimalSeparatorsEnumerator(g, c);

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
        MinimalSeparator separator = nodesEnumerator.next();

        return separator;
    }

    @Override
    public Set<MinimalSeparator> nextBatch() {
        return new HashSet<>();
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
    public Set<MinimalSeparator> nextBatch(long id) {
        return  new HashSet<>();
    }

    @Override
    public boolean hasNextNode(long id) {
        return hasNextNode();
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
        sb.append(", V=").append(nodesGenerated);
        sb.append('}');
        return sb.toString();
    }



}
