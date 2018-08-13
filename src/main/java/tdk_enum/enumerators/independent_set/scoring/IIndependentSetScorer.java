package tdk_enum.enumerators.independent_set.scoring;

import tdk_enum.graph.graphs.IGraph;
import tdk_enum.common.configuration.config_types.TriangulationScoringCriterion;

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
