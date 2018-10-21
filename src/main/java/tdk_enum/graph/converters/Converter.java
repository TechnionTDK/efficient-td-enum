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
        IncreasingWeightedNodeQueue queue = new IncreasingWeightedNodeQueue(g.accessVertices());
        int previousNumberOfNeighbors = -1;
        while (!queue.isEmpty())
        {
            Node currentNode = queue.peek();
            int currentNumberOfNeighbors = queue.getWeight(currentNode);
            queue.poll();
            if (currentNumberOfNeighbors <= previousNumberOfNeighbors)
            {
                NodeSetProducer separatorProducer = new NodeSetProducer(g.getNumberOfNodes());
//                for (Node v : g.accessNeighbors(currentNode))
                for (Node v : g.accessNeighbors(currentNode))
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
//            for (Node v : g.accessNeighbors(currentNode))
            for (Node v : g.accessNeighbors(currentNode))
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
            decompositionNode.setId(id);
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
                int weight = (int) u.accessItemList().stream().filter(v.accessItemList()::contains).count();
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
        treeDecomposition.update();



        return treeDecomposition;
    }

    static void makeDirectedTD(ITreeDecomposition treeDecomposition)
    {
        Map<Node, Integer> ranks = new HashMap<>();

        DecompositionNode root= null;
        for (Node bagId : treeDecomposition.accessVertices())
        {
            ranks.put(bagId, treeDecomposition.accessNeighbors(bagId).size());

        }
        Map<Node, Integer> ranksCopy = new HashMap<>(ranks);
        while(ranks.size()>0)
        {
            if(ranks.size() ==1)
            {
                for(Node bagId : ranks.keySet())
                {
                    DecompositionNode node = treeDecomposition.getBag(bagId);
                    //node.setParent(new Node(-1));
                    ranksCopy.remove(bagId);
                }
            }

            if(ranks.size() == 2)
            {
                Iterator<Node> iterator = ranks.keySet().iterator();
                DecompositionNode ubag = treeDecomposition.getBag(iterator.next());
                DecompositionNode vBag = treeDecomposition.getBag(iterator.next());

                ubag.addChild(vBag);
                vBag.setParent(ubag);
                ranksCopy.remove(new Node(ubag.getID()));
                ranksCopy.remove(vBag.getID());
                root = ubag;

            }

            if(ranks.size()>2)
            {
                for(Node bagId : ranks.keySet())
                {
                    if (ranks.get(bagId)==1)
                    {
                        DecompositionNode bag = treeDecomposition.getBag(bagId);
                        Node parentId  = new Node(-1);
                        if(treeDecomposition.accessNeighbors(bagId).size() ==1)
                        {
                            parentId = treeDecomposition.accessNeighbors(bagId).iterator().next();

                        }
                        else
                        {
                            for (Node neighbor : treeDecomposition.accessNeighbors(bagId))
                            {
                                if (!bag.getChildrenList().stream().anyMatch(decompositionNode -> decompositionNode.getID()==neighbor.intValue()))
                                {
                                    parentId = neighbor;
                                }
                            }
                        }

                        DecompositionNode parent = treeDecomposition.getBag(parentId);
                        parent.addChild(treeDecomposition.getBag(bagId));
                        bag.setParent(parent);
                        ranksCopy.remove(bagId);
                        ranksCopy.put(parentId, ranks.get(parentId)-1);
                        root = parent;
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
        DecompositionNode root = treeDecomposition.getRoot();
        List<DecompositionNode> bags = new ArrayList<>();
        List<Edge> edges = new ArrayList<>();




        DecompositionNode niceRoot = makeNiceDecompositionRoot(root, bags, edges, treeDecomposition.accessNodeList());
//        niceTreeDecomposition.setRoot(niceRoot);
//        niceTreeDecomposition.setBags(bags);
        INiceTreeDecomposition niceTreeDecomposition = new NiceTreeDecomposition(niceRoot, bags);
        for(Edge edge :edges)
        {
            niceTreeDecomposition.addEdge(edge.getKey(), edge.getValue());
        }
        return niceTreeDecomposition;

    }


    static DecompositionNode makeNiceDecompositionRoot(DecompositionNode originalBag,
                                                       List<DecompositionNode> niceBags,
                                                       List<Edge> niceEdges,
                                                       List<DecompositionNode> originalBags)
    {

        DecompositionNode niceRoot = new DecompositionNode(originalBag.getItemList());
        niceRoot.setId(niceBags.size());
        niceBags.add(niceRoot);
        niceRoot.setChildren(originalBag.accessChildrenList());
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
            List<DecompositionNode> originalChildren = niceBag.getChildrenList();
            if (originalChildren.size()==1)
            {
                int childId = originalChildren.iterator().next().getID();
                DecompositionNode originalChildBag = originalBags.get(childId);
                DecompositionNode niceChildBag = new DecompositionNode(originalChildBag.getItemList());
                niceChildBag.setChildren(originalChildBag.accessChildrenList());
                makeForgetIntroducePath(niceBag, niceChildBag, niceBags, niceEdges);
                makeNiceDecompositionNode(niceChildBag, niceBags, niceEdges, originalBags);
            }
            else if (originalChildren.size()==2)
            {
                Iterator<DecompositionNode> iterator = originalChildren.iterator();
                int leftOriginalChild =  iterator.next().getID();
                int rightOriginalChild = iterator.next().getID();
                DecompositionNode leftOriginalChildBag = originalBags.get(leftOriginalChild);
                DecompositionNode rightOriginalChildBag = originalBags.get(rightOriginalChild);

                DecompositionNode niceLeftChild = new DecompositionNode(leftOriginalChildBag.getItemList());
                niceLeftChild.setChildren(leftOriginalChildBag.accessChildrenList());
                DecompositionNode niceRightChild = new DecompositionNode(rightOriginalChildBag.getItemList());
                niceRightChild.setChildren(rightOriginalChildBag.accessChildrenList());

                if (! (niceBag.contentEquals(niceLeftChild) && niceBag.contentEquals(niceRightChild)))
                {
                    makeJoinBag(niceBag, niceLeftChild, niceRightChild, niceBags, niceEdges);
                }
                makeNiceDecompositionNode(niceLeftChild, niceBags, niceEdges, originalBags);
                makeNiceDecompositionNode(niceRightChild, niceBags, niceEdges, originalBags);
            }
            else if (originalChildren.size() >2)
            {
                DecompositionNode leftOriginalChild = originalChildren.get(0);
                originalChildren.remove(0);
                DecompositionNode leftCopy = new DecompositionNode(niceBag.getItemList());
                leftCopy.addChild(leftOriginalChild);
                DecompositionNode rightCopy = new DecompositionNode(niceBag.getItemList());
                rightCopy.setChildren(originalChildren);
                niceBag.accessChildrenList().clear();
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


        List<DecompositionNode> newChildren = new ArrayList<>();
        if(! niceBag.contentEquals(niceLeftChild))
        {
            DecompositionNode leftCopy = new DecompositionNode(niceBag.getItemList());
            makeNewNiceBagAndSetParent(niceBag, leftCopy, niceBags, niceEdges);
            newChildren.add(leftCopy);
            makeForgetIntroducePath(leftCopy, niceLeftChild, niceBags, niceEdges);
        }
        else
        {
            makeNewNiceBagAndSetParent(niceBag, niceLeftChild, niceBags, niceEdges);

            newChildren.add(niceLeftChild);
        }

        if (! niceBag.contentEquals(niceRightChild))
        {
            DecompositionNode rightCopy = new DecompositionNode(niceBag.getItemList());
            makeNewNiceBagAndSetParent(niceBag, rightCopy, niceBags, niceEdges);
            newChildren.add(rightCopy);
            makeForgetIntroducePath(rightCopy, niceRightChild, niceBags, niceEdges);
        }
        else
        {
            makeNewNiceBagAndSetParent(niceBag, niceRightChild, niceBags, niceEdges);

            newChildren.add(niceRightChild);
        }


        niceBag.setChildren(newChildren);
//        niceBag.updateNeighbors();




    }

    static void makeNewNiceBagAndSetParent(DecompositionNode parent,
                                           DecompositionNode child,
                                           List<DecompositionNode> bags,
                                           List<Edge> edges)
    {
        child.setId(bags.size());
        bags.add(child);
        child.setParent(parent);
        parent.addChild(child);
        edges.add(new Edge(parent.getID(), child.getID()));
    }


    private static void makeForgetIntroducePath(DecompositionNode niceSourceBag,
                                                DecompositionNode niceDestinationBag,
                                                List<DecompositionNode> niceBags,
                                                List<Edge> niceEdges) {
        if(niceSourceBag.contentEquals(niceDestinationBag))
        {

            return;
        }
        niceSourceBag.accessChildrenList().clear();
        DecompositionNode intersectionBag = new DecompositionNode(niceSourceBag.getItemList());
        intersectionBag.accessItemList().retainAll(niceDestinationBag.accessItemList());
        if(intersectionBag.contentEquals(niceSourceBag))
        {
            //make forget vertices
            if(niceSourceBag.accessItemList().size() == (niceDestinationBag.accessItemList().size()-1))
            {
                makeNewNiceBagAndSetParent(niceSourceBag, niceDestinationBag, niceBags, niceEdges);
                return;
            }
            else
            {
                for (int i = 0; i < niceDestinationBag.accessItemList().size(); i++)
                {
                    if (!niceSourceBag.accessItemList().contains(niceDestinationBag.accessItemList().get(i)))
                    {
                        DecompositionNode intermediateNode = new DecompositionNode(niceSourceBag.getItemList());
                        intermediateNode.accessItemList().add(niceDestinationBag.accessItemList().get(i));
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
            if (niceSourceBag.accessItemList().size() != (intersectionBag.accessItemList().size()+1))
            {
                DecompositionNode sourceNodesNotInIntersection = new DecompositionNode(niceSourceBag.getItemList());
                sourceNodesNotInIntersection.accessItemList().removeAll(intersectionBag.accessItemList());
                for (int i= 0; i < sourceNodesNotInIntersection.accessItemList().size()-1; i++)
                {
                    intersectionBag.accessItemList().add(sourceNodesNotInIntersection.accessItemList().get(i));
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
        return bag.accessChildrenList().size()==0;
    }

}
