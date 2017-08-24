package tdenum.graph.data_structures;

import tdenum.common.Utils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by dvir.dukhan on 7/5/2017.
 */

//public class NodeSetProducer <Node extends Integer>
public class NodeSetProducer
{


    TdMap<Boolean> isMemeberMap = new TdListMap<>();

    public NodeSetProducer(int sizeOfOriginalNodeSet)
    {
//        isMemeberMap = Stream.iterate(false, b->b).limit(sizeOfOriginalNodeSet).collect(Collectors.toList());
        isMemeberMap = new TdListMap<>(sizeOfOriginalNodeSet, false);

    }

    public NodeSetProducer()
    {

    }

    public void insert(Node v)
    {
        isMemeberMap.put(v, true);
    }

    public void remove(Node v)
    {
        isMemeberMap.put(v, false);
    }

    public  <T extends  NodeSet  > T produce(Class<T> tClass)
    {

        T members = null;
        try {
            members = tClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        List<Boolean> values = (ArrayList)isMemeberMap.values();
        for (int i =0; i < values.size(); i++)
        {
            if (values.get(i))
            {
                members.add(new Node(i));
            }
        }
//
//        for (Node v : isMemeberMap.keySet())
//        {
//            if (isMemeberMap.get(v))
//            {
//                members.add(v);
//            }
//        }
//        for (int i =0; i < isMemeberMap.size(); i++)
//        {
//            if (isMemeberMap.get(i))
//            {
//                members.add(new Node(i));
//            }
//        }
        return members;
    }


    public  <T extends  NodeSet  > T produce()
    {

        T members = (T) new NodeSet();

        List<Boolean> values = (ArrayList)isMemeberMap.values();


        for (int i =0; i < values.size(); i++)
        {
            if (values.get(i))
            {
                members.add(new Node(i));
            }
        }

//        for (Node v : isMemeberMap.keySet())
//        {
//            if (isMemeberMap.get(v))
//            {
//                members.add(v);
//            }
//        }
//        for (int i =0; i < isMemeberMap.size(); i++)
//        {
//            if (isMemeberMap.get(i))
//            {
//                members.add(new Node(i));
//            }
//        }
//        System.out.println("members " + members);
        return members;
    }
}
