package tdk_enum.graph.separators.scorer;

import tdk_enum.graph.data_structures.NodeSet;
import tdk_enum.graph.graphs.IGraph;
import tdk_enum.graph.separators.SeparatorsScoringCriterion;

public interface ISeparatorScorer {
    void setGraph(IGraph graph);

    void setCriterion(SeparatorsScoringCriterion criterion);

    <T extends NodeSet> int scoreSeparator(final T s);
}
