package tdenum.graph.graphs;

import org.junit.Test;
import tdenum.graph.TestsUtils;
import tdenum.graph.data_structures.MinimalSeparator;
import tdenum.graph.graphs.interfaces.IGraph;
import tdenum.graph.graphs.interfaces.ISeparatorGraph;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static tdenum.graph.independent_set.separators.SeparatorsScoringCriterion.UNIFORM;

/**
 * Created by dvir.dukhan on 7/12/2017.
 */
public class SeparatorGraphTest {




    @Test
    public void testSimpleCycle()
    {
        IGraph g = TestsUtils.circleGraph(4);
        ISeparatorGraph gSep = new SeparatorGraph(g, UNIFORM);
        assertTrue(gSep.hasNextNode());
        MinimalSeparator sep1 = gSep.nextNode();
        assertTrue(gSep.hasNextNode());
        MinimalSeparator sep2 = gSep.nextNode();
        assertFalse(gSep.hasNextNode());
        assertTrue(gSep.hasEdge(sep1, sep2));
        assertFalse(gSep.hasEdge(sep1, sep1));
    }

    @Test
    public void testSeparators()
    {
        IGraph g = TestsUtils.circleGraph(5);
        ISeparatorGraph gSep = new SeparatorGraph(g, UNIFORM);
        MinimalSeparator sep3 = new MinimalSeparator(TestsUtils.createNodesCollection(0,2));
        MinimalSeparator sep4 = new MinimalSeparator(TestsUtils.createNodesCollection(0,3));
        boolean sep3Found = false;
        boolean sep4Found = false;
        while(gSep.hasNextNode())
        {
            MinimalSeparator foundSep = gSep.nextNode();
            if(foundSep.equals(sep3))
            {
                sep3Found = true;
            }
            if(foundSep.equals(sep4))
            {
                sep4Found = true;
            }
        }

        assertTrue(sep3Found && sep4Found);
        assertFalse(gSep.hasEdge(sep3, sep4));
    }
    @Test
    public void testLongNeck()
    {
        IGraph g = TestsUtils.longNeckEightGraph();
        ISeparatorGraph gSep = new SeparatorGraph(g, UNIFORM);
        Set<MinimalSeparator> foundSeps = new HashSet<>();
        while (gSep.hasNextNode())
        {
            foundSeps.add(gSep.nextNode());

        }

        Set<MinimalSeparator> expectedSeps = new HashSet<>();
        MinimalSeparator sep1 = new MinimalSeparator(TestsUtils.createNodesCollection(0,2));
        MinimalSeparator sep2 = new MinimalSeparator(TestsUtils.createNodesCollection(1,3));
        MinimalSeparator sep3 = new MinimalSeparator(TestsUtils.createNodesCollection(3));
        MinimalSeparator sep4 = new MinimalSeparator(TestsUtils.createNodesCollection(4));
        MinimalSeparator sep5 = new MinimalSeparator(TestsUtils.createNodesCollection(4,6));
        MinimalSeparator sep6 = new MinimalSeparator(TestsUtils.createNodesCollection(5,7));
        expectedSeps.add(sep1);
        expectedSeps.add(sep2);
        expectedSeps.add(sep3);
        expectedSeps.add(sep4);
        expectedSeps.add(sep5);
        expectedSeps.add(sep6);
        assertEquals(expectedSeps, foundSeps);
        assertFalse(gSep.hasEdge(sep2,sep5));


    }

}