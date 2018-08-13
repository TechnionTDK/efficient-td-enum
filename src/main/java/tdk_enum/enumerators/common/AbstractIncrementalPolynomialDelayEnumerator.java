package tdk_enum.enumerators.common;

import tdk_enum.graph.graphs.succinct_graphs.ISuccinctGraphRepresentation;

import java.util.Iterator;

import static tdk_enum.enumerators.common.AlgorithmStep.BEGINNING;
import static tdk_enum.enumerators.common.AlgorithmStep.ITERATING_NODES;
import static tdk_enum.enumerators.common.AlgorithmStep.ITERATING_RESULTS;

public abstract class AbstractIncrementalPolynomialDelayEnumerator <NodeType, EnumType, GraphType extends ISuccinctGraphRepresentation<NodeType>> extends
        AbstractPolynomialDelayEnumerator <NodeType, EnumType, GraphType> {




    Iterator<EnumType> resultsIterator;


    @Override
    public void executeAlgorithm()
    {
        doFirstStep();
        while(!Q.isEmpty() && !finishCondition())
        {
            currentEnumResult = Q.poll();
            P.add(currentEnumResult);
            changeVIfNeeded();
            iteratingNodePhase();

            while(Q.isEmpty() && graph.hasNextNode() && !finishCondition() )
            {
                getAndSetNextNode();
                iteratingResultsPhase();

            }
        }
    }

    @Override
    protected void iteratingResultsPhase()
    {
        for (EnumType result : P)
        {
            if(finishCondition())
                break;
            tryGenerateNewResult(currentNode, result);

        }
    }

    protected void getAndSetNextNode()
    {
        currentNode = graph.nextNode();
        V.add(currentNode);
    }


    /**********************************************************************************************************/
    @Override
    public boolean hasNext()
    {
        if(!started)
        {
             stepByStepDoFirstStep();
        }
        if (nextResultReady)
        {
            return true;
        }
        else
        {
            if (step == BEGINNING)
            {
                return runStepByStepFullEnumeration();
            }
            else if (step == ITERATING_NODES)
            {
                if(stepByStepIteratingNodesPhase())
                    return true;


                while (Q.isEmpty() && graph.hasNextNode() && !finishCondition())
                {
                   getAndSetNextNode();
                    resultsIterator = P.iterator();
                    if(stepByStepIteratingResultsPhase())
                        return true;
                }
                return  runStepByStepFullEnumeration();
            }
            else if(step == ITERATING_RESULTS)
            {
                while(resultsIterator.hasNext() && !finishCondition())
                {
                    if(stepByStepIteratingResultsPhase())
                        return true;

                }
                while (Q.isEmpty() && graph.hasNextNode() && !finishCondition())
                {
                    getAndSetNextNode();
                    resultsIterator = P.iterator();
                    if(stepByStepIteratingResultsPhase())
                        return true;
                }
                return  runStepByStepFullEnumeration();
            }

        }
        return  false;
    }





    @Override
    protected boolean runStepByStepFullEnumeration() {
        while (!Q.isEmpty() && !finishCondition()) {
            getNextResultToManipulate();
            changeVIfNeeded();
            nodesIterator = V.iterator();
            if (stepByStepIteratingNodesPhase())
                return true;
            while(Q.isEmpty() && graph.hasNextNode() && !finishCondition() )
            {
                currentNode = graph.nextNode();
                V.add(currentNode);
                resultsIterator = P.iterator();
                if (stepByStepIteratingResultsPhase())
                    return true;
            }

        }
        return false;
    }

    @Override
    protected boolean stepByStepIteratingResultsPhase() {
        while (resultsIterator.hasNext() && !finishCondition())
        {
            EnumType result = resultsIterator.next();
            if(stepByStepTryGenerateNewResultFromResult(result)) {
                step = ITERATING_RESULTS;
                return true;
            }
        }
        return false;
    }

    @Override
    protected boolean stepByStepTryGenerateNewResultFromResult(EnumType result) {
        return stepByStepTryGenerateNewResult(currentNode, result);
    }



}
