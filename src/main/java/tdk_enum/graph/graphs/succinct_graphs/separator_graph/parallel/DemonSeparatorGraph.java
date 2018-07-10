package tdk_enum.graph.graphs.succinct_graphs.separator_graph.parallel;

import tdk_enum.graph.data_structures.MinimalSeparator;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class DemonSeparatorGraph extends ConcurrentSeparatorsGraph implements Runnable{


    Object batchLock = new Object();
    Set<MinimalSeparator> batch = ConcurrentHashMap.newKeySet();
    boolean hasNext = true;
    boolean stop = false;
    Map<Long, Set<MinimalSeparator>> threadsBatch = new ConcurrentHashMap<>();

    @Override
    public  Set<MinimalSeparator> nextBatch()
    {
        long id = Thread.currentThread().getId();
        return  nextBatch(id);


    }

    public void addId(long id)
    {
        if (!threadsBatch.containsKey(id))
        {
            threadsBatch.put(id,new HashSet<>());
        }
    }

    @Override
    public Set<MinimalSeparator> nextBatch(long id) {

        Set<MinimalSeparator> mySet =  threadsBatch.get(id);
        while(nodesGenerated.intValue() == mySet.size() && hasNext)
        {
            synchronized (batchLock)
            {
                try {
                    if(hasNext)
                    {
                        batchLock.wait();
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        Set<MinimalSeparator> result = new HashSet<>(batch);
        result.removeAll(mySet);
        mySet.addAll(result);
        return result;
    }

    @Override
    public boolean hasNextNode()
    {
        return hasNextNode(Thread.currentThread().getId());
    }

    @Override
    public boolean hasNextNode(long id) {
        //return hasNext || threadsBatch.get(id).size() < nodesGenerated.intValue();
        return hasNext || threadsBatch.get(id).size() < nodesGenerated.intValue();
    }

    @Override
    public void run() {
        while (nodesEnumerator.hasNext() && !timeLimitReached())
        {
           batch.add(nodesEnumerator.next());
           nodesGenerated.incrementAndGet();
           synchronized (batchLock)
           {
               batchLock.notifyAll();
           }
        }

        synchronized (batchLock)
        {
            hasNext = false;
            batchLock.notifyAll();
        }

    }

    boolean timeLimitReached()
    {
        return Thread.currentThread().isInterrupted();
    }

    public void stop()
    {
        stop = true;
    }

    synchronized void pushNext(MinimalSeparator separator)
    {
        batch.add(separator);

    }
}
