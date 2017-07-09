package tdenum.graph;

import tdenum.common.Utils;
import tdenum.graph.interfaces.IGraph;
import tdenum.graph.data_structures.NodeSetProducer;

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
    List<NodeSet> neighborSets = new ArrayList<>();


    public Graph()
    {

    }

    public Graph(int numberOfNodes)
    {
        this.numberOfNodes = numberOfNodes;
        neighborSets = new ArrayList<NodeSet>(numberOfNodes);
        for (int i = 0; i < numberOfNodes; i++)
        {
            Node v = new Node(i);
            nodes.add(v);
            neighborSets.add(i, new NodeSet()
            {
            });
        }

    }

    public Graph(final Graph g)
    {
        this.numberOfNodes = g.numberOfNodes;
        this.numberOfEdges = g.numberOfEdges;
        nodes.addAll(g.nodes);
        for (int i = 0; i < g.neighborSets.size(); i++)
        {
            NodeSet set = new NodeSet();
            set.addAll(g.neighborSets.get(i));
            neighborSets.add(set);
        }
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

    List<NodeSet> getComponenetsAux(List<Integer> visitedList, int numberOfUnhandeledNodes)
    {
        ArrayList<NodeSet> components = new ArrayList<>();
        NodeSetProducer componenetProducer = new NodeSetProducer(visitedList.size());
        while (numberOfUnhandeledNodes > 0)
        {
            ArrayDeque<Node> bfsQueue = new ArrayDeque<>();
            for (int i = 0; i < numberOfNodes; i++)
            {
                if (visitedList.get(i) == 0)
                {
                    Node node = new Node(i);
                    bfsQueue.push(node);
                    visitedList.set(i, 1);
                    componenetProducer.insert(node);
                    numberOfUnhandeledNodes--;
                    break;
                }
            }

            while (!bfsQueue.isEmpty())
            {
                Node v = bfsQueue.poll();
                for (Node u : neighborSets.get(v.intValue()))
                {
                    if (visitedList.get(u.intValue()) == 0)
                    {
                        bfsQueue.push(u);
                        visitedList.set(u.intValue(), 1);
                        componenetProducer.insert(u);
                        numberOfUnhandeledNodes--;
                    }
                }

            }
            components.add(componenetProducer.produce());
        }
        return components;
    }


    public void addEdge(Node u, Node v)
    {
        if (!isValidNode(u) || !isValidNode(v) || neighborSets.get(u.intValue()).contains(v))
        {
            return;
        }
        neighborSets.get(u.intValue()).add(v);
        neighborSets.get(v.intValue()).add(u);
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
        return getNeighbors(v.intValue());
    }

    @Override
    public NodeSet getNeighbors(int v)
    {
        if (!isValidNode(v))
        {
            System.out.println("Error: Requesting access to invalid node");
            return neighborSets.get(0);
        }
        return neighborSets.get(v);
    }


    @Override
    public NodeSet getNeighbors(List<Node> nodes)
    {
        NodeSetProducer neighborsProducer = new NodeSetProducer(numberOfNodes);
        for (Node v : nodes)
        {
            if (isValidNode(v))
            {
                return new NodeSet();
            }
            for (Node u : neighborSets.get(v.intValue()))
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
            for (Node u : neighborSets.get(v.intValue()))
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
    public List<Boolean> getNeighborsMap(Node v)
    {
        List<Boolean> result = Utils.generateFixedList(numberOfNodes, false);
        for (Node u : getNeighbors(v))
        {
            result.set(u.intValue(), true);
        }

        return result;
    }

    @Override
    public List<NodeSet> getComponents(List<Node> removeNodes)
    {
        List<Integer> visitedList = Utils.generateFixedList(numberOfNodes, 0);
        for (Node v : removeNodes)
        {
            if (!isValidNode(v))
            {
                return new ArrayList<NodeSet>();
            }
            visitedList.set(v.intValue(), -1);
        }
        int numberOfUnhandeledNodes = numberOfNodes - removeNodes.size();
        return getComponenetsAux(visitedList, numberOfUnhandeledNodes);
    }


    @Override
    public List<NodeSet> getComponents(NodeSet removeNodes)
    {
        List<Integer> visitedList = Utils.generateFixedList(numberOfNodes, 0);
        for (Node v : removeNodes)
        {
            if (!isValidNode(v))
            {
                return new ArrayList<NodeSet>();
            }
            visitedList.set(v.intValue(), -1);
        }
        int numberOfUnhandeledNodes = numberOfNodes - removeNodes.size();
        return getComponenetsAux(visitedList, numberOfUnhandeledNodes);
    }

    @Override
    public NodeSet getComponent(Node v, NodeSet removedNodes)
    {

        ArrayDeque<Node> q = new ArrayDeque<>();
        List<Boolean> insertedNodes = Utils.generateFixedList(numberOfNodes, false);
        NodeSet component = new NodeSet();
        for (Node removed : removedNodes)
        {
            insertedNodes.set(removed.intValue(), true);
        }

        component.add(v);
        q.push(v);
        insertedNodes.set(v.intValue(), true);

        while (!q.isEmpty())
        {
            v = q.poll();
            final NodeSet neighbors = getNeighbors(v);
            for (Node neighbor : neighbors)
            {
                if (!insertedNodes.get(neighbor.intValue()))
                {
                    q.push(neighbor);
                    insertedNodes.set(neighbor.intValue(), true);
                    component.add(neighbor);
                }
            }
        }
        return component;
    }

    @Override
    public boolean areNeighbors(Node u, Node v)
    {
        return neighborSets.get(u.intValue()).contains(v);
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        for (int v = 0; v < numberOfNodes; v++)
        {
            sb.append(v).append(" has neighbors: {");
            for (Node u : neighborSets.get(v))
            {
                sb.append(u.intValue()).append(" ");
            }
            sb.append("}").append(System.lineSeparator());
        }
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
        if (!getNodes().equals(graph.getNodes())) return false;
        return neighborSets.equals(graph.neighborSets);
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
}
