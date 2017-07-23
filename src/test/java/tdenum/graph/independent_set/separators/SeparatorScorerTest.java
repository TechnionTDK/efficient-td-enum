package tdenum.graph.independent_set.separators;

import org.junit.Test;
import tdenum.graph.TestsUtils;
import tdenum.graph.data_structures.MinimalSeparator;
import tdenum.graph.data_structures.Node;
import tdenum.graph.graphs.interfaces.IGraph;

import static org.junit.Assert.*;
import static tdenum.graph.independent_set.separators.SeparatorsScoringCriterion.*;

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