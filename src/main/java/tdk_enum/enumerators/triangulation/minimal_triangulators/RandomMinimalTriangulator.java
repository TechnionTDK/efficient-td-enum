package tdk_enum.enumerators.triangulation.minimal_triangulators;

import tdk_enum.common.configuration.config_types.TriangulationAlgorithm;
import tdk_enum.graph.data_structures.*;
import tdk_enum.graph.data_structures.weighted_queue.single_thread.IncreasingWeightRandomizedNodeQueue;
import tdk_enum.graph.graphs.IGraph;
import tdk_enum.graph.graphs.chordal_graph.IChordalGraph;
import tdk_enum.graph.graphs.chordal_graph.single_thread.ChordalGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static tdk_enum.common.configuration.config_types.TriangulationAlgorithm.LB_TRIANG;

public class RandomMinimalTriangulator extends MinimalTriangulator {

    public RandomMinimalTriangulator()
    {

    }

    public RandomMinimalTriangulator(TriangulationAlgorithm heuristic)
    {
        this.heuristic = heuristic;
    }

    @Override
    protected IChordalGraph getMinimalTriangulationUsingMSCM(IGraph g)
    {
        IChordalGraph triangulation = new ChordalGraph(g);
        IncreasingWeightRandomizedNodeQueue queue =new IncreasingWeightRandomizedNodeQueue(g.accessVertices());
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

    @Override
    protected IChordalGraph getMinimalTriangulationUsingLBTriang(IGraph g, TriangulationAlgorithm heuristic) {

        IChordalGraph result = new ChordalGraph(g);
        if (heuristic == LB_TRIANG)
        {
            NodeSet nodes = new NodeSet(g.accessVertices());
            while(nodes.size() > 0)
            {
                Node v = nodes.get( new Random().nextInt(nodes.size()));
                nodes.remove(v);
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
}
