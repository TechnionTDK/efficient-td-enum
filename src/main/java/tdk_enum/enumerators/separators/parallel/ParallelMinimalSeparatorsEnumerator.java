package tdk_enum.enumerators.separators.parallel;

import tdk_enum.enumerators.separators.single_thread.CachedMinimalSeparatorsEnumerator;
import tdk_enum.graph.data_structures.MinimalSeparator;
import tdk_enum.graph.data_structures.Node;
import tdk_enum.graph.data_structures.NodeSet;
import tdk_enum.graph.graphs.IGraph;
import tdk_enum.graph.data_structures.weighted_queue.parallel.ConcurrentQueueSet;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ParallelMinimalSeparatorsEnumerator extends CachedMinimalSeparatorsEnumerator {

    protected Thread mainThread;
//    IGraph graph;
//
//    Set<NodeSet> separatorsExtended = ConcurrentHashMap.newKeySet();
//    ConcurrentQueueSet<NodeSet> separatorsToExtend = new ConcurrentQueueSet<>();


    public ParallelMinimalSeparatorsEnumerator()
    {
        super();
        Q = new ConcurrentQueueSet<>();
        P = ConcurrentHashMap.newKeySet();
        componentsCache = ConcurrentHashMap.newKeySet();
    }

    public ParallelMinimalSeparatorsEnumerator(IGraph graph) {
        super();
        this.graph = graph;
        Q = new ConcurrentQueueSet<>();
        P = ConcurrentHashMap.newKeySet();

//        for (Node v : this.graph.accessVertices())
//        {
//            Set<Node> vAndNeighbors = graph.getNeighbors(v);
//            vAndNeighbors.add(v);
//            for (NodeSet nodeSet : graph.getComponents(vAndNeighbors))
//            {
//                NodeSet potentialSeparator = graph.accessNeighbors(nodeSet);
//                if (potentialSeparator.size() >0)
//                {
//                    separatorsToExtend.add(potentialSeparator);
//                }
//            }
//        }
    }

    @Override
    public void executeAlgorithm()
    {
        this.mainThread = Thread.currentThread();
        super.executeAlgorithm();
    }

    @Override
    protected void doFirstStep()
    {
        graph.accessVertices().parallelStream().forEach(v -> {
            Set<Node> vAndNeighbors = graph.getNeighbors(v);
            vAndNeighbors.add(v);
            for (NodeSet nodeSet : graph.getComponents(vAndNeighbors))
            {
                MinimalSeparator potentialSeparator = new MinimalSeparator(graph.getNeighbors(nodeSet));
                newResultFound(potentialSeparator);
            }
        });

    }

    @Override
    protected void iteratingNodePhase()
    {
        V.parallelStream().anyMatch(node-> {tryGenerateNewResult(node, currentEnumResult); return finishCondition();});
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
//            Set<Node> xNeighborsAndS = graph.getNeighbors(x);
//            xNeighborsAndS.addAll(s);
//            for (NodeSet nodeSet : graph.getComponents(xNeighborsAndS))
//            {
//                minimalSeparatorFound(graph.accessNeighbors(nodeSet));
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
////        for (Node v : this.graph.accessVertices())
////        {
////            Set<Node> vAndNeighbors = graph.getNeighbors(v);
////            vAndNeighbors.add(v);
////            for (NodeSet nodeSet : graph.getComponents(vAndNeighbors))
////            {
////                NodeSet potentialSeparator = graph.accessNeighbors(nodeSet);
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
