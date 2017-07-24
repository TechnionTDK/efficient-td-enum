package tdenum.graph.graphs;

import tdenum.common.Utils;
import tdenum.graph.data_structures.*;
import tdenum.graph.graphs.interfaces.IGraph;

import java.util.*;


/**
 * Created by dvir.dukhan on 7/5/2017.
 */
//public class Graph<Node extends Integer > implements IGraph<Node>
public class Graph implements IGraph
{


    int numberOfNodes = 0;
    int numberOfEdges = 0;
    NodeSet nodes = new NodeSet();



    TdMap<NodeSet> neighborSets = new TdListMap<>();


    public Graph()
    {

    }

    public Graph(int numberOfNodes)
    {
        this.numberOfNodes = numberOfNodes;
        neighborSets = new TdListMap<>(numberOfNodes);
        for (int i = 0; i < numberOfNodes; i++)
        {
            Node v = new Node(i);
            nodes.add(v);
            neighborSets.put(v, new NodeSet()
            {
            });
        }

    }

    public Graph(final IGraph g)
    {
        this.numberOfNodes = g.getNumberOfNodes();
        this.numberOfEdges = g.getNumberOfEdges();
        nodes.addAll(g.getNodes());
        for (Node v : nodes)
        {
            NodeSet set = new NodeSet();
            set.addAll(g.getNeighborSets().get(v));
            neighborSets.put(v, set);
        }
//        for (int i = 0; i < g.getNeighborSets().size(); i++)
//        {
//            NodeSet set = new NodeSet();
//            set.addAll(g.getNeighborSets().get(i));
//            neighborSets.add(set);
//        }
    }


    boolean isValidNode(Node v)
    {
        return isValidNode(v.intValue());
    }


    boolean isValidNode(int v)
    {
        if (v < 0 || v >= numberOfNodes)
        {
            System.out.println("Invalid input");
            return false;
        }
        return true;

    }

    List<NodeSet> getComponenetsAux(TdMap<Integer> visitedList, int numberOfUnhandeledNodes)
    {
        ArrayList<NodeSet> components = new ArrayList<>();

        while (numberOfUnhandeledNodes > 0)
        {
            NodeSetProducer componentProducer = new NodeSetProducer(visitedList.size());
            ArrayDeque<Node> bfsQueue = new ArrayDeque<>();
            for (Node v : nodes)
            {
                if (visitedList.get(v) == 0)
                {

                    bfsQueue.push(v);
                    visitedList.put(v, 1);
                    componentProducer.insert(v);
                    numberOfUnhandeledNodes--;
                    break;
                }
            }

            while (!bfsQueue.isEmpty())
            {
                Node v = bfsQueue.poll();
                for (Node u : neighborSets.get(v))
                {
                    if (visitedList.get(u) == 0)
                    {
                        bfsQueue.push(u);
                        visitedList.put(u, 1);
                        componentProducer.insert(u);
                        numberOfUnhandeledNodes--;
                    }
                }

            }
            components.add(componentProducer.produce());
        }
        return components;
    }


    public void addEdge(Node u, Node v)
    {
        if (!isValidNode(u) || !isValidNode(v) || neighborSets.get(u).contains(v))
        {
            return;
        }
        neighborSets.get(u).add(v);
        neighborSets.get(v).add(u);
        numberOfEdges++;

    }

    @Override
    public void addClique(NodeSet clique)
    {


        for (Node v : clique)
        {
            for (Node u : clique)
            {
                if (u.intValue() < v.intValue())
                {
                    addEdge(u, v);
                }
            }
        }

    }

    @Override
    public void addClique(List<Node> clique)
    {

        for (Node v : clique)
        {
            for (Node u : clique)
            {
                if (u.intValue() < v.intValue())
                {
                    addEdge(u, v);
                }
            }
        }
    }

    @Override
    public void saturateNodeSets(Set<? extends NodeSet> sets)
    {
        for (NodeSet set : sets)
        {
            addClique(set);
        }

    }

    @Override
    public NodeSet getNodes()
    {
//        Set<Node> nodes = IntStream.
//                rangeClosed(0, numberOfNodes).
//                boxed().map(i -> new Node(i)).collect(Collectors.toSet());
        return nodes;
    }

    @Override
    public int getNumberOfEdges()
    {
        return numberOfEdges;
    }

    @Override
    public int getNumberOfNodes()
    {
        return numberOfNodes;
    }

    @Override
    public NodeSet getNeighbors(Node v)
    {

        if (!isValidNode(v))
        {
            System.out.println("Error: Requesting access to invalid node");
            return new NodeSet();
        }
        return new NodeSet(neighborSets.get(v));
    }



    @Override
    public NodeSet getNeighbors(List<Node> nodes)
    {
        NodeSetProducer neighborsProducer = new NodeSetProducer(numberOfNodes);
        for (Node v : nodes)
        {
            if (!isValidNode(v))
            {
                return new NodeSet();
            }
            for (Node u : neighborSets.get(v))
            {
                neighborsProducer.insert(u);
            }
        }
        for (Node v : nodes)
        {
            neighborsProducer.remove(v);
        }
        return neighborsProducer.produce();
    }

    @Override
    public NodeSet getNeighbors(NodeSet nodes)
    {
        NodeSetProducer neighborsProducer = new NodeSetProducer(numberOfNodes);
        for (Node v : nodes)
        {
            if (!isValidNode(v))
            {
                return new NodeSet();
            }
            for (Node u : neighborSets.get(v))
            {
                neighborsProducer.insert(u);
            }
        }
        for (Node v : nodes)
        {
            neighborsProducer.remove(v);
        }
        return neighborsProducer.produce();
    }

    @Override
    public TdMap<Boolean> getNeighborsMap(Node v)
    {
        TdMap<Boolean>  result = new TdListMap<>(numberOfNodes, false);
        for (Node u : getNeighbors(v))
        {
            result.put(u, true);
        }

        return result;
    }

    @Override
    public List<NodeSet> getComponents(List<Node> removeNodes)
    {
        TdMap<Integer> visitedList = new TdListMap<>(numberOfNodes, 0);
        for (Node v : removeNodes)
        {
            if (!isValidNode(v))
            {
                return new ArrayList<>();
            }
            visitedList.put(v, -1);
        }
        int numberOfUnhandeledNodes = numberOfNodes - removeNodes.size();
        return getComponenetsAux(visitedList, numberOfUnhandeledNodes);
    }


    @Override
    public List<NodeSet> getComponents(NodeSet removeNodes)
    {
        TdMap<Integer> visitedList = new TdListMap<>(numberOfNodes, 0);
        for (Node v : removeNodes)
        {
            if (!isValidNode(v))
            {
                return new ArrayList<>();
            }
            visitedList.put(v, -1);
        }
        int numberOfUnhandeledNodes = numberOfNodes - removeNodes.size();
        return getComponenetsAux(visitedList, numberOfUnhandeledNodes);
    }

    @Override
    public NodeSet getComponent(Node v, NodeSet removedNodes)
    {

        ArrayDeque<Node> q = new ArrayDeque<>();
        TdMap<Boolean> insertedNodes = new TdListMap<>(numberOfNodes,false);
        NodeSet component = new NodeSet();
        for (Node removed : removedNodes)
        {
            insertedNodes.put(removed, true);
        }

        component.add(v);
        q.push(v);
        insertedNodes.put(v, true);

        while (!q.isEmpty())
        {
            v = q.poll();
            final NodeSet neighbors = getNeighbors(v);
            for (Node neighbor : neighbors)
            {
                if (!insertedNodes.get(neighbor))
                {
                    q.push(neighbor);
                    insertedNodes.put(neighbor, true);
                    component.add(neighbor);
                }
            }
        }
        return component;
    }

    @Override
    public boolean areNeighbors(Node u, Node v)
    {
        return neighborSets.get(u).contains(v);
    }

    @Override
    public TdMap<Integer>getComponentsMap(NodeSet removedNodes)
    {
        TdMap<Integer> visitedList = new TdListMap<>(getNumberOfNodes(),0);
//        for (Node v : nodes)
//        {
//            visitedList.put(v, 0);
//        }
        for (Node v : removedNodes)
        {
            if (!isValidNode(v))
            {
                return new TdListMap<>();
            }
            visitedList.put(v, -1);
        }

        int numberOfUnhandledNodes = numberOfNodes - removedNodes.size();
        int currentComponent = 1;
        while (numberOfUnhandledNodes > 0)
        {
            ArrayDeque<Node> bfsQueue = new ArrayDeque<>();
            for (Node v : nodes)
            {
                if (visitedList.get(v) == 0)
                {
                    bfsQueue.push(v);
                    visitedList.put(v, currentComponent);
                    numberOfUnhandledNodes--;
                    break;
                }

            }
            while(!bfsQueue.isEmpty())
            {
                Node v = bfsQueue.poll();
                for (Node u : getNeighbors(v))
                {
                    if (visitedList.get(u) == 0)
                    {
                        bfsQueue.push(u);
                        visitedList.put(u, currentComponent);
                        numberOfUnhandledNodes--;
                    }
                }
            }
            currentComponent++;
        }
        return visitedList;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Graph{");
        sb.append("numberOfNodes=").append(numberOfNodes);
        sb.append(", numberOfEdges=").append(numberOfEdges);
        sb.append(", nodes=").append(nodes);
        sb.append(", neighborSets=").append(neighborSets);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Graph)) return false;

        Graph graph = (Graph) o;

        if (getNumberOfNodes() != graph.getNumberOfNodes()) return false;
        if (getNumberOfEdges() != graph.getNumberOfEdges()) return false;
        return getNodes().equals(graph.getNodes()) && neighborSets.equals(graph.neighborSets);
    }

    @Override
    public int hashCode()
    {
        int result = getNumberOfNodes();
        result = 31 * result + getNumberOfEdges();
        result = 31 * result + getNodes().hashCode();
        result = 31 * result + neighborSets.hashCode();
        return result;
    }

    @Override
    public TdMap<NodeSet> getNeighborSets() {
        return neighborSets;
    }
}
