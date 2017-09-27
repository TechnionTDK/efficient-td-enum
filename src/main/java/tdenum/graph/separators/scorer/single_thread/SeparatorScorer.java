package tdenum.graph.separators.scorer.single_thread;

import tdenum.graph.data_structures.Node;
import tdenum.graph.data_structures.NodeSet;
import tdenum.graph.graphs.IGraph;
import tdenum.graph.separators.SeparatorsScoringCriterion;
import tdenum.graph.separators.scorer.AbstractSeparatorScorer;

/**
 * Created by dvird on 17/07/10.
 */
public class SeparatorScorer extends AbstractSeparatorScorer
{

    public SeparatorScorer()
    {

    }


    public SeparatorScorer(final IGraph g, SeparatorsScoringCriterion c)
    {
        graph = g;
        criterion = c;
    }


    @Override
     public <T extends NodeSet> int scoreSeparator(final T s)
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
                    if (v.equals(u))
                    {
                        break;
                    }
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
