package tdk_enum.graph.data_structures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IDConverter<T> {
    Map<T,Integer> sourceToID = new HashMap<>();
    List<T> idToSource = new ArrayList();


    public  T getSource(int id)
    {
        return  idToSource.get(id);
    }

    public int getID(T t)
    {
        if (!sourceToID.containsKey(t))
        {
            idToSource.add(t);
            sourceToID.put(t,idToSource.size()-1);

        }
        return sourceToID.get(t);
    }

}
