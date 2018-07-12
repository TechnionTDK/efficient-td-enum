package tdk_enum.graph.separators.parallel;

import tdk_enum.graph.data_structures.MinimalSeparator;
import tdk_enum.graph.data_structures.Node;
import tdk_enum.graph.data_structures.NodeSet;
import tdk_enum.graph.graphs.IGraph;
import tdk_enum.graph.separators.AbstractMinimalSeparatorsEnumerator;
import tdk_enum.graph.data_structures.weighted_queue.parallel.ConcurrentQueueSet;
import tdk_enum.graph.separators.single_thread.MinimalSeparatorsEnumerator;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ParallelMinimalSeparatorsEnumerator extends MinimalSeparatorsEnumerator {


//    IGraph graph;
//
//    Set<NodeSet> separatorsExtended = ConcurrentHashMap.newKeySet();
//    ConcurrentQueueSet<NodeSet> separatorsToExtend = new ConcurrentQueueSet<>();


    public ParallelMinimalSeparatorsEnumerator()
    {

    }

    public ParallelMinimalSeparatorsEnumerator(IGraph graph) {
        this.graph = graph;

//        for (Node v : this.graph.getNodes())
//        {
//            Set<Node> vAndNeighbors = graph.getNeighborsCopy(v);
//            vAndNeighbors.add(v);
//            for (NodeSet nodeSet : graph.getComponents(vAndNeighbors))
//            {
//                NodeSet potentialSeparator = graph.getNeighbors(nodeSet);
//                if (potentialSeparator.size() >0)
//                {
//                    separatorsToExtend.add(potentialSeparator);
//                }
//            }
//        }
    }

//    @Override
//    public MinimalSeparator next() {
//        if (!hasNext())
//        {
//            return new MinimalSeparator();
//        }
//        MinimalSeparator s =  new MinimalSeparator((NodeSet) separatorsToExtend.poll());
//        separatorsExtended.add(s);
//        for (Node x : s)
//        {
//            Set<Node> xNeighborsAndS = graph.getNeighborsCopy(x);
//            xNeighborsAndS.addAll(s);
//            for (NodeSet nodeSet : graph.getComponents(xNeighborsAndS))
//            {
//                minimalSeparatorFound(graph.getNeighbors(nodeSet));
//            }
//        }
//
//        return s;
//    }

//    @Override
//    public synchronized boolean hasNext() {
//        return !separatorsToExtend.isEmpty();
//    }
//
////    @Override
////    public synchronized  <T extends NodeSet> void minimalSeparatorFound(T s)
////    {
////        if (s.size() > 0 && !separatorsExtended.contains(s))
////        {
////            separatorsToExtend.add(s);
////        }
////    }
////
////    @Override
////    public void init() {
////        for (Node v : this.graph.getNodes())
////        {
////            Set<Node> vAndNeighbors = graph.getNeighborsCopy(v);
////            vAndNeighbors.add(v);
////            for (NodeSet nodeSet : graph.getComponents(vAndNeighbors))
////            {
////                NodeSet potentialSeparator = graph.getNeighbors(nodeSet);
////                if (potentialSeparator.size() >0)
////                {
////                    separatorsToExtend.add(potentialSeparator);
////                }
////            }
////        }
////    }
//
//    @Override
//    protected boolean finishCondition() {
//        return false;
//    }
//
//    @Override
//    protected MinimalSeparator manipulateNodeAndResult(Node node, MinimalSeparator result) {
//        return null;
//    }
//
//    @Override
//    protected void changeVIfNeeded() {
//
//    }
}
