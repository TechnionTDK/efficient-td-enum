package tdenum.graph.independent_set.triangulation;

import org.junit.Test;
import tdenum.graph.TestsUtils;
import tdenum.graph.data_structures.Node;
import tdenum.graph.graphs.interfaces.IGraph;

import java.util.Collection;
import java.util.Collections;

import static org.junit.Assert.*;
import static tdenum.graph.independent_set.separators.SeparatorsScoringCriterion.UNIFORM;
import static tdenum.graph.independent_set.triangulation.TriangulationAlgorithm.MCS_M;
import static tdenum.graph.independent_set.triangulation.TriangulationScoringCriterion.NONE;

public class MinimalTriangulationsEnumeratorTest {

    @Test
    public void testThreeCircleGraph()
    {
        IGraph g = TestsUtils.circleGraph(3);
        MinimalTriangulationsEnumerator enumerator = new MinimalTriangulationsEnumerator(g, NONE, UNIFORM, MCS_M);
        assertTrue(enumerator.hasNext());
        IGraph triangulation = enumerator.next();
        assertEquals(g, triangulation);
        assertFalse(enumerator.hasNext());
    }

    @Test
    public void testThreePathGraph()
    {
        IGraph g = TestsUtils.pathGraph(3);
        MinimalTriangulationsEnumerator enumerator = new MinimalTriangulationsEnumerator(g, NONE, UNIFORM, MCS_M);
        assertTrue(enumerator.hasNext());
        IGraph triangulation = enumerator.next();
        assertEquals(g, triangulation);
        assertFalse(enumerator.hasNext());
    }

    @Test
    public void testThreeCliqueGraph()
    {
        IGraph g = TestsUtils.cliqueGraph(3);
        MinimalTriangulationsEnumerator enumerator = new MinimalTriangulationsEnumerator(g, NONE, UNIFORM, MCS_M);
        assertTrue(enumerator.hasNext());
        IGraph triangulation = enumerator.next();
        assertEquals(g, triangulation);
        assertFalse(enumerator.hasNext());
    }

    @Test
    public void testFourCircleGraph()
    {
        IGraph g = TestsUtils.circleGraph(4);
        MinimalTriangulationsEnumerator enumerator = new MinimalTriangulationsEnumerator(g, NONE, UNIFORM, MCS_M);
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
    public void testFileCircleGraph()
    {
        IGraph g = TestsUtils.circleGraph(5);
        MinimalTriangulationsEnumerator enumerator = new MinimalTriangulationsEnumerator(g, NONE, UNIFORM, MCS_M);
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
        MinimalTriangulationsEnumerator enumerator = new MinimalTriangulationsEnumerator(g, NONE, UNIFORM, MCS_M);
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