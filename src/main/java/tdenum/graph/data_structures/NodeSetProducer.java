package tdenum.graph.data_structures;

import tdenum.common.Utils;
import tdenum.graph.Node;
import tdenum.graph.NodeSet;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by dvir.dukhan on 7/5/2017.
 */

//public class NodeSetProducer <Node extends Integer>
public class NodeSetProducer{


    HashMap<Node, Boolean> isMemeberMap = new HashMap<>();

    public NodeSetProducer(int sizeOfOriginalNodeSet)
    {
//        isMemeberMap = Stream.iterate(false, b->b).limit(sizeOfOriginalNodeSet).collect(Collectors.toList());
//        isMemeberMap = Utils.generateFixedList(sizeOfOriginalNodeSet,false);

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

    public NodeSet produce()
    {
        NodeSet members = new NodeSet();
        for (Node v : isMemeberMap.keySet())
        {
            if (isMemeberMap.get(v))
            {
                members.add(v);
            }
        }
//        for (int i =0; i < isMemeberMap.size(); i++)
//        {
//            if (isMemeberMap.get(i))
//            {
//                members.add(new Node(i));
//            }
//        }
        return members;
    }
}
