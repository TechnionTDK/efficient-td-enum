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

    private int distanceMatrix[][];

    private String originalPath = null;


    private List<HyperEdge> hyperEdges = new ArrayList<>();
    private HashMap<Node,String> vertexNameMap = new HashMap<>();
    private HashMap<String,Node> vertexIndexMap = new HashMap<>();
    private HashMap<Node,Integer> vertexEccentricity = new HashMap<>();
   // private HashMap<Integer,NodeSet> adjacentVertices = null;
  //  private HashMap<Integer,NodeSet> reachableVertices = null;

    private boolean updateNeeded = false;

    private boolean connected = true;

    protected int numberOfNodes = 0;
    protected int numberOfEdges = 0;
    protected NodeSet vertices = new NodeSet();



//    TdMap<NodeSet> adjacentVertices = new TdListMap<>();

    protected TdMap<Set<Node>> adjacentVertices = new TdListMap<>();
    protected TdMap<Set<Node>> reachableVertices = new TdListMap<>();


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
        this.distanceMatrix = new int[numberOfNodes][numberOfNodes];


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
        this.distanceMatrix = new int[numberOfNodes][numberOfNodes];
        update();
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

        updateNeeded = true;

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
        if (updateNeeded) {
            update();
        }

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
        if (updateNeeded) {
            update();
        }

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

    public final Set<Node> accessNeighbors(Node v) {

        if (updateNeeded) {
            update();
        }
        return adjacentVertices.get(v);
    }


    @Override
    public NodeSet getNeighbors(List<Node> nodes)
    {
        if (updateNeeded) {
            update();
        }
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
        if (updateNeeded) {
            update();
        }
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
        if (updateNeeded) {
            update();
        }
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
        if (updateNeeded) {
            update();
        }
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
        if (updateNeeded) {
            update();
        }
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
        if (updateNeeded) {
            update();
        }

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
        if (updateNeeded) {
            update();
        }
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

        if (updateNeeded) {
            update();
        }
        return adjacentVertices.get(u).contains(v);
    }

    @Override
    public TdMap<Integer>getComponentsMap(NodeSet removedNodes)
    {
        if (updateNeeded) {
            update();
        }
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

        if (updateNeeded) {
            update();
        }
        return adjacentVertices;
    }

    @Override
    public Set<Set<Node>> getEdgesDelta(NodeSet nodes) {
        if (updateNeeded) {
            update();
        }
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


    /********************** dflat domain **************/
    /**
     * @author ABSEHER Michael (abseher@dbai.tuwien.ac.at)
     */

    @Override
    public int getVertexIndex(String vertexName) {
        Node ret = new Node(-1);

        if (vertexIndexMap.containsKey(vertexName)) {
            ret = vertexIndexMap.get(vertexName);
        }

        return ret.intValue();
    }

    public String getVertexName(int vertexIndex) {
        return vertexNameMap.get(vertexIndex);
    }





    @Override
    public void addHyperEdge(HyperEdge hyperEdge) {
        if (hyperEdge != null) {
            hyperEdges.add(hyperEdge);

            for (String vertexName : hyperEdge.accessVertices())
            {
                Node vertex = new Node(-1);

                if (vertexIndexMap.containsKey(vertexName)) {
                    vertex = vertexIndexMap.get(vertexName);
                }
                else {
                    vertex = new Node(vertexIndexMap.size());

                    vertexNameMap.put(vertex, vertexName);
                    vertexIndexMap.put(vertexName, vertex);
                }

                if (!adjacentVertices.containsKey(vertex)) {
                    adjacentVertices.put(vertex,
                            new HashSet<>());
                }

                Set neighbors =
                        adjacentVertices.get(vertex);

                for (String neighborName : hyperEdge.accessVertices())
                {
                    Node neighbor = new Node(-1);

                    if (vertexIndexMap.containsKey(neighborName)) {
                        neighbor = vertexIndexMap.get(neighborName);
                    }
                    else {
                        neighbor = new Node(vertexIndexMap.size());

                        vertexNameMap.put(neighbor, neighborName);
                        vertexIndexMap.put(neighborName, neighbor);
                    }

                    if (neighbor != vertex && !neighbors.contains(neighbor)) {
                        neighbors.add(neighbor);
                        addEdge(vertex, neighbor);
                    }
                }
            }


        }
    }


    @Override
    public boolean isVertex(int id) {
        return vertexNameMap.containsKey(id);
    }

    @Override
    public boolean isVertex(String id) {
        return vertexIndexMap.containsKey(id);
    }

    @Override
    public boolean isConnected() {
        if (updateNeeded) {
            update();
        }

        return connected;
    }

    @Override
    public boolean isConnected(int vertex1, int vertex2) {
        boolean ret = false;

        if (updateNeeded) {
            update();
        }

        if (vertex1 >= 0 && vertex1 < numberOfNodes && vertex2 >= 0 && vertex2 < numberOfNodes) {
            ret = connected || vertex1 == vertex2 || distanceMatrix[vertex1][vertex2] > 0;
        }

        return ret;
    }

    @Override
    public List<Node> getReachableVertices(int vertex) {
        NodeSet ret = new NodeSet();

        if (updateNeeded) {
            update();
        }

        if (reachableVertices.containsKey(vertex)) {
            for (Node reachableVertex : reachableVertices.get(vertex)) {
                ret.add(reachableVertex);
            }
        }

        return ret;
    }


    @Override
    public List<Node> accessReachableVertices(int vertex) {
        List<Node> ret = new NodeSet();

        if (updateNeeded) {
            update();
        }

        if (reachableVertices.containsKey(vertex)) {
            ret = new ArrayList<>(reachableVertices.get(vertex));
        }

        return ret;
    }


    @Override
    public List<Node> getNeighbors(int vertex, DecompositionNode node) {
        List<Node> ret = new NodeSet();

        if (updateNeeded) {
            update();
        }

        if (node == null) {
            ret = new NodeSet(getNeighbors(new Node(vertex)));
        }
        else {
            if (vertex >= 0 && vertex < numberOfNodes) {
                for (Node item : node.accessItemList()) {
                    if (distanceMatrix[vertex][item.intValue()] == 1) {
                        ret.add(item);
                    }
                }
            }
        }

        return ret;
    }



    @Override
    public int getEccentricity(int vertex) {


       return getEccentricity(new Node(vertex));


    }

    @Override
    public int getEccentricity(Node vertex)
    {
        if (updateNeeded) {
            update();
        }
        int ret = -1;
        if (vertexEccentricity.containsKey(vertex)) {
            ret = vertexEccentricity.get(vertex);
        }

        return ret;
    }

    @Override
    public int getEccentricity(String vertex) {
        int ret = -1;

        if (vertexIndexMap.containsKey(vertex)) {
            ret = getEccentricity(vertexIndexMap.get(vertex));
        }

        return ret;
    }



    @Override
    public void update() {


        reachableVertices = new TdListMap<>();



        for (Node vertex : vertices) {

            updateDistance(vertex.intValue(), new boolean[numberOfNodes], 0, vertex.intValue());
        }

        Collections.sort(vertices);

        connected = true;

        if (numberOfNodes > 1)
        {
            for (int i = 1; connected && i < numberOfNodes; i++)
            {
                connected = distanceMatrix[0][i] > 0;
            }
        }

        if (connected) {
            for (Node vertex : vertices) {
                int max = 0;

                for (int i = 0; i < numberOfNodes; i++) {
                    if (distanceMatrix[vertex.intValue()][i] > max) {
                        max = distanceMatrix[vertex.intValue()][i];
                    }
                }

                Set<Node> reachable = new HashSet<>();

                for (int i = 0; i < numberOfNodes; i++) {
                    if (distanceMatrix[vertex.intValue()][i] > 0) {
                        reachable.add(new Node(i));
                    }
                }

                reachableVertices.put(vertex,reachable);

                reachableVertices.get(vertex).remove(vertex);

                vertexEccentricity.put(vertex, max);
            }
        }
        else {
            for (Node vertex : vertices) {
                vertexEccentricity.put(vertex, -1);

                Set<Node> reachable = new HashSet<>();

                for (int i = 0; i < numberOfNodes; i++) {
                    if (distanceMatrix[vertex.intValue()][i] > 0) {
                        reachable.add(new Node(i));
                    }
                }

                reachableVertices.put(vertex, reachable);
            }
        }
        updateNeeded = false;

    }

    private void updateDistance(int vertex, boolean[] visitedVertices, int currentDistance, int originalIndex) {
        boolean[] neighbors =
                new boolean[numberOfNodes];

        if (!visitedVertices[vertex]) {
            visitedVertices[vertex] = true;

            distanceMatrix[originalIndex][vertex] = currentDistance;
        }

        for (Node neighbor : adjacentVertices.get(new Node(vertex))) {
            if (!visitedVertices[neighbor.intValue()]) {
                neighbors[neighbor.intValue()] = true;

                visitedVertices[neighbor.intValue()] = true;
            }
        }

        for (int i = 0; i < numberOfNodes; i++) {
            if (neighbors[i])
            {
                updateDistance(i,
                        visitedVertices,
                        currentDistance + 1, originalIndex);

                distanceMatrix[originalIndex][i] = currentDistance + 1;
            }
        }
    }

    @Override
    public String getOriginalPath() {
        return originalPath;
    }

    @Override
    public void setOriginalPath(String originalPath) {
        this.originalPath = originalPath;
    }
}
