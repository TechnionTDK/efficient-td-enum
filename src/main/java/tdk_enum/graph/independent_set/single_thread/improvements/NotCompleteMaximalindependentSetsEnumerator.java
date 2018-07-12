package tdk_enum.graph.independent_set.single_thread.improvements;

import tdk_enum.graph.graphs.IGraph;
import tdk_enum.graph.graphs.succinct_graphs.ISuccinctGraphRepresentation;
import tdk_enum.graph.separators.IMinimalSeparatorsEnumerator;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import static tdk_enum.enumerators.AlgorithmStep.BEGINNING;
import static tdk_enum.enumerators.AlgorithmStep.ITERATING_NODES;
import static tdk_enum.enumerators.AlgorithmStep.ITERATING_RESULTS;

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
    protected boolean newSetFound(final Set<T> generatedSet)
    {


        if (!P.contains(generatedSet))
        {
            if(setsNotExtended.add(generatedSet))
            {
                Q.setWeight(generatedSet, scorer.scoreIndependentSet(generatedSet));

                nextIndependentSet = generatedSet;
                nextSetReady = true;
                if (!switched)
                {
                    V2.addAll(generatedSet);
                }

                return true;
            }

            return false;
        }

        return false;
    }

    @Override
    public boolean hasNext()
    {
        if (nextSetReady)
        {
            return true;
        }
        else
        {
            if (step == BEGINNING)
            {
                return runFullEnumeration();
            }
            else if (step == ITERATING_NODES)
            {
                while(nodesIterator.hasNext() && !timeLimitReached())
                {
                    T node =nodesIterator.next();
                    if (handleIterationNodePhase(node))
                    {
                        return true;
                    }
                }

                while(setsNotExtended.isEmpty() && graph.hasNextNode() && !timeLimitReached())
                {
                    currentNode = graph.nextNode();
                    V.add(currentNode);
                    setsIterator = P.iterator();

                    while(setsIterator.hasNext() && !timeLimitReached())
                    {
                        Set<T> s= setsIterator.next();

                        if(handleIterationSetPhase(s))
                        {
                            return true;
                        }
                    }

                }
                return  runFullEnumeration();
            }
            else if( step == ITERATING_RESULTS)
            {
                while(setsIterator.hasNext() && !timeLimitReached())
                {
                    Set<T> s= setsIterator.next();

                    if(handleIterationSetPhase(s))
                    {
                        return true;
                    }
                }


                while (setsNotExtended.isEmpty() && graph.hasNextNode() && !timeLimitReached())
                {
                    currentNode = graph.nextNode();
                    if (!switched)
                    {
                        if (V2.contains(currentNode))
                        {
                            boolean hasNext = graph.hasNextNode();
                            while(V2.contains(currentNode) && hasNext && !timeLimitReached())
                            {
                                currentNode = graph.nextNode();
                                hasNext = graph.hasNextNode();
                            }
                            if (timeLimitReached())
                            {
                                return false;
                            }


                            else if (!hasNext)
                            {
                                graph = new FakeSuccinctGraphRepresentation<>(graph, V2);
                                switched = true;
                                V2 = new HashSet<>();
                            }
                        }

                    }

                    V.add(currentNode);
                    setsIterator = P.iterator();

                    while(setsIterator.hasNext() && !timeLimitReached())
                    {
                        Set<T> s= setsIterator.next();

                        if(handleIterationSetPhase(s))
                        {
                            return true;
                        }
                    }

                }
                return runFullEnumeration();
            }
        }
        return false;
    }


    protected boolean runFullEnumeration()
    {
        while(!Q.isEmpty() && !timeLimitReached())
        {

            getNextSetToExtend();

            nodesIterator = V.iterator();

            while (nodesIterator.hasNext() && !timeLimitReached())
            {
                T node = nodesIterator.next();
                if (handleIterationNodePhase(node))
                {
                    return true;
                }
            }


        }

        while(setsNotExtended.isEmpty() && graph.hasNextNode() && !timeLimitReached())
        {
            currentNode = graph.nextNode();
            if (!switched)
            {
                if (V2.contains(currentNode))
                {
                    boolean hasNext = graph.hasNextNode();
                    while(V2.contains(currentNode) && hasNext && !timeLimitReached())
                    {
                        currentNode = graph.nextNode();
                        hasNext = graph.hasNextNode();
                    }
                    if (timeLimitReached())
                    {
                        return false;
                    }


                    else if (!hasNext)
                    {
                        graph = new FakeSuccinctGraphRepresentation<>(graph, V2);
                        switched = true;
                        V2 = new HashSet<>();

                    }
                }

            }

            V.add(currentNode);
            setsIterator = P.iterator();
            while(setsIterator.hasNext() && !timeLimitReached())
            {
                Set<T> s = setsIterator.next();
//                Set<T> generatedSet = extendSetInDirectionOfNode(s, currentNode);
                if (extendSetInDirectionOfNode(s, currentNode))
                {
                    step = ITERATING_RESULTS;

                    return true;
                }
            }
        }

        return false;
    }

}
