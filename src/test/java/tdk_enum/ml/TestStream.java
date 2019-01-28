package tdk_enum.ml;

import org.junit.Test;
import weka.Run;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestStream {

    @Test
    public void test()
    {
        System.out.println(Runtime.getRuntime().availableProcessors());
        List<Integer>  list = new ArrayList<>();
        for(int i =100; i< 10000; i+=100)
        {
            list.add(i);
        }
        System.out.println(list);

        List<Double> l = list.parallelStream().map(integer -> (double)integer/3).collect(Collectors.toList());
        System.out.println(l);
    }


}
