package tdenum.graph.data_structures;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by dvir.dukhan on 7/10/2017.
 */
public class TdListMapTest {

    @Test
    public void listMapTest()
    {
        TdListMap<Integer> map = new TdListMap<>(6);

        map.put(new Node(5), 1);

        assertEquals(6, map.size());

    }

}