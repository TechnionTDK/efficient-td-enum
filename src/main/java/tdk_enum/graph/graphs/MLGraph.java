package tdk_enum.graph.graphs;

import tdk_enum.graph.data_structures.*;
import tdk_enum.graph.graphs.tree_decomposition.single_thread.DecompositionNode;

import java.util.*;

public class MLGraph extends Graph implements IMLGraph {



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
    protected TdMap<Set<Node>> reachableVertices = new TdListMap<>();






    public MLGraph()
    {
        super();
    }



    public MLGraph(int numberOfNodes)
    {
       super((numberOfNodes));
        this.distanceMatrix = new int[numberOfNodes][numberOfNodes];


    }

    public MLGraph(final IGraph g)
    {
        super(g);

        this.distanceMatrix = new int[numberOfNodes][numberOfNodes];
        update();

    }







    @Override
    public void addEdge(Node u, Node v)
    {

        super.addEdge(u, v);
        updateNeeded = true;

    }







    @Override
    public NodeSet getVertices()
    {
        if (updateNeeded) {
            update();
        }
        return super.getVertices();
    }

    @Override
    public NodeSet accessVertices() {
        if (updateNeeded) {
            update();
        }

        return super.accessVertices();
    }


    @Override
    public Set<Node> getNeighbors(Node v)
    {
        if (updateNeeded) {
            update();
        }
        return super.getNeighbors(v);

    }

    @Override

    public  Set<Node> accessNeighbors(Node v) {

        if (updateNeeded) {
            update();
        }
        return super.accessNeighbors(v);
    }


    @Override
    public NodeSet getNeighbors(List<Node> nodes)
    {
        if (updateNeeded) {
            update();
        }
        return getNeighbors(nodes);

    }

    @Override
    public NodeSet getNeighbors(NodeSet nodeSet)
    {
        if (updateNeeded) {
            update();
        }
        return super.getNeighbors(nodeSet);

    }

    @Override
    public TdMap<Boolean> getNeighborsMap(Node v)
    {
        if (updateNeeded) {
            update();
        }
        return super.getNeighborsMap(v);

//
    }

    @Override
    public List<NodeSet> getComponents(Set<Node> removeNodes)
    {
        if (updateNeeded) {
            update();
        }
        return super.getComponents(removeNodes);

    }


    @Override
    public List<NodeSet> getComponents(NodeSet removeNodes)
    {
        if (updateNeeded) {
            update();
        }
        return super.getComponents(removeNodes);

    }

    @Override
    public NodeSet getComponent(Node v, NodeSet removedNodes)
    {
        if (updateNeeded) {
            update();
        }
        return super.getComponent(v, removedNodes);

    }

    @Override
    public NodeSet getComponent(Node v, Set<Node> removedNodes) {
        if (updateNeeded) {
            update();
        }
        return super.getComponent(v, removedNodes);

    }

    @Override
    public boolean areNeighbors(Node u, Node v)
    {

        if (updateNeeded) {
            update();
        }
        return super.areNeighbors(u, v);
    }

    @Override
    public TdMap<Integer>getComponentsMap(NodeSet removedNodes)
    {
        if (updateNeeded) {
            update();
        }

        return super.getComponentsMap(removedNodes);
    }



    @Override
//    public TdMap<NodeSet> getAdjacentVertices() {
//        return adjacentVertices;
//    }
    public TdMap<Set<Node>> getAdjacentVertices() {

        if (updateNeeded) {
            update();
        }
        return super.getAdjacentVertices();
    }

    @Override
    public Set<Set<Node>> getEdgesDelta(NodeSet nodes) {
        if (updateNeeded) {
            update();
        }

        return super.getEdgesDelta(nodes);

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
    public boolean isConnected(Node vertex1, Node vertex2) {
        return isConnected(vertex1.intValue(), vertex2.intValue());
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
    public List<Node> getNeighbors(Node vertex, DecompositionNode node) {
        return getNeighbors(vertex.intValue(), node);
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
