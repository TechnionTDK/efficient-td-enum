package tdenum.graph.graphs;

import tdenum.graph.data_structures.*;
import tdenum.graph.graphs.interfaces.IChordalGraph;
import tdenum.graph.graphs.interfaces.IGraph;

import java.util.*;

/**
 * Created by dvir.dukhan on 7/6/2017.
 */
public class ChordalGraph extends Graph implements IChordalGraph
{


    public ChordalGraph()
    {
        super();
    }

    public ChordalGraph(final IGraph graph)
    {
        super(graph);
    }

    public ChordalGraph(int numberOfNodes)
    {
        super(numberOfNodes);
    }


    @Override
    public Set<NodeSet> getMaximalCliques()
    {
        Set<NodeSet> cliques = new HashSet<>();
        TdMap<Boolean> isVisited = new TdListMap<>(getNumberOfNodes(), false);
        IncreasingWeightedNodeQueue queue = new IncreasingWeightedNodeQueue(nodes);
        int previousNumberOfNeighbors = -1;
        Node previousNode = new Node(-1);

        while (!queue.isEmpty())
        {
            Node currentNode = queue.top();
            int currentNumberOfNeighbors = queue.getWeight(currentNode);
            queue.pop();
            if (currentNumberOfNeighbors <= previousNumberOfNeighbors)
            {
                NodeSetProducer cliqueProducer = new NodeSetProducer(getNumberOfNodes());
                cliqueProducer.insert(previousNode);
//                for (Node v : getNeighborsCopy(previousNode))
                for (Node v : getNeighbors(previousNode))
                {
                    if (isVisited.get(v))
                    {
                        cliqueProducer.insert(v);
                    }
                }
                cliques.add(cliqueProducer.produce());
            }

//            for (Node v : getNeighborsCopy(currentNode))
            for (Node v : getNeighbors(currentNode))
            {
                if (!isVisited.get(v))
                {
                    queue.increaseWeight(v);
                }
            }
            isVisited.put(currentNode, true);
            previousNumberOfNeighbors = currentNumberOfNeighbors;
            previousNode = currentNode;
        }
        NodeSetProducer cliqueProducer = new NodeSetProducer(getNumberOfNodes());
        cliqueProducer.insert(previousNode);
//        for (Node v : getNeighborsCopy(previousNode))
        for (Node v : getNeighbors(previousNode))
        {
            cliqueProducer.insert(v);
        }
        cliques.add(cliqueProducer.produce());

        return cliques;
    }

    @Override
    public List<Edge> getFillEdges(IGraph origin)
    {
        List<Edge> edges = new ArrayList<>();
        for (Node v : nodes)
        {
//            for (Node u : getNeighborsCopy(v))
            for (Node u : getNeighbors(v))
            {
                if (v.intValue() < u.intValue() && !origin.areNeighbors(v, u))
                {
                    Edge edge = new Edge(v,u);
//                    edge.add(v);
//                    edge.add(u);
                    edges.add(edge);
                }
            }
        }
        return edges;
    }

    @Override
    public int getFillIn(IGraph origin)
    {
        return getNumberOfEdges() - origin.getNumberOfEdges();
    }

    @Override
    public int getTreeWidth()
    {
        Set<NodeSet> maximalCliques = getMaximalCliques();
        int maxSize = 0;

        for (NodeSet set : maximalCliques)
        {
            maxSize = maxSize < set.size() ? set.size() : maxSize;
        }

        return maxSize - 1;
    }

    @Override
    public long getExpBagsSize()
    {
        Set<NodeSet> maximalCliques = getMaximalCliques();
        long result = 0;

        for (NodeSet set : maximalCliques)
        {
            result += Math.pow(2, set.size());
        }

        return result;

    }

    @Override
    public void printTriangulation(final IGraph origin)
    {
        for (Edge edge : getFillEdges(origin))
        {
            Node u = edge.getKey();
            Node v = edge.getValue();
            StringBuilder sb = new StringBuilder().append(u).append("-").append(v);
            System.out.println(sb);
        }

    }

    @Override
    public void printMaximumClique()
    {
        Set<NodeSet> maximalCliques = getMaximalCliques();
        int maxSize = 0;
        for (NodeSet set : maximalCliques)
        {
            maxSize = maxSize < set.size() ? set.size() : maxSize;
        }

        for (NodeSet set : maximalCliques)
        {
            if (set.size() == maxSize)
            {
                System.out.println(set);
            }
        }
    }


    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder("ChordalGraph{");
        sb.append("numberOfNodes=").append(numberOfNodes);
        sb.append(", numberOfEdges=").append(numberOfEdges);
        sb.append(", nodes=").append(nodes);
        sb.append(", neighborSets=").append(neighborSets);
        sb.append('}');
        return sb.toString();
    }



}
