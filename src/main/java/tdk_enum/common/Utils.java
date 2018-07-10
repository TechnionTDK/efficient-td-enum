package tdk_enum.common;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by dvir.dukhan on 7/6/2017.
 */
public class Utils
{

    public static List<Integer> generateRangedIntList(int size)
    {
        return IntStream.
                rangeClosed(0, size).
                boxed().
                collect(Collectors.toList());
    }

    public static <T> List<T> generateFixedList(int size, T value)
    {
        return Stream.
                iterate(value, t -> t).
                limit(size).
                collect(Collectors.toList());
    }


}
