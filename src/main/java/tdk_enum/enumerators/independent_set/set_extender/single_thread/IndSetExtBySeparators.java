package tdk_enum.enumerators.independent_set.set_extender.single_thread;

import tdk_enum.graph.data_structures.MinimalSeparator;
import tdk_enum.graph.data_structures.Node;
import tdk_enum.graph.data_structures.NodeSet;
import tdk_enum.graph.graphs.Graph;
import tdk_enum.graph.graphs.sub_graph.single_thread.SubGraph;
import tdk_enum.enumerators.independent_set.set_extender.AbstractIndependentSetExtender;
import tdk_enum.graph.graphs.IGraph;
import tdk_enum.graph.graphs.sub_graph.ISubGraph;

import java.util.*;

/**
 * Created by dvird on 17/07/10.
 */
public class IndSetExtBySeparators extends AbstractIndependentSetExtender
{
    IGraph graph = new Graph();

    public IndSetExtBySeparators()
    {

    }

    public IndSetExtBySeparators(IGraph graph)
    {
        this.graph = graph;
    }

    NodeSet getUnconnectedNodes(final IGraph graph)
    {
        NodeSet unconnectedNodes = new NodeSet();

        for (int i =0; i < graph.getNumberOfNodes(); i++)
        {
            for (int j =i+1; j < graph.getNumberOfNodes(); j++)
            {
                Node u = graph.getNodes().get(i);
                Node v = graph.getNodes().get(j);
                if (!graph.areNeighbors(u, v))
                {

                    if (u.intValue() > v.intValue())
                    {
                        unconnectedNodes.add(v);
                        unconnectedNodes.add(u);
                    }
                    else
                    {
                        unconnectedNodes.add(u);
                        unconnectedNodes.add(v);
                    }
                    return unconnectedNodes;
                }
            }
        }
//        for (Node u : graph.getNodes())
//        {
//            for (Node v : graph.getNodes())
//            {
//                if (!graph.areNeighbors(u, v))
//                {
//
//                    if (u.intValue() > v.intValue())
//                    {
//                        unconnectedNodes.add(v);
//                        unconnectedNodes.add(u);
//                    }
//                    else
//                    {
//                        unconnectedNodes.add(u);
//                        unconnectedNodes.add(v);
//                    }
//                    return unconnectedNodes;
//                }
//            }
//        }

        return unconnectedNodes;
    }

    <T extends MinimalSeparator> void includeNodesToMaximalSet(Map<Node, Node> cNodeIndsInMainGraph,
                                 Set<T> maximalSet,
                                 final NodeSet cComponentNeighbors,
                                 final NodeSet minimalSepInC)
    {
        T newSep = (T)new MinimalSeparator();
        Set<Node> newSepSet = new HashSet<>();
        for (Node neighbor : cComponentNeighbors)
        {
            if(isNodeInSet(neighbor, minimalSepInC))
//            if (minimalSepInC.contains(neighbor))
            {
                Node neighborInMain = cNodeIndsInMainGraph.get(neighbor);
                newSepSet.add(neighborInMain);
            }
        }
        newSep.addAll(newSepSet);

        maximalSet.add(newSep);
    }

    NodeSet findMinSep(NodeSet unconnectedNodes, Graph graph)
    {
        Node v = unconnectedNodes.get(0);
        Node u = unconnectedNodes.get(1);

//        NodeSet uNeighbors = graph.getNeighbors(u);
        Set<Node> uNeighbors = graph.getNeighbors(u);
        NodeSet vComponent = graph.getComponent(v, uNeighbors);

        return graph.getNeighbors(vComponent);
    }

    NodeSet mergeComponentAndNeighbors(final NodeSet component, final NodeSet compNeighbors)
    {
        NodeSet mergedComp = new NodeSet();
        mergedComp.addAll(component);
        mergedComp.addAll(compNeighbors);
        return mergedComp;
    }

    Queue<SubGraph> decompose(final ISubGraph mainSubGraph, final Set<? extends NodeSet> s )
    {
        Queue<SubGraph> Q = new ArrayDeque<>();
        Queue<SubGraph> resultComponents = new ArrayDeque<>();
        SubGraph sg = new SubGraph(mainSubGraph, s);
        Q.add(sg);

        while(!Q.isEmpty())
        {
            SubGraph c = Q.poll();
            Set<MinimalSeparator> cSeps = (Set<MinimalSeparator> )c.getSeps();
            if (cSeps.isEmpty())
            {
                resultComponents.add(c);
            }
            else
            {
                MinimalSeparator S = cSeps.iterator().next();
                c.addClique(S);
                List<NodeSet> cComponents = c.getComponents(S);

                for (NodeSet cComponent: cComponents)
                {
                    NodeSet cComponentNeighbors = c.getNeighbors(cComponent);
                    NodeSet cComponentNeighborsMerged = mergeComponentAndNeighbors(
                            cComponent, cComponentNeighbors);
                    SubGraph cComponentSubGraph = new SubGraph(c,
                            cComponentNeighborsMerged);
                    Set<MinimalSeparator> cComponentSeps = c.createNewSepGroup(
                            cComponentSubGraph.getNodeMaptoMainGraph(), S, cSeps);
                    cComponentSubGraph.setSeps(cComponentSeps);
                    Q.add(cComponentSubGraph);
                }
            }

        }
        return resultComponents;
    }




    @Override
    public Set<MinimalSeparator> extendToMaxIndependentSet(Set<MinimalSeparator> minSeps)
    {
        return extendToMaxIndependentSet(minSeps, graph);
//        Queue<SubGraph> queue = new ArrayDeque<>();
//        Set<MinimalSeparator> maximalSet = new HashSet<>();
//        maximalSet.addAll(minSeps);
//        SubGraph sg = new SubGraph(graph);
//        if (maximalSet.isEmpty())
//        {
//            queue.add(sg);
//        }
//        else
//        {
//            queue = decompose(sg, minSeps);
//        }
//
//
//
//        while(!queue.isEmpty())
//        {
//            SubGraph c = queue.poll();
//            NodeSet unconnectedNodes = getUnconnectedNodes(c);
//            if (!unconnectedNodes.isEmpty())
//            {
//                NodeSet minSepInC = findMinSep(unconnectedNodes, c);
//                c.addClique(minSepInC);
//
//                List<NodeSet> cComponents = c.getComponents(minSepInC);
//
//                for (NodeSet cComponent : cComponents)
//                {
//                    NodeSet cComponentNeighbors = c.getNeighbors(cComponent);
//                    Map<Node, Node> cNodeMapInMainGraph = c.getNodeMaptoMainGraph();
//                    includeNodesToMaximalSet(cNodeMapInMainGraph, maximalSet,
//                            cComponentNeighbors, minSepInC);
//                    NodeSet cComponentNeighborsMerged = mergeComponentAndNeighbors(
//                            cComponent, cComponentNeighbors);
//                    SubGraph cComponentSubGraph = new SubGraph(c, cComponentNeighborsMerged);
//                    queue.add(cComponentSubGraph);
//                }
//
//            }
//            queue.poll();
//        }
//        return maximalSet;
    }

    @Override
    public Set<MinimalSeparator> extendToMaxIndependentSet(Set<MinimalSeparator> minSeps, IGraph graph) {
        Queue<SubGraph> queue = new ArrayDeque<>();
        Set<MinimalSeparator> maximalSet = new HashSet<>();
        maximalSet.addAll(minSeps);
        SubGraph sg = new SubGraph(graph);
        if (maximalSet.isEmpty())
        {
            queue.add(sg);
        }
        else
        {
            queue = decompose(sg, minSeps);
        }



        while(!queue.isEmpty())
        {
            SubGraph c = queue.poll();
            NodeSet unconnectedNodes = getUnconnectedNodes(c);
            if (!unconnectedNodes.isEmpty())
            {
                NodeSet minSepInC = findMinSep(unconnectedNodes, c);
                c.addClique(minSepInC);

                List<NodeSet> cComponents = c.getComponents(minSepInC);

                for (NodeSet cComponent : cComponents)
                {
                    NodeSet cComponentNeighbors = c.getNeighbors(cComponent);
                    Map<Node, Node> cNodeMapInMainGraph = c.getNodeMaptoMainGraph();
                    includeNodesToMaximalSet(cNodeMapInMainGraph, maximalSet,
                            cComponentNeighbors, minSepInC);
                    NodeSet cComponentNeighborsMerged = mergeComponentAndNeighbors(
                            cComponent, cComponentNeighbors);
                    SubGraph cComponentSubGraph = new SubGraph(c, cComponentNeighborsMerged);
                    queue.add(cComponentSubGraph);
                }

            }
            queue.poll();
        }
        return maximalSet;
    }

    boolean isNodeInSet(Node node, NodeSet sortedNodes)
    {

        int n = sortedNodes.size();
        int i = 0;
        while (n > 0) {
            int j = i + n / 2;
            if (sortedNodes.get(j).equals(node)) {
                return true;
            } else if (node.intValue() < sortedNodes.get(j).intValue()) {
                n = n / 2;
            } else {
                i = j + 1;
                n = n - n / 2 - 1;
            }
        }

        return false;
    }


}
