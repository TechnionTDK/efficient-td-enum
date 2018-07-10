package tdk_enum.graph.separators;

import org.junit.Test;
import tdk_enum.graph.TestsUtils;
import tdk_enum.graph.data_structures.MinimalSeparator;
import tdk_enum.graph.data_structures.Node;
import tdk_enum.graph.graphs.IGraph;
import tdk_enum.graph.separators.scorer.single_thread.SeparatorScorer;

import static org.junit.Assert.*;
import static tdk_enum.graph.separators.SeparatorsScoringCriterion.*;

public class SeparatorScorerTest {

    @Test
    public void testSeparatorsScorer()
    {
        IGraph g = TestsUtils.circleGraph(4);
        SeparatorScorer noScore = new SeparatorScorer(g, UNIFORM);
        SeparatorScorer sizeScore = new SeparatorScorer(g, ASCENDING_SIZE);
        SeparatorScorer fillScore = new SeparatorScorer(g, FILL_EDGES);
        MinimalSeparator sep = new MinimalSeparator();
        sep.add(new Node(0));
        sep.add(new Node(2));

        assertEquals(0, noScore.scoreSeparator(sep));
        assertEquals(2, sizeScore.scoreSeparator(sep));
        assertEquals(1, fillScore.scoreSeparator(sep));
    }

}