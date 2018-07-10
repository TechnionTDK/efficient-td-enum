package tdk_enum.graph.data_structures;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by dvir.dukhan on 7/10/2017.
 */
public class TdListMapTest {

    @Test
    public void listMapTest() {
        TdListMap<Integer> map = new TdListMap<>();

        map.put(new Node(5), 1);

        assertEquals(6, map.size());

        for (int i = 0; i <= 5; i++)
        {
            if (i!= 5)
            {
                assertNull(map.get(new Node(i)));
            }
            else
            {
                assertEquals(1,(int) map.get(new Node(i)));
            }
        }


    }

}