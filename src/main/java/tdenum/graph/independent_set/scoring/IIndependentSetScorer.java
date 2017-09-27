package tdenum.graph.independent_set.scoring;

import tdenum.graph.graphs.IGraph;
import tdenum.graph.triangulation.TriangulationScoringCriterion;

import java.util.Set;

/**
 * Created by dvird on 17/07/10.
 */
public interface IIndependentSetScorer<T>
{
    int scoreIndependentSet(final Set<T> s);
    boolean mayScoreChange();
    void independentSetUsed(final Set<T> s);

    void setGraph(IGraph graph);

    void setCriterion(TriangulationScoringCriterion criterion);
}
