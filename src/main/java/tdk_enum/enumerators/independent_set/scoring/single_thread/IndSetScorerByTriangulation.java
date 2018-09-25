package tdk_enum.enumerators.independent_set.scoring.single_thread;

import tdk_enum.graph.data_structures.Edge;
import tdk_enum.graph.data_structures.MinimalSeparator;
import tdk_enum.graph.graphs.Graph;
import tdk_enum.graph.graphs.chordal_graph.IChordalGraph;
import tdk_enum.graph.graphs.IGraph;
import tdk_enum.enumerators.independent_set.scoring.AbstractIndependentSetScorer;
import tdk_enum.graph.converters.Converter;
import tdk_enum.common.configuration.config_types.TriangulationScoringCriterion;

/**
 * Created by dvir.dukhan on 7/11/2017.
 */
import java.util.List;
import java.util.Set;

import static tdk_enum.common.configuration.config_types.TriangulationScoringCriterion.*;

public class IndSetScorerByTriangulation extends AbstractIndependentSetScorer {





    public IndSetScorerByTriangulation()
    {

    }

    public IndSetScorerByTriangulation(IGraph g, TriangulationScoringCriterion c)
    {
        graph = new Graph(g);
        criterion = c;
    }

    @Override
    public int scoreIndependentSet(Set<MinimalSeparator> s) {
        if (criterion == NONE)
        {
            return  0;
        }
        else if (criterion == WIDTH)
        {
            IChordalGraph g = Converter.minimalSeparatorsToTriangulation(graph, s);
            return g.getTreeWidth();
        }
        else if(criterion == FILL)
        {
            IChordalGraph g = Converter.minimalSeparatorsToTriangulation(graph, s);
            return g.getFillIn(graph);
        }
        else if (criterion == MAX_SEP_SIZE)
        {
            int maxSeparatorsSize = 0;
            for (MinimalSeparator sep : s)
            {
                if (sep.size() > maxSeparatorsSize)
                {
                    maxSeparatorsSize = sep.size();
                }
            }
            return  maxSeparatorsSize;
        }
        else if (criterion == DIFFERENECE)
        {
            int score = 0;
            IChordalGraph g = Converter.minimalSeparatorsToTriangulation(graph, s);
            List<Edge> fillEdges =  g.getFillEdges(graph);
            for(Edge e : fillEdges)
            {
                if (seenFillEdges.contains(e))
                {
                    score++;
                }
            }
            return score;

        }
        return 0;
    }

    @Override
    public boolean mayScoreChange() {
        return (criterion == DIFFERENECE);
    }

    @Override
    public void independentSetUsed(Set<MinimalSeparator> s) {
        if(criterion == DIFFERENECE)
        {
            IChordalGraph g = Converter.minimalSeparatorsToTriangulation(graph, s);
            List<Edge> fillEdges = g.getFillEdges(graph);
            seenFillEdges.addAll(fillEdges);
        }

    }
}


