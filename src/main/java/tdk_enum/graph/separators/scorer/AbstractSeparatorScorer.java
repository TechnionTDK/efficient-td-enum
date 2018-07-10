package tdk_enum.graph.separators.scorer;

import tdk_enum.graph.graphs.IGraph;
import tdk_enum.graph.separators.SeparatorsScoringCriterion;

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
