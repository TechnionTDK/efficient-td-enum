package tdk_enum.enumerators.separators.scorer.single_thread;

import tdk_enum.graph.data_structures.Node;
import tdk_enum.graph.data_structures.NodeSet;
import tdk_enum.graph.graphs.IGraph;
import tdk_enum.common.configuration.config_types.SeparatorsScoringCriterion;
import tdk_enum.enumerators.separators.scorer.AbstractSeparatorScorer;

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
