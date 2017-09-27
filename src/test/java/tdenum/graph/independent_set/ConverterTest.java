package tdenum.graph.independent_set;

import org.junit.Test;
import tdenum.graph.TestsUtils;
import tdenum.graph.data_structures.MinimalSeparator;
import tdenum.graph.data_structures.Node;
import tdenum.graph.data_structures.NodeSet;
import tdenum.graph.graphs.Graph;
import tdenum.graph.graphs.IGraph;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by dvir.dukhan on 7/17/2017.
 */
public class ConverterTest {

    @Test
    public void testCycleGraphWithChord()
    {
        IGraph g = TestsUtils.cycleGraphWithChord();
        Set<MinimalSeparator> minSep = Converter.triangulationToMinimalSeparators(g);
        assertEquals(1, minSep.size());
        MinimalSeparator expected = new MinimalSeparator();
        expected.add(new Node(1));
        expected.add(new Node(2));
        assertEquals(expected, minSep.iterator().next());

    }

    @Test
    public void testConverterGraph()
    {
        IGraph g = new Graph(4);
        NodeSet s1 = new NodeSet();
        s1. add(new Node(0));
        s1.add(new Node(1));
        NodeSet s2 = new NodeSet();
        s2.add(new Node(1));
        s2.add(new Node(3));
        NodeSet s3 = new NodeSet();
        s3.add(new Node(3));
        s3.add(new Node(2));
        NodeSet s4 = new NodeSet();
        s4.add(new Node(2));
        s4.add(new Node(0));
        g.addClique(s1);
        g.addClique(s2);
        g.addClique(s3);
        g.addClique(s4);
        MinimalSeparator minSep = new MinimalSeparator();
        minSep.add(new Node(1));
        minSep.add(new Node(2));
        Set<MinimalSeparator> minSeps = new HashSet<>();
        minSeps.add(minSep);
        IGraph triangulation = Converter.minimalSeparatorsToTriangulation(g, minSeps);
        assertEquals(3, triangulation.getNeighbors(new Node(1)).size());


    }

}