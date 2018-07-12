package tdk_enum.graph.separators.single_thread;

import tdk_enum.enumerators.AlgorithmStep;
import tdk_enum.graph.data_structures.*;
import tdk_enum.graph.data_structures.weighted_queue.single_thread.WeightedNodeSetQueue;
import tdk_enum.graph.graphs.IGraph;
import tdk_enum.graph.separators.AbstractMinimalSeparatorsEnumerator;
import tdk_enum.graph.separators.scorer.single_thread.SeparatorScorer;
import tdk_enum.graph.separators.SeparatorsScoringCriterion;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import static tdk_enum.enumerators.AlgorithmStep.ITERATING_NODES;

/**
 * Created by dvird on 17/07/10.
 */
public class MinimalSeparatorsEnumerator extends AbstractMinimalSeparatorsEnumerator
{




    public MinimalSeparatorsEnumerator()
    {
        //stepByStepDoFirstStep();
    }


    public MinimalSeparatorsEnumerator(IGraph g, SeparatorsScoringCriterion c)
    {
        graph = g;
        scorer = new SeparatorScorer(g, c);
//        separatorsToExtend = new WeightedNodeSetQueue();
//        separatorsExtended = new NodeSetSet();
////        init();
//        for (Node v : g.getNodes())
//        {
//            Set<Node> vAndNeighbors = graph.getNeighborsCopy(v);
//            vAndNeighbors.add(v);
//            for (NodeSet nodeSet : graph.getComponents(vAndNeighbors))
//            {
//                NodeSet potentialSeparator = graph.getNeighbors(nodeSet);
//                if (potentialSeparator.size() >0)
//                {
//                    int score = scorer.scoreSeparator(potentialSeparator);
//                    separatorsToExtend.setWeight( potentialSeparator, score);
//                }
//            }
//        }

       // stepByStepDoFirstStep();

//        System.out.println("seperators number" + separatorsToExtend.size());
//        System.out.println("seperators " + separatorsToExtend.getKeys());
    }
//    @Override
//    public MinimalSeparator next()
//    {
//        if (!hasNext())
//        {
//            return new MinimalSeparator();
//        }
//        MinimalSeparator s =  new MinimalSeparator((NodeSet) separatorsToExtend.poll());
//        separatorsExtended.add(s);
////        System.out.println("poped " + s);
////        System.out.println("separatorsToExtend size " + separatorsToExtend.size());
//        for (Node x : s)
//        {
//            Set<Node> xNeighborsAndS = graph.getNeighborsCopy(x);
//            xNeighborsAndS.addAll(s);
////            System.out.println("for " + xNeighborsAndS + "componenets are " + graph.getComponents(xNeighborsAndS));
//            for (NodeSet nodeSet : graph.getComponents(xNeighborsAndS))
//            {
////                NodeSet ns = new NodeSet(nodeSet.stream().sorted().collect(Collectors.toList()));
//                minimalSeparatorFound(graph.getNeighbors(nodeSet));
//            }
//        }
//
//        return s;
//    }
//
//
//
//    @Override
//    public boolean hasNext()
//    {
//        return !separatorsToExtend.isEmpty();
//    }

//    @Override
//    public <T extends NodeSet> void minimalSeparatorFound(final T s)
//    {
////        System.out.println("trying for " + s + "size = " + s.size() + " extended = " + separatorsExtended.contains(s));
//
//        if (s.size() > 0 && !separatorsExtended.contains(s))
//        {
//            int score = scorer.scoreSeparator(s);
////            System.out.println("pushed " + s);
//            separatorsToExtend.setWeight( s, score);
//
//        }
//    }


//
//    @Override
//    public void init()
//    {
//        for (Node v : graph.getNodes())
//        {
//            Set<Node> vAndNeighbors = graph.getNeighborsCopy(v);
//            vAndNeighbors.add(v);
//            for (NodeSet nodeSet : graph.getComponents(vAndNeighbors))
//            {
//                NodeSet potentialSeparator = graph.getNeighbors(nodeSet);
//                minimalSeparatorFound(potentialSeparator);
//            }
//        }
//    }




    /****************************************************************************************************/


    @Override
    protected void doFirstStep()
    {

        for (Node v : graph.getNodes())
        {
            Set<Node> vAndNeighbors = graph.getNeighborsCopy(v);
            vAndNeighbors.add(v);
            for (NodeSet nodeSet : graph.getComponents(vAndNeighbors))
            {
                MinimalSeparator potentialSeparator = new MinimalSeparator(graph.getNeighbors(nodeSet));
                newResultFound(potentialSeparator);
            }
        }
    }

    @Override
    public void stepByStepDoFirstStep()
    {
        V = graph.getNodes();
        nodesIterator = V.iterator();
        currentEnumResult = new MinimalSeparator();
        step = ITERATING_NODES;
    }

    @Override
    protected void changeVIfNeeded()
    {
        V = currentEnumResult;
    }



    @Override
    protected void tryGenerateNewResult(Node node, MinimalSeparator result)
    {
        Set<Node> xNeighborsAndS = graph.getNeighborsCopy(node);
        xNeighborsAndS.addAll(result);
        for (NodeSet nodeSet : graph.getComponents(xNeighborsAndS))
        {
            newResultFound(new MinimalSeparator(graph.getNeighbors(nodeSet)));
        }
    }




    @Override
    protected boolean finishCondition() {
        return Thread.currentThread().isInterrupted();
    }



    @Override
    protected MinimalSeparator manipulateNodeAndResult(Node node, MinimalSeparator result) {
        return null;
    }


    @Override
    protected void newResultFound(MinimalSeparator sep)
    {
        if (sep.size() > 0 && !P.contains(sep))
        {
            Q.add(sep);

        }
    }


    @Override
    protected Boolean newStepByStepResultFound(MinimalSeparator sep)
    {
        if (sep.size() >0 )
        {
            return super.newStepByStepResultFound(sep);
        }
        else
            return false;
    }


    Collection<NodeSet> componenets;
    Iterator<NodeSet> componenetsIterator;
    boolean isIteratingComponenets =false;


    @Override
    protected boolean iteratingNodesPhase()
    {
        if(isIteratingComponenets)
        {
            if(iteratingComponents())
                return true;

            isIteratingComponenets =false;
        }
        while (nodesIterator.hasNext() && !finishCondition()) {
            isIteratingComponenets = true;
            currentNode = nodesIterator.next();
            if (stepByStepTryGenerateNewResultFromNode(currentNode))
                return true;

            isIteratingComponenets = false;
        }
        return false;
    }

    boolean iteratingComponents()
    {
        while(componenetsIterator.hasNext() && !finishCondition())
        {
            NodeSet componenet = componenetsIterator.next();
            if(newStepByStepResultFound(new MinimalSeparator(graph.getNeighbors(componenet))))
                return true;
        }
        return false;
    }


    @Override
    protected boolean stepByStepTryGenerateNewResultFromNode(Node node)
    {
        Set<Node> xNeighborsAndS = graph.getNeighborsCopy(node);
        xNeighborsAndS.add(node);
        xNeighborsAndS.addAll(currentEnumResult);
        componenets = graph.getComponents(xNeighborsAndS);
        componenetsIterator = componenets.iterator();
        if (iteratingComponents())
            return true;
        return false;
    }







}
