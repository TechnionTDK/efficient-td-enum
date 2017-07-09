package tdenum.graph;

import tdenum.graph.data_structures.IncreasingWeightNodeQueue;
import tdenum.graph.data_structures.NodeSetProducer;
import tdenum.graph.interfaces.IChordalGraph;

import java.util.*;

/**
 * Created by dvir.dukhan on 7/6/2017.
 */
public class ChordalGraph extends Graph implements IChordalGraph {


    public ChordalGraph()
    {
        super();
    }

    public ChordalGraph(final Graph graph)
    {
        super(graph.getNumberOfNodes());
    }



    @Override
    public Set<NodeSet> getMaximalCliques() {
       Set<NodeSet> cliques = new HashSet<>();
       Map<Node, Boolean> isVisited = new HashMap<>();
       IncreasingWeightNodeQueue queue = new IncreasingWeightNodeQueue(getNumberOfNodes());
       int previousNumberOfNeighbors = -1;
       Node previousNode = new Node(-1);

       while(!queue.isEmpty())
       {
           Node currentNode = queue.pop();
           int currentNumberOfNeighbors = queue.getWeight(currentNode);
           if (currentNumberOfNeighbors <= previousNumberOfNeighbors)
           {
               NodeSetProducer cliqueProducer = new NodeSetProducer(getNumberOfNodes());
               cliqueProducer.insert(previousNode);
               for (Node v : getNeighbors(currentNode))
               {
                   if (isVisited.get(v))
                   {
                       cliqueProducer.insert(v);
                   }
               }
               cliques.add(cliqueProducer.produce());
           }

           for (Node v : getNeighbors(currentNode))
           {
               if(!isVisited.get(v))
               {
                   queue.increaseWeight(v);
               }
           }
           isVisited.put(currentNode,true);
           previousNumberOfNeighbors = currentNumberOfNeighbors;
           previousNode = currentNode;
       }
        NodeSetProducer cliqueProducer = new NodeSetProducer(getNumberOfNodes());
        cliqueProducer.insert(previousNode);
        for (Node v : getNeighbors(previousNode))
        {
            cliqueProducer.insert(v);
        }
        cliques.add(cliqueProducer.produce());

       return  cliques;
    }

    @Override
    public List<Edge> getFillEdges(Graph origin) {
        List<Edge> edges = new ArrayList<>();
        for (Node v : nodes)
        {
            for (Node u : getNeighbors(v))
            {
                if (v.intValue() <- u.intValue() && !origin.areNeighbors(v,u))
                {
                    Edge edge = new Edge();
                    edge.add(v);
                    edge.add(u);
                    edges.add(edge);
                }
            }
        }
        return edges;
    }

    @Override
    public int getFillIn(Graph origin) {
        return  getNumberOfEdges() - origin.getNumberOfEdges();
    }

    @Override
    public int getTreeWidht() {
        Set<NodeSet> maximalCliques = getMaximalCliques();
        int maxSize = 0;

        for (NodeSet set : maximalCliques)
        {
            maxSize = maxSize < set.size() ? set.size() : maxSize;
        }

        return maxSize - 1;
    }

    @Override
    public long getExpBagsSize() {
       Set<NodeSet> maximalCliques = getMaximalCliques();
       long result = 0;

       for (NodeSet set : maximalCliques)
       {
           result += Math.pow(2, set.size());
       }

       return result;

    }

    @Override
    public void printTriangulation(final Graph origin) {
        for (Edge edge: getFillEdges(origin))
        {
            Node u = edge.iterator().next();
            Node v = edge.iterator().next();
            StringBuilder sb = new StringBuilder().append(u).append("-").append(v);
            System.out.println(sb);
        }

    }

    @Override
    public void printMaximumClique() {
        Set<NodeSet> maximalCliques = getMaximalCliques();
        int maxSize = 0;
        for (NodeSet set : maximalCliques)
        {
            maxSize = maxSize < set.size() ? set.size() : maxSize;
        }

        for (NodeSet set: maximalCliques)
        {
            if (set.size() == maxSize)
            {
                System.out.println(set);
            }
        }
    }
}
