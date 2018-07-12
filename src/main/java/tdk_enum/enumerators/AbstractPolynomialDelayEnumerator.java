package tdk_enum.enumerators;

import static tdk_enum.enumerators.AlgorithmStep.BEGINNING;
import static tdk_enum.enumerators.AlgorithmStep.ITERATING_NODES;

public abstract class AbstractPolynomialDelayEnumerator <NodeType, EnumType, GraphType> extends  AbstractEnumerator <NodeType, EnumType, GraphType> {




    @Override
    public void executeAlgorithm()
    {
        doFirstStep();
        while(!Q.isEmpty() && !finishCondition())
        {
            currentEnumResult = Q.poll();
            P.add(currentEnumResult);
            changeVIfNeeded();
            for (NodeType node : V)
            {
                if(finishCondition())
                    break;
                tryGenerateNewResult(node, currentEnumResult);
            }
        }
    }



    @Override
    protected void doFirstStep()
    {
        EnumType a = enumTypeDefaultBuilder.createInstance();
        EnumType c = generator.generateNew(graph, a);
        newResultFound(c);
    }


    @Override
    protected void tryGenerateNewResult(NodeType node, EnumType result)
    {
        EnumType b = manipulateNodeAndResult(node, result);
        EnumType c = generator.generateNew(graph,b);
        newResultFound(c);
    }

    @Override
    protected void newResultFound(EnumType c)
    {
        if (!P.contains(c) && Q.add(c))
        {
            //assume Q checks before insertion
            resultPrinter.print(c);
        }
    }


    /*******************************************************************************************************/
    @Override
    public EnumType next()
    {
        if( nextResultReady || hasNext())
        {
            nextResultReady = false;
            return  nextResult;
        }
        return enumTypeDefaultBuilder.createInstance();
    }


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
                if(iteratingNodesPhase())
                    return true;
                return  runStepByStepFullEnumeration();
            }
            return false;
        }
    }

    @Override
    public void stepByStepDoFirstStep()
    {

        EnumType a = enumTypeDefaultBuilder.createInstance();
        EnumType c = generator.generateNew(graph, a);
        newStepByStepResultFound(c);
        started=true;
        step = BEGINNING;

    }

    @Override
    void getNextResultToManipulate()
    {
        currentEnumResult = Q.poll();
        P.add(currentEnumResult);
    }


    @Override
    protected boolean runStepByStepFullEnumeration() {
        while (!Q.isEmpty() && !finishCondition()) {
            getNextResultToManipulate();
            changeVIfNeeded();
            nodesIterator = V.iterator();
            if (iteratingNodesPhase())
                return true;

        }
        return false;
    }

    @Override
    protected boolean stepByStepTryGenerateNewResultFromNode(NodeType node) {
        return stepByStepTryGenerateNewResult(node, currentEnumResult);
    }


    @Override
    protected boolean stepByStepTryGenerateNewResult(NodeType node, EnumType currentResult) {
        EnumType b = manipulateNodeAndResult(node, currentResult);
        EnumType c = generator.generateNew(graph,b);
        if( newStepByStepResultFound(c))
        {
            step = ITERATING_NODES;
            return true;
        }
        return false;
    }


    @Override
    protected Boolean newStepByStepResultFound(EnumType c)
    {
        //assume that Q checks membership before insertion
        if (!P.contains(c) &&  Q.add(c))
        {
            nextResult = c;
            nextResultReady = true;
            return true;
        }
        return false;
    }

    @Override
    protected  boolean stepByStepTryGenerateNewResultFromResult(EnumType result)
    {
        return false;
    }


    @Override
    protected boolean iteratingNodesPhase()
    {
        while (nodesIterator.hasNext() && !finishCondition()) {
            currentNode = nodesIterator.next();
            if (stepByStepTryGenerateNewResultFromNode(currentNode)) {
                return true;
            }
        }
        return false;
    }


}
