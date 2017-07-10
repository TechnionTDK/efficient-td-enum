package tdenum.graph.separators;

import tdenum.graph.data_structures.Node;
import tdenum.graph.data_structures.NodeSet;
import tdenum.graph.graphs.interfaces.IGraph;

/**
 * Created by dvird on 17/07/10.
 */
public class SeparatorScorer
{
    IGraph graph;
    SeparatorsScoringCriterion criterion;

    public SeparatorScorer(final IGraph g, SeparatorsScoringCriterion c)
    {
        graph = g;
        criterion = c;
    }

     <T extends NodeSet> int scoreSeparator(final T s)
    {
        if (criterion == SeparatorsScoringCriterion.UNIFORM)
        {
            return 0;
        }
        else if (criterion == SeparatorsScoringCriterion.ASCENDING_SIZE)
        {
            return s.size();
        }
        else if (criterion == SeparatorsScoringCriterion.FILL_EDGES)
        {
            int fillEdges = 0;
            for (Node u : s)
            {
                for (Node v : s)
                {
                    if(!graph.areNeighbors(u, v))
                    {
                        fillEdges++;
                    }
                }
            }
            return fillEdges;
        }
        return 0;
    }

}
