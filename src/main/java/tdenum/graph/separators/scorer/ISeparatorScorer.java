package tdenum.graph.separators.scorer;

import tdenum.graph.data_structures.NodeSet;
import tdenum.graph.graphs.IGraph;
import tdenum.graph.separators.SeparatorsScoringCriterion;

public interface ISeparatorScorer {
    void setGraph(IGraph graph);

    void setCriterion(SeparatorsScoringCriterion criterion);

    <T extends NodeSet> int scoreSeparator(final T s);
}
