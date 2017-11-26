package tdenum.graph.triangulation;

import org.junit.Test;
import tdenum.graph.TestsUtils;
import tdenum.graph.data_structures.Node;
import tdenum.graph.graphs.IGraph;
import tdenum.graph.triangulation.minimal_triangulators.MinimalTriangulator;
import tdenum.graph.triangulation.minimal_triangulators.RandomMinimalTriangulator;

import java.util.Collection;
import java.util.Collections;

import static org.junit.Assert.*;
import static tdenum.graph.triangulation.minimal_triangulators.TriangulationAlgorithm.*;

public class MinimalTriangulatorTest {

    @Test
    public void testThreeNodesCircleGraphMCS_M()
    {
        IGraph g = TestsUtils.circleGraph(3);
        MinimalTriangulator triangulator = new RandomMinimalTriangulator(MCS_M);
        IGraph h = triangulator.triangulate(g);
        assertEquals(g, h);
    }
    @Test
    public void testFourNodesCircleGraphMCS_M()
    {
        IGraph g = TestsUtils.circleGraph(4);
        MinimalTriangulator triangulator = new RandomMinimalTriangulator(MCS_M);
        IGraph h = triangulator.triangulate(g);
        Node n0 = new Node(0);
        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);
        assertEquals(1, findFrequency(n3, h.getNeighbors(n1))+findFrequency(n2, h.getNeighbors(n0)));
        assertEquals(5, h.getNumberOfEdges());
    }

    @Test
    public void testFiveNodesCircleGraphMCS_M()
    {
        IGraph g = TestsUtils.circleGraph(5);
        MinimalTriangulator triangulator = new RandomMinimalTriangulator(MCS_M);
        IGraph h = triangulator.triangulate(g);
        assertEquals(7, h.getNumberOfEdges());
    }

    @Test
    public void testPathGraphMCS_M()
    {
        IGraph g = TestsUtils.pathGraph(3);
        MinimalTriangulator triangulator = new RandomMinimalTriangulator(MCS_M);
        IGraph h = triangulator.triangulate(g);
        assertEquals(g, h);
    }

    @Test
    public void testCliqueGraphMCS_M()
    {
        IGraph g = TestsUtils.cliqueGraph(4);
        MinimalTriangulator triangulator = new RandomMinimalTriangulator(MCS_M);
        IGraph h = triangulator.triangulate(g);
        assertEquals(g, h);
    }

    @Test
    public void testLongNeckEightGraphMCS_M()
    {
        IGraph g = TestsUtils.longNeckEightGraph();
        MinimalTriangulator triangulator = new RandomMinimalTriangulator(MCS_M);
        IGraph h = triangulator.triangulate(g);
        assertEquals(11, h.getNumberOfEdges());
        Node n0 = new Node(0);
        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);
        Node n4 = new Node(4);
        Node n5 = new Node(5);
        Node n6 = new Node(6);
        Node n7 = new Node(7);

        assertEquals(1, findFrequency(n3, h.getNeighbors(n1)) + findFrequency(n2, h.getNeighbors(n0)));
        assertEquals(1, findFrequency(n7, h.getNeighbors(n5)) + findFrequency(n6, h.getNeighbors(n4)));
        assertEquals(11, h.getNumberOfEdges());
    }



    @Test
    public void testThreeNodesCircleGraphMIN_DEGREE_LB_TRIANG()
    {
        IGraph g = TestsUtils.circleGraph(3);
        MinimalTriangulator triangulator = new RandomMinimalTriangulator(MIN_DEGREE_LB_TRIANG);
        IGraph h = triangulator.triangulate(g);
        assertEquals(g, h);
    }
    @Test
    public void testFourNodesCircleGraphMIN_DEGREE_LB_TRIANG()
    {
        IGraph g = TestsUtils.circleGraph(4);
        MinimalTriangulator triangulator = new RandomMinimalTriangulator(MIN_DEGREE_LB_TRIANG);
        IGraph h = triangulator.triangulate(g);
        Node n0 = new Node(0);
        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);
        assertEquals(1, findFrequency(n3, h.getNeighbors(n1))+findFrequency(n2, h.getNeighbors(n0)));
        assertEquals(5, h.getNumberOfEdges());
    }

    @Test
    public void testFiveNodesCircleGraphMIN_DEGREE_LB_TRIANG()
    {
        IGraph g = TestsUtils.circleGraph(5);
        MinimalTriangulator triangulator = new RandomMinimalTriangulator(MIN_DEGREE_LB_TRIANG);
        IGraph h = triangulator.triangulate(g);
        assertEquals(7, h.getNumberOfEdges());
    }

    @Test
    public void testPathGraphMIN_DEGREE_LB_TRIANG()
    {
        IGraph g = TestsUtils.pathGraph(3);
        MinimalTriangulator triangulator = new RandomMinimalTriangulator(MIN_DEGREE_LB_TRIANG);
        IGraph h = triangulator.triangulate(g);
        assertEquals(g, h);
    }

    @Test
    public void testCliqueGraphMIN_DEGREE_LB_TRIANG()
    {
        IGraph g = TestsUtils.cliqueGraph(4);
        MinimalTriangulator triangulator = new RandomMinimalTriangulator(MIN_DEGREE_LB_TRIANG);
        IGraph h = triangulator.triangulate(g);
        assertEquals(g, h);
    }

    @Test
    public void testLongNeckEightGraphMIN_DEGREE_LB_TRIANG()
    {
        IGraph g = TestsUtils.longNeckEightGraph();
        MinimalTriangulator triangulator = new RandomMinimalTriangulator(MIN_FILL_LB_TRIANG);
        IGraph h = triangulator.triangulate(g);
        assertEquals(11, h.getNumberOfEdges());
        Node n0 = new Node(0);
        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);
        Node n4 = new Node(4);
        Node n5 = new Node(5);
        Node n6 = new Node(6);
        Node n7 = new Node(7);

        assertEquals(1, findFrequency(n3, h.getNeighbors(n1)) + findFrequency(n2, h.getNeighbors(n0)));
        assertEquals(1, findFrequency(n7, h.getNeighbors(n5)) + findFrequency(n6, h.getNeighbors(n4)));
        assertEquals(11, h.getNumberOfEdges());
    }


    @Test
    public void testThreeNodesCircleGraphMIN_FILL_LB_TRIANG()
    {
        IGraph g = TestsUtils.circleGraph(3);
        MinimalTriangulator triangulator = new RandomMinimalTriangulator(MIN_FILL_LB_TRIANG);
        IGraph h = triangulator.triangulate(g);
        assertEquals(g, h);
    }
    @Test
    public void testFourNodesCircleGraphMIN_FILL_LB_TRIANG()
    {
        IGraph g = TestsUtils.circleGraph(4);
        MinimalTriangulator triangulator = new RandomMinimalTriangulator(MIN_FILL_LB_TRIANG);
        IGraph h = triangulator.triangulate(g);
        Node n0 = new Node(0);
        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);
        assertEquals(1, findFrequency(n3, h.getNeighbors(n1))+findFrequency(n2, h.getNeighbors(n0)));
        assertEquals(5, h.getNumberOfEdges());
    }

    @Test
    public void testFiveNodesCircleGraphMIN_FILL_LB_TRIANG()
    {
        IGraph g = TestsUtils.circleGraph(5);
        MinimalTriangulator triangulator = new RandomMinimalTriangulator(MIN_FILL_LB_TRIANG);
        IGraph h = triangulator.triangulate(g);
        assertEquals(7, h.getNumberOfEdges());
    }

    @Test
    public void testPathGraphMIN_FILL_LB_TRIANG()
    {
        IGraph g = TestsUtils.pathGraph(3);
        MinimalTriangulator triangulator = new RandomMinimalTriangulator(MIN_FILL_LB_TRIANG);
        IGraph h = triangulator.triangulate(g);
        assertEquals(g, h);
    }

    @Test
    public void testCliqueGraphMIN_FILL_LB_TRIANG()
    {
        IGraph g = TestsUtils.cliqueGraph(4);
        MinimalTriangulator triangulator = new RandomMinimalTriangulator(MIN_FILL_LB_TRIANG);
        IGraph h = triangulator.triangulate(g);
        assertEquals(g, h);
    }

    @Test
    public void testLongNeckEightGraphMIN_FILL_LB_TRIANG()
    {
        IGraph g = TestsUtils.longNeckEightGraph();
        MinimalTriangulator triangulator = new RandomMinimalTriangulator(MIN_FILL_LB_TRIANG);
        IGraph h = triangulator.triangulate(g);
        assertEquals(11, h.getNumberOfEdges());
        Node n0 = new Node(0);
        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);
        Node n4 = new Node(4);
        Node n5 = new Node(5);
        Node n6 = new Node(6);
        Node n7 = new Node(7);

        assertEquals(1, findFrequency(n3, h.getNeighbors(n1)) + findFrequency(n2, h.getNeighbors(n0)));
        assertEquals(1, findFrequency(n7, h.getNeighbors(n5)) + findFrequency(n6, h.getNeighbors(n4)));
        assertEquals(11, h.getNumberOfEdges());
    }



    <T> int  findFrequency(T obj, Collection<T> collection)
    {
        return Collections.frequency(collection, obj);
    }

}