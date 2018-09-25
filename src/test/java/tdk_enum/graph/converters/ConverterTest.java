package tdk_enum.graph.converters;

import org.junit.Test;
import tdk_enum.graph.data_structures.Node;
import tdk_enum.graph.data_structures.NodeSet;
import tdk_enum.graph.graphs.tree_decomposition.INiceTreeDecomposition;
import tdk_enum.graph.graphs.tree_decomposition.ITreeDecomposition;
import tdk_enum.graph.graphs.tree_decomposition.single_thread.DecompositionNode;
import tdk_enum.graph.graphs.tree_decomposition.single_thread.NiceTreeDecomposition;
import tdk_enum.graph.graphs.tree_decomposition.single_thread.TreeDecomposition;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

public class ConverterTest {

    @Test
    public void testSimpleForgetPath()
    {
        NodeSet source = new NodeSet();
        for (int i = 0; i < 3; i++)
        {
            source.add(new Node(i));
        }
        DecompositionNode sourceBag = new DecompositionNode(source);
        sourceBag.setBagId(new Node(0));

        NodeSet destination = new NodeSet();
        for (int i = 0; i < 5; i++)
        {
            destination.add(new Node(i));
        }

        DecompositionNode destinationBag = new DecompositionNode(destination);
        destinationBag.setBagId(new Node(1));
        sourceBag.addChild(new Node(1));
        destinationBag.setParent(new Node(0));

        ArrayList<DecompositionNode> bags = new ArrayList();
        bags.add(sourceBag);
        bags.add(destinationBag);
        TreeDecomposition treeDecomposition = new TreeDecomposition();
        treeDecomposition.setBags(bags);
        treeDecomposition.setRoot(new Node(0));

        INiceTreeDecomposition niceTreeDecomposition = Converter.treeDecompositionToNiceTreeDecomposition(treeDecomposition);
        System.out.println(niceTreeDecomposition);


    }

    @Test
    public void testSimpleForgetPath2()
    {
        NodeSet destination = new NodeSet();

        NodeSet source = new NodeSet();
        for (int i = 0; i < 5; i++)
        {
            source.add(new Node(i));
        }

        for (int i = 0; i < 3; i++) {
            destination.add(new Node(i));

        }
        DecompositionNode sourceBag = new DecompositionNode(source);
        sourceBag.setBagId(new Node(0));
        DecompositionNode destinationBag = new DecompositionNode(destination);
        destinationBag.setBagId(new Node(1));
        sourceBag.addChild(new Node(1));
        destinationBag.setParent(new Node(0));

        ArrayList<DecompositionNode> bags = new ArrayList();
        bags.add(sourceBag);
        bags.add(destinationBag);
        TreeDecomposition treeDecomposition = new TreeDecomposition();
        treeDecomposition.setBags(bags);
        treeDecomposition.setRoot(new Node(0));

        INiceTreeDecomposition niceTreeDecomposition = Converter.treeDecompositionToNiceTreeDecomposition(treeDecomposition);
        System.out.println(niceTreeDecomposition);


    }

    @Test
    public void testSimpleForgetPath3()
    {


        NodeSet source = new NodeSet();
        NodeSet destination = new NodeSet();
        for (int i = 0; i < 5; i++)
        {
            source.add(new Node(i));
        }

        for (int i = 0; i < 3; i++) {
            destination.add(new Node(i));

        }
        destination.add(new Node(5));
        destination.add(new Node(6));
        DecompositionNode sourceBag = new DecompositionNode(source);
        sourceBag.setBagId(new Node(0));
        DecompositionNode destinationBag = new DecompositionNode(destination);
        destinationBag.setBagId(new Node(1));
        sourceBag.addChild(new Node(1));
        destinationBag.setParent(new Node(0));

        ArrayList<DecompositionNode> bags = new ArrayList();
        bags.add(sourceBag);
        bags.add(destinationBag);
        TreeDecomposition treeDecomposition = new TreeDecomposition();
        treeDecomposition.setBags(bags);
        treeDecomposition.setRoot(new Node(0));

        INiceTreeDecomposition niceTreeDecomposition = Converter.treeDecompositionToNiceTreeDecomposition(treeDecomposition);
        System.out.println(niceTreeDecomposition);


    }

    @Test
    public void testSimpleJoinNode()
    {
        NodeSet source = new NodeSet();
        NodeSet destination = new NodeSet();
        for (int i = 0; i < 3; i++)
        {
            source.add(new Node(i));
        }

        for (int i = 0; i < 4; i++) {
            destination.add(new Node(i));

        }

        DecompositionNode sourceBag = new DecompositionNode(source);
        sourceBag.setBagId(new Node(0));

        DecompositionNode leftDestinationBag = new DecompositionNode(destination);
        leftDestinationBag.setBagId(new Node(1));
        sourceBag.addChild(new Node(1));
        leftDestinationBag.setParent(new Node(0));

        source.add(new Node(4));
        DecompositionNode rightDestinationBag = new DecompositionNode(source);
        rightDestinationBag.setBagId(new Node(2));
        sourceBag.addChild(new Node(2));
        leftDestinationBag.setParent(new Node(0));

        ArrayList<DecompositionNode> bags = new ArrayList();
        bags.add(sourceBag);
        bags.add(rightDestinationBag);
        bags.add(leftDestinationBag);
        TreeDecomposition treeDecomposition = new TreeDecomposition();
        treeDecomposition.setBags(bags);
        treeDecomposition.setRoot(new Node(0));

        INiceTreeDecomposition niceTreeDecomposition = Converter.treeDecompositionToNiceTreeDecomposition(treeDecomposition);
        System.out.println(niceTreeDecomposition);

    }

    @Test
    public void testMultipleJoinNode()
    {
        NodeSet source = new NodeSet();
        for (int i = 0; i < 3; i++)
        {
            source.add(new Node(i));
        }


        DecompositionNode sourceBag = new DecompositionNode(source);
        sourceBag.setBagId(new Node(0));
        ArrayList<DecompositionNode> bags = new ArrayList();
        bags.add(sourceBag);
        for (int i =3; i <7; i++)
        {
            source.add(new Node(i));
            DecompositionNode destination = new DecompositionNode(source);
            int id = i-2;
            destination.setBagId(new Node(id));
            destination.setBagId(new Node(0));
            sourceBag.addChild(new Node(id));
            bags.add(destination);
            source.remove(new Node(i));
        }

        TreeDecomposition treeDecomposition = new TreeDecomposition();
        treeDecomposition.setBags(bags);
        treeDecomposition.setRoot(new Node(0));

        INiceTreeDecomposition niceTreeDecomposition = Converter.treeDecompositionToNiceTreeDecomposition(treeDecomposition);
        System.out.println(niceTreeDecomposition);
    }

    @Test
    public void testRealCaseNode()
    {
        int[] sourceArray = new int[]{2,3,5,6,8,9,11,13,14,16,24};
        int[] destinationArray = new int[]{6,9,10,11,12,14,18,19,20,24,25};
        NodeSet source = new NodeSet();
        NodeSet destination = new NodeSet();
        for (int i = 0; i < sourceArray.length; i++)
        {
            source.add(new Node(sourceArray[i]));
        }

        for (int i = 0; i< destinationArray.length; i++)
        {
            destination.add(new Node(destinationArray[i]));
        }

        DecompositionNode sourceBag = new DecompositionNode(source);
        sourceBag.setBagId(new Node(0));
        DecompositionNode destinationBag = new DecompositionNode(destination);
        destinationBag.setBagId(new Node(1));
        sourceBag.addChild(new Node(1));
        destinationBag.setParent(new Node(0));

        ArrayList<DecompositionNode> bags = new ArrayList();
        bags.add(sourceBag);
        bags.add(destinationBag);
        TreeDecomposition treeDecomposition = new TreeDecomposition();
        treeDecomposition.setBags(bags);
        treeDecomposition.setRoot(new Node(0));

        INiceTreeDecomposition niceTreeDecomposition = Converter.treeDecompositionToNiceTreeDecomposition(treeDecomposition);
        System.out.println(niceTreeDecomposition);
    }

    @Test
    public void testRealCase2()
    {
        int[] bag0 =new int[]{1, 2, 4, 5, 7, 8, 9, 10, 11, 12, 13, 15, 18, 20, 22, 23, 24, 26};
        int[] bag1 = new int[]{1, 3, 5, 7, 8, 9, 13, 18, 23, 25, 26};
        int[] bag2 = new int[]{1, 2, 4, 5, 6, 7, 8, 9, 10, 12, 13, 15, 18, 20, 22, 23, 24, 26};
        int[] bag3 = new int[]{1, 4, 5, 8, 13, 15, 16, 18, 19, 22, 24};
        int[] bag4 = new int[]{1, 2, 4, 7, 12, 13, 14, 18, 21, 22, 23};
        int[] bag5 = new int[]{1, 2, 4, 5, 7, 8, 9, 10, 12, 13, 15, 18, 20, 21, 22, 23, 24, 26};
        int[] bag6 = new int[]{1, 5, 6, 7, 9, 10, 12, 13, 17, 22, 24};
        int[] bag7 = new int[]{0, 1, 4, 8, 9, 11, 12, 13, 20, 23, 24};
        int[] bag8 = new int[]{1, 2, 4, 5, 7, 8, 9, 10, 12, 13, 15, 16, 18, 20, 22, 23, 24, 26};
        int[] bag9 = new int[]{1, 2, 3, 4, 5, 7, 8, 9, 10, 12, 13, 15, 18, 20, 22, 23, 24, 26};

        ArrayList<DecompositionNode> bags = new ArrayList<>();

        NodeSet nodesArr0 = new NodeSet();
        for (int i : bag0)
        {
            nodesArr0.add(new Node(i));
        }
        DecompositionNode node0 = new DecompositionNode(nodesArr0);
        node0.setBagId(new Node(0));
        bags.add(node0);


        NodeSet nodesArr1 = new NodeSet();
        for (int i : bag1)
        {
            nodesArr1.add(new Node(i));
        }
        DecompositionNode node1 = new DecompositionNode(nodesArr1);
        node1.setBagId(new Node(1));
        bags.add(node1);


        NodeSet nodesArr2 = new NodeSet();
        for (int i : bag2)
        {
            nodesArr2.add(new Node(i));
        }
        DecompositionNode node2 = new DecompositionNode(nodesArr2);
        node2.setBagId(new Node(2));
        bags.add(node2);

        NodeSet nodesArr3 = new NodeSet();
        for (int i : bag3)
        {
            nodesArr3.add(new Node(i));
        }
        DecompositionNode node3 = new DecompositionNode(nodesArr3);
        node3.setBagId(new Node(3));
        bags.add(node3);

        NodeSet nodesArr4 = new NodeSet();
        for (int i : bag4)
        {
            nodesArr4.add(new Node(i));
        }
        DecompositionNode node4 = new DecompositionNode(nodesArr4);
        node4.setBagId(new Node(4));
        bags.add(node4);

        NodeSet nodesArr5 = new NodeSet();
        for (int i : bag5)
        {
            nodesArr5.add(new Node(i));
        }
        DecompositionNode node5 = new DecompositionNode(nodesArr5);
        node5.setBagId(new Node(5));
        bags.add(node5);

        NodeSet nodesArr6 = new NodeSet();
        for (int i : bag6)
        {
            nodesArr6.add(new Node(i));
        }
        DecompositionNode node6 = new DecompositionNode(nodesArr6);
        node6.setBagId(new Node(6));
        bags.add(node6);

        NodeSet nodesArr7 = new NodeSet();
        for (int i : bag7)
        {
            nodesArr7.add(new Node(i));
        }
        DecompositionNode node7 = new DecompositionNode(nodesArr7);
        node7.setBagId(new Node(7));
        bags.add(node7);

        NodeSet nodesArr8 = new NodeSet();
        for (int i : bag8)
        {
            nodesArr8.add(new Node(i));
        }
        DecompositionNode node8 = new DecompositionNode(nodesArr8);
        node8.setBagId(new Node(8));
        bags.add(node8);

        NodeSet nodesArr9 = new NodeSet();
        for (int i : bag9)
        {
            nodesArr9.add(new Node(i));
        }
        DecompositionNode node9 = new DecompositionNode(nodesArr9);
        node9.setBagId(new Node(9));
        bags.add(node9);

        node1.addChild(new Node(0));
        node0.setParent(new Node(1));

        node1.addChild(new Node(2));
        node2.setParent(new Node(1));

        node1.addChild(new Node(4));
        node4.setParent(new Node(1));

        node1.addChild(new Node(5));
        node5.setParent(new Node(1));

        node1.addChild(new Node(6));
        node6.setParent(new Node(1));

        node1.addChild(new Node(7));
        node7.setParent(new Node(1));

        node1.addChild(new Node(8));
        node8.setParent(new Node(1));

        node1.addChild(new Node(3));
        node3.setParent(new Node(1));

        node3.addChild(new Node(9));
        node9.setParent(new Node(3));

        TreeDecomposition treeDecomposition = new TreeDecomposition();
        treeDecomposition.setBags(bags);
        treeDecomposition.setRoot(new Node(1));

        System.out.println(treeDecomposition);

        ITreeDecomposition niceTreeDecomposition = Converter.treeDecompositionToNiceTreeDecomposition(treeDecomposition);
        System.out.println(niceTreeDecomposition);


    }

}