package tdenum.graph.data_structures;

import org.junit.Test;
import tdenum.graph.Node;
import tdenum.graph.NodeSet;

import static org.junit.Assert.*;

/**
 * Created by dvir.dukhan on 7/6/2017.
 */
public class NodeSetProducerTest {
    @Test
    public void produce() throws Exception {

        NodeSetProducer producer = new NodeSetProducer(10);
        NodeSet set = producer.produce();
        assertEquals(0, set.size());

        for (int i =0 ; i < 10; i+=2)
        {
            producer.insert(new Node(i));
        }

        set = producer.produce();
        assertEquals(5, set.size());
        for (int i =0 ; i < 10; i+=2)
        {
            assertTrue(set.contains(new Node(i)));
        }
        for (int i =0 ; i < 10; i++)
        {
            producer.insert(new Node(i));
        }
        set = producer.produce();
        assertEquals(10, set.size());
        for (int i =0 ; i < 10; i++)
        {
            assertTrue(set.contains(new Node(i)));
        }

        for (int i =0 ; i < 10; i+=2)
        {
            producer.remove(new Node(i));
        }
        set = producer.produce();
        assertEquals(5, set.size());
        for (int i =0 ; i < 10; i+=2)
        {
            assertFalse(set.contains(new Node(i)));
            assertTrue(set.contains(new Node(i+1)));
        }
    }

}