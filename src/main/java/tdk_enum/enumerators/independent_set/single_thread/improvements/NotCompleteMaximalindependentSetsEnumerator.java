package tdk_enum.enumerators.independent_set.single_thread.improvements;

import tdk_enum.graph.graphs.IGraph;
import tdk_enum.graph.graphs.succinct_graphs.ISuccinctGraphRepresentation;
import tdk_enum.enumerators.separators.IMinimalSeparatorsEnumerator;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class NotCompleteMaximalindependentSetsEnumerator<T> extends ImprovedJvCachingMaximalIndependentSetsEnumerator<T> {


    class FakeSuccinctGraphRepresentation<T> implements ISuccinctGraphRepresentation<T>
    {
        ISuccinctGraphRepresentation<T> graph;
        Queue<T> queue;


        public FakeSuccinctGraphRepresentation(ISuccinctGraphRepresentation<T> graph, Set<T> set )
        {
            this.graph = graph;
            queue = new LinkedList<>(set);
        }

        @Override
        public boolean hasNextNode() {
            return !queue.isEmpty();
        }

        @Override
        public T nextNode() {
            return queue.poll();
        }

        @Override
        public Set<T> nextBatch() {
            Set<T> result = new HashSet<>();
            result.add(queue.poll());
            return result;
        }

        @Override
        public boolean hasEdge(T u, T v) {
            return graph.hasEdge(u, v);
        }

        @Override
        public int getNumberOfNodesGenerated() {
            return graph.getNumberOfNodesGenerated();
        }

        @Override
        public void setGraph(IGraph graph) {
            this.graph.setGraph(graph);
        }

        @Override
        public IGraph getGraph() {
            return this.graph.getGraph();
        }

        @Override
        public void setNodesEnumerator(IMinimalSeparatorsEnumerator nodesEnumerator) {

        }

        @Override
        public Set<T> nextBatch(long id) {
            Set<T> result = new HashSet<>();
            result.add(queue.poll());
            return result;
        }

        @Override
        public boolean hasNextNode(long id) {
            return hasNextNode();
        }
    }

    Set<T> V2 = new HashSet<>();
    boolean switched = false;



    @Override
    protected void newResultFound(Set<T> generatedSet)
    {
        if (!P.contains(generatedSet) && Q.add(generatedSet))
        {
            //assume Q checks before insertion
            resultPrinter.print(generatedSet);
            if (!switched)
            {
                V2.addAll(generatedSet);
            }
        }
    }


    @Override
    protected boolean newStepByStepResultFound(Set<T> generatedSet)
    {

        if (!P.contains(generatedSet) &&  Q.add(generatedSet))
        {
            nextResult = generatedSet;
            nextResultReady = true;
            if (!switched)
            {
                V2.addAll(generatedSet);
            }
            return true;
        }
        return false;
    }





    @Override
    protected void getAndSetNextNode()
    {
        currentNode = graph.nextNode();
        if(!switched)
        {
            findNextNewNode();
        }

        V.add(currentNode);
    }

    protected void findNextNewNode()
    {
        if (V2.contains(currentNode))
        {
            boolean hasNext = graph.hasNextNode();
            while(V2.contains(currentNode) && hasNext && !finishCondition())
            {
                currentNode = graph.nextNode();
                hasNext = graph.hasNextNode();
            }
            if (finishCondition())
            {
                return;
            }


            else if (!hasNext)
            {
                graph = new FakeSuccinctGraphRepresentation<>(graph, V2);
                switched = true;
                V2 = new HashSet<>();
            }
        }
    }



//    @Override
//    protected boolean newSetFound(final Set<T> generatedSet)
//    {
//
//
//        if (!P.contains(generatedSet))
//        {
//            if(setsNotExtended.add(generatedSet))
//            {
//                Q.setWeight(generatedSet, scorer.scoreIndependentSet(generatedSet));
//
//                nextIndependentSet = generatedSet;
//                nextSetReady = true;
//                if (!switched)
//                {
//                    V2.addAll(generatedSet);
//                }
//
//                return true;
//            }
//
//            return false;
//        }
//
//        return false;
//    }

//    @Override
//    public boolean hasNext()
//    {
//        if (nextSetReady)
//        {
//            return true;
//        }
//        else
//        {
//            if (step == BEGINNING)
//            {
//                return runFullEnumeration();
//            }
//            else if (step == ITERATING_NODES)
//            {
//                while(nodesIterator.hasNext() && !finishCondition())
//                {
//                    T node =nodesIterator.next();
//                    if (handleIterationNodePhase(node))
//                    {
//                        return true;
//                    }
//                }
//
//                while(setsNotExtended.isEmpty() && graph.hasNextNode() && !finishCondition())
//                {
//                    currentNode = graph.nextNode();
//                    V.add(currentNode);
//                    setsIterator = P.iterator();
//
//                    while(setsIterator.hasNext() && !finishCondition())
//                    {
//                        Set<T> s= setsIterator.next();
//
//                        if(handleIterationSetPhase(s))
//                        {
//                            return true;
//                        }
//                    }
//
//                }
//                return  runFullEnumeration();
//            }
//            else if( step == ITERATING_RESULTS)
//            {
//                while(setsIterator.hasNext() && !finishCondition())
//                {
//                    Set<T> s= setsIterator.next();
//
//                    if(handleIterationSetPhase(s))
//                    {
//                        return true;
//                    }
//                }
//
//
//                while (setsNotExtended.isEmpty() && graph.hasNextNode() && !finishCondition())
//                {
//                    currentNode = graph.nextNode();
//                    if (!switched)
//                    {
//                        if (V2.contains(currentNode))
//                        {
//                            boolean hasNext = graph.hasNextNode();
//                            while(V2.contains(currentNode) && hasNext && !finishCondition())
//                            {
//                                currentNode = graph.nextNode();
//                                hasNext = graph.hasNextNode();
//                            }
//                            if (finishCondition())
//                            {
//                                return false;
//                            }
//
//
//                            else if (!hasNext)
//                            {
//                                graph = new FakeSuccinctGraphRepresentation<>(graph, V2);
//                                switched = true;
//                                V2 = new HashSet<>();
//                            }
//                        }
//
//                    }
//
//                    V.add(currentNode);
//                    setsIterator = P.iterator();
//
//                    while(setsIterator.hasNext() && !finishCondition())
//                    {
//                        Set<T> s= setsIterator.next();
//
//                        if(handleIterationSetPhase(s))
//                        {
//                            return true;
//                        }
//                    }
//
//                }
//                return runFullEnumeration();
//            }
//        }
//        return false;
//    }
//
//
//    protected boolean runFullEnumeration()
//    {
//        while(!Q.isEmpty() && !finishCondition())
//        {
//
//            getNextSetToExtend();
//
//            nodesIterator = V.iterator();
//
//            while (nodesIterator.hasNext() && !finishCondition())
//            {
//                T node = nodesIterator.next();
//                if (handleIterationNodePhase(node))
//                {
//                    return true;
//                }
//            }
//
//
//        }
//
//        while(setsNotExtended.isEmpty() && graph.hasNextNode() && !finishCondition())
//        {
//            currentNode = graph.nextNode();
//            if (!switched)
//            {
//                if (V2.contains(currentNode))
//                {
//                    boolean hasNext = graph.hasNextNode();
//                    while(V2.contains(currentNode) && hasNext && !finishCondition())
//                    {
//                        currentNode = graph.nextNode();
//                        hasNext = graph.hasNextNode();
//                    }
//                    if (finishCondition())
//                    {
//                        return false;
//                    }
//
//
//                    else if (!hasNext)
//                    {
//                        graph = new FakeSuccinctGraphRepresentation<>(graph, V2);
//                        switched = true;
//                        V2 = new HashSet<>();
//
//                    }
//                }
//
//            }
//
//            V.add(currentNode);
//            setsIterator = P.iterator();
//            while(setsIterator.hasNext() && !finishCondition())
//            {
//                Set<T> s = setsIterator.next();
////                Set<T> generatedSet = extendSetInDirectionOfNode(s, currentNode);
//                if (extendSetInDirectionOfNode(s, currentNode))
//                {
//                    step = ITERATING_RESULTS;
//
//                    return true;
//                }
//            }
//        }
//
//        return false;
//    }

}
