package tdk_enum.graph.graphs;

import tdk_enum.graph.data_structures.*;
import tdk_enum.graph.data_structures.weighted_queue.single_thread.IncreasingWeightedNodeQueue;
import tdk_enum.graph.graphs.chordal_graph.single_thread.ChordalGraph;
import tdk_enum.graph.graphs.chordal_graph.IChordalGraph;
import tdk_enum.graph.graphs.tree_decomposition.ITreeDecomposition;
import tdk_enum.graph.graphs.tree_decomposition.single_thread.DecompositionNode;
import tdk_enum.graph.graphs.tree_decomposition.single_thread.TreeDecomposition;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by dvird on 17/07/09.
 */
public class Converter
{

    static class DisjointSets
    {
        int n;
        int[] parent;
        int [] rnk;

        public DisjointSets(int n)
        {
            this.n = n;
            parent = new int[n+1];
            rnk = new int[n+1];
            for (int i =0; i<=n; i++)
            {
                rnk[i] = 0;
                parent[i] = i;
            }
        }

        public int find (int u)
        {
            if (u != parent[u])
            {
                parent[u] = find(parent[u]);

            }
            return parent[u];
        }

        public void merge(int x, int y)
        {
            x = find(x);
            y = find(y);
            if (rnk[x] > rnk[y])
            {
                parent[y] = x;
            }
            else
            {
                parent[x] = y;
            }

            if(rnk[x] == rnk[y])
            {
                rnk[y] = rnk[y]+1;
            }
        }
    }


    public static IChordalGraph minimalSeparatorsToTriangulation(final IGraph g,
                                                                 final Set<? extends NodeSet> minimalSeparators)
    {
        IChordalGraph triangulation = new ChordalGraph(g);
        triangulation.saturateNodeSets(minimalSeparators);
        return triangulation;
    }

    public static Set<MinimalSeparator> triangulationToMinimalSeparators(final IGraph g)
    {
        Set<MinimalSeparator> minimalSeparators = new HashSet<>();
        TdMap<Boolean> isVisited = new TdListMap<>(g.getNumberOfNodes(), false);
        IncreasingWeightedNodeQueue queue = new IncreasingWeightedNodeQueue(g.getNodes());
        int previousNumberOfNeighbors = -1;
        while (!queue.isEmpty())
        {
            Node currentNode = queue.peek();
            int currentNumberOfNeighbors = queue.getWeight(currentNode);
            queue.poll();
            if (currentNumberOfNeighbors <= previousNumberOfNeighbors)
            {
                NodeSetProducer separatorProducer = new NodeSetProducer(g.getNumberOfNodes());
//                for (Node v : g.getNeighbors(currentNode))
                for (Node v : g.getNeighbors(currentNode))
                {
                    Boolean iv = isVisited.get(v);
                    if (iv != null && iv)
                    {
                        separatorProducer.insert(v);
                    }
                }
                MinimalSeparator currentSeparator = separatorProducer.produce(MinimalSeparator.class);

                if (!currentSeparator.isEmpty())
                {
                    minimalSeparators.add(currentSeparator);
                }
            }
//            for (Node v : g.getNeighbors(currentNode))
            for (Node v : g.getNeighbors(currentNode))
            {
                Boolean iv = isVisited.get(v);
                if (iv != null && !iv)
                {
                    queue.increaseWeight(v);
                }
            }
            isVisited.put(currentNode, true);
            previousNumberOfNeighbors = currentNumberOfNeighbors;
        }
        return minimalSeparators;

    }


    public static ITreeDecomposition chordalGraphToProperTreeDecomposition(IChordalGraph chordalGraph)
    {
       List<DecompositionNode> bags = new ArrayList<>();
        int id = 0;
        for (NodeSet clique : chordalGraph.getMaximalCliques())
        {
            DecompositionNode decompositionNode = new DecompositionNode(clique);
            decompositionNode.setBagId(new Node(id));
            bags.add(decompositionNode);
            id++;
        }
        List<WeightedEdge> edges = new ArrayList<>();
        for(int i=0; i<bags.size(); i++)
        {
            for (int j = i+1; j < bags.size(); j++)
            {
                DecompositionNode u = bags.get(i);
                DecompositionNode v = bags.get(j);
                int weight = (int) u.stream().filter(v::contains).count();
                WeightedEdge edge = new WeightedEdge(i,j,weight);
                edges.add(edge);


            }
        }

        ITreeDecomposition treeDecomposition = MST(bags, edges);

        makeDirectedTD(treeDecomposition);

        return treeDecomposition;


    }

    static ITreeDecomposition MST(List<DecompositionNode> decompositionNodes, List<WeightedEdge> edges)
    {
        ITreeDecomposition treeDecomposition = new TreeDecomposition(decompositionNodes.size());
        treeDecomposition.setBags(decompositionNodes);

        DisjointSets ds = new DisjointSets(decompositionNodes.size()-1);

        edges = edges.stream().sorted((o1, o2) -> o1.getWeight().compareTo(o2.getWeight())).collect(Collectors.toList());
        for (WeightedEdge edge: edges) {
                Node u = edge.getKey();
                Node v = edge.getValue();

                int set_u = ds.find(u.intValue());
                int set_v = ds.find(v.intValue());

                if (set_u != set_v)
                {
                    treeDecomposition.addEdge(u,v);
                    ds.merge(set_u, set_v);
                }

        }


        return treeDecomposition;
    }

    static void makeDirectedTD(ITreeDecomposition treeDecomposition)
    {
        Map<Node, Integer> ranks = new HashMap<>();

        Node root= new Node(-1);
        for (Node bagId : treeDecomposition.getNodes())
        {
            ranks.put(bagId, treeDecomposition.getNeighbors(bagId).size());

        }
        Map<Node, Integer> ranksCopy = new HashMap<>(ranks);
        while(ranks.size()>0)
        {
            if(ranks.size() ==1)
            {
                for(Node bagId : ranks.keySet())
                {
                    DecompositionNode node = treeDecomposition.getBag(bagId);
                    node.setParent(new Node(-1));
                    ranksCopy.remove(bagId);
                }
            }

            if(ranks.size() == 2)
            {
                Iterator<Node> iterator = ranks.keySet().iterator();
                DecompositionNode ubag = treeDecomposition.getBag(iterator.next());
                DecompositionNode vBag = treeDecomposition.getBag(iterator.next());

                ubag.addChild(vBag.getBagId());
                vBag.setParent(ubag.getBagId());
                ranksCopy.remove(ubag.getBagId());
                ranksCopy.remove(vBag.getBagId());

            }

            if(ranks.size()>2)
            {
                for(Node bagId : ranks.keySet())
                {
                    if (ranks.get(bagId)==1)
                    {
                        DecompositionNode bag = treeDecomposition.getBag(bagId);
                        Node parentId  = new Node(-1);
                        if(treeDecomposition.getNeighbors(bagId).size() ==1)
                        {
                            parentId = treeDecomposition.getNeighbors(bagId).iterator().next();

                        }
                        else
                        {
                            for (Node neighbor : treeDecomposition.getNeighbors(bagId))
                            {
                                if (!bag.getChildren().contains(neighbor))
                                {
                                    parentId = neighbor;
                                }
                            }
                        }

                        DecompositionNode parent = treeDecomposition.getBag(parentId);
                        parent.addChild(bagId);
                        bag.setParent(parentId);
                        ranksCopy.remove(bagId);
                        ranks.put(parentId, ranks.get(parentId)-1);
                        root = parentId;
                    }
                }
            }
            ranks = ranksCopy;
        }



        treeDecomposition.setRoot(root);

    }

}
