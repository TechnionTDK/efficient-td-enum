package tdenum.graph.independent_set.parallel;

import tdenum.graph.independent_set.AbstractMaximalIndependentSetsEnumerator;

import java.util.Set;
import java.util.concurrent.Callable;


public class ParallelMaximalIndependentSetsEnumerator<T> extends AbstractMaximalIndependentSetsEnumerator<T> implements Callable<Void> {
    @Override
    public Void call() throws Exception {
        return null;
    }

    @Override
    protected boolean timeLimitReached() {
        return false;
    }



    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public Set<T> next() {
        return null;
    }

    @Override
    public void doFirstStep() {

    }


//    @Override
//    protected boolean timeLimitReached() {
//        return false;
//    }
//
//    enum STATUS
//    {
//        RUNNING, WAITING, FINISHED
//    }
//
//
//    Map<Long, STATUS> statusMap;
//
//    AtomicInteger atomiceGeneralStatus;
//
//    Set<Long> waitingSet;
//
//    long threadID;
//
//
//
//    STATUS status = RUNNING;
//
//
//
//    protected boolean newSetFound(final Set<T> generatedSet)
//    {
//        Set<T2> codedGeneratedSet = convertTtoT2(generatedSet);
//        if (!P.contains(codedGeneratedSet))
//        {
//            if(setsNotExtended.add(codedGeneratedSet))
//            {
//
//                if(!atomiceGeneralStatus.compareAndSet(0,0))
//                {
//                    synchronized (statusMap)
//                    {
//                        statusMap.notify();
//                    }
//                }
//
//                return true;
//            }
//
//        }
//        return false;
//    }
//
//    @Override
//    protected Set<T2> convertTtoT2(Set<T> input) {
//        return null;
//    }
//
//
//    void updateStatus(STATUS status)
//    {
//        this.status = status;
//        synchronized (statusMap)
//        {
//            statusMap.put(threadID, status);
//        }
//    }
//
//    void runFullEnumeration()
//    {
//
//
//        Set<T> j = Q.poll();
//        if(j != null)
//        {
//            P.add(j);
//
//            iterateNodes(j);
//        }
//        else
//        {
//            T v = graph.nextNode();
//            if (v != null)
//            {
//                V.add(v);
//                iterateSets(v);
//            }
//            else
//            {
//                checkForFinish();
//            }
//        }
//    }
//
//
//    void checkForFinish()
//    {
//        this.status = WAITING;
//        synchronized (statusMap)
//        {
//
//            for(Long otherThreadId : statusMap.keySet())
//            {
//
//                if (otherThreadId.equals(this.threadID))
//                {
//                    continue;
//                }
//                STATUS otherStatus = statusMap.get(otherThreadId);
//
//                if (otherStatus.equals(FINISHED))
//                {
//                    break;
//                }
//                if(otherStatus.equals(RUNNING))
//                {
//                    atomiceGeneralStatus.incrementAndGet();
//                    statusMap.put(threadID, WAITING);
//                    try {
//                        otherStatus.wait();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    //thread wakes up
//                    atomiceGeneralStatus.decrementAndGet();
//                    this.status = RUNNING;
//                    statusMap.put(threadID,status);
//                    break;
//                }
//            }
//        }
//    }
//
//    @Override
//    public Void call(){
//
//        this.threadID = Thread.currentThread().getId();
//        while(status.equals(RUNNING) && !Thread.currentThread().isInterrupted())
//        {
//            runFullEnumeration();
//        }
//        updateStatus(FINISHED);
//        statusMap.notifyAll();
//
//        System.out.println("Thread " + Thread.currentThread().getId() + " finished " + System.nanoTime());
//        return null;
//    }
//
//    void iterateSets(T v)
//    {
//        for(Set<T> j : P)
//        {
//            if (j.contains(v))
//            {
//                continue;
//            }
//            Set<T> generatedSet = extendSetInDirectionOfNode(j, v);
//            newSetFound(generatedSet);
//        }
//    }
//
//    void iterateNodes(Set<T> j)
//    {
//        for (T v : V)
//        {
//            if (j.contains(v))
//            {
//                continue;
//            }
//            Set<T> generatedSet = extendSetInDirectionOfNode(j, v);
//            newSetFound(generatedSet);
//        }
//    }
//
//
//
//    protected Set<T> extendSetInDirectionOfNode(final Set<T> s, final T node)
//    {
//        Set<T> baseNodes = new HashSet<>();
//        baseNodes.add(node);
//        for (T t : s)
//        {
//            if(!graph.hasEdge(node, t))
//            {
//                baseNodes.add(t);
//            }
//        }
//        return extender.extendToMaxIndependentSet(baseNodes);
//    }
//
//
//    @Override
//    public boolean hasNext() {
//        return status != FINISHED;
//    }
//
//    @Override
//    public Set next() {
//        return null;
//    }
//
//    @Override
//    public void doFirstStep() {
//
//    }
//



}
