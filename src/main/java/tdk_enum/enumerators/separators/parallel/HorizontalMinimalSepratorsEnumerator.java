package tdk_enum.enumerators.separators.parallel;

import tdk_enum.graph.data_structures.MinimalSeparator;
import tdk_enum.graph.data_structures.Node;
import tdk_enum.graph.graphs.IGraph;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class HorizontalMinimalSepratorsEnumerator extends ParallelMinimalSeparatorsEnumerator {


    protected Set<MinimalSeparator> workingSet = ConcurrentHashMap.newKeySet();

    protected Set<MinimalSeparator> storageSet = ConcurrentHashMap.newKeySet();



    public HorizontalMinimalSepratorsEnumerator()
    {
        super();

    }

    public HorizontalMinimalSepratorsEnumerator(IGraph graph) {
        super();
        this.graph = graph;


//        for (Node v : this.graph.getNodes())
//        {
//            Set<Node> vAndNeighbors = graph.getNeighborsCopy(v);
//            vAndNeighbors.add(v);
//            for (NodeSet nodeSet : graph.getComponents(vAndNeighbors))
//            {
//                NodeSet potentialSeparator = graph.getNeighbors(nodeSet);
//                if (potentialSeparator.size() >0)
//                {
//                    separatorsToExtend.add(potentialSeparator);
//                }
//            }
//        }
    }

//
//    @Override
//    protected void iteratingNodePhase()
//    {
//        V.parallelStream().anyMatch(node-> {tryGenerateNewResult(node, currentEnumResult); return finishCondition();});
//    }



    @Override
    public void executeAlgorithm() {
        mainThread = Thread.currentThread();
        doFirstStep();
        while(!storageSet.isEmpty() && !finishCondition())
        {
            iteratingNodePhase();



        }

    }



    @Override
    protected void iteratingNodePhase() {
        workingSet = storageSet;
        storageSet = ConcurrentHashMap.newKeySet();
        workingSet.parallelStream().anyMatch(minimalSeparator -> {

            for(Node v : minimalSeparator)
            {
                if(finishCondition())
                {
                    return true;
                }
                tryGenerateNewResult(v, minimalSeparator);
            }
            P.add(minimalSeparator);
            return finishCondition();
        });


    }


    @Override
    protected void newResultFound(final MinimalSeparator generatedSep)
    {

        if (!P.contains(generatedSep) && !workingSet.contains(generatedSep))
        {
            if(storageSet.add(generatedSep))
            {
                resultPrinter.print(generatedSep);
            }

        }
    }


}
