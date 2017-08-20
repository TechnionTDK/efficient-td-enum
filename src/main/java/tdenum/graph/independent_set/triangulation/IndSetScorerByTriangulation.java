package tdenum.graph.independent_set.triangulation;

import tdenum.graph.data_structures.Edge;
import tdenum.graph.data_structures.MinimalSeparator;
import tdenum.graph.graphs.Graph;
import tdenum.graph.graphs.interfaces.IChordalGraph;
import tdenum.graph.graphs.interfaces.IGraph;
import tdenum.graph.independent_set.interfaces.IIndependentSetScorer;
import tdenum.graph.independent_set.Converter;

/**
 * Created by dvir.dukhan on 7/11/2017.
 */
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static tdenum.graph.independent_set.triangulation.TriangulationScoringCriterion.*;

public class IndSetScorerByTriangulation  implements IIndependentSetScorer<MinimalSeparator> {

    IGraph graph;
    TriangulationScoringCriterion criterion;
    Set<Edge> seenFillEdges = new HashSet<>();



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


