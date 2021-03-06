package tdk_enum.enumerators.separators;

import org.junit.Test;
import tdk_enum.common.IO.GraphReader;
import tdk_enum.graph.data_structures.MinimalSeparator;
import tdk_enum.graph.data_structures.Node;
import tdk_enum.graph.data_structures.NodeSet;
import tdk_enum.graph.graphs.Graph;
import tdk_enum.graph.graphs.IGraph;
import tdk_enum.enumerators.separators.parallel.HorizontalMinimalSepratorsEnumerator;
import tdk_enum.enumerators.separators.parallel.ParallelMinimalSeparatorsEnumerator;
import tdk_enum.enumerators.separators.single_thread.MinimalSeparatorsEnumerator;

import static org.junit.Assert.*;
import static tdk_enum.common.configuration.config_types.SeparatorsScoringCriterion.UNIFORM;

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
        cliqueGraph.addClique(cliqueGraph.accessVertices());
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

    @Test
    public void realGraphTest()
    {
        IGraph g = GraphReader.read("C:\\tddatasets\\Datasets\\Easy\\Random\\Probability70percent\\20.csv");

        MinimalSeparatorsEnumerator gEnumerator = new MinimalSeparatorsEnumerator(g, UNIFORM);
        assertEquals(69, countSeparators(gEnumerator));
    }

    int countSeparators(MinimalSeparatorsEnumerator e) {
        int count = 0;
        while (e.hasNext()) {
            System.out.println(e.next());
            count++;
        }
        return count;
    }

    @Test
    public void customTest()
    {
        IGraph g = GraphReader.read("C:\\tddatasets\\Datasets\\logicBlox\\bayes.uai");

        MinimalSeparatorsEnumerator gEnumerator = new MinimalSeparatorsEnumerator(g, UNIFORM);
        System.out.println("total number of seps = " + countSeparators(gEnumerator));
    }

    @Test
    public void CustomTest2()
    {
        IGraph g = GraphReader.read("C:\\tddatasets\\Datasets\\pace2016\\100\\AhrensSzekeresGeneralizedQuadrangleGraph_3.gr");

        MinimalSeparatorsEnumerator gEnumerator = new MinimalSeparatorsEnumerator(g, UNIFORM);
        MinimalSeparatorsEnumerator gEnumerator2 = new MinimalSeparatorsEnumerator(g, UNIFORM);
        ParallelMinimalSeparatorsEnumerator pEnumerator = new ParallelMinimalSeparatorsEnumerator(g);
        HorizontalMinimalSepratorsEnumerator hEnumerator = new HorizontalMinimalSepratorsEnumerator(g);

        countSeparators(gEnumerator);
        int seps1Count = gEnumerator.numberOfSeparators();




        gEnumerator2.executeAlgorithm();
        pEnumerator.executeAlgorithm();
        hEnumerator.executeAlgorithm();
        System.out.println(seps1Count + " " +gEnumerator2.numberOfSeparators());
        assertEquals(seps1Count, gEnumerator2.numberOfSeparators());
        assertEquals(seps1Count,pEnumerator.numberOfSeparators());
        assertEquals(seps1Count, hEnumerator.numberOfSeparators());
    }

}