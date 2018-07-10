package tdk_enum.graph.data_structures.weighted_queue.parallel;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class BlockingConcurrentQueueSet<T> extends ConcurrentQueueSet<T> {




    int threads = Runtime.getRuntime().availableProcessors();


    Set<Long> waiting = ConcurrentHashMap.newKeySet();
    boolean finished = false;

    public void setThreadsNumber(int threads)
    {
        this.threads = threads;
    }

    @Override
    public boolean isEmpty() {

        while (queueSet.isEmpty())
        {
            if (finished)
            {
                return true;
            }
            if (waiting.size() == threads -1)
            {
                finished = true;
                return true;
            }
            waiting.add(Thread.currentThread().getId());
            try {
                waiting.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        return false;

    }


}
