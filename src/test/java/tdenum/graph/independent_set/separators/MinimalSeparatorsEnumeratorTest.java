package tdenum.graph.independent_set.separators;

import org.junit.Test;
import tdenum.graph.data_structures.MinimalSeparator;
import tdenum.graph.data_structures.Node;
import tdenum.graph.data_structures.NodeSet;
import tdenum.graph.graphs.Graph;
import tdenum.graph.graphs.interfaces.IGraph;

import static org.junit.Assert.*;
import static tdenum.graph.independent_set.separators.SeparatorsScoringCriterion.UNIFORM;

public class MinimalSeparatorsEnumeratorTest {

    @Test
    public void testEmptyGraph()
    {
        IGraph emptyGraph = new Graph();
        MinimalSeparatorsEnumerator emptyEnumerator = new MinimalSeparatorsEnumerator(emptyGraph, UNIFORM);
        assertFalse(emptyEnumerator.hasNext());

    }

    @Test
    public void testCliqueGraph()
    {
        IGraph cliqueGraph = new Graph(4);
        cliqueGraph.addClique(cliqueGraph.getNodes());
        MinimalSeparatorsEnumerator cliqueGraphEnumerator = new MinimalSeparatorsEnumerator(cliqueGraph, UNIFORM);
        assertFalse(cliqueGraphEnumerator.hasNext());
    }

    @Test
    public  void testNotConnectedGraph()
    {
        IGraph g0 = new Graph(4);
        MinimalSeparatorsEnumerator g0Enumerator = new MinimalSeparatorsEnumerator(g0, UNIFORM);
        assertFalse(g0Enumerator.hasNext());

    }

    @Test
    public void testTwoCliques()
    {
        IGraph g = new Graph(4);
        NodeSet s1 = new NodeSet();
        s1.add(new Node(0));
        s1.add(new Node(1));
        s1.add(new Node(2));
        NodeSet s2 = new NodeSet();
        s2.add(new Node(1));
        s2.add(new Node(2));
        s2.add(new Node(3));
        g.addClique(s1);
        g.addClique(s2);
        MinimalSeparatorsEnumerator gEnumerator = new MinimalSeparatorsEnumerator(g, UNIFORM);
        assertTrue(gEnumerator.hasNext());
        MinimalSeparator expectedSeperator = new MinimalSeparator();
        expectedSeperator.add(new Node(1));
        expectedSeperator.add(new Node(2));
        assertEquals(expectedSeperator, gEnumerator.next());
        assertFalse(gEnumerator.hasNext());

    }

    @Test
    public void testPairs()
    {
        IGraph g = new Graph(5);

        NodeSet s1 = new NodeSet();
        s1.add(new Node(1));
        s1.add(new Node(0));

        NodeSet s2 = new NodeSet();
        s2.add(new Node(1));
        s2.add(new Node(2));

        NodeSet s3 = new NodeSet();
        s3.add(new Node(2));
        s3.add(new Node(3));

        NodeSet s4 = new NodeSet();
        s4.add(new Node(3));
        s4.add(new Node(4));

        NodeSet s5 = new NodeSet();
        s5.add(new Node(4));
        s5.add(new Node(0));

        g.addClique(s1);
        g.addClique(s2);
        g.addClique(s3);
        g.addClique(s4);
        g.addClique(s5);
        MinimalSeparatorsEnumerator gEnumerator = new MinimalSeparatorsEnumerator(g, UNIFORM);
        assertEquals(5, countSeparators(gEnumerator));

    }

    @Test
    public void testPairSkipOneConnectToLast()
    {
        IGraph g = new Graph(11);

        NodeSet s1 = new NodeSet();
        s1.add(new Node(0));
        s1.add(new Node(1));

        NodeSet s2 = new NodeSet();
        s2.add(new Node(1));
        s2.add(new Node(2));

        NodeSet s3 = new NodeSet();
        s3.add(new Node(0));
        s3.add(new Node(10));

        NodeSet s4 = new NodeSet();
        s4.add(new Node(2));
        s4.add(new Node(3));
        s4.add(new Node(9));
        s4.add(new Node(10));

        NodeSet s5 = new NodeSet();
        s5.add(new Node(8));
        s5.add(new Node(10));

        NodeSet s6 = new NodeSet();
        s6.add(new Node(7));
        s6.add(new Node(8));

        NodeSet s7 = new NodeSet();
        s7.add(new Node(7));
        s7.add(new Node(9));

        NodeSet s8 = new NodeSet();
        s8.add(new Node(5));
        s8.add(new Node(6));
        s8.add(new Node(10));

        NodeSet s9 = new NodeSet();
        s9.add(new Node(5));
        s9.add(new Node(6));
        s9.add(new Node(9));

        NodeSet s10 = new NodeSet();
        s10.add(new Node(4));
        s10.add(new Node(6));

        NodeSet s11 = new NodeSet();
        s11.add(new Node(3));
        s11.add(new Node(5));

        g.addClique(s1);
        g.addClique(s2);
        g.addClique(s3);
        g.addClique(s4);
        g.addClique(s5);
        g.addClique(s6);
        g.addClique(s7);
        g.addClique(s8);
        g.addClique(s9);
        g.addClique(s10);
        g.addClique(s11);
        MinimalSeparatorsEnumerator gEnumerator = new MinimalSeparatorsEnumerator(g, UNIFORM);
        assertEquals(9, countSeparators(gEnumerator));

    }

    @Test
    public void testHeavyMiddleAndEnd()
    {
        IGraph g = new Graph(11);

        NodeSet s1 = new NodeSet();
        s1.add(new Node(1));
        s1.add(new Node(2));
        s1.add(new Node(10));

        NodeSet s2 = new NodeSet();
        s2.add(new Node(2));
        s2.add(new Node(3));
        s2.add(new Node(9));
        s2.add(new Node(10));

        NodeSet s3 = new NodeSet();
        s3.add(new Node(8));
        s3.add(new Node(9));
        s3.add(new Node(10));

        NodeSet s4 = new NodeSet();
        s4.add(new Node(9));
        s4.add(new Node(8));
        s4.add(new Node(7));

        NodeSet s5 = new NodeSet();
        s5.add(new Node(3));
        s5.add(new Node(4));
        s5.add(new Node(6));

        NodeSet s6 = new NodeSet();
        s6.add(new Node(5));
        s6.add(new Node(6));
        s6.add(new Node(3));

        NodeSet s7 = new NodeSet();
        s7.add(new Node(5));
        s7.add(new Node(10));
        s7.add(new Node(9));

        NodeSet s8 = new NodeSet();
        s8.add(new Node(6));
        s8.add(new Node(9));
        s8.add(new Node(10));

        NodeSet s9 = new NodeSet();
        s9.add(new Node(0));
        s9.add(new Node(10));
        s9.add(new Node(1));

        g.addClique(s1);
        g.addClique(s2);
        g.addClique(s3);
        g.addClique(s4);
        g.addClique(s5);
        g.addClique(s6);
        g.addClique(s7);
        g.addClique(s8);
        g.addClique(s9);

        MinimalSeparatorsEnumerator gEnumerator = new MinimalSeparatorsEnumerator(g, UNIFORM);
        assertEquals(6 , countSeparators(gEnumerator));



    }

    int countSeparators(MinimalSeparatorsEnumerator e) {
        int count = 0;
        while (e.hasNext()) {
            e.next();
            count++;
        }
        return count;
    }

}