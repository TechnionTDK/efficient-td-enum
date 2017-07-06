package tdenum.graph;

import tdenum.common.Utils;
import tdenum.graph.interfaces.IGraph;
import tdenum.graph.data_structures.NodeSetProducer;

import java.util.*;


/**
 * Created by dvir.dukhan on 7/5/2017.
 */
//public class Graph<Node extends Integer > implements IGraph<Node>
public class Graph implements IGraph {


    int numberOfNodes = 0;
    int numberOfEdges = 0;
    NodeSet nodes = new NodeSet();
    List<Set<Node>> neighborSets = new ArrayList<Set<Node>>();


    public Graph()
    {

    }

    public Graph(int numberOfNodes)
    {
        this.numberOfNodes = numberOfNodes;
        neighborSets = new ArrayList<Set<Node>>(numberOfNodes);
        for (int i = 0; i < numberOfNodes; i++)
        {
            Node v = new Node(i);
            nodes.add(v);
            neighborSets.add(i, new HashSet<Node>() {
            });
        }

    }



    boolean isValidNode(Node v)
    {
        return isValidNode(v.intValue());
    }

    boolean isValidNode(int v)
    {
        if (v < 0 || v >= numberOfNodes) {
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
            ArrayDeque<Node> bfsQueue =new ArrayDeque<>();
            for(int i = 0; i < numberOfNodes; i++)
            {
                if (visitedList.get(i)==0)
                {
                    Node node = new Node(i);
                    bfsQueue.push(node);
                    visitedList.set(i, 1);
                    componenetProducer.insert(node);
                    numberOfUnhandeledNodes--;
                    break;
                }
            }

            while(!bfsQueue.isEmpty())
            {
                Node v = bfsQueue.poll();
                for (Node u : neighborSets.get(v.intValue()))
                {
                    if (visitedList.get(u.intValue())==0)
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





    public void addEdge(Node u, Node v) {
        if (!isValidNode(u) || !isValidNode(v) || neighborSets.get(u.intValue()).contains(v)) {
            return;
        }
        neighborSets.get(u.intValue()).add(v);
        neighborSets.get(v.intValue()).add(u);
        numberOfEdges++;

    }

    public void addClique(Set<Node> clique) {

        for(Node v: clique)
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

    public void addClique(List<Node> clique) {

        for(Node v: clique)
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

    public void saturateNodeSets(Set<? extends Set<Node>> sets) {
        for (Set<Node> set : sets)
        {
            addClique(set);
        }

    }

    public Set<Node> getNodes() {
//        Set<Node> nodes = IntStream.
//                rangeClosed(0, numberOfNodes).
//                boxed().map(i -> new Node(i)).collect(Collectors.toSet());
        return nodes;
    }

    public int getNumberOfEdges() {
        return numberOfEdges;
    }

    public int getNumberOfNodes() {
        return numberOfNodes;
    }

    public Set<Node> getNeighbors(Node v) {
       return getNeighbors(v.intValue());
    }

    @Override
    public Set<Node> getNeighbors(int v) {
        if (!isValidNode(v))
        {
            System.out.println("Error: Requesting access to invalid node");
            return neighborSets.get(0);
        }
        return neighborSets.get(v);
    }


    public NodeSet getNeighbors(List<Node> nodes) {
        NodeSetProducer neighborsProducer = new NodeSetProducer(numberOfNodes);
        for(Node v : nodes)
        {
            if (isValidNode(v))
            {
                return new NodeSet();
            }
            for(Node u : neighborSets.get(v.intValue()))
            {
                neighborsProducer.insert(u);
            }
        }
        for(Node v : nodes)
        {
            neighborsProducer.remove(v);
        }
        return neighborsProducer.produce();
    }


    public NodeSet getNeighbors(Set<Node> nodes) {
        NodeSetProducer neighborsProducer = new NodeSetProducer(numberOfNodes);
        for(Node v : nodes)
        {
            if (isValidNode(v))
            {
                return new NodeSet();
            }
            for(Node u : neighborSets.get(v.intValue()))
            {
                neighborsProducer.insert(u);
            }
        }
        for(Node v : nodes)
        {
            neighborsProducer.remove(v);
        }
        return neighborsProducer.produce();
    }

    public List<Boolean> getNeighborsMap(Node v) {
        List<Boolean> result = Utils.generateFixedList(numberOfNodes, false);
        for(Node u : getNeighbors(v))
        {
            result.set(u.intValue(), true);
        }

        return result;
    }

    public List<NodeSet> getComponents(Set<Node> removeNodes) {
        List<Integer> visitedList = Utils.generateFixedList(numberOfNodes, 0);
        for(Node v: removeNodes)
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

    public List<NodeSet> getComponents(NodeSet removeNodes) {
        List<Integer> visitedList = Utils.generateFixedList(numberOfNodes, 0);
        for(Node v: removeNodes)
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

    public Set<Node> getComponent(Node v, Set<Node> removedNodes) {
        ArrayDeque<Node> q = new ArrayDeque<>();
        List<Boolean> insertedNodes = Utils.generateFixedList(numberOfNodes, false);
        Set<Node> component = new HashSet<>();
        for(Node removed: removedNodes)
        {
            insertedNodes.set(removed.intValue(), true);
        }

        component.add(v);
        q.push(v);
        insertedNodes.set(v.intValue(),true);

        while(!q.isEmpty())
        {
            v = q.poll();
            final Set<Node> neighbors = getNeighbors(v);
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
        return  component;
    }


    public boolean areNeighbors(Node u, Node v) {
        return neighborSets.get(u.intValue()).contains(v);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int v = 0; v < numberOfNodes; v++)
        {
            sb.append(v).append(" has neighbors: {");
            for (Node u: neighborSets.get(v))
            {
                sb.append(u.intValue()).append(" ");
            }
            sb.append("}").append(System.lineSeparator());
        }
        return sb.toString();
    }
}
