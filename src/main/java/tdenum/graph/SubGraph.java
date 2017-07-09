package tdenum.graph;

import tdenum.common.Utils;
import tdenum.graph.interfaces.IGraph;
import tdenum.graph.interfaces.ISubGraph;

import java.util.*;

/**
 * Created by dvir.dukhan on 7/9/2017.
 */
public class SubGraph extends Graph implements ISubGraph  {

    IGraph mainGraph = null;
    Map<Node, Node> nodeMapToMainGraph = new HashMap<>();

    Set<MinimalSeparator> seps =new HashSet<>();


    public SubGraph(final Graph mainGraph)
    {
        super(mainGraph);
        this.mainGraph = mainGraph;


        for (Node n : mainGraph.nodes)
        {
            nodeMapToMainGraph.put(n, n);
        }

    }

    public SubGraph(final SubGraph fatherGraph, NodeSet nodeSetInFatherGraph)
    {
        super(nodeSetInFatherGraph.size());
        initializeFromFatherSubgraph(fatherGraph, nodeSetInFatherGraph);

    }

    public SubGraph(final SubGraph fatherGraph, NodeSet nodeSetInFatherGraph, final Set<MinimalSeparator> seps)
    {
        super(nodeSetInFatherGraph.size());
        initializeFromFatherSubgraph(fatherGraph, nodeSetInFatherGraph);
        this.seps = seps;

    }

    private void initializeFromFatherSubgraph(final SubGraph fatherGraph, NodeSet nodeSetInFatherGraph)
    {
        mainGraph = fatherGraph.mainGraph;
        final Map<Node, Node> fatherNodesMapInMain = fatherGraph.nodeMapToMainGraph;

        NodeSet nodeSetInFather = nodeSetInFatherGraph;
        Map<Node, Node>  subNodesInMain = nodeMapToMainGraph;
        Set<Node> fatherNodesInSub = new HashSet<>();


        for (Node nodeInFather : nodeSetInFather)
        {
            Node nodeInMain = fatherNodesMapInMain.get(nodeInFather);
            subNodesInMain.put(nodeInFather, nodeInMain);
            fatherNodesInSub.add(nodeInFather);
        }


        for (Node nodeInFather: nodeSetInFather)
        {
            fatherNodesInSub.remove(nodeInFather);
            NodeSet neighbors = fatherGraph.getNeighbors(nodeInFather);
            for (Node neighborInFather : neighbors)
            {
                if(fatherNodesInSub.contains(neighborInFather))
                {
                    addEdge(nodeInFather, neighborInFather);
                }
            }
        }
    }

    @Override
    public Set<MinimalSeparator> getSeps() {
        return seps;
    }

    @Override
    public void setSeps(Set<MinimalSeparator> seps) {
        this.seps = seps;
    }

    @Override
    public Set<Edge> createEdgeSet() {
        HashSet<Edge> edgeSet = new HashSet<>();
        Map<Node, Node> nodeIndsInMain = getNodeMaptoMainGraph();
        int n = getNumberOfNodes();

        for (int nis =0; nis < n; nis++)
        {
            for (int ois = nis +1; ois < n; ois++)
            {
                Node u = new Node(nis);
                Node v = new Node(ois);
                if (areNeighbors(u, v))
                {
                    Edge e = new Edge();

                    Node e0 = nodeIndsInMain.get(nis);
                    Node e1 = nodeIndsInMain.get(ois);
                    if (e0.intValue() > e1.intValue())
                    {
                        e.add(e1);
                        e.add(e0);
                    }
                    else
                    {
                        e.add(e0);
                        e.add(e1);
                    }

                    edgeSet.add(e);
                }
            }
        }

        return edgeSet;
    }

    @Override
    public Map<Node, Node> getNodeMaptoMainGraph() {
        return nodeMapToMainGraph;
    }

    @Override
    public Set<MinimalSeparator> createNewSepGroup(Map<Node, Node>  subNodesInFather, MinimalSeparator excludeSep,
                                                   Set<MinimalSeparator> sepsInFatherGraph) {
        Set<MinimalSeparator> sepsOfSub = new HashSet<>();
        HashMap<Node, Boolean> nodeInSubNodes = new HashMap<>();
        for (Node v : subNodesInFather.keySet())
        {
            nodeInSubNodes.put(v, true);
        }
        HashMap<Node, Node> fatherNodesMapToSub = new HashMap<>();
        for (Node v : subNodesInFather.keySet())
        {
            fatherNodesMapToSub.put(subNodesInFather.get(v),v);
        }

        for (MinimalSeparator sepInFather : sepsInFatherGraph)
        {
            if (sepInFather.equals(excludeSep))
            {
                continue;
            }
            boolean allNodesInSubNodes = true;
            for (Node nodeInSep : sepInFather)
            {
                if (!nodeInSubNodes.get(nodeInSep))
                {
                    allNodesInSubNodes = false;
                    break;
                }
            }
            if (allNodesInSubNodes)
            {
                MinimalSeparator sepInSub = new MinimalSeparator();
                sepInSub.addAll(fatherNodesMapToSub.values());
                sepsOfSub.add(sepInSub);

            }


        }
        return sepsOfSub;
    }

    @Override
    public void print() {
        System.out.println(this.toString());
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < nodeMapToMainGraph.size(); i++)
        {
            sb.append("[ ").append(i).append(" - ").append(nodeMapToMainGraph.get(i)).append(" ]")
                    .append(System.lineSeparator());
        }
        return sb.toString();
    }
}
