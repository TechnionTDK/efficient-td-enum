package tdk_enum.graph.converters;

import tdk_enum.graph.data_structures.*;
import tdk_enum.graph.data_structures.weighted_queue.single_thread.IncreasingWeightedNodeQueue;
import tdk_enum.graph.graphs.IGraph;
import tdk_enum.graph.graphs.chordal_graph.single_thread.ChordalGraph;
import tdk_enum.graph.graphs.chordal_graph.IChordalGraph;
import tdk_enum.graph.graphs.tree_decomposition.INiceTreeDecomposition;
import tdk_enum.graph.graphs.tree_decomposition.ITreeDecomposition;
import tdk_enum.graph.graphs.tree_decomposition.single_thread.DecompositionNode;
import tdk_enum.graph.graphs.tree_decomposition.single_thread.NiceTreeDecomposition;
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
                if(weight!=0)
                {
                    WeightedEdge edge = new WeightedEdge(i,j,weight);
                    edges.add(edge);
                }



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

        edges = edges.stream().sorted((o1, o2) -> o2.getWeight().compareTo(o1.getWeight())).collect(Collectors.toList());
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
                root = ubag.getBagId();

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
                        ranksCopy.put(parentId, ranks.get(parentId)-1);
                        root = parentId;
                        break;
                    }
                }
            }
            ranks = ranksCopy;
        }



        treeDecomposition.setRoot(root);

    }

    public static INiceTreeDecomposition chordalGraphToNiceTreeDecomposition(IChordalGraph chordalGraph)
    {
        ITreeDecomposition properTreeDecomposition = chordalGraphToProperTreeDecomposition(chordalGraph);
        return treeDecompositionToNiceTreeDecomposition(properTreeDecomposition);
    }

    static INiceTreeDecomposition treeDecompositionToNiceTreeDecomposition(ITreeDecomposition treeDecomposition)
    {
        Node root = treeDecomposition.getRoot();
        List<DecompositionNode> bags = new ArrayList<>();
        List<Edge> edges = new ArrayList<>();



        INiceTreeDecomposition niceTreeDecomposition = new NiceTreeDecomposition();
        DecompositionNode niceRoot = makeNiceDecompositionRoot(root, bags, edges, treeDecomposition.getBags());
        niceTreeDecomposition.setRoot(niceRoot.getBagId());
        niceTreeDecomposition.setBags(bags);
        for(Edge edge :edges)
        {
            niceTreeDecomposition.addEdge(edge.getKey(), edge.getValue());
        }
        return niceTreeDecomposition;

    }


    static DecompositionNode makeNiceDecompositionRoot(Node originalNodeId,
                                                       List<DecompositionNode> niceBags,
                                                       List<Edge> niceEdges,
                                                       List<DecompositionNode> originalBags)
    {
        DecompositionNode originalBag = originalBags.get(originalNodeId.intValue());
        DecompositionNode niceRoot = new DecompositionNode(originalBag);
        niceRoot.setBagId(new Node(niceBags.size()));
        niceBags.add(niceRoot);
        niceRoot.setChildren(originalBag.getChildren());
        makeNiceDecompositionNode(niceRoot, niceBags, niceEdges, originalBags);


        return niceRoot;
    }
    static void makeNiceDecompositionNode(DecompositionNode niceBag,
                                          List<DecompositionNode> niceBags,
                                          List<Edge> niceEdges,
                                          List<DecompositionNode> originalBags)
    {
//        niceBag.setBagId(new Node(niceBags.size()));
//        niceBags.add(niceBag);
        if (!isLeaf(niceBag))
        {
            List<Node> originalChildren = new ArrayList<>(niceBag.getChildren());
            if (originalChildren.size()==1)
            {
                Node childId = originalChildren.iterator().next();
                DecompositionNode originalChildBag = originalBags.get(childId.intValue());
                DecompositionNode niceChildBag = new DecompositionNode(originalChildBag);
                niceChildBag.setChildren(originalChildBag.getChildren());
                makeForgetIntroducePath(niceBag, niceChildBag, niceBags, niceEdges);
                makeNiceDecompositionNode(niceChildBag, niceBags, niceEdges, originalBags);
            }
            else if (originalChildren.size()==2)
            {
                Iterator<Node> iterator = originalChildren.iterator();
                Node leftOriginalChild =  iterator.next();
                Node rightOriginalChild = iterator.next();
                DecompositionNode leftOriginalChildBag = originalBags.get(leftOriginalChild.intValue());
                DecompositionNode rightOriginalChildBag = originalBags.get(rightOriginalChild.intValue());

                DecompositionNode niceLeftChild = new DecompositionNode(leftOriginalChildBag);
                niceLeftChild.setChildren(leftOriginalChildBag.getChildren());
                DecompositionNode niceRightChild = new DecompositionNode(rightOriginalChildBag);
                niceRightChild.setChildren(rightOriginalChildBag.getChildren());

                if (! (niceBag.contentEquals(niceLeftChild) && niceBag.contentEquals(niceRightChild)))
                {
                    makeJoinBag(niceBag, niceLeftChild, niceRightChild, niceBags, niceEdges);
                }
                makeNiceDecompositionNode(niceLeftChild, niceBags, niceEdges, originalBags);
                makeNiceDecompositionNode(niceRightChild, niceBags, niceEdges, originalBags);
            }
            else if (originalChildren.size() >2)
            {
                Node leftOriginalChild = originalChildren.get(0);
                originalChildren.remove(0);
                DecompositionNode leftCopy = new DecompositionNode(niceBag);
                leftCopy.addChild(leftOriginalChild);
                DecompositionNode rightCopy = new DecompositionNode(niceBag);
                rightCopy.setChildren(originalChildren);
                niceBag.getChildren().clear();
                makeNewNiceBagAndSetParent(niceBag, leftCopy, niceBags, niceEdges);
                makeNewNiceBagAndSetParent(niceBag, rightCopy, niceBags, niceEdges);
                makeNiceDecompositionNode(leftCopy, niceBags, niceEdges, originalBags);
                makeNiceDecompositionNode(rightCopy, niceBags, niceEdges, originalBags);

            }
        }
    }

    private static void makeJoinBag(DecompositionNode niceBag,
                                    DecompositionNode niceLeftChild,
                                    DecompositionNode niceRightChild,
                                    List<DecompositionNode> niceBags,
                                    List<Edge> niceEdges) {


        List<Node> newChildren = new ArrayList<>();
        if(! niceBag.contentEquals(niceLeftChild))
        {
            DecompositionNode leftCopy = new DecompositionNode(niceBag);
            makeNewNiceBagAndSetParent(niceBag, leftCopy, niceBags, niceEdges);
            newChildren.add(leftCopy.getBagId());
            makeForgetIntroducePath(leftCopy, niceLeftChild, niceBags, niceEdges);
        }
        else
        {
            makeNewNiceBagAndSetParent(niceBag, niceLeftChild, niceBags, niceEdges);

            newChildren.add(niceLeftChild.getBagId());
        }

        if (! niceBag.contentEquals(niceRightChild))
        {
            DecompositionNode rightCopy = new DecompositionNode(niceBag);
            makeNewNiceBagAndSetParent(niceBag, rightCopy, niceBags, niceEdges);
            newChildren.add(rightCopy.getBagId());
            makeForgetIntroducePath(rightCopy, niceRightChild, niceBags, niceEdges);
        }
        else
        {
            makeNewNiceBagAndSetParent(niceBag, niceRightChild, niceBags, niceEdges);

            newChildren.add(niceRightChild.getBagId());
        }


        niceBag.setChildren(newChildren);
        niceBag.updateNeighbors();




    }

    static void makeNewNiceBagAndSetParent(DecompositionNode parent,
                                           DecompositionNode child,
                                           List<DecompositionNode> bags,
                                           List<Edge> edges)
    {
        child.setBagId(new Node(bags.size()));
        bags.add(child);
        child.setParent(parent.getBagId());
        parent.addChild(child.getBagId());
        edges.add(new Edge(parent.getBagId(), child.getBagId()));
    }


    private static void makeForgetIntroducePath(DecompositionNode niceSourceBag,
                                                DecompositionNode niceDestinationBag,
                                                List<DecompositionNode> niceBags,
                                                List<Edge> niceEdges) {
        if(niceSourceBag.contentEquals(niceDestinationBag))
        {

            return;
        }
        niceSourceBag.getChildren().clear();
        DecompositionNode intersectionBag = new DecompositionNode(niceSourceBag);
        intersectionBag.retainAll(niceDestinationBag);
        if(intersectionBag.contentEquals(niceSourceBag))
        {
            //make forget nodes
            if(niceSourceBag.size() == (niceDestinationBag.size()-1))
            {
                makeNewNiceBagAndSetParent(niceSourceBag, niceDestinationBag, niceBags, niceEdges);
                return;
            }
            else
            {
                for (int i = 0; i < niceDestinationBag.size(); i++)
                {
                    if (!niceSourceBag.contains(niceDestinationBag.get(i)))
                    {
                        DecompositionNode intermediateNode = new DecompositionNode(niceSourceBag);
                        intermediateNode.add(niceDestinationBag.get(i));
                        makeNewNiceBagAndSetParent(niceSourceBag, intermediateNode, niceBags, niceEdges);
                        makeForgetIntroducePath(intermediateNode, niceDestinationBag, niceBags, niceEdges);
                        return;
                    }
                }
                System.out.println("error");
            }

        }
        else
        {
            if (niceSourceBag.size() != (intersectionBag.size()+1))
            {
                DecompositionNode sourceNodesNotInIntersection = new DecompositionNode(niceSourceBag);
                sourceNodesNotInIntersection.removeAll(intersectionBag);
                for (int i= 0; i < sourceNodesNotInIntersection.size()-1; i++)
                {
                    intersectionBag.add(sourceNodesNotInIntersection.get(i));
                }
            }

            makeNewNiceBagAndSetParent(niceSourceBag, intersectionBag, niceBags, niceEdges);
            makeForgetIntroducePath(intersectionBag, niceDestinationBag, niceBags, niceEdges);
            return;
        }
        System.out.println("done nothing");


    }

    static boolean isLeaf(DecompositionNode bag)
    {
        return bag.getChildren().size()==0;
    }

}
