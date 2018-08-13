package tdk_enum.enumerators.separators.scorer;

import tdk_enum.graph.graphs.IGraph;
import tdk_enum.common.configuration.config_types.SeparatorsScoringCriterion;

public abstract class AbstractSeparatorScorer implements ISeparatorScorer {

    protected IGraph graph;
    protected SeparatorsScoringCriterion criterion;

    @Override
    public void setGraph(IGraph graph) {
        this.graph = graph;
    }

    @Override
    public void setCriterion(SeparatorsScoringCriterion criterion) {
        this.criterion = criterion;
    }
}
