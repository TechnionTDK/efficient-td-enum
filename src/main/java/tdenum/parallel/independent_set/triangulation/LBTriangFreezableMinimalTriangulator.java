package tdenum.parallel.independent_set.triangulation;

import tdenum.graph.data_structures.Node;
import tdenum.graph.data_structures.NodeQueue;
import tdenum.graph.data_structures.NodeSet;
import tdenum.graph.graphs.ChordalGraph;
import tdenum.graph.graphs.interfaces.IChordalGraph;
import tdenum.graph.graphs.interfaces.IGraph;
import tdenum.graph.independent_set.triangulation.TriangulationAlgorithm;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static tdenum.graph.independent_set.triangulation.TriangulationAlgorithm.LB_TRIANG;

public class LBTriangFreezableMinimalTriangulator extends AbstractFreezableMinimalTriangulator {

    enum STATE
    {
        LB_SIMPLICAL,
    }

    TriangulationAlgorithm heuristic;
    IChordalGraph result;
    NodeQueue queue;

    Iterator<Node> nodesIterator;
    List<NodeSet> components;
    Set<NodeSet> substars;

    public LBTriangFreezableMinimalTriangulator(TriangulationAlgorithm heuristic)
    {
        this.heuristic = heuristic;
    }


    @Override
    public IChordalGraph triangulate() {

        if (heuristic == LB_TRIANG)
        {
            iterateMakeNodeLBSimplicial();
        }
        else
        {

            queueMakeNodeLBSimplicial();
        }
        return result;
    }

    void iterateMakeNodeLBSimplicial()
    {
        while(nodesIterator.hasNext() && !Thread.currentThread().isInterrupted())
        {
            makeNodeLBSimplicial(g, result, nodesIterator.next());
        }
    }


    void queueMakeNodeLBSimplicial()
    {
        while (!queue.isEmpty() && !Thread.currentThread().isInterrupted())
        {
            makeNodeLBSimplicial(g, result, queue.pop());
        }
    }


    @Override
    public IChordalGraph continueTriangulate() {
        return null;
    }

    @Override
    public void setGraph(IGraph graph)
    {
        g = graph;
        result = new ChordalGraph(g);
        nodesIterator = g.getNodes().iterator();
        queue = new NodeQueue(result, heuristic);
    }


    private void makeNodeLBSimplicial(IGraph g, IGraph gi, Node v)
    {
        Set<NodeSet> substars = getSubstars(g, gi, v);
        gi.saturateNodeSets(substars);
    }

    private Set<NodeSet> getSubstars(IGraph g, IGraph gi, Node v)
    {
        Set<Node> removedNodes = gi.getNeighborsCopy(v);
        removedNodes.add(v);
        components = g.getComponents(removedNodes);
        substars = new HashSet<>();
        for (NodeSet componenet : components)
        {
            substars.add(g.getNeighbors(componenet));
        }
        return substars;
    }

    void addCompnentsNeighborsToSubstars()
    {

    }
}
