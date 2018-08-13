package tdk_enum.enumerators.independent_set.parallel;

import tdk_enum.graph.data_structures.weighted_queue.parallel.ConcurrentQueueSet;
import tdk_enum.enumerators.independent_set.single_thread.improvements.ImprovedJvCachingMaximalIndependentSetsEnumerator;

import java.util.concurrent.ConcurrentHashMap;


public class ParallelMaximalIndependentSetsEnumerator<T> extends ImprovedJvCachingMaximalIndependentSetsEnumerator<T> {

    protected Thread mainThread;

    public ParallelMaximalIndependentSetsEnumerator()
    {
        super();
        P = ConcurrentHashMap.newKeySet();
        V = ConcurrentHashMap.newKeySet();
//        setsNotExtended = ConcurrentHashMap.newKeySet();
        Q = new ConcurrentQueueSet<>();

    }

    @Override
    public void executeAlgorithm()
    {
        this.mainThread = Thread.currentThread();
        super.executeAlgorithm();
    }

    @Override
    protected boolean finishCondition() {
        return mainThread.isInterrupted();
    }



    @Override
    protected void iteratingNodePhase()
    {

        V.parallelStream().anyMatch(v->
        {
            tryGenerateNewResult(v, currentEnumResult);
            return finishCondition();
        });
    }


    @Override
    protected void iteratingResultsPhase()
    {
        P.parallelStream().anyMatch(extendedMis ->{ tryGenerateNewResult(currentNode, extendedMis );
            return finishCondition();}
        );
    }


//    @Override
//    public boolean hasNext() {
//        if (nextSetReady)
//        {
//            return true;
//        }
//        else
//        {
//            runFullEnumeration();
//            return false;
//        }
//    }
//
//    @Override
//    public Set<T> next() {
//        if (nextSetReady || hasNext()) {
//            nextSetReady = false;
//            resultPrinter.print(nextIndependentSet);
//            return nextIndependentSet;
//        }
//        return new HashSet<>();
//    }
//
//    @Override
//    public void doFirstStep() {
//        newSetFound(extender.extendToMaxIndependentSet(new HashSet<T>()));
//    }
//
//
//    protected void parallelNewSetFound(final Set<T> generatedSet)
//    {
//
//        if (!P.contains(generatedSet))
//        {
//            if(setsNotExtended.add(generatedSet))
//            {
//                Q.setWeight(generatedSet, scorer.scoreIndependentSet(generatedSet));
//                resultPrinter.print(generatedSet);
//            }
//
//        }
//    }
//
//
//    protected void extendSetInDirectionOfNode(final Set<T> s, final T node)
//    {
//        if (s.contains(node))
//        {
//            return;
//        }
//        Set<T> baseNodes = new HashSet<>();
//        baseNodes.add(node);
//        for (T t : s)
//        {
//            if(!graph.hasEdge(node, t))
//            {
//                baseNodes.add(t);
//            }
//        }
//
//        if(jvCache.add(baseNodes))
//        {
//            Set<T> result =   extender.extendToMaxIndependentSet(baseNodes);
//
//            parallelNewSetFound(result);
//        }
//    }
//
//
//    protected void runFullEnumeration()
//    {
//        mainThread = Thread.currentThread();
//        while(!Q.isEmpty() && !timeLimitReached())
//        {
//            getNextSetToExtend();
//            handleIterationNodePhase();
//
//            while(setsNotExtended.isEmpty() && graph.hasNextNode() && !timeLimitReached())
//            {
//                currentNode = graph.nextNode();
//                V.add(currentNode);
//                handleIterationSetPhase();
//            }
//
//        }
//
//
//    }
//
//    protected void getNextSetToExtend()
//    {
//        Set<T> currentScoredSet = Q.peek();
//        if (scorer.mayScoreChange())
//        {
//
//            int currentScore = scorer.scoreIndependentSet(currentScoredSet);
//            while (currentScore > Q.getWeight(currentScoredSet))
//            {
//                Q.setWeight(currentScoredSet, currentScore);
//                currentScoredSet = Q.peek();
//                currentScore = scorer.scoreIndependentSet(currentScoredSet);
//            }
//        }
//        currentSet = currentScoredSet;
//        scorer.independentSetUsed(currentSet);
//        P.add(currentSet);
//        setsNotExtended.remove(currentSet);
//        Q.poll();
//
//    }





}
