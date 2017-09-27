package tdenum.graph.separators.scorer;

import tdenum.graph.graphs.IGraph;
import tdenum.graph.separators.SeparatorsScoringCriterion;

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
