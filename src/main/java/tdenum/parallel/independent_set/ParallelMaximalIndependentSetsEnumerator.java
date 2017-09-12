package tdenum.parallel.independent_set;

import tdenum.graph.graphs.interfaces.ISuccinctGraphRepresentation;
import tdenum.graph.independent_set.IMaximalIndependentSetsEnumerator;
import tdenum.graph.independent_set.interfaces.IIndependentSetExtender;
import tdenum.graph.independent_set.interfaces.IIndependentSetScorer;

import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

import static tdenum.parallel.independent_set.ParallelMaximalIndependentSetsEnumerator.STATUS.FINISHED;
import static tdenum.parallel.independent_set.ParallelMaximalIndependentSetsEnumerator.STATUS.RUNNING;
import static tdenum.parallel.independent_set.ParallelMaximalIndependentSetsEnumerator.STATUS.WAITING;

public class ParallelMaximalIndependentSetsEnumerator<T>  implements Callable<Void> {


    enum STATUS
    {
        RUNNING, WAITING, FINISHED
    }


    ISuccinctGraphRepresentation<T> graph;
    IIndependentSetExtender extender;
    IIndependentSetScorer scorer;

    Set<T> V;

    Set<Set<T>> P;
    Queue<Set<T>> Q;

    Map<Long, STATUS> statusMap;

    AtomicInteger atomiceGeneralStatus;

    Set<Long> waitingSet;

    long threadID;


    public ParallelMaximalIndependentSetsEnumerator(ISuccinctGraphRepresentation<T> graph,
                                                    IIndependentSetExtender extender,
                                                    IIndependentSetScorer scorer,
                                                    Set<T> v,
                                                    Set<Set<T>> p,

                                                    Queue<Set<T>> queue) {
        this.graph = graph;
        this.extender = extender;
        this.scorer = scorer;
        V = v;
        P = p;
        Q = queue;


    }

    STATUS status = RUNNING;



    boolean newSetFound(final Set<T> generatedSet)
    {
        if (!P.contains(generatedSet))
        {
            if(Q.add(generatedSet))
            {

                if(!atomiceGeneralStatus.compareAndSet(0,0))
                {
                    synchronized (statusMap)
                    {
                        statusMap.notify();
                    }
                }

                return true;
            }

        }
        return false;
    }



    void updateStatus(STATUS status)
    {
        this.status = status;
        synchronized (statusMap)
        {
            statusMap.put(threadID, status);
        }
    }

    void runFullEnumeration()
    {


        Set<T> j = Q.poll();
        if(j != null)
        {
            P.add(j);

            iterateNodes(j);
        }
        else
        {
            T v = graph.nextNode();
            if (v != null)
            {
                V.add(v);
                iterateSets(v);
            }
            else
            {
                checkForFinish();
            }
        }
    }


    void checkForFinish()
    {
        this.status = WAITING;
        synchronized (statusMap)
        {

            for(Long otherThreadId : statusMap.keySet())
            {

                if (otherThreadId.equals(this.threadID))
                {
                    continue;
                }
                STATUS otherStatus = statusMap.get(otherThreadId);

                if (otherStatus.equals(FINISHED))
                {
                    break;
                }
                if(otherStatus.equals(RUNNING))
                {
                    atomiceGeneralStatus.incrementAndGet();
                    statusMap.put(threadID, WAITING);
                    try {
                        otherStatus.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //thread wakes up
                    atomiceGeneralStatus.decrementAndGet();
                    this.status = RUNNING;
                    statusMap.put(threadID,status);
                    break;
                }
            }
        }
    }

    @Override
    public Void call(){

        this.threadID = Thread.currentThread().getId();
        while(status.equals(RUNNING) && !Thread.currentThread().isInterrupted())
        {
            runFullEnumeration();
        }
        updateStatus(FINISHED);
        statusMap.notifyAll();

        System.out.println("Thread " + Thread.currentThread().getId() + " finished " + System.nanoTime());
        return null;
    }

    void iterateSets(T v)
    {
        for(Set<T> j : P)
        {
            if (j.contains(v))
            {
                continue;
            }
            Set<T> generatedSet = extendSetInDirectionOfNode(j, v);
            newSetFound(generatedSet);
        }
    }

    void iterateNodes(Set<T> j)
    {
        for (T v : V)
        {
            if (j.contains(v))
            {
                continue;
            }
            Set<T> generatedSet = extendSetInDirectionOfNode(j, v);
            newSetFound(generatedSet);
        }
    }



    protected Set<T> extendSetInDirectionOfNode(final Set<T> s, final T node)
    {
        Set<T> baseNodes = new HashSet<>();
        baseNodes.add(node);
        for (T t : s)
        {
            if(!graph.hasEdge(node, t))
            {
                baseNodes.add(t);
            }
        }
        return extender.extendToMaxIndependentSet(baseNodes);
    }




}
