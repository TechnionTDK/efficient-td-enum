package tdenum.graph.graphs;

import org.junit.Test;
import tdenum.graph.data_structures.Node;
import tdenum.graph.data_structures.NodeSet;

import java.util.HashSet;

import static org.junit.Assert.*;

/**
 * Created by dvir.dukhan on 7/9/2017.
 */
public class GraphTest
{

    @Test
    public void graphTest()
    {

        Node node0 = new Node(0);
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);

        Graph emptyGraph = new Graph();
        assertEquals(0, emptyGraph.getNodes().size());
        assertEquals(emptyGraph.getNodes().size(), emptyGraph.getNumberOfNodes());


        Graph g = new Graph(4);
        NodeSet expectedNodeSet = new NodeSet();
        expectedNodeSet.add(node0);
        expectedNodeSet.add(node1);
        expectedNodeSet.add(node2);
        expectedNodeSet.add(node3);
        assertEquals(expectedNodeSet, g.getNodes());

        assertEquals(0, g.getNeighbors(node0).size());
        g.saturateNodeSets(new HashSet<>());
        assertEquals(0, g.getNeighbors(node0).size());

        NodeSet clique1 = new NodeSet();
        NodeSet clique2 = new NodeSet();

        clique1.add(node0);
        clique1.add(node1);

        clique2.add(node0);
        clique2.add(node2);

        HashSet<NodeSet> cliques = new HashSet<>();
        cliques.add(clique1);
        cliques.add(clique2);
        g.saturateNodeSets(cliques);
        assertEquals(expectedNodeSet, g.getNodes());
        assertEquals(2, g.getNeighbors(node0).size());
        assertEquals(1, g.getNeighbors(node1).size());
        assertEquals(0, g.getNeighbors(node3).size());
        assertEquals(2, g.getComponents(new NodeSet()).size());

        NodeSet s1 = new NodeSet();
        s1.add(node0);
        assertEquals(2, g.getNeighbors(s1).size());
        assertEquals(3, g.getComponents(s1).size());
        s1.add(node1);
        assertEquals(1, g.getNeighbors(s1).size());
        assertEquals(2, g.getComponents(s1).size());

        NodeSet clique3 = new NodeSet();
        clique3.add(node0);
        clique3.add(node1);
        clique3.add(node2);
        clique3.add(node3);

        g.addClique(clique3);
        assertEquals(3, g.getNeighbors(node0).size());
        assertEquals(0, g.getNeighbors(clique3).size());
        assertEquals(1, g.getComponents(new NodeSet()).size());
        assertEquals(0, g.getComponents(clique3).size());

        NodeSet s2 = new NodeSet();
        s2.add(node0);
        assertEquals(1, g.getComponents(s2).size());
        assertEquals(3, g.getComponents(s2).get(0).size());

    }

}