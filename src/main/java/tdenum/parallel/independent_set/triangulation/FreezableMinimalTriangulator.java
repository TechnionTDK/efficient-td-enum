package tdenum.parallel.independent_set.triangulation;

import tdenum.graph.data_structures.*;
import tdenum.graph.graphs.ChordalGraph;
import tdenum.graph.graphs.interfaces.IChordalGraph;
import tdenum.graph.graphs.interfaces.IGraph;
import tdenum.graph.independent_set.triangulation.TriangulationAlgorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import static tdenum.parallel.independent_set.triangulation.FreezableMinimalTriangulator.STATE.*;

public class FreezableMinimalTriangulator implements Callable<IChordalGraph> {


    enum STATE
    {
        BEFORE_INIT, IN_FOR , IN_WHILE, UPDATE
    }


    private TriangulationAlgorithm heuristic;
    IGraph g;
    IChordalGraph triangulation;
    IncreasingWeightedNodeQueue queue;
    TdMap<Boolean> handled;

    Node v;
    NodeSet nodesToUpdate;
    TdMap<Boolean> reached;

    List<NodeSet> reachedByMaxWeight;

    int maxWeight;

    boolean finished = false;
    STATE state = BEFORE_INIT;




    public FreezableMinimalTriangulator (TriangulationAlgorithm h)
    {
        heuristic = h;
    }

    public void setGraph(IGraph graph)
    {
        g = graph;
        finished = false;
        state = BEFORE_INIT;
        triangulation = new ChordalGraph(g);
        handled = new TdListMap<>(g.getNumberOfNodes(), false);


    }


    @Override
    public IChordalGraph call() {


        return recoverTriangulation();


    }


    private IChordalGraph getMinimalTriangulationUsingMSCM()
    {

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

    void init()
    {
        v = queue.pop();
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

        state = IN_FOR;
    }

    void loopForMaxWeight()
    {
        for (maxWeight = 0; maxWeight < g.getNumberOfNodes() && !Thread.currentThread().isInterrupted() ; maxWeight++)
        {
            state = IN_WHILE;
            loopWhileNotReachedByMaxWeightEmpty();
        }
        if(maxWeight == g.getNumberOfNodes())
        {
            state = UPDATE;
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
        state = IN_FOR;
    }




    void updateQueueAndTriangulation()
    {
        for (Node u : nodesToUpdate)
        {
            queue.increaseWeight(u);
            triangulation.addEdge(u, v);
        }
        state = BEFORE_INIT;
    }

    IChordalGraph recoverTriangulation()
    {
        if (finished)
        {
            return triangulation;
        }
        else
        {
            switch (state)
            {
                case BEFORE_INIT:
                {
                    return getMinimalTriangulationUsingMSCM();
                }

                case IN_FOR:
                {
                    loopForMaxWeight();
                    updateQueueAndTriangulation();
                    return  getMinimalTriangulationUsingMSCM();
                }

                case IN_WHILE:
                {
                    loopWhileNotReachedByMaxWeightEmpty();
                    loopForMaxWeight();
                    updateQueueAndTriangulation();
                    return  getMinimalTriangulationUsingMSCM();
                }
                case UPDATE:
                {
                    updateQueueAndTriangulation();
                    return  getMinimalTriangulationUsingMSCM();
                }
            }
        }
        return  null;
    }
}
