package tdenum.graph.graphs.sub_graph.single_thread;

import tdenum.graph.data_structures.*;
import tdenum.graph.graphs.IGraph;
import tdenum.graph.graphs.Graph;
import tdenum.graph.graphs.sub_graph.ISubGraph;

import java.util.*;

/**
 * Created by dvir.dukhan on 7/9/2017.
 */
public class SubGraph extends Graph implements ISubGraph
{

    //Map<Node, Node> nodeMapToMainGraph = new HashMap<>();

    TdMap<Node> nodeMapToMainGraph = new TdListMap<>();

    Set<? extends NodeSet> seps = new HashSet<>();

    IGraph mainGraph = null;

    public void setNodeMapToMainGraph(TdMap<Node> nodeMapToMainGraph) {
        this.nodeMapToMainGraph = nodeMapToMainGraph;
    }

    public TdMap<Node>getNodeMapToMainGraph() {
        return nodeMapToMainGraph;
    }








    public SubGraph(final IGraph mainGraph)
    {
        super(mainGraph);
        this.mainGraph = mainGraph;


        for (Node n : mainGraph.getNodes())
        {
            nodeMapToMainGraph.put(n, n);
        }

    }

    public SubGraph(final ISubGraph fatherGraph, NodeSet nodeSetInFatherGraph)
    {
        super(nodeSetInFatherGraph.size());
        initializeFromFatherSubgraph(fatherGraph, nodeSetInFatherGraph);

    }

    public SubGraph(final ISubGraph fatherGraph, NodeSet nodeSetInFatherGraph, final Set<? extends NodeSet> seps)
    {
        super(nodeSetInFatherGraph.size());
        initializeFromFatherSubgraph(fatherGraph, nodeSetInFatherGraph);
        this.seps = seps;

    }

    public SubGraph(final IGraph mainGraph, final Set<? extends NodeSet> seps)
    {
        super(mainGraph);
        this.mainGraph = mainGraph;
        for (Node n: nodes)
        {
            nodeMapToMainGraph.put(n,n);
        }
        this.seps = seps;
    }

    private void initializeFromFatherSubgraph(final ISubGraph fatherGraph, NodeSet nodeSetInFatherGraph)
    {
        mainGraph = fatherGraph.getMainGraph();
        final TdMap<Node>fatherNodesMapInMain = fatherGraph.getNodeMaptoMainGraph();

        TdMap<Node> subNodesInMain = nodeMapToMainGraph;
        Set<Node> fatherNodesInSub = new HashSet<>();


        for (Node nodeInFather : nodeSetInFatherGraph)
        {
            Node nodeInMain = fatherNodesMapInMain.get(nodeInFather);
            subNodesInMain.put(nodeInFather, nodeInMain);
            fatherNodesInSub.add(nodeInFather);
        }


        for (Node nodeInFather : nodeSetInFatherGraph)
        {
            fatherNodesInSub.remove(nodeInFather);
//            NodeSet neighbors = fatherGraph.getNeighbors(nodeInFather);
//            NodeSet neighbors = fatherGraph.getNeighbors(nodeInFather);
            Set<Node> neighbors = fatherGraph.getNeighbors(nodeInFather);
            for (Node neighborInFather : neighbors)
            {
                if (fatherNodesInSub.contains(neighborInFather))
                {
                    addEdge(nodeInFather, neighborInFather);
                }
            }
        }
    }

    @Override
    public IGraph getMainGraph() {
        return mainGraph;
    }

    @Override
    public Set<? extends NodeSet> getSeps() {
        return seps;
    }

    @Override
    public void setSeps(Set<? extends NodeSet> seps) {
        this.seps = seps;
    }


    @Override
    public Set<Edge> createEdgeSet()
    {
        HashSet<Edge> edgeSet = new HashSet<>();
        TdMap<Node> nodeIndsInMain = getNodeMaptoMainGraph();
        int n = getNumberOfNodes();

        for (int nis = 0; nis < n; nis++)
        {
            for (int ois = nis + 1; ois < n; ois++)
            {
                Node u = new Node(nis);
                Node v = new Node(ois);
                if (areNeighbors(u, v))
                {
                    Edge e;

                    Node e0 = nodeIndsInMain.get(u);
                    Node e1 = nodeIndsInMain.get(v);
                    if (e0.intValue() > e1.intValue())
                    {
                        e = new Edge(e1, e0);

                    } else
                    {
                        e = new Edge(e0, e1);
                    }

                    edgeSet.add(e);
                }
            }
        }

        return edgeSet;
    }

    @Override
    public TdMap<Node> getNodeMaptoMainGraph()
    {
        return nodeMapToMainGraph;
    }

    @Override
    public Set<MinimalSeparator> createNewSepGroup(TdMap<Node> subNodesInFather, MinimalSeparator excludeSep,
                                                   Set<MinimalSeparator> sepsInFatherGraph)
    {
        Set<MinimalSeparator> sepsOfSub = new HashSet<>();
        TdMap<Boolean> nodeInSubNodes = new TdListMap<>();
        for (Node v : subNodesInFather.keySet())
        {
            nodeInSubNodes.put(v, true);
        }
        TdMap<Node> fatherNodesMapToSub = new TdListMap<>();
        for (Node v : subNodesInFather.keySet())
        {
            fatherNodesMapToSub.put(subNodesInFather.get(v), v);
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
    public void print()
    {
        System.out.println(this.toString());
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        for (Node key : getNodeMaptoMainGraph().keySet())
        {

            sb.append("[ ").append(key).append(" - ").append(nodeMapToMainGraph.get(key)).append(" ]")
                    .append(System.lineSeparator());
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof SubGraph)) return false;
        if (!super.equals(o)) return false;

        SubGraph subGraph = (SubGraph) o;

        if (!getNodeMapToMainGraph().equals(subGraph.getNodeMapToMainGraph())) return false;
        if (!getSeps().equals(subGraph.getSeps())) return false;
        return getMainGraph().equals(subGraph.getMainGraph());
    }

    @Override
    public int hashCode()
    {
        int result = super.hashCode();
        result = 31 * result + getNodeMapToMainGraph().hashCode();
        result = 31 * result + getSeps().hashCode();
        result = 31 * result + getMainGraph().hashCode();
        return result;
    }
}
