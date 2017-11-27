package tdenum.graph.triangulation;

import org.junit.Before;
import org.junit.Test;
import tdenum.TDEnum;
import tdenum.common.IO.GraphReader;
import tdenum.factories.TDEnumFactory;
import tdenum.graph.TestsUtils;
import tdenum.graph.data_structures.Node;
import tdenum.graph.graphs.IGraph;
import tdenum.graph.graphs.chordal_graph.IChordalGraph;
import tdenum.graph.graphs.chordal_graph.single_thread.ChordalGraph;
import tdenum.legacy.graph.triangulation.LegacyMinimalTriangulationsEnumerator;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static tdenum.graph.separators.SeparatorsScoringCriterion.UNIFORM;
import static tdenum.graph.triangulation.TriangulationScoringCriterion.NONE;
import static tdenum.graph.triangulation.minimal_triangulators.TriangulationAlgorithm.MCS_M;

public class MinimalTriangulationEnumeratorTest {


    @Before
    public void readFile()
    {
        IGraph g = GraphReader.read("C:\\tddatasets\\Datasets\\Easy\\BN\\CSP\\54.wcsp.uai");

        TDEnumFactory.init(g);
    }

    @Test
    public void testThreeCircleGraph()
    {
        IGraph g = TestsUtils.circleGraph(3);

        TDEnumFactory.init(g);
        IMinimalTriangulationsEnumerator enumerator = TDEnumFactory.getMinimalTriangulationsEnumeratorFactory().produce();


        assertTrue(enumerator.hasNext());
        IGraph triangulation = enumerator.next();
        assertEquals(g, triangulation);
        assertFalse(enumerator.hasNext());
    }

    @Test
    public void testThreePathGraph()
    {
        IGraph g = TestsUtils.pathGraph(3);

        TDEnumFactory.init(g);
        IMinimalTriangulationsEnumerator enumerator = TDEnumFactory.getMinimalTriangulationsEnumeratorFactory().produce();
        assertTrue(enumerator.hasNext());
        IGraph triangulation = enumerator.next();
        assertEquals(g, triangulation);
        assertFalse(enumerator.hasNext());
    }

    @Test
    public void testThreeCliqueGraph()
    {
        IGraph g = TestsUtils.cliqueGraph(3);

        TDEnumFactory.init(g);
        IMinimalTriangulationsEnumerator enumerator = TDEnumFactory.getMinimalTriangulationsEnumeratorFactory().produce();
        assertTrue(enumerator.hasNext());
        IGraph triangulation = enumerator.next();
        assertEquals(g, triangulation);
        assertFalse(enumerator.hasNext());
    }

    @Test
    public void testFourCircleGraph()
    {
        IGraph g = TestsUtils.circleGraph(4);

        TDEnumFactory.init(g);
        IMinimalTriangulationsEnumerator enumerator = TDEnumFactory.getMinimalTriangulationsEnumeratorFactory().produce();
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

        TDEnumFactory.init(g);
        IMinimalTriangulationsEnumerator enumerator = TDEnumFactory.getMinimalTriangulationsEnumeratorFactory().produce();
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

        TDEnumFactory.init(g);
        IMinimalTriangulationsEnumerator enumerator = TDEnumFactory.getMinimalTriangulationsEnumeratorFactory().produce();
        int num = 0;
        while(enumerator.hasNext())
        {
            enumerator.next();
            num++;
        }
        assertEquals(4, num);
    }

    @Test
    public void testCompareVannilaToNew()
    {
        IGraph g = GraphReader.read("C:\\tddatasets\\Datasets\\Easy\\BN\\CSP\\54.wcsp.uai");

        TDEnumFactory.init(g);
        IMinimalTriangulationsEnumerator enumerator = TDEnumFactory.getMinimalTriangulationsEnumeratorFactory().produce();
        Set<IChordalGraph> newResultSet = new HashSet<>();
        while (enumerator.hasNext())
        {
            newResultSet.add(enumerator.next());
        }

        LegacyMinimalTriangulationsEnumerator legacyEnumerator = new LegacyMinimalTriangulationsEnumerator(g, NONE, UNIFORM, MCS_M);
        Set<IChordalGraph> legacyResults = new HashSet<>();
        while (legacyEnumerator.hasNext())
        {
            legacyResults.add(legacyEnumerator.next());
        }
        assertEquals(legacyResults.size(), newResultSet.size());
        assertEquals(legacyResults,newResultSet);
    }

    <T> int  findFrequency(T obj, Collection<T> collection)
    {
        return Collections.frequency(collection, obj);
    }

}
