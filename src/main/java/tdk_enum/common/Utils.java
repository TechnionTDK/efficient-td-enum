package tdk_enum.common;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
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




    public static boolean doesObjectContainField(Object object, String fieldName) {
        return Arrays.stream(object.getClass().getFields())
                .anyMatch(f -> f.getName().equals(fieldName));
    }

    public static Object getFieldValue(Object object, String fieldName, Object defaultValue)
    {
//        try {
//            return object.getClass().getField(fieldName).get(object);
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (NoSuchFieldException e) {
//            Class oclass = object.getClass();
//            e.printStackTrace();
//
//        }
//        return defaultValue;
        try {
            Class oclass = object.getClass();
            Field field = getField(oclass, fieldName);
            return runGetter(field, object, defaultValue);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

    static Field getField(Class oclass, String fieldName) throws NoSuchFieldException {
        for (Class<?> c = oclass; c != null; c = c.getSuperclass())
        {
            Field[] fields = c.getDeclaredFields();
            for (Field classField : fields)
            {
                if (classField.getName()==fieldName)
                {
                    return classField;
                }
            }
        }
        throw new NoSuchFieldException(fieldName);
    }

    public static Object runGetter(Field field, Object o, Object defualtValue)
    {
        // MZ: Find the correct method
        for (Method method : o.getClass().getMethods())
        {
            if ((method.getName().startsWith("get")) && (method.getName().length() == (field.getName().length() + 3)))
            {
                if (method.getName().toLowerCase().endsWith(field.getName().toLowerCase()))
                {
                    // MZ: Method found, run it
                    try
                    {
                        return method.invoke(o);
                    }
                    catch (IllegalAccessException e)
                    {
                        e.printStackTrace();
                    }
                    catch (InvocationTargetException e)
                    {
                        e.printStackTrace();
                    }

                }
            }
        }


        return defualtValue;
    }

}
