package tdenum.graph.data_structures;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by dvir.dukhan on 7/6/2017.
 */
public class IncreasingWeightNodeQueueTest
{
    @Before
    public void setUp() throws Exception
    {
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void increaseWeight() throws Exception
    {
        Node v = new Node(0);
        WeightedNodeQueue queue = new WeightedNodeQueue(1);
        queue.increaseWeight(v);
        assertEquals(1, queue.getWeight(v));
    }

    @org.junit.Test
    public void getWeight() throws Exception
    {

        WeightedNodeQueue queue = new WeightedNodeQueue(1);
        assertEquals(0, queue.getWeight(new Node(0)));
    }

    @org.junit.Test
    public void isEmpty() throws Exception
    {
        WeightedNodeQueue queue = new WeightedNodeQueue(1);
        assertFalse(queue.isEmpty());
    }

    @org.junit.Test
    public void pop() throws Exception
    {
        WeightedNodeQueue queue = new WeightedNodeQueue(3);
        queue.increaseWeight(new Node(0));
        assertEquals(new Node(1), queue.pop());
        assertEquals(new Node(2), queue.pop());
        assertEquals(new Node(0), queue.pop());

    }

}