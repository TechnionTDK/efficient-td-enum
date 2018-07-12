package tdk_enum.graph.triangulation;

import org.junit.BeforeClass;
import org.junit.Test;
import tdk_enum.RunningMode;
import tdk_enum.common.IO.GraphReader;
import tdk_enum.factories.TDEnumFactory;
import tdk_enum.graph.TestsUtils;
import tdk_enum.graph.data_structures.Node;
import tdk_enum.graph.graphs.IGraph;
import tdk_enum.graph.graphs.chordal_graph.IChordalGraph;
import tdk_enum.legacy.graph.triangulation.LegacyMinimalTriangulationsEnumerator;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;
import static tdk_enum.RunningMode.SINGLE;
import static tdk_enum.graph.separators.SeparatorsScoringCriterion.UNIFORM;
import static tdk_enum.graph.triangulation.TriangulationScoringCriterion.NONE;
import static tdk_enum.graph.triangulation.minimal_triangulators.TriangulationAlgorithm.MCS_M;

public class MinimalTriangulationEnumeratorTest {


    @BeforeClass
    public static void checkLinear()
    {
        IGraph g = GraphReader.read("C:\\tddatasets\\Datasets\\pace2016\\100\\AhrensSzekeresGeneralizedQuadrangleGraph_3.gr");

        TDEnumFactory.init(g);
        RunningMode mode = RunningMode.valueOf(TDEnumFactory.getProperties().getProperty("mode"));
        assumeTrue(mode.equals(SINGLE));
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
        IGraph g = GraphReader.read("C:\\tddatasets\\Datasets\\pace2016\\100\\AhrensSzekeresGeneralizedQuadrangleGraph_3.gr");

        TDEnumFactory.init(g);
        IMinimalTriangulationsEnumerator enumerator = TDEnumFactory.getMinimalTriangulationsEnumeratorFactory().produce();
        Set<IChordalGraph> newResultSet = new HashSet<>();
        while (enumerator.hasNext())
        {
            newResultSet.add(enumerator.next());
        }

        LegacyMinimalTriangulationsEnumerator legacyEnumerator = new LegacyMinimalTriangulationsEnumerator(TDEnumFactory.getGraph(), NONE, UNIFORM, MCS_M);
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