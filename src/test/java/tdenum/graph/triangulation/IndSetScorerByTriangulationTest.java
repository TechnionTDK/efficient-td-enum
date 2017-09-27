package tdenum.graph.triangulation;

import org.junit.Test;
import tdenum.graph.TestsUtils;
import tdenum.graph.data_structures.MinimalSeparator;
import tdenum.graph.data_structures.Node;
import tdenum.graph.graphs.IGraph;
import tdenum.graph.independent_set.scoring.single_thread.IndSetScorerByTriangulation;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static tdenum.graph.triangulation.TriangulationScoringCriterion.*;

public class IndSetScorerByTriangulationTest {

    @Test
    public void indSetScorerTest()
    {
        IGraph g = TestsUtils.circleGraph(4);

        IndSetScorerByTriangulation noScore = new IndSetScorerByTriangulation(g, NONE);
        IndSetScorerByTriangulation widthScore = new IndSetScorerByTriangulation(g, WIDTH);
        IndSetScorerByTriangulation fillScore = new IndSetScorerByTriangulation(g, FILL);
        IndSetScorerByTriangulation sepSizeScore = new IndSetScorerByTriangulation(g, MAX_SEP_SIZE);
        IndSetScorerByTriangulation differenceScore = new IndSetScorerByTriangulation(g, DIFFERENECE);

        MinimalSeparator sep = new MinimalSeparator();
        sep.add(new Node(0));
        sep.add(new Node(2));
        Set<MinimalSeparator> indSet = new HashSet<>();
        indSet.add(sep);

        assertEquals(0, noScore.scoreIndependentSet(indSet));
        assertEquals(2, widthScore.scoreIndependentSet(indSet));
        assertEquals(1, fillScore.scoreIndependentSet(indSet));
        assertEquals(2, sepSizeScore.scoreIndependentSet(indSet));
        assertEquals(0, differenceScore.scoreIndependentSet(indSet));
    }

}