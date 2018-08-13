package tdk_enum.graph.graphs;

import tdk_enum.graph.data_structures.Node;
import tdk_enum.graph.data_structures.TdListMap;
import tdk_enum.graph.graphs.tree_decomposition.ITreeDecomposition;
import tdk_enum.graph.graphs.tree_decomposition.single_thread.DecompositionNode;

import java.util.*;

public class TreeDecompositionValidator {

    public static boolean isValidDecomposition(ITreeDecomposition t, IGraph g)
    {
        if (!t.isTree())
        {
            System.out.println("Error: Not A Tree");
            return  false;
        }
        else if (!check_vertex_coverage(t, g)) {
            return false;
        }
        else if (!check_edge_coverage(t, g)) {
            return false;
        }
        else if (!check_connectedness(t,g))
        {
            System.out.println("Error: some vertex induces disconnected components in the tree");
            return false;
        }
        return true;


    }

    private static boolean check_connectedness(ITreeDecomposition t, IGraph g) {

        List<DecompositionNode> nodes = new ArrayList<>(t.getBags());
        TdListMap<Boolean> forgotten = new TdListMap<>(g.getNumberOfNodes(), false);
        Stack<Integer> parent_stack = new Stack<>();
        int NIL = t.getNumberOfNodes() +1;
        int next = NIL;
        int head = NIL;
        Stack<Integer> stack = new Stack<>();
        stack.push(0);
        parent_stack.push(NIL);
        while(!stack.isEmpty())
        {
            head = next;
            next = stack.peek();

            if(head == next)
            {
                stack.pop();
                if(head == parent_stack.peek())
                {
                    parent_stack.pop();
                }
                int parent = parent_stack.peek();
                DecompositionNode bag = t.getBag(new Node(head));
                for (Node node: bag) {
                    if (forgotten.get(node)==true)
                    {
                        return false;
                    }
                    if (parent != NIL)
                    {
                        DecompositionNode parentBag = t.getBag(new Node(parent));
                        if(!parentBag.contains(node))
                        {
                            forgotten.put(node,true);
                        }
                    }

                }
                nodes.remove(new Node(head));
            }
            else
            {
                parent_stack.push(next);
                for (Node neighbor : t.getNeighbors(new Node(next)))
                {
                    if (neighbor.intValue() != head && nodes.contains(neighbor))
                    {
                        stack.push(neighbor.intValue());
                    }
                }
            }
        }

        return true;
    }

    private static boolean check_edge_coverage(ITreeDecomposition t, IGraph g) {
        Set<Set<Node>> edges = new HashSet<>();
        for (Node node : g.getNodes())
        {
            for (Node neighbor : g.getNeighbors(node))
            {
                Set<Node> edge = new HashSet<>();
                edge.add(node);
                edge.add(neighbor);
                edges.add(edge);
            }

        }

        for (DecompositionNode bag : t.getBags())
        {
            for (Node node : bag)
            {
                for (Node neighbor : g.getNeighbors(node))
                {
                    Set<Node> edge = new HashSet<>();
                    edge.add(node);
                    edge.add(neighbor);
                    edges.remove(edge);
                }
            }
        }

        if(edges.size() >0)
        {
            for (Set<Node> edge : edges)
            {
                System.out.println("The edge " + edge + " does not appear in any bag");
            }
        }
        return edges.size()==0;
    }

    private static boolean check_vertex_coverage(ITreeDecomposition t, IGraph g) {
        TdListMap<Boolean> seen = new TdListMap<>(g.getNumberOfNodes(), false);
        for (DecompositionNode bag : t.getBags())
        {
            for (Node node : bag)
            {
                seen.put(node, true);
            }
        }
        for (Node node : seen.keySet())
        {
            if (seen.get(node) == false)
            {
                System.out.println("Node " + node + " does not appear in any bag");
            }
        }

        return seen.values().stream().filter(aBoolean -> aBoolean.equals(false)).count() == 0;
    }
}
