package tdenum.graph.data_structures;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by dvir.dukhan on 7/12/2017.
 */
public class NodeSetTest
{

    @Test
    public  void testNodeSet()
    {
        Node n = new Node(0);
        NodeSet set = new NodeSet();
        set.add(n);
        assertEquals(1, set.size());
        set.add(new Node(0));
        assertEquals(1, set.size());
    }

}