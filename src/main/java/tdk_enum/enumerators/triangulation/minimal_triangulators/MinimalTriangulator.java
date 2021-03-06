package tdk_enum.enumerators.triangulation.minimal_triangulators;

import tdk_enum.common.configuration.config_types.TriangulationAlgorithm;
import tdk_enum.graph.data_structures.*;
import tdk_enum.graph.data_structures.NodeQueue;
import tdk_enum.graph.data_structures.weighted_queue.single_thread.IncreasingWeightedNodeQueue;
import tdk_enum.graph.graphs.chordal_graph.single_thread.ChordalGraph;
import tdk_enum.graph.graphs.chordal_graph.IChordalGraph;
import tdk_enum.graph.graphs.IGraph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static tdk_enum.common.configuration.config_types.TriangulationAlgorithm.COMBINED;
import static tdk_enum.common.configuration.config_types.TriangulationAlgorithm.LB_TRIANG;
import static tdk_enum.common.configuration.config_types.TriangulationAlgorithm.MCS_M;

/**
 * Created by dvir.dukhan on 7/11/2017.
 */
public class MinimalTriangulator extends AbstractMinimalTriangulator {

    public MinimalTriangulator()
    {

    }

    public MinimalTriangulator(TriangulationAlgorithm heuristic)
    {
        this.heuristic = heuristic;
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

    protected IChordalGraph getMinimalTriangulationUsingLBTriang(IGraph g, TriangulationAlgorithm heuristic) {

        IChordalGraph result = new ChordalGraph(g);
        if (heuristic == LB_TRIANG)
        {
            for (Node v: g.accessVertices())
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
        Set<Node> removedNodes = gi.getNeighbors(v);
        removedNodes.add(v);
        List<NodeSet>components = g.getComponents(removedNodes);
        Set<NodeSet> substars = new HashSet<>();
        for (NodeSet componenet : components)
        {
            substars.add(g.getNeighbors(componenet));
        }
        return substars;
    }

    protected void makeNodeLBSimplicial(IGraph g, IGraph gi, Node v)
    {
        Set<NodeSet> substars = getSubstars(g, gi, v);
        gi.saturateNodeSets(substars);
    }

    protected IChordalGraph getMinimalTriangulationUsingMSCM(IGraph g)
    {
        IChordalGraph triangulation = new ChordalGraph(g);
        IncreasingWeightedNodeQueue queue =new IncreasingWeightedNodeQueue(g.accessVertices());
        TdMap<Boolean> handled = new TdListMap<>(g.getNumberOfNodes(), false);
        while(!queue.isEmpty())
        {
            Node v = queue.poll();
            handled.put(v, true);
            NodeSet nodesToUpdate = new NodeSet();
            TdMap<Boolean> reached = new TdListMap(g.getNumberOfNodes(), false);
            List<NodeSet> reachedByMaxWeight = new ArrayList<>();
            for (int i = 0; i< g.getNumberOfNodes(); i++)
            {
                reachedByMaxWeight.add(new NodeSet());
            }
//            for (Node u : g.accessNeighbors(v))
            for (Node u : g.accessNeighbors(v))
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
//                    for (Node u : g.accessNeighbors(w))
                    for(Node u: g.accessNeighbors(w))
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
