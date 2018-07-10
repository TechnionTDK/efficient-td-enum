package tdk_enum.graph.triangulation.parallel.freezable;

import tdk_enum.graph.data_structures.*;
import tdk_enum.graph.data_structures.weighted_queue.single_thread.IncreasingWeightedNodeQueue;
import tdk_enum.graph.graphs.chordal_graph.single_thread.ChordalGraph;
import tdk_enum.graph.graphs.chordal_graph.IChordalGraph;
import tdk_enum.graph.graphs.IGraph;

import java.util.ArrayList;
import java.util.List;

import static tdk_enum.graph.triangulation.parallel.freezable.McsmFreezableMinimalTriangulator.STATE.*;


public class McsmFreezableMinimalTriangulator extends AbstractFreezableMinimalTriangulator {


    enum STATE
    {
        MCS_BEFORE_INIT, MCS_IN_FOR, MCS_IN_WHILE, MCS_UPDATE
    }

    IChordalGraph triangulation;
    IncreasingWeightedNodeQueue queue;
    TdMap<Boolean> handled;

    Node v;
    NodeSet nodesToUpdate;
    TdMap<Boolean> reached;

    List<NodeSet> reachedByMaxWeight;

    int maxWeight;

    boolean finished = false;
    STATE state = MCS_BEFORE_INIT;



    @Override
    public IChordalGraph triangulate() {
        while(!queue.isEmpty() && !Thread.currentThread().isInterrupted())
        {
            init();
            loopForMaxWeight();
            updateQueueAndTriangulation();
        }
        if (queue.isEmpty())
        {
            finished = true;
        }

        return  triangulation;

    }

    @Override
    public IChordalGraph continueTriangulate() {
        if (finished)
        {
            return triangulation;
        }
        else
        {
            switch (state)
            {
                case MCS_BEFORE_INIT:
                {
                    return triangulate();
                }

                case MCS_IN_FOR:
                {
                    loopForMaxWeight();
                    updateQueueAndTriangulation();
                    return  triangulate();
                }

                case MCS_IN_WHILE:
                {
                    loopWhileNotReachedByMaxWeightEmpty();
                    loopForMaxWeight();
                    updateQueueAndTriangulation();
                    return  triangulate();
                }
                case MCS_UPDATE:
                {
                    updateQueueAndTriangulation();
                    return  triangulate();
                }
            }
        }
        return  null;

    }

    @Override
    public void setGraph(IGraph graph) {
        g = graph;
        finished = false;
        state = MCS_BEFORE_INIT;
        triangulation = new ChordalGraph(g);
        handled = new TdListMap<>(g.getNumberOfNodes(), false);
    }



    void init()
    {
        v = queue.poll();
        handled.put(v, true);
        nodesToUpdate = new NodeSet();
        reached = new TdListMap(g.getNumberOfNodes(), false);
        reachedByMaxWeight = new ArrayList<>();
        for (int i = 0; i< g.getNumberOfNodes(); i++)
        {
            reachedByMaxWeight.add(new NodeSet());
        }
        for (Node u : g.getNeighbors(v))
        {
            if (!handled.get(u))
            {
                nodesToUpdate.add(u);
                reached.put(u, true);
                reachedByMaxWeight.get(queue.getWeight(u)).add(u);
            }
        }

        state = MCS_IN_FOR;
    }

    void loopForMaxWeight()
    {
        for (maxWeight = 0; maxWeight < g.getNumberOfNodes() && !Thread.currentThread().isInterrupted() ; maxWeight++)
        {
            state = MCS_IN_WHILE;
            loopWhileNotReachedByMaxWeightEmpty();
        }
        if(maxWeight == g.getNumberOfNodes())
        {
            state = MCS_UPDATE;
        }

    }

    void loopWhileNotReachedByMaxWeightEmpty()
    {

        while(!reachedByMaxWeight.get(maxWeight).isEmpty()  && !Thread.currentThread().isInterrupted())
        {
            NodeSet ns = reachedByMaxWeight.get(maxWeight);
            Node w = ns.remove(ns.size()-1);
//                    for (Node u : g.getNeighbors(w))
            for(Node u: g.getNeighbors(w))
            {
                if (!handled.get(u) && !reached.get(u))
                {
                    if (queue.getWeight(u) > maxWeight)
                    {
                        nodesToUpdate.add(u);
                    }
                    reached.put(u , true);
                    reachedByMaxWeight.get(Math.max(maxWeight, queue.getWeight(u))).add(u);
                }
            }
        }
        if (reachedByMaxWeight.get(maxWeight).isEmpty())
            state = MCS_IN_FOR;
    }




    void updateQueueAndTriangulation()
    {
        for (Node u : nodesToUpdate)
        {
            queue.increaseWeight(u);
            triangulation.addEdge(u, v);
        }
        state = MCS_BEFORE_INIT;
    }
}
