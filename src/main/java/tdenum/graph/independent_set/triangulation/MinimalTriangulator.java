package tdenum.graph.independent_set.triangulation;

import tdenum.graph.data_structures.*;
import tdenum.graph.graphs.ChordalGraph;
import tdenum.graph.graphs.interfaces.IChordalGraph;
import tdenum.graph.graphs.interfaces.IGraph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static tdenum.graph.independent_set.triangulation.TriangulationAlgorithm.COMBINED;
import static tdenum.graph.independent_set.triangulation.TriangulationAlgorithm.LB_TRIANG;
import static tdenum.graph.independent_set.triangulation.TriangulationAlgorithm.MCS_M;

/**
 * Created by dvir.dukhan on 7/11/2017.
 */
public class MinimalTriangulator {


    TriangulationAlgorithm heuristic;
    int time = 0;

    public MinimalTriangulator(TriangulationAlgorithm h)
    {
        heuristic = h;
    }


    public IChordalGraph triangulate(final IGraph g)
    {
        time++;
        if(heuristic == MCS_M || (heuristic == COMBINED && time % 2 ==0))
        {
            return getMinimalTriangulationUsingMSCM(g);
        }
        return getMinimalTriangulationUsingLBTriang(g, heuristic);


    }

    private IChordalGraph getMinimalTriangulationUsingLBTriang(IGraph g, TriangulationAlgorithm heuristic) {

        IChordalGraph result = new ChordalGraph(g);
        if (heuristic == LB_TRIANG)
        {
            for (Node v: g.getNodes())
            {
                makeNodeLBSimplicial(g, result, v);
            }
        }
        else
        {
            NodeQueue queue = new NodeQueue(result, heuristic);
            while (!queue.isEmpty())
            {
                makeNodeLBSimplicial(g, result, queue.pop());
            }
        }
        return result;
    }


    private Set<NodeSet> getSubstars(IGraph g, IGraph gi, Node v)
    {
        Set<Node> removedNodes = gi.getNeighborsCopy(v);
        removedNodes.add(v);
        List<NodeSet>components = g.getComponents(removedNodes);
        Set<NodeSet> substars = new HashSet<>();
        for (NodeSet componenet : components)
        {
            substars.add(g.getNeighbors(componenet));
        }
        return substars;
    }

    private void makeNodeLBSimplicial(IGraph g, IGraph gi, Node v)
    {
        Set<NodeSet> substars = getSubstars(g, gi, v);
        gi.saturateNodeSets(substars);
    }

    private IChordalGraph getMinimalTriangulationUsingMSCM(IGraph g)
    {
        IChordalGraph triangulation = new ChordalGraph(g);
        IncreasingWeightedNodeQueue queue =new IncreasingWeightedNodeQueue(g.getNodes());
        TdMap<Boolean> handled = new TdListMap<>(g.getNumberOfNodes(), false);
        while(!queue.isEmpty())
        {
            Node v = queue.pop();
            handled.put(v, true);
            NodeSet nodesToUpdate = new NodeSet();
            TdMap<Boolean> reached = new TdListMap(g.getNumberOfNodes(), false);
            List<NodeSet> reachedByMaxWeight = new ArrayList<>();
            for (int i = 0; i< g.getNumberOfNodes(); i++)
            {
                reachedByMaxWeight.add(new NodeSet());
            }
//            for (Node u : g.getNeighbors(v))
            for (Node u : g.getNeighbors(v))
            {
                if (!handled.get(u))
                {
                    nodesToUpdate.add(u);
                    reached.put(u, true);
                    reachedByMaxWeight.get(queue.getWeight(u)).add(u);
                }
            }
            for (int maxWeight = 0; maxWeight < g.getNumberOfNodes(); maxWeight++)
            {
                while(! reachedByMaxWeight.get(maxWeight).isEmpty())
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
            }
            for (Node u : nodesToUpdate)
            {
                queue.increaseWeight(u);
                triangulation.addEdge(u, v);
            }
        }
        return  triangulation;
    }
}
