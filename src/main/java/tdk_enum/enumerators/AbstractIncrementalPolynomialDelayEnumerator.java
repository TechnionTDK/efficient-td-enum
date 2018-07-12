package tdk_enum.enumerators;

import tdk_enum.graph.graphs.succinct_graphs.ISuccinctGraphRepresentation;

import java.util.Iterator;

import static tdk_enum.enumerators.AlgorithmStep.BEGINNING;
import static tdk_enum.enumerators.AlgorithmStep.ITERATING_NODES;
import static tdk_enum.enumerators.AlgorithmStep.ITERATING_RESULTS;

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
            for (NodeType node : V)
            {
                if(finishCondition())
                    break;
                tryGenerateNewResult(node, currentEnumResult);

            }

            while(Q.isEmpty() && graph.hasNextNode() && !finishCondition() )
            {
                NodeType node = graph.nextNode();
                V.add(node);
                for (EnumType result : P)
                {
                    if(finishCondition())
                        break;
                    tryGenerateNewResult(node, result);

                }
            }
        }
    }


    /**********************************************************************************************************/
    @Override
    public boolean hasNext()
    {
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
                while (nodesIterator.hasNext() && !finishCondition())
                {
                    NodeType node = nodesIterator.next();
                    if (stepByStepTryGenerateNewResultFromNode(node)) {
                        return true;
                    }
                }

                while (Q.isEmpty() && graph.hasNextNode() && !finishCondition())
                {
                    currentNode = graph.nextNode();
                    V.add(currentNode);
                    resultsIterator = P.iterator();
                    while (resultsIterator.hasNext() && !finishCondition())
                    {
                        EnumType result = resultsIterator.next();
                        if(stepByStepTryGenerateNewResultFromResult(result))
                            return true;

                    }
                }
                return  runStepByStepFullEnumeration();
            }
            else if(step == ITERATING_RESULTS)
            {
                while(resultsIterator.hasNext() && !finishCondition())
                {
                    EnumType result = resultsIterator.next();
                    if(stepByStepTryGenerateNewResultFromResult(result))
                        return true;
                }
                while (Q.isEmpty() && graph.hasNextNode() && !finishCondition())
                {
                    currentNode = graph.nextNode();
                    V.add(currentNode);
                    resultsIterator = P.iterator();
                    while (resultsIterator.hasNext() && !finishCondition())
                    {
                        EnumType result = resultsIterator.next();
                        if(stepByStepTryGenerateNewResultFromResult(result))
                            return true;

                    }
                }
                return  runStepByStepFullEnumeration();
            }

        }
        return  false;
    }

    @Override
    protected boolean stepByStepTryGenerateNewResultFromResult(EnumType result) {
        return stepByStepTryGenerateNewResult(currentNode, result);
    }
}
