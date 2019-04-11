package tdk_enum.graph.graphs;

import tdk_enum.graph.data_structures.*;
import tdk_enum.graph.graphs.tree_decomposition.single_thread.DecompositionNode;

import java.util.*;


/**
 * Created by dvir.dukhan on 7/5/2017.
 */
//public class Graph<Node extends Integer > implements IGraph<Node>
public class Graph implements IGraph
{
   // private int size = 0;


    protected int numberOfNodes = 0;
    protected int numberOfEdges = 0;
    protected NodeSet vertices = new NodeSet();



//    TdMap<NodeSet> adjacentVertices = new TdListMap<>();

    protected TdMap<Set<Node>> adjacentVertices = new TdListMap<>();



    public Graph()
    {

    }



    public Graph(int numberOfNodes)
    {
        this.numberOfNodes = numberOfNodes;
        adjacentVertices = new TdListMap<>(numberOfNodes);
        for (int i = 0; i < numberOfNodes; i++)
        {
            Node v = new Node(i);
            vertices.add(v);
//            adjacentVertices.put(v, new NodeSet()
//            {
//            });

            adjacentVertices.put(v, new HashSet<>());
        }


    }

    public Graph(final IGraph g)
    {
        this.numberOfNodes = g.getNumberOfNodes();
        this.numberOfEdges = g.getNumberOfEdges();
        vertices.addAll(g.accessVertices());
        for (Node v : vertices)
        {
//            NodeSet set = new NodeSet();
            Set set = new HashSet();
            set.addAll(g.getAdjacentVertices().get(v));
            adjacentVertices.put(v, set);
        }

//        for (int i = 0; i < g.getAdjacentVertices().size(); i++)
//        {
//            NodeSet set = new NodeSet();
//            set.addAll(g.getAdjacentVertices().get(i));
//            adjacentVertices.add(set);
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
            for (Node v : vertices)
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
                for (Node u : adjacentVertices.get(v))
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
        if (!isValidNode(u) || !isValidNode(v) || adjacentVertices.get(u).contains(v))
        {
            return;
        }
        adjacentVertices.get(u).add(v);
        adjacentVertices.get(v).add(u);
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
    public NodeSet getVertices()
    {
//        Set<Node> vertices = IntStream.
//                rangeClosed(0, numberOfNodes).
//                boxed().map(i -> new Node(i)).collect(Collectors.toSet());
        return new NodeSet(vertices);
    }

    @Override
    public NodeSet accessVertices() {

        return vertices;
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
    public Set<Node> getNeighbors(Node v)
    {


        if (!isValidNode(v))
        {
            System.out.println("Error: Requesting access to invalid node");
            return new HashSet<>();
        }
        return new HashSet<>(adjacentVertices.get(v));
    }

    @Override
//    public final NodeSet accessNeighbors(Node v) {
//        return adjacentVertices.get(v);
//    }

    public  Set<Node> accessNeighbors(Node v) {


        return adjacentVertices.get(v);
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
            for (Node u : adjacentVertices.get(v))
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
    public NodeSet getNeighbors(NodeSet nodeSet)
    {

        NodeSetProducer neighborsProducer = new NodeSetProducer(numberOfNodes);
        for (Node v : nodeSet)
        {
            if (!isValidNode(v))
            {
                return new NodeSet();
            }
            for (Node u : adjacentVertices.get(v))
            {
                neighborsProducer.insert(u);
            }
        }
        for (Node v : nodeSet)
        {
            neighborsProducer.remove(v);
        }
        return neighborsProducer.produce();
    }

    @Override
    public TdMap<Boolean> getNeighborsMap(Node v)
    {

        TdMap<Boolean>  result = new TdListMap<>(numberOfNodes, false);
//        for (Node u : accessNeighbors(v))
        for (Node u : adjacentVertices.get(v))
        {
            result.put(u, true);
        }

        return result;
    }

    @Override
    public List<NodeSet> getComponents(Set<Node> removeNodes)
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
//            final NodeSet neighbors = adjacentVertices.get(v);
            final Set<Node> neighbors = adjacentVertices.get(v);
//            final NodeSet neighbors = accessNeighbors(v);
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
    public NodeSet getComponent(Node v, Set<Node> removedNodes) {

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
//            final NodeSet neighbors = adjacentVertices.get(v);
            final Set<Node> neighbors = adjacentVertices.get(v);
//            final NodeSet neighbors = accessNeighbors(v);
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


        return adjacentVertices.get(u).contains(v);
    }

    @Override
    public TdMap<Integer>getComponentsMap(NodeSet removedNodes)
    {

        TdMap<Integer> visitedList = new TdListMap<>(getNumberOfNodes(),0);
//        for (Node v : vertices)
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
            for (Node v : vertices)
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
                for (Node u : adjacentVertices.get(v))
//                for (Node u : accessNeighbors(v))
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
        sb.append(", vertices=").append(vertices);
        sb.append(", adjacentVertices=").append(adjacentVertices);
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
        return this.accessVertices().equals(graph.accessVertices()) && adjacentVertices.equals(graph.adjacentVertices);
    }

    @Override
    public int hashCode()
    {
        int result = getNumberOfNodes();
        result = 31 * result + getNumberOfEdges();
        result = 31 * result + this.accessVertices().hashCode();
        result = 31 * result + adjacentVertices.hashCode();
        return result;
    }

    @Override
//    public TdMap<NodeSet> getAdjacentVertices() {
//        return adjacentVertices;
//    }
    public TdMap<Set<Node>> getAdjacentVertices() {


        return adjacentVertices;
    }

    @Override
    public Set<Set<Node>> getEdgesDelta(NodeSet nodes) {

        Set edges = new HashSet();
        for (Node u : nodes)
        {
            for (Node v : nodes)
            {
                if (u.intValue() < v.intValue())
                {
                    if (!areNeighbors(u,v))
                    {
                        Set edge = new HashSet();
                        edge.add(u);
                        edge.add(v);
                        edges.add(edge);
                    }
                }
            }
        }
        return edges;
    }


}
