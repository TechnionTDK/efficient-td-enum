package tdenum.graph;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by dvir.dukhan on 7/6/2017.
 */
public class NodeTest {
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void intValue() throws Exception {
        Node v = new Node(1);
        assertEquals(1, v.intValue());
    }

}