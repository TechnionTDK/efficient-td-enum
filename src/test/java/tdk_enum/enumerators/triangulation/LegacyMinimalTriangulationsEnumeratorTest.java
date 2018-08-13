package tdk_enum.enumerators.triangulation;

import org.junit.Test;
import tdk_enum.graph.TestsUtils;
import tdk_enum.graph.data_structures.Node;
import tdk_enum.graph.graphs.IGraph;
import tdk_enum.legacy.graph.triangulation.LegacyMinimalTriangulationsEnumerator;

import java.util.Collection;
import java.util.Collections;

import static org.junit.Assert.*;
import static tdk_enum.common.configuration.config_types.SeparatorsScoringCriterion.UNIFORM;
import static tdk_enum.common.configuration.config_types.TriangulationAlgorithm.MCS_M;
import static tdk_enum.common.configuration.config_types.TriangulationScoringCriterion.NONE;

public class LegacyMinimalTriangulationsEnumeratorTest {

    @Test
    public void testThreeCircleGraph()
    {
        IGraph g = TestsUtils.circleGraph(3);
        LegacyMinimalTriangulationsEnumerator enumerator = new LegacyMinimalTriangulationsEnumerator(g, NONE, UNIFORM, MCS_M);
        assertTrue(enumerator.hasNext());
        IGraph triangulation = enumerator.next();
        assertEquals(g, triangulation);
        assertFalse(enumerator.hasNext());
    }

    @Test
    public void testThreePathGraph()
    {
        IGraph g = TestsUtils.pathGraph(3);
        LegacyMinimalTriangulationsEnumerator enumerator = new LegacyMinimalTriangulationsEnumerator(g, NONE, UNIFORM, MCS_M);
        assertTrue(enumerator.hasNext());
        IGraph triangulation = enumerator.next();
        assertEquals(g, triangulation);
        assertFalse(enumerator.hasNext());
    }

    @Test
    public void testThreeCliqueGraph()
    {
        IGraph g = TestsUtils.cliqueGraph(3);
        LegacyMinimalTriangulationsEnumerator enumerator = new LegacyMinimalTriangulationsEnumerator(g, NONE, UNIFORM, MCS_M);
        assertTrue(enumerator.hasNext());
        IGraph triangulation = enumerator.next();
        assertEquals(g, triangulation);
        assertFalse(enumerator.hasNext());
    }

    @Test
    public void testFourCircleGraph()
    {
        IGraph g = TestsUtils.circleGraph(4);
        LegacyMinimalTriangulationsEnumerator enumerator = new LegacyMinimalTriangulationsEnumerator(g, NONE, UNIFORM, MCS_M);
        assertTrue(enumerator.hasNext());
        IGraph triangulation1 = enumerator.next();
        assertTrue(enumerator.hasNext());
        IGraph triangulation2 = enumerator.next();
        assertFalse(enumerator.hasNext());
        Node n0 = new Node(0);
        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);

        assertEquals(1, findFrequency(n3, triangulation1.getNeighbors(n1)) + findFrequency(n3, triangulation2.getNeighbors(n1)));
        assertEquals(1, findFrequency(n2, triangulation1.getNeighbors(n0)) + findFrequency(n2, triangulation2.getNeighbors(n0)));
    }

    @Test
    public void testFiveCircleGraph()
    {
        IGraph g = TestsUtils.circleGraph(5);
        LegacyMinimalTriangulationsEnumerator enumerator = new LegacyMinimalTriangulationsEnumerator(g, NONE, UNIFORM, MCS_M);
        for (int i =0; i<5; i++)
        {
            assertTrue(enumerator.hasNext());
            enumerator.next();
        }
        assertFalse(enumerator.hasNext());
    }

    @Test
    public void testLongNeckEightGraph()
    {
        IGraph g = TestsUtils.longNeckEightGraph();
        LegacyMinimalTriangulationsEnumerator enumerator = new LegacyMinimalTriangulationsEnumerator(g, NONE, UNIFORM, MCS_M);
        int num = 0;
        while(enumerator.hasNext())
        {
            enumerator.next();
            num++;
        }
        assertEquals(4, num);
    }

    <T> int  findFrequency(T obj, Collection<T> collection)
    {
        return Collections.frequency(collection, obj);
    }

}