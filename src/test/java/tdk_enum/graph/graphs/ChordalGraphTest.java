package tdk_enum.graph.graphs;

import org.junit.Test;
import tdk_enum.graph.TestsUtils;
import tdk_enum.graph.data_structures.Node;
import tdk_enum.graph.data_structures.NodeSet;
import tdk_enum.graph.graphs.chordal_graph.IChordalGraph;
import tdk_enum.graph.graphs.chordal_graph.single_thread.ChordalGraph;

import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by dvird on 17/07/24.
 */
public class ChordalGraphTest
{

    @Test
    public void testCliques()
    {
        IChordalGraph g = TestsUtils.cycleGraphWithChord();
        Set<NodeSet> cliques =  g.getMaximalCliques();
        assertEquals(2, cliques.size());
        NodeSet expected1 = new NodeSet();
        expected1.add(new Node(0));
        expected1.add(new Node(1));
        expected1.add(new Node(2));

        NodeSet expected2 = new NodeSet();

        expected2.add(new Node(1));
        expected2.add(new Node(2));
        expected2.add(new Node(3));

        assertTrue(cliques.contains(expected1));
        assertTrue(cliques.contains(expected2));
    }

    @Test
    public void testFourPathGraph()
    {
        IChordalGraph g = new ChordalGraph(TestsUtils.pathGraph(4));
        Set<NodeSet> cliques = g.getMaximalCliques();
        assertEquals(3, cliques.size());

    }

    @Test
    public void testFourCliqueGraph()
    {
        IChordalGraph g = new ChordalGraph(TestsUtils.cliqueGraph(4));
        Set<NodeSet> cliques = g.getMaximalCliques();
        NodeSet expected1 = new NodeSet();
        expected1.add(new Node(0));
        expected1.add(new Node(1));
        expected1.add(new Node(2));
        expected1.add(new Node(3));
        assertTrue(cliques.contains(expected1));
    }

    @Test
    public void testFourCircleGraph()
    {
        IChordalGraph g = new ChordalGraph(TestsUtils.circleGraph(4));
        IChordalGraph f = TestsUtils.cycleGraphWithChord();
        assertEquals(1, f.getFillIn(g));
        assertEquals(2, f.getTreeWidth());
    }

}