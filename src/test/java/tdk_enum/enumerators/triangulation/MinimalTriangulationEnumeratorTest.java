package tdk_enum.enumerators.triangulation;

import org.junit.BeforeClass;
import org.junit.Test;
import tdk_enum.common.IO.GraphReader;
import tdk_enum.factories.TDKEnumFactory;
import tdk_enum.factories.enumeration.minimal_triangulations_enumerator_factory.ParallelMinimalTriangulationsEnumeratorFactory;
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
import static tdk_enum.common.configuration.config_types.SeparatorsScoringCriterion.UNIFORM;
import static tdk_enum.common.configuration.config_types.TriangulationScoringCriterion.NONE;
import static tdk_enum.common.configuration.config_types.TriangulationAlgorithm.MCS_M;

public class MinimalTriangulationEnumeratorTest {


    @BeforeClass
    public static void checkLinear()
    {
        IGraph g = GraphReader.read("C:\\tddatasets\\Datasets\\pace2016\\100\\AhrensSzekeresGeneralizedQuadrangleGraph_3.gr");

//        TDKEnumFactory.init(g);
//        RunningMode mode = RunningMode.valueOf(TDKEnumFactory.getProperties().getProperty("mode"));
//        assumeTrue(mode.equals(SINGLE_THREAD));
    }

    @Test
    public void testThreeCircleGraph()
    {
        IGraph g = TestsUtils.circleGraph(3);

        TDKEnumFactory.init(g);
        IMinimalTriangulationsEnumerator enumerator = new ParallelMinimalTriangulationsEnumeratorFactory().produce();


        assertTrue(enumerator.hasNext());
        IGraph triangulation = enumerator.next();
        assertEquals(g, triangulation);
        assertFalse(enumerator.hasNext());
    }

    @Test
    public void testThreePathGraph()
    {
        IGraph g = TestsUtils.pathGraph(3);

        TDKEnumFactory.init(g);
        IMinimalTriangulationsEnumerator enumerator = new ParallelMinimalTriangulationsEnumeratorFactory().produce();
        assertTrue(enumerator.hasNext());
        IGraph triangulation = enumerator.next();
        assertEquals(g, triangulation);
        assertFalse(enumerator.hasNext());
    }

    @Test
    public void testThreeCliqueGraph()
    {
        IGraph g = TestsUtils.cliqueGraph(3);

        TDKEnumFactory.init(g);
        IMinimalTriangulationsEnumerator enumerator = new ParallelMinimalTriangulationsEnumeratorFactory().produce();
        assertTrue(enumerator.hasNext());
        IGraph triangulation = enumerator.next();
        assertEquals(g, triangulation);
        assertFalse(enumerator.hasNext());
    }

    @Test
    public void testFourCircleGraph()
    {
        IGraph g = TestsUtils.circleGraph(4);

        TDKEnumFactory.init(g);
        IMinimalTriangulationsEnumerator enumerator = new ParallelMinimalTriangulationsEnumeratorFactory().produce();
        assertTrue(enumerator.hasNext());
        IGraph triangulation1 = enumerator.next();
        assertTrue(enumerator.hasNext());
        IGraph triangulation2 = enumerator.next();
        assertFalse(enumerator.hasNext());
        Node n0 = new Node(0);
        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);

        assertEquals(1, findFrequency(n3, triangulation1.accessNeighbors(n1)) + findFrequency(n3, triangulation2.accessNeighbors(n1)));
        assertEquals(1, findFrequency(n2, triangulation1.accessNeighbors(n0)) + findFrequency(n2, triangulation2.accessNeighbors(n0)));
    }

    @Test
    public void testFiveCircleGraph()
    {
        IGraph g = TestsUtils.circleGraph(5);

        TDKEnumFactory.init(g);
        IMinimalTriangulationsEnumerator enumerator = new ParallelMinimalTriangulationsEnumeratorFactory().produce();
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

        TDKEnumFactory.init(g);
        IMinimalTriangulationsEnumerator enumerator = new ParallelMinimalTriangulationsEnumeratorFactory().produce();
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

        TDKEnumFactory.init(g);
        IMinimalTriangulationsEnumerator enumerator = new ParallelMinimalTriangulationsEnumeratorFactory().produce();
        Set<IChordalGraph> newResultSet = new HashSet<>();
        while (enumerator.hasNext())
        {
            newResultSet.add(enumerator.next());
        }

        LegacyMinimalTriangulationsEnumerator legacyEnumerator = new LegacyMinimalTriangulationsEnumerator(TDKEnumFactory.getGraph(), NONE, UNIFORM, MCS_M);
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
