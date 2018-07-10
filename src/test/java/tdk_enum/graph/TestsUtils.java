package tdk_enum.graph;

import tdk_enum.graph.data_structures.Node;
import tdk_enum.graph.data_structures.NodeSet;
import tdk_enum.graph.graphs.chordal_graph.single_thread.ChordalGraph;
import tdk_enum.graph.graphs.Graph;
import tdk_enum.graph.graphs.chordal_graph.IChordalGraph;
import tdk_enum.graph.graphs.IGraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by dvir.dukhan on 7/12/2017.
 */
public class TestsUtils {

    public static IGraph circleGraph(int size)
    {
        IGraph g = new Graph(size);
        for (int i =0; i < size; i++)
        {
            NodeSet s = new NodeSet();
            s.add(new Node(i));
            s.add(new Node((i+1)%size));
            g.addClique(s);
        }
        return g;
    }

    public static IGraph pathGraph(int size)
    {
        IGraph g = new Graph(size);
        for (int i =0; i < size-1; i++)
        {
            NodeSet s = new NodeSet();
            s.add(new Node(i));
            s.add(new Node((i+1)%size));
            g.addClique(s);
        }
        return g;
    }

    public static IGraph cliqueGraph(int size)
    {

        IGraph g = new Graph(size);
        NodeSet s = new NodeSet();
        for (int i =0; i < size; i++)
        {
            s.add(new Node(i));
        }
        g.addClique(s);
        return g;
    }

    public static IChordalGraph cycleGraphWithChord()
    {
        IChordalGraph g = new ChordalGraph(4);
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
        return g;
    }

    public static IGraph longNeckEightGraph()
    {
        IGraph g = new Graph(8);
        for (int i =0; i < 4; i++)
        {
            NodeSet s = new NodeSet();
            s.add(new Node(i));
            s.add(new Node((i+1)%4));
            g.addClique(s);
        }

        NodeSet s = new NodeSet();
        s.add(new Node(3));
        s.add(new Node(4));
        g.addClique(s);
        for (int i =0; i < 4; i++)
        {
            s = new NodeSet();
            s.add(new Node(i+4));
            s.add(new Node(4+((i+1)%4)));
            g.addClique(s);
        }
        return g;
    }

    public static Collection<Node> createNodesCollection(Node... nodes)
    {
        return Arrays.asList(nodes);
    }

    public static Collection<Node> createNodesCollection(Integer... nodes)
    {
        ArrayList<Node> res = new ArrayList<>();
        for (Integer i : nodes)
        {
            res.add(new Node(i));
        }
        return  res;
    }

    <T> int  findFrequency(T obj, Collection<T> collection)
    {
        return Collections.frequency(collection, obj);
    }


}
