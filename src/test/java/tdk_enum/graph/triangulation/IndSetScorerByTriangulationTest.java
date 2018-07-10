package tdk_enum.graph.triangulation;

import org.junit.Test;
import tdk_enum.graph.TestsUtils;
import tdk_enum.graph.data_structures.MinimalSeparator;
import tdk_enum.graph.data_structures.Node;
import tdk_enum.graph.graphs.IGraph;
import tdk_enum.graph.independent_set.scoring.single_thread.IndSetScorerByTriangulation;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static tdk_enum.graph.triangulation.TriangulationScoringCriterion.*;

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